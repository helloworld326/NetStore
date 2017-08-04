<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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

<script type="text/javascript">
	function _changeImg(obj) { // 不能至于页面加载函数内，因为域的问题，方法内部的方法，外界无法访问;
			obj.src = "${pageContext.request.contextPath}/user?method=userLoginCheckImg&time=" + new Date().getTime();
	}
	
	$(function() {
		$("#checkCode").keyup(function() { // 需要先加载页面
			// 失去焦点获得输入框的内容
			var codeInput = $(this).val();
			// 2.去服务器端校验用户名是否正确
			$.post(
				"${pageContext.request.contextPath }/user?method=checkImg", // 1.后台处理的uri
				{
					"checkImg" : codeInput // 2.代表请求服务器端的数据,传到服务器
				},
				function(data) { // 3.回调函数  data? 根据回传信息 ，动态显示提示信息
					var flag = data.flag; // true 表示正确
					var checkInfo = "";
					if (!flag) {
						// 如果验证码错误
						checkInfo = "验证码错误，请重新输入";
						$("#checkInfo").css("color", "red");
					} else {
						checkInfo = "验证码正确";
						$("#checkInfo").css("color", "green");
					}
					$("#checkInfo").html(checkInfo);
				},
				"json" // 4.数据类型
			);
		});
	})
</script>

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
	color: #666;
	font-size: 22px;
	font-weight: normal;
	padding-right: 17px;
}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="container"
		style="width: 100%; height: 460px; background: #FF2C4C url('images/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="./image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN
					<div>
						<span style="color:red;">${LoginInfo }</span>
					</div>
					<form class="form-horizontal"
						action="${pageContext.request.contextPath}/user?method=userAuto" method="post">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<c:if test="${empty username }">
								<input type="text" class="form-control" id="username" value="" name="username">
								</c:if>
								<c:if test="${!empty username }">
								<input type="text" class="form-control" id="username" value="${username }" name="username">
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<c:if test="${empty password }">
								<input type="password" class="form-control" value="" id="inputPassword3" placeholder="请输入密码" name="password">
								</c:if>
								<c:if test="${!empty password }">
								<input type="password" class="form-control" value="${password }" id="inputPassword3" placeholder="请输入密码" name="password">
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input id="checkCode" type="text" class="form-control"
									placeholder="请输入验证码" name="checkImg">
							</div>
							<div class="col-sm-3" style="width:200px;">
								<img src="${pageContext.request.contextPath}/user?method=userLoginCheckImg"
									onclick="_changeImg(this)" />
									<div><span id="checkInfo"></span></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="autoLogin">
										自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox" name="rememberUsername"> 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									style="background: url('./images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>