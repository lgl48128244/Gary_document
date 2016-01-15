<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户[${username}]</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<s:form action="user_updateSelf" method="post">
	<s:hidden name="id"/>
	<s:textfield label="用户昵称" name="nickname"/>
	<s:textfield label="用户邮件" name="email"/>
	<s:submit value="修改个人信息"/>
</s:form>
</body>
</html>