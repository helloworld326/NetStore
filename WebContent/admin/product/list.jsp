<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script language="javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function addProduct() {
		window.location.href = "${pageContext.request.contextPath}/admin/product/add.jsp";
	}

	function delProduct(pid) { // 个体删除
		var isDel = confirm("数据无价,您确定要删除?");
		if (isDel) {
			location.href = "${pageContext.request.contextPath }/adminProduct?method=adminDelProduct&pid=" + pid;
		}
	}
	
	$(function() {
		$("#isHot option[value = '${valueProduct.is_hot}']").prop("selected", true);
		$("#cid option[value = '${valueProduct.cid}']").prop("selected", true);
		
		// 要包含在页面加载方法内，不然找到对应的标签
		$("#mycheck").click(function(){
			$("input[name='pid']").prop("checked", this.checked);
		});
		
		$("#batchDel").click(function(){
			if(confirm("数据珍贵,您确定要批量删除?"))
				$("#Form1").submit();
		});
	});
</script>

</HEAD>
<body>
	<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/adminProduct?method=adminFindProduct" method="post">
			<div>
				商品名称:<input id="searchByKey" style="position:relative;" type="text" id="searchPro" onkeyup="adminSearchProduct(this)" name="pname" value="${valueProduct.pname  }">&nbsp;&nbsp;&nbsp;&nbsp;
				<div id="showDiv" style="left:68px;position: absolute; background-color:#FFF; display:none; border:1px solid black;float:left; width:159px;"></div>
				是否热门:<select id="isHot" name="is_hot">
						<option value="">不限</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				商品类别:<select id="cid" name="cid">
					<option value="">不限</option>
					<c:forEach items="${categoryList }" var="category">
						<option value="${category.cid }">${category.cname }</option>
					</c:forEach>
				   </select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="搜索">
			</div>
			<table style="margin-top:10px;" cellSpacing="1" cellPadding="0" width="100%" align="center"	bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="8%">
									<input id="mycheck" type="checkbox" >
								</td>
								<td align="center" width="10%">序号</td>
								<td align="center" width="10%">商品图片</td>
								<td align="center" width="24%">商品名称</td>
								<td align="center" width="7%">商品价格</td>
								<td align="center" width="7%">是否热门</td>
								<td align="center" width="14%">所属分类</td>
								<td width="10%" align="center">编辑</td>
								<td width="10%" align="center">删除</td>
							</tr>
							<c:forEach items="${productList }" var="product" varStatus="status">
								<tr id="ProductTr" onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';" id="ProductId">
									<td style="CURSOR: hand; HEIGHT: px" align="center" width="8%">
										<input type="checkbox" name="pid" value="${product.pid }">
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="10%">
										${status.count }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="10%">
										<img width="40" height="45" src="${pageContext.request.contextPath}/${product.pimage}">
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="24%">
										${product.pname }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="7%">
										${product.shop_price }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="7%">
										<c:if test="${product.is_hot == 1 }">是</c:if>
										<c:if test="${product.is_hot == 0 }">否</c:if>
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"width="10%">
										${product.category.cname }
									</td>
									<td align="center" style="HEIGHT: 22px">
										<a href="${pageContext.request.contextPath}/adminProduct?method=adminShowProduct&pid=${product.pid }">
											<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
										</a>
									</td>
									<td align="center" style="HEIGHT: 22px">
										<a href="javascript:void(0);" onclick="delProduct('${product.pid}');"> 
											<img src="${pageContext.request.contextPath}/images/i_del.gif"
												width="16" height="16" border="0" style="CURSOR: hand">
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</TBODY>
		</table>
	</form>
</body>
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

	function adminSearchProduct(obj){
		// 1.获得输出框中的内容;
		var key = $(obj).val();
		var content = "";  // 用于拼接字符串
		$.post(
			"${pageContext.request.contextPath }/product?method=findByKey", // url
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

</script>
</HTML>

