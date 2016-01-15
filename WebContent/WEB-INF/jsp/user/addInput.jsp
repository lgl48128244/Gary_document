<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nav.jsp"/>
<s:form action="user_add" method="post">
	<s:textfield label="用户名称" name="username"/>
	<s:textfield label="用户昵称" name="nickname"/>
	<s:password label="用户密码" name="password"/>
	<s:textfield label="用户邮件" name="email"/>
	<s:select list="#ds" listKey="id" listValue="name" name="depId" label="用户所在部门"/>
	<s:radio list="#{'0':'普通用户','1':'管理员' }" listKey="key" listValue="value" label="用户类型" name="type"/>
	<s:submit value="添加用户"/>
</s:form>
</body>
</html>