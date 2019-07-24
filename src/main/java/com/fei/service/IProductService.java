package com.fei.service;

import com.fei.domain.PageBean;
import com.fei.domain.Product;

import java.util.List;

public interface IProductService {

    // 通过页数查询商品
     PageBean<Product> findByPageAndCid(String cid, int pageNum) throws Exception;

     // 通过pid查询商品信息
    Product findProByPid(String pid) throws  Exception;

    // 查询热门商品
    List<Product> findHotPro() throws Exception;

    //查询最新商品
    List<Product> findNewPro() throws Exception;
}
