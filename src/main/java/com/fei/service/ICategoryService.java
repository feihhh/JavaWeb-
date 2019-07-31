package com.fei.service;

import com.fei.domain.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAll() throws Exception;

    Category findCategoryByCid(String cid) throws Exception;
}
