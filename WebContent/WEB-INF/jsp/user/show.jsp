<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nav.jsp"/>
<table width="900" cellpadding="0" cellspacing="0" class="ct">
	<tr>
	<td>标识:</td><td>${id }</td>
	</tr>
	<tr>
	<td>用户名称:</td><td>${username }</td>
	</tr>
	<tr>
	<td>用户昵称:</td><td>${nickname }</td>
	</tr>
	<tr>
	<td>用户邮件:</td><td>${email }</td>
	</tr>
	<tr>
	<td>用户类型:</td><td><s:if test="type==0">普通用户</s:if><s:else>管理员</s:else> </td>
	</tr>
	<tr>
	<td>用户所在部门:</td><td>${department.name}</td>
	
	</tr>
</table>
</body>
</html>