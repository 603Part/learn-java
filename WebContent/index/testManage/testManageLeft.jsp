<%@page import="ujs.mlearn.entity.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>习题管理功能列表</title>

<style>
li {
	float: left !important;
	float: none;
	width: auto;
	padding-top: 6px;
	padding-bottom: 6px;
	border-bottom: 1px solid #AAA;
}

a {
	text-decoration: NONE
}

a {
	text-decoration: none;
	font-size: 15px;
	display: block;
	padding: 8px;
	color: #000000;
}

a:hover {
	background-color: #ffffff;
	color: orange;
}
</style>
</head>
<body style="background-image: url(${pageContext.request.contextPath}/index/tleft.jpg)">
	<%
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("target", "all");
		} else {
			request.setAttribute("target", "body");
		}
	%>
	<ul style="list-style-type: none">
		<li><a
			href="${pageContext.request.contextPath }/WebTestServlet?action=goShowPage"
			target="${requestScope.target}">添加课程习题</a></li>
		<li><a
			href="${pageContext.request.contextPath }/WebTestServlet?action=findMyCourse"
			target="${requestScope.target}">我的课程习题</a></li>

	</ul>
</body>
</html>