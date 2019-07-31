package com.fei.dao.impl;


import com.fei.dao.IOrderDao;
import com.fei.domain.OrderItems;
import com.fei.domain.Orders;
import com.fei.domain.PageBean;
import com.fei.utils.DaoUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements IOrderDao {

    QueryRunner qr = new QueryRunner(DaoUtil.getDatasource());

    @Override
    public void insertItems(OrderItems items) throws Exception {
        String sql = "insert into orderitem values (?, ?, ?, ?, ?)";
        qr.update(sql, items.getItemid(), items.getCount(), items.getSubtotal(), items.getProduct
                ().getPid(), items.getOrders().getOid());
    }

    @Override
    public void insertOrders(Orders orders) throws Exception {
        String sql = "insert into orders(oid) values(?)";
        qr.update(sql, orders.getOid());
    }

    @Override
    public void updateOrderByOid(String oid, Orders orders) throws Exception {

        String sql = "update orders set ordertime = ? , total = ?, address = ?, name = ?, " +
                "telephone = ? , state = ? ";
        if (orders.getUser() != null){
            sql = sql + ", uid = '" + orders.getUser().getUid()+"'";
        }
        sql = sql + "where oid = ?";

        qr.update(sql, orders.getOrdertime(), orders.getTotal(), orders.getAddress(), orders
                .getName(), orders.getTelephone(), 0, oid);
    }

    @Override
    public List<Orders> selectAllOrders(PageBean pageBean) throws Exception {
        String sql = "select * from orders order by ordertime asc limit ?, ?";
        return qr.query(sql, new BeanListHandler<Orders>(Orders.class), (pageBean.getCurPage()-1)
                *pageBean.getCurPageNum(), pageBean.getCurPageNum());
    }

    @Override
    public List<OrderItems> findOrderItemByOid(String oid) throws Exception {
        String sql = "select * from orderitem where oid = ?" ;
        return qr.query(sql, new BeanListHandler<OrderItems>(OrderItems.class), oid);
    }

    @Override
    public int totalOrder() throws Exception{
        String sql = "select count(*) from orders ";
        return ((Long)(qr.query(sql, new ScalarHandler<>()))).intValue();
    }
}
