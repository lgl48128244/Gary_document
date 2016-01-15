<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加私人信件</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/xheditor/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#content").xheditor({tools:"full"});
		var ab = document.getElementById("addAttach");
		var ac = document.getElementById("attachContainer");
		ab.onclick = function(){
			var node = "<span><br/><input type='file' name='atts'/><input type='button' onclick='remove(this)' value='移除'/></span>";
			ac.innerHTML = ac.innerHTML+node;
		};
	});
	
	function remove(obj) {
		var ac = document.getElementById("attachContainer");
		ac.removeChild(obj.parentNode);
	}
</script>
</head>
<body>
<jsp:include page="nav.jsp"/>
<s:fielderror/>
<form action="message_add.action" method="post" enctype="multipart/form-data">
<table class="ct" width="900" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
	<td>信件标题:</td>
	<td><input type="text" size="40" name="title"/>
	</td>
	</tr>
	<tr>
	<td>邮件发送:</td>
	<td>选中就可以通过邮件发送<input type="checkbox" value="1" name="isEmail"/>
	</td>
	</tr>
	<tr>
		<td colspan="2">选择用户:</td>
	</tr>
	<tr>
		<td colspan="2">
			<s:if test="#us.size()<=0">
				没有可以发送的用户
			</s:if>
			<s:else>
				<s:checkboxlist theme="simple" list="#us" listKey="id" listValue="nickname" name="sus"/>
			</s:else>
		</td>
	</tr>
	<tr>
		<td colspan="2">添加附件:</td>
	</tr>
	<tr>
		<td colspan="2">
		<input type="button" value="添加附件" id="addAttach"/>
			<div id="attachContainer">
			<input type="file" name="atts"/>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">内容</td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea rows="30" cols="130" id="content" name="content"></textarea>
		</td>
	</tr>
	<tr>
	<td colspan="2" align="center">
	<s:if test="#us.size()>0">
		<input type="submit" value="发送"/>
	</s:if>
	</td>
	</tr>
</table>
</form>
</body>
</html>