<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已发送公文列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	type="text/css" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<table class="ct" width="900" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
		<td colspan="4">
		<form action="document_listSend.action" method="get">
			关键字:<input name="con" value="${con }"/> 
			选择部门:<s:select theme="simple" list="#deps" name="depId" 
					listKey="id" listValue="name" headerKey="0" headerValue="选择部门"/>
			<input type="submit" value="检索"/>
		</form>
		</td>
		</tr>
		<tr>
			<td>公文id</td>
			<td>公文标题</td>
			<td>发送时间</td>
			<td>操作</td>
		</tr>
		<s:if test="#pages.totalRecord==0">
			<tr>
				<td colspan="6">没有公文存在</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="#pages.datas">
				<tr>
					<td>${id }</td>
					<td><a href="document_show.action?id=${id }">${title}</a></td>
					<td><s:date name="createDate" format="yyyy-MM-dd"/></td>
					<td><a href="document_delete.action?id=${id }">删除</a>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="7"><jsp:include page="/inc/pager.jsp">
						<jsp:param value="document_listSend.action" name="url" />
						<jsp:param value="${pages.totalRecord }" name="items" />
						<jsp:param value="con,depId" name="params" />
					</jsp:include></td>
			</tr>
		</s:else>
	</table>
</body>
</html>