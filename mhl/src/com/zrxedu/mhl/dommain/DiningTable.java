package com.zrxedu.mhl.dommain;

import java.time.LocalDateTime;

@SuppressWarnings({"all"})
public class DiningTable {
    private Integer id;
    private String state;
    private String orderName;
    private String orderTel;
    private LocalDateTime orderTime;

    public DiningTable(Integer id, String state, String orderName, String orderTel, LocalDateTime orderTime) {
        this.id = id;
        this.state = state;
        this.orderName = orderName;
        this.orderTel = orderTel;
        this.orderTime = orderTime;
    }

    public DiningTable() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "\t\t"+id + "\t\t" +state;
    }
}
