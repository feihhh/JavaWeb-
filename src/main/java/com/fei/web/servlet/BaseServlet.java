package com.fei.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "baseServlet" , urlPatterns = "/base")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 获取子类的class对象
            Class clazz = this.getClass();
            // 通过url获取要执行的方法名称
            String method = req.getParameter("method");

            // 为了保证代码的健壮性 如果地址栏中没有method，代码不会报错，而是访问主页
            if (method == null || method.equals(""))
            {
                method = "index";
            }

            // 通过反射获取对应的方法
            Method clazzMethod = clazz.getMethod(method, HttpServletRequest.class,
                    HttpServletResponse.class);
            // 执行方法
            String str = (String) clazzMethod.invoke(this, req, resp);
            // 请求转发
            if (str != null && !str.equals(""))
                req.getRequestDispatcher(str).forward(req, resp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
