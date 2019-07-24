package com.fei.dao;

import com.fei.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDao {
    // 查询所有分类
    List<Category> findAll() throws Exception;
    // 通过cid查询分类
    Category findCateByCid(String cid) throws Exception;
}
