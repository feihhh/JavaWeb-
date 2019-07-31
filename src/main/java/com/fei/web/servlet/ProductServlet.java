package com.fei.web.servlet;

import com.fei.domain.*;
import com.fei.service.ICategoryService;
import com.fei.service.IProductService;
import com.fei.service.impl.CategoryServiceImpl;
import com.fei.service.impl.ProductServiceImpl;
import com.fei.utils.CookieUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet(name = "productServlet", urlPatterns = "/product")
public class ProductServlet extends BaseServlet {

    IProductService pService = new ProductServiceImpl();

    ICategoryService cService = new CategoryServiceImpl();

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
        String cid = request.getParameter("cid");
        System.out.println("cid = "+cid);

        // 记录浏览记录
        browseReocrd(pid, request, response);

        // 调用service层查询对应商品
        Product product = pService.findProByPid(pid);
        Category category = cService.findCategoryByCid(cid);
        product.setCategory(category);
        System.out.println(product);
        // 将商品信息放在request域中
        request.setAttribute("product", product);
        // 返回到单个商品的页面
        return  "/jsp/product_info.jsp";
    }

    public String findProByName(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        String proName = request.getParameter("proName");
        PageBean<Product> bean = pService.findProByName(proName, curPage);
        request.setAttribute("bean", bean);
        return "/jsp/product_list.jsp";
    }


    public void browseReocrd(String pid, HttpServletRequest request, HttpServletResponse response){
        Cookie record = CookieUtil.getCookie("record", request.getCookies());
        if (pid.length() == 1){
            pid = "0" + pid;
        }
        pid = "/products/1/c_00" + pid + ".jpg";
        if (record == null){
            // 如果当前还没有浏览记录，创建cookie
            record = new Cookie("record", pid);
        }else {
            // 如果已经有了浏览记录，将其中的值取出，然后拼接新的记录，再把值返回去
            String[] reds = record.getValue().split("-");
            List<String> list1 = Arrays.asList(reds);
            LinkedList<String> list = new LinkedList<>(list1);
            // 如果浏览记录中包含当前商品，删除该商品，再头插当前商品
            if (list.contains(pid)){
                list.remove(pid);
                list.addFirst(pid);
            }
            // 如果浏览记录中不存在当前商品 判断当前浏览记录个数
            //    如果个数大于等于6个  删除最后一个  将当前商品头插
            //    如果个数小于6个  将当前商品头插
            else {
                if (list.size() >= 6){
                    list.removeLast();
                }
                list.addFirst(pid);
            }
            StringBuffer sb = new StringBuffer();
            for (String str : list){
                sb.append(str+"-");
            }
            sb.deleteCharAt(sb.length()-1);
            record.setValue(sb.toString());
        }
        // 最大生存时间为一个月
        record.setMaxAge(2592000);
        record.setPath(request.getContextPath());
        response.addCookie(record);
    }
}
