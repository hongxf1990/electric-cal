package com.zer.electric.controller;

import com.zer.electric.bean.ElectricNumBean;
import com.zer.electric.bean.Json;
import com.zer.electric.utils.Pool;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author hongxf
 * @create 2016-11-28 14:39
 */
@RestController
public class ElectricController {

    @ApiOperation(value = "计算最新消费电量并保存", httpMethod = "POST", response = Json.class, notes = "返回计算成功与否")
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public Json calculate(
            @RequestParam(value = "totalOfPay") Double totalOfPay, //当月账单总共金额
            @RequestParam(value = "totalOfElectric") Double totalOfElectric, //当月账单总共电度数
            @RequestParam(value = "hxf") Double hxfCurrent, //当月洪电表度数
            @RequestParam(value = "jx") Double jxCurrent, //当月金潇电表度数
            @RequestParam(value = "wc") Double wcCurrent //当月魏超电表度数
    ) {
        Json json = new Json();
        //获取当前月份，取得前一个月份
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int beforeMonth = currentMonth - 1;
        int beforeYear = currentYear;
        if (beforeMonth == 0) {
            beforeMonth = 12;
            beforeYear -= 1;
        }
        try(Jedis conn = Pool.getPool().getResource()) {
            //获取上一个月的各自电表度数
//            Pipeline pipe = conn.pipelined();
            //key：money:1/2016
            Map<String, String> beforeMonthElec = conn.hgetAll("electric:" + beforeMonth + "/" + beforeYear);
            if (!beforeMonthElec.isEmpty()) { //如果不是第一次
                //上个月的度数
                Double hxfBefore = Double.parseDouble(beforeMonthElec.get("hxf"));
                Double jxBefore = Double.parseDouble(beforeMonthElec.get("jx"));
                Double wcBefore = Double.parseDouble(beforeMonthElec.get("wc"));
                //这个月的每个人的花费
                Double hxfElec = hxfCurrent - hxfBefore;
                Double jxElec = jxCurrent - jxBefore;
                Double wcElec = wcCurrent - wcBefore;
                Double pubElec = totalOfElectric - hxfElec - jxElec - wcElec;
                //存储当月电度数
                Map<String, String> eleMap = new HashMap<>();
                eleMap.put("hxf", String.valueOf(hxfElec));
                eleMap.put("jx", String.valueOf(jxElec));
                eleMap.put("wc", String.valueOf(wcElec));
                eleMap.put("pub", String.valueOf(pubElec));
                eleMap.put("totalOfElectric", String.valueOf(totalOfElectric));
                conn.hmset("electric:" + currentMonth + "/" + currentYear, eleMap);
                //存储有哪些electric键
                conn.zadd("electric:", calendar.getTimeInMillis(), "electric:" + currentMonth + "/" + currentYear);
                //存储费用
                Map<String, String> payMap = calPay(hxfElec, jxElec, wcElec, pubElec, totalOfElectric, totalOfPay);
                conn.hmset("money:" + currentMonth + "/" + currentYear, payMap);
                conn.zadd("money:", calendar.getTimeInMillis(), "money:" + currentMonth + "/" + currentYear);
            } else {
                //存储第一个月的电度数，假设电表为0
                Double pubElec = totalOfElectric - hxfCurrent - jxCurrent - wcCurrent;
                Map<String, String> eleMap = new HashMap<>();
                eleMap.put("hxf", String.valueOf(hxfCurrent));
                eleMap.put("jx", String.valueOf(jxCurrent));
                eleMap.put("wc", String.valueOf(wcCurrent));
                eleMap.put("pub", String.valueOf(pubElec));
                eleMap.put("totalOfElectric", String.valueOf(totalOfElectric));
                conn.hmset("electric:" + currentMonth + "/" + currentYear, eleMap);
                //存储有哪些money键
                conn.zadd("electric:", calendar.getTimeInMillis(), "electric:" + currentMonth + "/" + currentYear);
                //存储费用
                Map<String, String> payMap = calPay(hxfCurrent, jxCurrent, wcCurrent, pubElec, totalOfElectric, totalOfPay);
                conn.hmset("money:" + currentMonth + "/" + currentYear, payMap);
                conn.zadd("money:", calendar.getTimeInMillis(), "money:" + currentMonth + "/" + currentYear);
            }

            json.setSuccess(true);
            json.setMsg("计算费用成功");

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("计算费用失败");
        }
        return json;
    }

    private Map<String, String> calPay(Double hxf, Double jx, Double wc,
                                       Double pub, Double totalOfElec, Double totalOfPay) {
        Map<String, String> map = new HashMap<>();
        double hxfPay = (hxf / totalOfElec + pub / totalOfElec / 3) * totalOfPay;
        double jxPay = (jx / totalOfElec + pub / totalOfElec / 3) * totalOfPay;
        double wcPay = (wc / totalOfElec + pub / totalOfElec / 3) * totalOfPay;
        map.put("hxf", String.valueOf(hxfPay));
        map.put("jx", String.valueOf(jxPay));
        map.put("wc", String.valueOf(wcPay));
        map.put("totalOfPay", String.valueOf(totalOfPay));
        return map;
    }

    @ApiOperation(value = "获取所有电度数使用记录", httpMethod = "GET", response = Json.class, notes = "返回各自电度数")
    @RequestMapping(value = "/recent-electrics", method = RequestMethod.GET)
    public List<ElectricNumBean> getRecentElec() {
        List<ElectricNumBean> list = new ArrayList<>();
        try(Jedis conn = Pool.getPool().getResource()) {
//            Pipeline pipe = conn.pipelined();
            DecimalFormat df = new DecimalFormat(".0");
            Set<String> elecSet = conn.zrevrange("electric:", 0, -1);
            for (String elecId : elecSet) {
                ElectricNumBean electricNumBean = new ElectricNumBean();
                String monthAndYear = elecId.substring(elecId.indexOf(":") + 1);
                electricNumBean.setTime(monthAndYear);
                electricNumBean.setHxf(df.format(Double.valueOf(conn.hget(elecId, "hxf"))));
                electricNumBean.setJx(df.format(Double.valueOf(conn.hget(elecId, "jx"))));
                electricNumBean.setWc(df.format(Double.valueOf(conn.hget(elecId, "wc"))));
                electricNumBean.setPub(df.format(Double.valueOf(conn.hget(elecId, "pub"))));
                electricNumBean.setTotalOfElec(df.format(Double.valueOf(conn.hget(elecId, "totalOfElectric"))));
                list.add(electricNumBean);
            }
        }
        return list;
    }
}
