<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fly.domain.Category" %>
<%@ page import="com.fly.service.CategoryService" %>

<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<a href="${pageContext.request.contextPath }/"><img src="img/logo2.png" /></a>
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
		<c:if test="${empty user }">
			<li><a href="login.jsp">登录</a></li>
			<li><a href="register.jsp">注册</a></li>
		</c:if>
		<c:if test="${!empty user}">
			<li><span style="color:blue;">欢迎您，${user.username }！</span></li>
			<li><a id="exit" href="${pageContext.request.contextPath }/user?method=exitLogin">退出</a><li>
		</c:if>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
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
				<a class="navbar-brand" href="javascript:void(0);" onclick="_click0()" >首页</a>
			</div>
			
			<%--
				// 准备分类商品 -- List<Category>
				CategoryService categoryService = new CategoryService();
				List<Category> categoryList = categoryService.findAllCategory();
				if(categoryList != null){
					request.setAttribute("categoryList", categoryList);
				}
			 --%>
			<!-- 导航标题 -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul id="categoryUl" class="nav navbar-nav">
				<%-- 方式一 jstl表达式
					<c:forEach items="${categoryList}" var="category">
						<li><a href="#">${category.cname }</a></li>
					</c:forEach>
				--%>
				<%-- 方式二 ajax方式 --%>
				<%-- 方式三 filter --%>
				<c:forEach items="${categoryList}" var="category">
						<c:if test="${category.cid == cid }">
							<li class="active"><a href="${pageContext.request.contextPath }/product?method=pageProductList&cid=${category.cid}&currentPage=1">${category.cname }</a></li>
						</c:if>
						<c:if test="${category.cid != cid }">
							<li><a href="${pageContext.request.contextPath }/product?method=pageProductList&cid=${category.cid}&currentPage=1">${category.cname }</a></li>
						</c:if>
				</c:forEach>
				</ul>
				<form action="${pageContext.request.contextPath }/product?method=showProductInfo" class="navbar-form navbar-right" role="search" method="post">
					<div class="form-group">
						<input id="searchByKey" name="pname" type="text" class="form-control" placeholder="Search"  onkeyup="searchWord(this)" >
						<div id="showDiv" style="position:absolute;display:none; z-index:1000; background:white; width:200px; height:300px; background:white;"></div>						
					</div>
					<button id="" type="submit" class="btn btn-default">Submit</button>
				</form>
				<script type="text/javascript">
					function _click(obj){
						$("#searchByKey").val($(obj).html());
						$("#showDiv").css("display", "none");
					}

					function _over(obj){
						$(obj).css("background", "#DBEAF9");
					}
					
					function _out(obj){
						$(obj).css("background", "#fff");
					}
				
					// ajax实现数据初始化
					function searchWord(obj){
						// 1.获得输入框的内容
						var key = $(obj).val();
						var content = "";
						// 2.根据输入框中的内容对数据库进行模糊查询 -- list<Product> -- json
						$.post(
							"${pageContext.request.contextPath }/product?method=findByKey",  // url
							{"key":key},
							function(data){
								if(data.length > 0){
									for(var i = 0; i < data.length; i++){
										content += "<div style='padding:5px;cursor:pointer;' onclick='_click(this)' onmouseover='_over(this)' onmouseout='_out(this)'>" 
											+ data[i].pname + "</div>";
									}
									// 3.将返回的信息动态显示在showDiv中
									$("#showDiv").html(content);
									$("#showDiv").css("display", "block");
								}
							},
							"json"
						);
					}
					
					/*$(function(){
						var content = "";
						$.post(
							"${pageContext.request.contextPath }/product?method=headerInit",
							function(data){
								// data接受返回信息
								for(var i = 0; i < data.length; i++){
									content += "<li><a href='#'>" + data[i].cname + "</a></li>";
								}
								$("#categoryUl").html(content);
							},
							"json"
						);
					});*/
				</script>
			</div>
		</div>
	</nav>
</div>