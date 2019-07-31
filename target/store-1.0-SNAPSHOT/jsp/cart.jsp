<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<script>

            $("#count").blur(function () {
				$("#subtotal_id").load("${pageContext.request.contextPath}/cart?method=deleteOne");
            });

	</script>

	<body>

		<div class="container">

			<%@include file="head.jsp"%>

			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">我的购物车</strong>
					<div style="text-align: right; margin-top: 10px;margin-bottom: 10px">
						<a
								href="${pageContext.request.contextPath}/order?method=allOrders&pageNum=1">全部订单
						</a>
					</div>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>

							<c:choose>
								<c:when test="${sessionScope.cart == null
								||sessionScope.cart.totalCount eq 0}">
									<tr>
										<td colspan="6">
											购物车空空如也...
											<a href="${pageContext.request.contextPath}/index">亲，先去商店逛逛把</a>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${sessionScope.cart.map}" var="cMap">
										<tr class="active">
											<td width="60" width="40%">
												<input type="hidden" name="id" value="22">
												<img src="${pageContext.request.contextPath}/
												${cMap.value.product.pimage}"
													 width="70" height="60">
											</td>
											<td width="30%">
												<a target="_blank">${cMap.value.product.pname}</a>
											</td>
											<td width="20%">
												￥${cMap.value.product.shop_price}
											</td>
											<td width="10%">
												<a href="${pageContext.request.contextPath}/cart?method=deleteOneItem&pid=${cMap.key}">-</a>
													<input id="count" type="text"
														   value="${cMap.value.count}"
														   name="quantity" style="width: 50px"
														   disabled>
												<a href="${pageContext.request.contextPath}/cart?method=addOneItem&pid=${cMap.key}">+</a>
												<%--<input type="number" name="quantity"--%>
													   <%--value="${pageContext.request.contextPath}/--%>
										<%--${cMap.value.count}" --%>
													   <%--maxlength="4" size="10">--%>
											</td>
											<td width="15%">
												<span id="subtotal_id" class="subtotal">￥
														${cMap.value.subtotal}</span>
											</td>
											<td>
												<a	href="${pageContext.request.contextPath}/cart?method=deleteItems&itemPid=${cMap.key}" class="delete">删除</a> <br/>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">

					<c:if test="${sessionScope.cart != null && sessionScope.cart.totalCount ne 0}">
						<em style="color:#ff6600;">登录后确认是否享有优惠&nbsp;&nbsp;
						</em> 赠送积分: <em style="color:#ff6600;">${sessionScope.cart.totalCredits}</em>&nbsp; 商品金额:<strong
						style="color:#ff6600;">￥ ${sessionScope.cart.totalMoney}元</strong>
					</c:if>

					<c:if test="${sessionScope.cart == null ||  sessionScope.cart.totalCount eq 0}">
						<em style="color:#ff6600;">登录后确认是否享有优惠&nbsp;&nbsp;
						</em> 赠送积分: <em style="color:#ff6600;">0</em>&nbsp; 商品金额:<strong
						style="color:#ff6600;">￥0元</strong>
					</c:if>

				</div>

				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="${pageContext.request.contextPath}/index">继续购物</a>
					<a href="${pageContext.request.contextPath}/cart?method=cleraCart" id="clear"
					   class="clear">
						清空购物车</a>
					<a href="${pageContext.request.contextPath}/order?method=handleOrder">
						<input type="submit" width="100" value="立即下单" name="submit" border="0"
							   style="background:
									   url('${pageContext.request.contextPath}/images/register.gif')
									   no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2009-202 品优商城 版权所有
		</div>

	</body>

</html>