package com.fei.service;

import com.fei.domain.OrderItems;
import com.fei.domain.Orders;
import com.fei.domain.PageBean;

import java.util.List;

public interface IOrderService {

    void insertOrderItem(OrderItems items) throws Exception;

    void insertOrders(Orders orders) throws Exception ;

    void updateOrderByOid(String oid, Orders orders) throws Exception;

    List<Orders> selectAllOrders(PageBean pageBean) throws Exception;

    List<OrderItems> findProAndItemByOid(String oid) throws Exception;

    int totalOrder() throws Exception;
}
