package com.fei.web.servlet;

import com.fei.domain.PageBean;
import com.fei.domain.Product;
import com.fei.service.IProductService;
import com.fei.service.impl.ProDuctServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "productServlet", urlPatterns = "/product")
public class ProductServlet extends BaseServlet {

    IProductService pService = new ProDuctServiceImpl();

    /**
     * 通过类型查询商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findProductByCid(HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        // 获取当前商品分类id
        String cid = request.getParameter("cid");
        // 获取当前页面信息（是第几页）
        int pageNum = Integer.parseInt(request.getParameter("thisPage"));
        PageBean<Product> pageBean = pService.findByPageAndCid(cid, pageNum);
        request.setAttribute("bean", pageBean);
        request.setAttribute("cid", cid);
        return "/jsp/product_list.jsp";
    }

    /**
     * 通过pid查询商品
     */
    public String findProByPid(HttpServletRequest request, HttpServletResponse response) throws
            Exception{
        // 获取前台传过来的商品pid
        String pid = request.getParameter("pid");
        // 调用service层查询对应商品
        Product product = pService.findProByPid(pid);
        System.out.println(product);
        // 将商品信息放在request域中
        request.setAttribute("product", product);
        // 返回到单个商品的页面
        return  "/jsp/product_info.jsp";

    }
}
