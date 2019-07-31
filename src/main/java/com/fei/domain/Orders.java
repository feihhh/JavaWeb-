package com.fei.domain;

import org.apache.commons.dbutils.ResultSetHandler;

import java.util.Date;

public class Orders {

    // 订单编号
    private String oid ;
    // 生成订单时间
    private String ordertime;
    // 总钱数
    private double total;
    // 订单状态
    private int state ;
    // 地址
    private String address ;
    // 收件人名字
    private String name;
    // 收件人电话
    private String telephone ;
    // 订单属于哪一个用户
    private User user;

    public Orders() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", state=" + state +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", user=" + user +
                '}';
    }
}
