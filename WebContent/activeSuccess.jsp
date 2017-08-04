<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
    
  </head>
  
  <body>
	<div class="container-fluid">
		<jsp:include page="${pageContext.request.contextPath }/header.jsp"></jsp:include>
		<h3>恭喜您激活成功,点击下面可进入登陆页面</h3>
		<a href="http://localhost:8080/NetStore/login.jsp">进入登陆页面</a>
		<jsp:include page="${pageContext.request.contextPath }/foot.jsp"></jsp:include>
	</div>
  </body>
</html>
