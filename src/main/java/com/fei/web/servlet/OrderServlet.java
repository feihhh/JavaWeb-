package com.fei.web.servlet;

import com.fei.domain.*;
import com.fei.service.IOrderService;
import com.fei.service.impl.OrderServiceIplm;
import com.fei.utils.RandomStrUtil;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "orderServlet", urlPatterns = "/order")
public class OrderServlet extends BaseServlet {

    private IOrderService service = new OrderServiceIplm();

    /**
     * 从购车车生成订单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String handleOrder(HttpServletRequest request, HttpServletResponse response)throws Exception{

        // 通过session获取购物车中的信息所有商品信息
        Map<String, CartItems> map  = ((Cart) request.getSession().getAttribute("cart")).getMap();
        List<OrderItems> list = new ArrayList<>();
        // 生成一个随机的id作为订单编号
        String oid = RandomStrUtil.randUid();
        Orders orders = new Orders();
        // 封装oid，将订单编号存入数据库
        orders.setOid(oid);
        // 封装完之后将orderitem添加到数据库
//         先将订单的id添加到orders表中
//         再将每一个items到orderitems表中，否则有主外键冲突
        service.insertOrders(orders);
        // 计算总钱数
        double totalMoney = 0;
        for (String pid : map.keySet())
        {
            // 封装OrderItem
            CartItems cartItems = map.get(pid);
            OrderItems items = new OrderItems();
            items.setCount(cartItems.getCount());
            items.setProduct(cartItems.getProduct());
            items.setSubtotal(cartItems.getSubtotal());
            items.setItemid(RandomStrUtil.randUid().substring(0, 15));
            items.setOrders(orders);
            service.insertOrderItem(items);
            totalMoney += items.getSubtotal();
            list.add(items);
        }
        orders.setTotal(totalMoney);
        OrderExten orderExten = new OrderExten(list, orders);
        request.setAttribute("order", orderExten);
        return "/jsp/order_info.jsp";
    }


    /**
     * 确认订单之后，请求到这里
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String makeOrder(HttpServletRequest request, HttpServletResponse response)throws
            Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Orders order = new Orders();
        //使用Apache提供的commons jar包中的方法 封装order对象
        BeanUtils.populate(order, parameterMap);
        // 封装订单时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 封装用户id
        User user = null;
        if ((user = (User) request.getSession().getAttribute("loginUser")) != null){
            order.setUser(user);
        }
        order.setOrdertime(simpleDateFormat.format(new Date()));
        System.out.println(order);
        // 将订单信息插入数据库 由于上面已经存了对应的oid在数据库中，所以现在只需要根据oid更新数据库中的内容
        service.updateOrderByOid(order.getOid(), order);
        request.setAttribute("msg", "订单生成成功，请"+"<a href = ''>前往付款</a>");
        return "/jsp/msg.jsp";
    }

    public String allOrders(HttpServletRequest request, HttpServletResponse response) throws
            Exception{

        // 将订单信息封装到一个PageBean对象中
        PageBean<OrderExten> pageBean = new PageBean<>();
        // 当前是第几页
        pageBean.setCurPage(Integer.parseInt(request.getParameter("pageNum")));
        // 一个页面存放多少个条目（订单）
        pageBean.setCurPageNum(2);
        // 获取一个页面所需的所有订单
            // 查询orders表里面的所有订单信息
            // 通过orders表里面查出来的oid，查询一个oid对应的所有orderitem
            // 通过orderitem里面查出来的pid，查询每一个orderitem对应的product，将product封装到orderitem中
        List<Orders> oList = service.selectAllOrders(pageBean);
        // System.out.println(oList);
        // 存放所有订单信息  OrderExten - order的扩展类
        List<OrderExten> oeList = new ArrayList<>();
        // 遍历所有订单信息，获取每个订单的oid，通过oid查询orderitem
        for(Orders order : oList){
            OrderExten oExten = new OrderExten();
            String oid = order.getOid();
            List<OrderItems> list = service.findProAndItemByOid(oid);
            // 封装一个order信息
            oExten.setOrders(order);
            oExten.setList(list);
            oeList.add(oExten);
        }
        // 一共有条目页（一共有多少订单）
        pageBean.setTotalNum(service.totalOrder());
        // 总页数
        pageBean.setTotalPage(pageBean.getTotalNum()/pageBean.getCurPageNum());
        // page存放的东西集合
        pageBean.setList(oeList);
        // 将所有的订单信息存放到requset域中
        request.setAttribute("orders", pageBean);
        return "/jsp/order_list.jsp";
    }
}
