package com.fei.dao;

import com.fei.domain.PageBean;
import com.fei.domain.Product;

import java.util.List;

public interface IProductDao {

    /**
     * 通过类别id和页码查询的商品
     */
    List<Product> findByPageAndCid(PageBean<Product> bean, String cid) throws Exception;

    /**
     * 通过类型cid 统计一共有多少个商品
     * @param cid
     * @return
     */
    Integer computerCountByCid(String cid) throws Exception;

    Product findProByPid(String pid) throws Exception;

    List<Product> findHotPro() throws Exception;

    List<Product> findNewPro() throws Exception;
}
