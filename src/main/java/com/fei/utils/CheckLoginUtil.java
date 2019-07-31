package com.fei.utils;

import com.fei.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginUtil {
    /**
     * 有些操作需要用户登录之后才能去操作，比如查看购物车
     * @param request
     * @param response
     * @throws Exception
     */
    public static void checkLogin(HttpServletRequest request, HttpServletResponse response) throws
            Exception{
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null)
        {
            request.setAttribute("msg", "你还没有登录," +
                    "请先<a href='"+request.getContextPath()+"/user?method=loginUI'>登录</a>在继续操作...");
            request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
        }
    }

}
