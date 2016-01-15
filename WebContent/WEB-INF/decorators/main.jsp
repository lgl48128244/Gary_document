<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="欢迎使用文档管理系统"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css"/>
<decorator:head/>
</head>
<body>
<s:if test="#session.loginUser!=null">
	欢迎[${loginUser.nickname }]登录我们的系统&nbsp;
	<s:if test="#session.loginUser.type==1">
	<a href="<%=request.getContextPath()%>/user_list.action">用户管理</a>
	<a href="<%=request.getContextPath()%>/department_list.action">部门管理</a>
	</s:if>
	<a href="<%=request.getContextPath()%>/document_listReceive.action">公文管理</a>
	<a href="<%=request.getContextPath()%>/message_listReceive.action">私人信件</a>
	<a href="<%=request.getContextPath()%>/user_updateSelfInput.action?id=${loginUser.id}">修改个人信息</a>&nbsp;
	<a href="<%=request.getContextPath()%>/user_showSelf.action?id=${loginUser.id}">个人信息查询</a>&nbsp;
	<a href="<%=request.getContextPath()%>/user_addEmailInput.action">绑定邮箱</a>&nbsp;
	<a href="<%=request.getContextPath()%>/login!logout.action">退出系统</a>
</s:if>
<s:else>
<a href="<%=request.getContextPath()%>/login!loginInput.action">用户登录</a>
</s:else>
<hr/>
<h3 align="center"><decorator:title default="文档管理系统"/></h3>
<decorator:body/>
<div align="center" style="width:100%;border-top:1px solid; float:left;margin-top:10px;">
	CopyRight@2012-2015<br/>
	开发培训教学项目
</div>
</body>
</html>