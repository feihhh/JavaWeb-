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
		</style>
	</head>

	<body>

			<%@include file="head.jsp"%>


		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>所有订单</strong>
					<table class="table table-bordered">


						<c:choose>
							<c:when test="${empty orders} || ${empty orders.list}">
								<tr>
									<td>
										您现在没有还没有创建任何订单...
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${orders.list}" var="odList">
									<tbody>
										<tr class="success">
											<th colspan="5">订单编号:${odList.orders.oid} </th>
										</tr>
										<tr class="warning">
											<th>图片</th>
											<th>商品</th>
											<th>价格</th>
											<th>数量</th>
											<th>小计</th>
										</tr>
										<c:forEach  items="${odList.list}" var="itemList">
											<tr class="active">
												<td width="60" width="40%">
													<input type="hidden" name="id" value="22">
													<img src="${pageContext.request.contextPath}/
											${itemList.product.pimage}"
														 width="70" height="60">
												</td>
												<td width="30%">
													<a target="_blank"> ${itemList.product.pname}</a>
												</td>
												<td width="20%">
													￥ ${itemList.product.shop_price}
												</td>
												<td width="10%">
														${itemList.count}
												</td>
												<td width="15%">
													<span class="subtotal">￥${itemList.subtotal}</span>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<%--前一页--%>
					<c:choose>
						<c:when test="${orders.curPage eq 1}">
							<li class="disabled"><a href=" javascript:void(0)" aria-label="Previous"><span
									aria-hidden="true">&laquo;</span></a></li>
						</c:when>
						<c:otherwise>
							<li><a
									href="${pageContext.request.contextPath}/order?method=allOrders&pageNum=${orders.curPage-1}"
									aria-label="Previous"><span
									aria-hidden="true">&laquo;</span></a></li>
						</c:otherwise>
					</c:choose>
					<%--遍历页码--%>
					<c:forEach begin="1" end="${orders.totalPage}" var="i">
						<c:if test="${i eq orders.curPage}">
							<li class="active"><a href=" javascript:void(0)">${i}</a> </li>
						</c:if>

						<c:if test="${i ne orders.curPage}">
							<li>
								<a	href="${pageContext.request.contextPath}/order?method=allOrders&pageNum=${i}">
									${i}
								</a>
							</li>
						</c:if>
					</c:forEach>
						<%--下一页--%>

						<c:choose>
							<%--如果是最后一页，下一页不能点--%>
							<c:when test="${orders.curPage eq orders.totalPage}">
								<li class="disabled">
									<a href="javascript:void(0)" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a
											href="${pageContext.request.contextPath}/order?method=allOrders&pageNum=${orders.curPage+1}"
											aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>





					<%--<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>--%>
					<%--<li class="active"><a href="#">1</a></li>--%>
					<%--<li><a href="#">2</a></li>--%>
					<%--<li><a href="#">3</a></li>--%>
					<%--<li><a href="#">4</a></li>--%>
					<%--<li><a href="#">5</a></li>--%>
					<%--<li><a href="#">6</a></li>--%>
					<%--<li><a href="#">7</a></li>--%>
					<%--<li><a href="#">8</a></li>--%>
					<%--<li><a href="#">9</a></li>--%>
					<%--<li>--%>
						<%--<a href="#" aria-label="Next">--%>
							<%--<span aria-hidden="true">&raquo;</span>--%>
						<%--</a>--%>
					<%--</li>--%>
				</ul>
			</div>

		</div>
		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />

		</div>
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