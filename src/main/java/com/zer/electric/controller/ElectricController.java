package com.zer.electric.controller;

import com.zer.electric.bean.Json;
import com.zer.electric.utils.Pool;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        int currentMonth = calendar.get(Calendar.MONTH);
        int beforeMonth = currentMonth - 1;
        try(Jedis conn = Pool.getPool().getResource()) {
            //获取上一个月的各自电表度数
            Pipeline pipe = conn.pipelined();
            Response<Map<String, String>> beforeMonthResponse = pipe.hgetAll("money:" + beforeMonth);
            Map<String, String> beforeMonthElec = beforeMonthResponse.get();
            if (!beforeMonthElec.isEmpty()) { //如果不是第一个月
                Double hxfBefore = Double.parseDouble(beforeMonthElec.get("hxf"));
                Double jxBefore = Double.parseDouble(beforeMonthElec.get("jx"));
                Double wcBefore = Double.parseDouble(beforeMonthElec.get("wc"));
                //这个月的每个人的花费
                Double hxfElec = hxfCurrent - hxfBefore;
                Double jxElec = jxCurrent - jxBefore;
                Double wcElec = wcCurrent - wcBefore;
                Double pubElec = totalOfElectric - hxfElec - jxElec - wcElec;
            } else {
                //存储第一个月的电度数，假设电表为0
                Double pubElec = totalOfElectric - hxfCurrent - jxCurrent - wcCurrent;
                Map<String, String> elcMap = new HashMap<>();
                elcMap.put("hxf", String.valueOf(hxfCurrent));
                elcMap.put("jx", String.valueOf(jxCurrent));
                elcMap.put("wc", String.valueOf(wcCurrent));
                elcMap.put("pub", String.valueOf(pubElec));
                elcMap.put("totalOfElectric", String.valueOf(totalOfElectric));
                pipe.hmset("money:" + currentMonth, elcMap);
                pipe.sadd("money:", "money:" + currentMonth);
                //存储费用
                Map<String, String> payMap = calPay(hxfCurrent, jxCurrent, wcCurrent, pubElec, totalOfElectric, totalOfPay);
                pipe.hmset("electric:" + currentMonth, payMap);
            }
            pipe.exec();
        }
        return json;
    }

    private Map<String, String> calPay(Double hxf, Double jx, Double wc,
                                       Double pub, Double totalOfElec, Double totalOfPay) {
        Map<String, String> map = new HashMap<>();
        double hxfPay = (hxf / totalOfElec + pub / totalOfElec / 4) * totalOfPay;
        double jxPay = (hxf / totalOfElec + pub / totalOfElec / 4) * totalOfPay;
        double wcPay = (hxf / totalOfElec + pub / totalOfElec / 4) * totalOfPay;
        map.put("hxf", String.valueOf(hxfPay));
        map.put("jx", String.valueOf(jxPay));
        map.put("wc", String.valueOf(wcPay));
        return map;
    }
}
