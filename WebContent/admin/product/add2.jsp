<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="/NetStore/css/Style1.css" type="text/css" rel="stylesheet">
		<script>
			"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"64206",secure:"64211"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);
		</script>
	</HEAD>
	<style>
		#myDiv{
			background: #afd1f3;
		}
		body form table td{
			background: #f5fafe;
		}
	</style>
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-48" data-genuitec-path="/NetStore/WebContent/admin/product/add2.jsp">
		<div id="myDiv" align="center">
			<strong>添加商品</strong>
		</div>
		<form action="/NetStore/adminAddProduct" method="post" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-48" data-genuitec-path="/NetStore/WebContent/admin/product/add2.jsp">
			<table id="tab" align="center" border="1px solid #8ba7e3" style="cellspacing:0">
				<tr>
					<td class="ta_01" style="align:'center'; bgColor:'#afd1f3'; colSpan:'4'; width:200px; height:'26'" >
						<strong>商品名称</strong>
					</td>
					<td class="ta_01" style="align:'center';  colSpan:'4'; height:'26'" >
						<input type="text" name="pname"  />
					</td>
					<td class="ta_01" style="align:'center'; bgColor:'#afd1f3'; colSpan:'4'; height:'26'" >
						<strong>是否热门</strong>
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<select name="is_hot">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><strong>市场价格</strong></td>
					<td><input type="text" name="market_price" /></td>
					<td><strong>商城价格</strong></td>
					<td><input type="text" name="shop_price" /></td>
				</tr>
				<tr>
					<td>商品图片</td>
					<td><input type="file" name="" ></td>
				</tr>
				<tr>
					<td>所属分类</td>
					<td>
						<select name="cid">
							<c:forEach items="${categoryList }" var="category">
								<option value="${category.cid }">${category.cname }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>商品描述</td>
					<td><textarea name="pdesc" rows="5" cols="30"></textarea></td>
					<td>
						<input type="submit" value="提交">
						<input type="reset" value="重置">
						<input type="button" onclick="history.go(-1)" value="返回">
					</td>
				</tr>
			</table>
		</form>		
	</body>
</HTML>