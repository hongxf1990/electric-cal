package com.zer.electric.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author zer
 * @create 2016-11-28 15:31
 */
public class ElectricNumBean {
    private Double hxf;
    private Double wc;
    private Double jx;
    private Double pub;
    private Double totalOfBill;

    @JsonFormat(pattern="MM/yyyy", timezone="Asia/Shanghai")
    private Date createTime;

    public Double getHxf() {
        return hxf;
    }

    public void setHxf(Double hxf) {
        this.hxf = hxf;
    }

    public Double getWc() {
        return wc;
    }

    public void setWc(Double wc) {
        this.wc = wc;
    }

    public Double getJx() {
        return jx;
    }

    public void setJx(Double jx) {
        this.jx = jx;
    }

    public Double getPub() {
        return pub;
    }

    public void setPub(Double pub) {
        this.pub = pub;
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
