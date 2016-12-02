package com.zer.electric.controller;

import com.zer.electric.bean.MoneyBean;
import com.zer.electric.utils.Pool;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zer
 * @create 2016-11-28 14:38
 */
@RestController
public class MoneyController {

    @ApiOperation(value = "获取最近支付记录", httpMethod = "GET", response = List.class, notes = "返回最近支付的记录")
    @RequestMapping(value = "/recent-pays", method = RequestMethod.GET)
    public List<MoneyBean> findRecentPay() {
        List<MoneyBean> list = new ArrayList<>();
        try(Jedis conn = Pool.getPool().getResource()) {
//            Pipeline pipe = conn.pipelined();
            DecimalFormat df = new DecimalFormat(".00");
            Set<String> moneySet = conn.zrevrange("money:", 0, -1);
            for (String moneyId : moneySet) {
                MoneyBean moneyBean = new MoneyBean();
                moneyBean.setTime(moneyId.substring(moneyId.indexOf(":") + 1));
                moneyBean.setHxf(df.format(Double.valueOf(conn.hget(moneyId, "hxf"))));
                moneyBean.setJx(df.format(Double.valueOf(conn.hget(moneyId, "jx"))));
                moneyBean.setWc(df.format(Double.valueOf(conn.hget(moneyId, "wc"))));
                moneyBean.setTotalOfPay(df.format(Double.valueOf(conn.hget(moneyId, "totalOfPay"))));
                list.add(moneyBean);
            }
        }
        return list;
    }
}
