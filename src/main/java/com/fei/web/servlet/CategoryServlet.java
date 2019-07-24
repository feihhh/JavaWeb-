package com.fei.web.servlet;

import com.fei.domain.Category;
import com.fei.service.ICategoryService;
import com.fei.service.impl.CategoryServiceImpl;
import com.fei.utils.JsonUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "categoryServlet" , urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {

    ICategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /**
         * 加载商品的分类信息
         */
        List<Category> clist = categoryService.findAll();

        // 把list集合转为json格式的字符串,因为ajax的需要json格式的数据
        String json = JsonUtil.list2json(clist);
        // 防止响应数据乱码
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(json);
    }
}
