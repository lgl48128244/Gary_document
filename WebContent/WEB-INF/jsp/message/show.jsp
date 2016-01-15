<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看信件</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"
	type="text/css" />
</head>
<body>
	<jsp:include page="nav.jsp" />
	<h2>${title }</h2>
	<p><s:date name="createDate" format="yyyy-MM-dd hh:ss"/>----
		${user.nickname }[${user.department.name }]
	</p>
	<hr/>
	<p>附件:
		<s:if test="atts.size()<=0">
			没有相关附件
		</s:if>
		<s:else>
			<s:iterator value="#atts">
				[<a href="<%=request.getContextPath()%>/upload/${newName}">${oldName}</a>]
			</s:iterator>
		</s:else>
	</p>
	<p>
		${content }
	</p>
</body>
</html>