package com.fei.domain;

import java.util.List;

/**
 * 订单的扩展类
 */
public class OrderExten {

    //一个订单中的所有商品的集合
    private List<OrderItems> list;

    // 一个订单的信息
    private Orders orders;

    public List<OrderItems> getList() {
        return list;
    }

    public void setList(List<OrderItems> list) {
        this.list = list;
    }

    public OrderExten() {
    }

    public OrderExten(List<OrderItems> list, Orders orders) {
        this.list = list;
        this.orders = orders;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
