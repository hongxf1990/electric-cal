package com.zer.electric.entity;

import java.util.Date;

/**
 * 每个人付的钱
 * @author zer
 * @create 2016-11-28 15:19
 */
public class Money {
    private Long id;
    private Double hxf;
    private Double jx;
    private Double wc;
    private Double totalOfBill;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHxf() {
        return hxf;
    }

    public void setHxf(Double hxf) {
        this.hxf = hxf;
    }

    public Double getJx() {
        return jx;
    }

    public void setJx(Double jx) {
        this.jx = jx;
    }

    public Double getWc() {
        return wc;
    }

    public void setWc(Double wc) {
        this.wc = wc;
    }

    public Double getTotalOfBill() {
        return totalOfBill;
    }

    public void setTotalOfBill(Double totalOfBill) {
        this.totalOfBill = totalOfBill;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
