<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/7/18
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@page pageEncoding="UTF-8" %>
<!--
描述：菜单栏
-->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="${pageContext.request.contextPath}/img/logo2.png" />
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png" />
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${empty sessionScope.loginUser}">
                <li><a href="${pageContext.request.contextPath}/user?method=loginUI">登录
                </a></li>
                <li><a href="${pageContext.request.contextPath}/user?method=registUI">注册</a></li>
                <li><a href="${pageContext.request.contextPath}/cart?method=cartUI">购物车</a></li>
            </c:if>
            <c:if test="${! empty sessionScope.loginUser}">
                <li><a href="${pageContext.request.contextPath}/user?method=userExit">退出
                </a></li>
                <li><a href="register.htm">我的订单</a></li>
                <li><a href="${pageContext.request.contextPath}/cart?method=cartUI">购物车</a></li>
            </c:if>
        </ol>
    </div>
</div>
<!--
描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand"
                   href="${pageContext.request.contextPath}/index">首页
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul id="category" class="nav navbar-nav">
                </ul>
                <form method="post"
                      action="${pageContext.request.contextPath}/product?method=findProByName&curPage=1"
                      class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" name="proName" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">search</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>

<script>
    <%--页面载入事件， 加载首页的时候，向后台发送异步请求，获取head.jsp中的分类信息--%>
    $(function () {
        $.get("${pageContext.request.contextPath}/category?method=findAll", {}, function (data) {
            // alert(data); // 用来测试结果
            // 遍历结果
            // jquery的方式，通过id获取ui标签对象
            var $category = $("#category");
            // 遍历集合数据
            $(data).each(function () {
               $category.append($("<li><a href='${pageContext.request.contextPath}/product?method=findProductByCid&cid="+this.cid+"&thisPage=1'>"+this.cname+"</a></li>"));
            });

        }, "json");
    });
</script>

