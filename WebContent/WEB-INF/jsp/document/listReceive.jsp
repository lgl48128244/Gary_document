<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接收公文</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	type="text/css" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<table class="ct" width="900" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
		<td colspan="5">
		<form action="document_listReceive.action" method="get">
			<input type="hidden" name="isRead" value="${param.isRead }"/>
			关键字:<input name="con" value="${con }"/> 
			选择部门:<s:select theme="simple" list="#deps" name="depId" 
					listKey="id" listValue="name" headerKey="0" headerValue="选择部门"/>
			<input type="submit" value="检索"/>
		</form>
		<a href="document_listReceive.action?isRead=1">已读公文</a>
		<a href="document_listReceive.action?isRead=0">未读公文</a>
		</td>
		</tr>
		<tr>
			<td>公文id</td>
			<td>公文标题</td>
			<td>发送者</td>
			<td>发送时间</td>
			<td>操作</td>
		</tr>
		<s:if test="#pages.totalRecord==0">
			<tr>
				<td colspan="6">没有消息存在</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="#pages.datas">
				<tr>
					<td>${id }</td>
					<td><a href="document_updateRead.action?id=${id }&isRead=${isRead}">${title}</a></td>
					<td>${user.nickname }[${user.department.name}]</td>
					<td><s:date name="createDate" format="yyyy-MM-dd"/></td>
					<td>&nbsp;</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="7"><jsp:include page="/inc/pager.jsp">
						<jsp:param value="document_listReceive.action" name="url" />
						<jsp:param value="${pages.totalRecord }" name="items" />
						<jsp:param value="con,isRead,depId" name="params" />
					</jsp:include></td>
			</tr>
		</s:else>
	</table>
</body>
</html>