package com.fei.dao.impl;

import com.fei.dao.ICategoryDao;
import com.fei.domain.Category;
import com.fei.service.impl.CategoryServiceImpl;
import com.fei.utils.DaoUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {

    private QueryRunner qr = new QueryRunner(DaoUtil.getDatasource()) ;

    @Override
    public List<Category> findAll() throws Exception {

        String sql = "select * from category" ;

        return qr.query(sql, new BeanListHandler<Category>(Category.class));
    }

    @Override
    public Category findCateByCid(String cid) throws Exception {
        String sql = "select * from category where cid = ?" ;
        return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
    }
}
