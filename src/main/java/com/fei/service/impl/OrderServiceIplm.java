package com.fei.service.impl;

import com.fei.dao.IOrderDao;
import com.fei.dao.IProductDao;
import com.fei.dao.impl.OrderDaoImpl;
import com.fei.dao.impl.ProductDaoImpl;
import com.fei.domain.OrderItems;
import com.fei.domain.Orders;
import com.fei.domain.PageBean;
import com.fei.domain.Product;
import com.fei.service.IOrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceIplm implements IOrderService {

    private IOrderDao oDao = new OrderDaoImpl();

    private IProductDao pDao = new ProductDaoImpl();

    @Override
    public void insertOrderItem(OrderItems items) throws Exception{
        oDao.insertItems(items);
    }

    @Override
    public void insertOrders(Orders orders) throws Exception {
        oDao.insertOrders(orders) ;
    }

    @Override
    public void updateOrderByOid(String oid, Orders orders) throws Exception {
        oDao.updateOrderByOid(oid, orders);
    }

    @Override
    public List<Orders> selectAllOrders(PageBean pageBean) throws Exception {
        return oDao.selectAllOrders(pageBean);
    }

    @Override
    public List<OrderItems> findProAndItemByOid(String oid) throws Exception {

        List<OrderItems> oiList = new ArrayList<>();
        // 通过oid查询所有的OrderItem 获取orderitem里面的pid
        oiList = oDao.findOrderItemByOid(oid);
        // 变量oiList通过每一个orderitem的pid 查询对应的 查询Product
        // 将product封装到OrderItem对象中
        for(OrderItems oi : oiList)
        {
            String pid = oi.getPid();
            oi.setProduct(pDao.findProByPid(pid));
        }
        return oiList;
    }

    @Override
    public int totalOrder() throws Exception {
        return oDao.totalOrder();
    }
}
