<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门信息</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nav.jsp"/>
<form action="department_setDepScope.action" method="post">
<table width="900" cellpadding="0" cellspacing="0" class="ct">
	<tr>
	<td>标识:</td><td>${dep.id }</td>
	</tr>
	<tr>
	<td>名称:</td><td>${dep.name }</td>
	</tr>
	<tr>
	<td colspan="2">设置可发文部门</td>
	</tr>
	<tr>
	<td colspan="2">
	<s:checkboxlist theme="simple" list="#ds" listKey="id" listValue="name"
	 name="sdeps" value="#ads" />
	 <s:hidden name="id" value="%{dep.id }"/>
	</td>
	</tr>
	<tr>
	<td colspan="2"><input type="submit" value="设置发文部门"/></td>
	</tr>
</table>
</form>
</body>
</html>