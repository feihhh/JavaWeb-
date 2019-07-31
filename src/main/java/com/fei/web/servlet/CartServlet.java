package com.fei.web.servlet;

import com.fei.domain.Cart;
import com.fei.domain.CartItems;
import com.fei.domain.Product;
import com.fei.service.IProductService;
import com.fei.service.impl.ProductServiceImpl;
import com.fei.utils.CheckLoginUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "cartServlet", urlPatterns = "/cart")
public class CartServlet extends BaseServlet {

    IProductService pService = new ProductServiceImpl();

    /**
     * 跳转到购物车页面
     * @param request
     * @param response
     * @return
     */
    public String cartUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CheckLoginUtil.checkLogin(request, response);
        return "/jsp/cart.jsp" ;
    }


    // 添加商品到购物车
    public String add2Cart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取pid
        String pid = request.getParameter("pid");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // 通过pid查询对应的商品
        Product product = pService.findProByPid(pid);
        CartItems items = new CartItems();
        // 把对应的信息封装到CartItems对象里面
        items.setProduct(product);
        items.setCount(quantity);
        items.setSubtotal(items.getCount()*(items.getProduct().getShop_price()));

        Cart c = (Cart) request.getSession(false).getAttribute("cart");
        Map<String, CartItems> map = null;
        int total = 0;
        if (c == null) {
            // 购物车中还没有存放商品信息，新建一个购物车的map，把当前商品放入购物车中
            c = new Cart();
            map = new TreeMap<>();
            map.put(pid, items);
        }
        else {
            // 已经有购物车了
            map = c.getMap();
            total = c.getTotalCount();
            // 如果购物车中有当前商品，只需要改变当前商品的数量，如果没有，再把当前商品放进去，总数增加
            if (map.keySet().contains(pid)) {
                // 原来购物车中这个商品的数量再 原来的基础上再增加新添加的数量
                CartItems tmpItem = map.get(pid);
                tmpItem.setCount(tmpItem.getCount()+quantity);
                tmpItem.setSubtotal(tmpItem.getSubtotal()+product.getShop_price()*quantity);
            }else {
                // 如果没有
                map.put(pid, items);
            }
        }
        total += quantity;
        c.setTotalCount(total);
        c.setMap(map);
        c.setTotalMoney( );
        // 积分为总价钱向上取整/100
        c.setTotalCredits((((Double)(Math.ceil(c.getTotalMoney()))).intValue())/100);
        c.addCart(c, request, response);
        return "/jsp/cart.jsp";
    }

    /**
     * 删除购物车中的某一个商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String deleteItems(HttpServletRequest request, HttpServletResponse response) throws
            Exception{
        String itemPid = request.getParameter("itemPid");
        return deleteItems(request, response, itemPid);
    }

    public String addOneItem(HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        // 获取前台参数pid
        String pid = request.getParameter("pid");
        // 获取购物车信息
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 获取购物车里面的map集合
        Map<String, CartItems> map = cart.getMap();
        // 通过pid获取对应的商品
        CartItems cartItems = map.get(pid);
        // 数量加一
        cartItems.setCount(cartItems.getCount()+1);
        cartItems.setSubtotal(cartItems.getSubtotal()+cartItems.getProduct().getShop_price());
        map.put(pid, cartItems);
        cart.setMap(map);
        cart.setTotalMoney();
        cart.setTotalCredits((((Double)(Math.ceil(cart.getTotalMoney()))).intValue())/100);
        return "/jsp/cart.jsp";
    }

        public String deleteOneItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取前台参数pid
        String pid = request.getParameter("pid");
        // 获取购物车信息
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 获取购物车里面的map集合
        Map<String, CartItems> map = cart.getMap();
        // 根据pid，获取对应的CartItem
        CartItems cartItems = map.get(pid);
        // 如果只剩一件当前商品， 再减一就没了，直接删除当前商品
        if(cartItems.getCount() == 1){
            return deleteItems(request, response, pid);
        }
        // 如果数量大于1 当前商品数量减一 购物车商品总数减一
        cartItems.setCount(cartItems.getCount()-1);
        cartItems.setSubtotal(cartItems.getSubtotal()-cartItems.getProduct().getShop_price());
        cart.setTotalCount(cart.getTotalCount()-1);
        // 总价钱减去当前商品价格
        cart.setTotalMoney();
        // 积分为总价钱向上取整/100
        cart.setTotalCredits((((Double)(Math.ceil(cart.getTotalMoney()))).intValue())/100);
        map.put(pid, cartItems);
        cart.setMap(map);
        return "/jsp/cart.jsp";
    }

    // 删除所有指定pid的商品
    public String deleteItems(HttpServletRequest request, HttpServletResponse response, String pid)
            throws Exception{
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 能点删除说明cart一定不为空
        Map<String, CartItems> map = cart.getMap();
        // 获取要删除的商品个数
        Integer count = map.get(pid).getCount();
        map.remove(pid);
        cart.setTotalCount(cart.getTotalCount()-count);
        cart.setTotalMoney();
        cart.setMap(map);
        cart.addCart(cart, request, response);
        return "/jsp/cart.jsp";
    }


    public String cleraCart(HttpServletRequest request, HttpServletResponse response) throws
            Exception{
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        return "jsp/cart.jsp";
    }
}
