<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="row" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath }">首页</a></li>
			</ol>
		</div>
		<c:forEach items="${pageBean.productList }" var="product">
			<div class="col-md-2" style="height:270px;">
				<a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}"> 
					<img src="${product.pimage }" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}" style='color: green'>${product.pname }</a></p>
				<p><font color="#FF0000">商城价：&yen;${product.shop_price }</font></p>
			</div>
		</c:forEach>
	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<%-- 如果当前是第一页那么previous链接会跳转到本页 --%>
			<c:if test="${pageBean.currentPage == 1 }">
				<li class="disabled">
					<a aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
				</li>
			</c:if>
			<c:if test="${pageBean.currentPage != 1 }">
				<li>
					<a href="${pageContext.request.contextPath }/product?method=pageProductList&cid=${pageBean.cid}&currentPage=${pageBean.currentPage-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
				</li>
			</c:if>
			<c:forEach var="page" begin="1" end="${pageBean.totalPage }" step="1">
				<c:if test="${page == pageBean.currentPage }">
				<li class="active"><a href="javascript:void(0);">${page}</a></li>
				</c:if>
				<c:if test="${page != pageBean.currentPage }">
				<li><a href="${pageContext.request.contextPath }/product?method=pageProductList&cid=${pageBean.cid}&currentPage=${page}">${page}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${pageBean.totalPage == pageBean.currentPage }">
				<li class="disabled">
					<a href="javascript:void(0);" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
				</li>
			</c:if>
			<c:if test="${pageBean.totalPage != pageBean.currentPage }">
				<li>
					<a href="${pageContext.request.contextPath }/product?method=pageProductList&cid=${pageBean.cid}&currentPage=${pageBean.currentPage+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
				</li>
			</c:if>
		</ul>
	</div>
	<!-- 分页结束 -->
	<!--商品浏览记录-->
	<div style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">
		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div id="" style="width: 50%; float: right; text-align: right;">
			<a href="javascript:void(0);" onclick="_click()">more</a>
		</div>
		<div style="clear: both;"></div>
		<div style="overflow: hidden; float:left; ">
			<ul style="list-style: none;">
				<c:forEach items="${productRecordList }" var="product" begin="0" end="4" step="1">
					<li style="width: 220px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
						<a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}">
							<img src="${product.pimage }" width="130px" height="140px" />
						</a>
						<a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}"><p>${product.pname }</p></a>
					</li>
				</c:forEach>
				<div id="moreDiv" style="display:none; float:left;">
				<c:forEach items="${productRecordList }" var="product" begin="5" end="${productRecordList.size() }" step="1">
					<li style="width: 220px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
						<a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}">
							<img src="${product.pimage }" width="130px" height="140px" />
						</a>
						<a href="${pageContext.request.contextPath }/product?method=showProductInfo&pid=${product.pid}"><p>${product.pname }</p></a>
					</li>
				</c:forEach>
				</div>
			</ul>
		</div>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
<script type="text/javascript">
	function _click(){
		if($("#moreDiv").css("display")){
			$("#moreDiv").css("display","block");
			return ;
		}
		$("#moreDiv").css("display", "none");
	}
</script>
</html>