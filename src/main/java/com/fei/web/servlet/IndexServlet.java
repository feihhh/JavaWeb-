package com.fei.web.servlet;

import com.fei.domain.Category;
import com.fei.domain.Product;
import com.fei.service.ICategoryService;
import com.fei.service.IProductService;
import com.fei.service.impl.CategoryServiceImpl;
import com.fei.service.impl.ProDuctServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "indexServlet", urlPatterns = "/index")
public class IndexServlet extends BaseServlet {

    IProductService pService = new ProDuctServiceImpl();

    public String index (HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 加载热门商品和最新商品
        List<Product> hotList = pService.findHotPro();
        List<Product> newList = pService.findNewPro();
        request.setAttribute("hotPros", hotList);
        request.setAttribute("newPros", newList);
        return "/jsp/index.jsp";
    }
}
