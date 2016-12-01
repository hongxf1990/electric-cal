package com.zer.electric.controller;

import com.zer.electric.bean.MoneyBean;
import com.zer.electric.utils.Mysql.JdbcHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        String sql = "select * from money order by createTime desc";

        try {
            List<Map<String, Object>> queryList = JdbcHelper.query(sql);
            for (Map<String, Object> map: queryList) {
                MoneyBean moneyBean = new MoneyBean();
                Double hxf = (Double) map.get("hxf");
                Double wc = (Double) map.get("wc");
                Double jx = (Double) map.get("jx");
                Double totalOfBill = (Double) map.get("totalOfBill");
                Date time = (Date) map.get("createTime");
                moneyBean.setHxf(hxf);
                moneyBean.setWc(wc);
                moneyBean.setJx(jx);
                moneyBean.setTotalOfBill(totalOfBill);
                moneyBean.setCreateTime(time);
                list.add(moneyBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
