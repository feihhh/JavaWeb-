package com.fei.dao;

import com.fei.domain.OrderItems;
import com.fei.domain.Orders;
import com.fei.domain.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface IOrderDao {
    // 插入一个订单项
    void insertItems(OrderItems items) throws Exception;

    // 插入一条订单信息
    void insertOrders(Orders orders) throws Exception ;

    // 通过oid更新订单其他信息
    void updateOrderByOid(String oid, Orders orders) throws Exception;

    // 查询当前页面所有的订单
    List<Orders> selectAllOrders(PageBean pageBean) throws Exception;

    // 通过oid查询orderitem
    List<OrderItems> findOrderItemByOid(String oid) throws Exception;

    // 订单总数
    int totalOrder() throws Exception;
}
