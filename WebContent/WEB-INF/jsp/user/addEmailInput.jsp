<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定用户邮件${loginUser.nickname }</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
绑定的用户的邮箱：${loginUser.email }
<s:form action="user_addEmail" method="post">
<input type="hidden" name="userId" value="${loginUser.id }"/>
	<s:hidden name="update"/>
	<s:textfield label="主机名称" name="ue.host"/>
	<s:textfield label="传输协议" name="ue.protocol"/>
	<s:textfield label="邮件用户名" name="ue.username"/>
	<s:password label="邮件用户密码" name="ue.password"/>
	<s:submit value="绑定用户邮件"/>
</s:form>
</body>
</html>