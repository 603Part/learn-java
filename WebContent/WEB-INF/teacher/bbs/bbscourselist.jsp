<%@page import="java.util.List"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程列表</title>
<base>
<style type="text/css">
body {
	font-family: arial;
}

table {
	border: 1px solid #ccc;
	width: 95%;
	margin: 0;
	padding: 0;
	border-collapse: collapse;
	border-spacing: 0;
	margin: 0 auto;
}

table tr {
	border: 1px solid #ddd;
	padding: 5px;
}

table th, table td {
	padding: 10px;
	text-align: center;
}

table th {
	text-transform: uppercase;
	font-size: 14px;
	letter-spacing: 1px;
}

@media screen and (max-width: 600px) {
	table {
		border: 0;
	}
	table thead {
		display: none;
	}
	table tr {
		margin-bottom: 10px;
		display: block;
		border-bottom: 2px solid #ddd;
	}
	table td {
		display: block;
		text-align: right;
		font-size: 13px;
		border-bottom: 1px dotted #ccc;
	}
	table td:last-child {
		border-bottom: 0;
	}
	table td:before {
		content: attr(data-label);
		float: left;
		text-transform: uppercase;
		font-weight: bold;
	}
}

.note {
	max-width: 80%;
	margin: 0 auto;
}
</style>

</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<script type="text/javascript">
		var i = 1;
	</script>
	<table>
		<thead>
			<tr>
				<td><b>编号</b></td>
				<td><b>课程名称</b></td>
				<!--<td><b>课程简介</b></td>-->
				<td><b>帖子数量</b></td>
				<td><b>操作</b></td>
			</tr>
		</thead>
		<tbody>
			<%
				List<Course> cList = (List<Course>) request.getAttribute("cList");
				List<Integer> BBSNumber = (List<Integer>) request.getAttribute("BBSNumber");
				for(int i = 0; i < cList.size(); i++) {
					Course course = cList.get(i);
					Integer number = BBSNumber.get(i);
			%>
				<tr>
					<td data-label="编号"><%=i+1%></td>
					<td data-label="课程名称"><%=course.getCourseName()%></td>
					<!--<td data-label="课程简介"><%=course.getCourseAbstract()%></td>-->
					<td data-label="帖子数量"><%=number %></td>
					<td data-lable="操作">
					<a href="${pageContext.request.contextPath }/WebBbsServlet?action=findQuestionList&courseID=<%=course.getCourseID() %>">查看问题</a>
					</td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>