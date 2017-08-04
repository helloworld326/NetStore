<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<script type="text/javascript">
	$(function() {
		$("#username").keyup(function() {
			// 失去焦点获得输入框的内容
			var usernameInput = $(this).val();
			// 2.去服务器端校验用户名是否正确
			$.post(
				"${pageContext.request.contextPath}/user?method=checkUsername", // 1.后台处理的uri
				{
					"username" : usernameInput  // 2.代表请求服务器端的数据
				}, 
				function(data) { // 3.回调函数  data? 根据回传信息 ，动态显示提示信息
					var isExist = data.isExist;
					var usernameInfo = "";
					if (!isExist) { // 用户名存在,不能注册
						usernameInfo = "用户名存在，不能注册";
						$("#usernameInfo").css("color", "red");
					} else {
						usernameInfo = "用户名不存在，可以注册";
						$("#usernameInfo").css("color", "green");
					}
					$("#usernameInfo").html(usernameInfo);
				},
				"json" // 4.数据类型
			);
		});

		$("#checkCode").keyup(function(){
			{
			// 失去焦点获得输入框的内容
			var codeInput = $(this).val();
			// 2.去服务器端校验用户名是否正确
			$.post(
				"${pageContext.request.contextPath }/user?method=checkImg", // 1.后台处理的uri
				{
					"checkImg" : codeInput  // 2.代表请求服务器端的数据,传到服务器
				}, 
				function(data) { // 3.回调函数  data? 根据回传信息 ，动态显示提示信息
					var flag = data.flag; // true 表示正确
					var checkInfo = "";
					if(!flag){
						// 如果验证码错误
						checkInfo = "验证码错误，请重新输入";
						$("#checkInfo").css("color", "red");
					}else{
						checkInfo = "验证码正确";
						$("#checkInfo").css("color", "green");
					}
					$("#checkInfo").html(checkInfo);
				},
				"json" // 4.数据类型
			);
		}
		
		});
		$("#registerForm").validate({
			rules : {
				username : {
					required : true,
					rangelength : [ 3, 12 ]
				},
				password : {
					required : true,
					rangelength : [ 3, 12 ]
				},
				repassword : {
					equalTo : "[name='password']"
				},
				email : {
					required : true
				},
				name : {
					required : true
				},
				sex : {
					required : true,
				},
			},
			messages : {
				username : {
					required : "用户名不能为空",
					rangelength : "用户名长度为3-12"
				},
				password : {
					required : "密码不能为空",
					rangelength : "密码长度必须为3-12个字符(汉字算一个字符)"
				},
				repassword : {
					equalTo : "两次密码不一致"
				},
				email : {
					required : "email不能为空",
					email : "邮箱格式不正确"
				},
				name : {
					required : "真实姓名不能为空"
				}
			}
		});
	});

	function _changeImg(obj) {
		obj.src = "${pageContext.request.contextPath}/user?method=userLoginCheckImg&time=" + new Date().getTime();
	}
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

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}

.error {
	color: red;
}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form id="registerForm" class="form-horizontal"
					style="margin-top: 5px;"
					action="${pageContext.request.contextPath }/user?method=register" method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								name="username" placeholder="请输入用户名">
							<span id="usernameInfo"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="inputPassword3"
								name="password" placeholder="请输入密码" id="psd">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码" name="repassword">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3"
								placeholder="Email" name="email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名" name="name">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="sex1" value="male">男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="sex2" value="female">女
							</label> <label class="error" for="sex" style="display:none">性别必选</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input id="checkCode" type="text" class="form-control" name="checkCode">
						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath}/user?method=userLoginCheckImg"
								onclick="_changeImg(this)" />
							<span style="color:red">${LoginInfo }</span>
						</div>
						<div><span id="checkInfo"></span></div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>
