<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	type="text/css" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<table class="ct" width="900" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
		<td colspan="7">
		<form action="user_list.action" method="get">
		<s:select theme="simple" list="#ds" listKey="id" listValue="name" value="depId" name="depId"
			headerKey="0" headerValue="全部部门"/>
		<s:submit theme="simple" value="筛选"/>
		</form>
		</td>
		</tr>
		<tr>
			<td>用户id</td>
			<td>用户名称</td>
			<td>用户昵称</td>
			<td>用户邮箱</td>
			<td>用户类型</td>
			<td>所在部门</td>
			<td>操作</td>
		</tr>
		<s:if test="#pages.totalRecord==0">
			<tr>
				<td colspan="6">没有用户存在</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="#pages.datas">
				<tr>
					<td>${id }</td>
					<td><a href="user_show.action?id=${id }">${username}</a></td>
					<td>${nickname }</td>
					<td>${email }</td>
					<td>
				<s:if test="type==0">普通用户</s:if>
				<s:else>
					管理员
				</s:else>
					</td>
					<td>${department.name }</td>
					<td><a href="user_updateInput.action?id=${id }">更新</a> <a
						href="user_delete.action?id=${id }">删除</a>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="7"><jsp:include page="/inc/pager.jsp">
						<jsp:param value="user_list.action" name="url" />
						<jsp:param value="${pages.totalRecord }" name="items" />
						<jsp:param value="depId" name="params" />
					</jsp:include></td>
			</tr>
		</s:else>
	</table>
</body>
</html>