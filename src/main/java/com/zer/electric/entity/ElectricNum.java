package com.zer.electric.entity;

import java.util.Date;

/**
 * 每个月使用的电量
 * @author zer
 * @create 2016-11-28 15:22
 */
public class ElectricNum {
    private Long id;
    private Double hxf;
    private Double wc;
    private Double jx;
    private Double pub;
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
