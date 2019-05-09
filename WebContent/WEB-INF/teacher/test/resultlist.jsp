<%@page import="ujs.mlearn.entity.StudentCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>答题情况</title>
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
	<%
		HashMap<Integer, String> name=(HashMap<Integer,String>)request.getAttribute("name");
	 	List<StudentCourse> sCourses=(List<StudentCourse>)request.getAttribute("studentcourse");
	 %>
	<script type="text/javascript">
		var i = 1;
	</script>
	<div style="text-align : center; font-size : 30px"><b><label>《<%=request.getAttribute("courseName") %>》课程的答题情况</b></label></div>
	<table>
		<thead>
			<tr>
				<td><b>学生姓名</b></td>
				<td><b>学生分数</b></td>
				<td><b>学生答案</b></td>
			</tr>
		</thead>
	
		<%for(StudentCourse sCourse:sCourses) { %>
		<tr>
			<td data-label="学生姓名">
				<%out.print(name.get(sCourse.getStudentID())); %>
			</td>
			<td data-label="学生分数">
				<%out.print(sCourse.getStudentGrade()); %>
			</td>
			<td data-label="学生答案">
				<%out.print(sCourse.getStudentAnswer()); %>
			</td>
		</tr>
		<%}%>
		
		
		<tbody>
			<%for(StudentCourse sCourse:sCourses) { %>
			<tr>
				<td data-label="学生姓名">
					<%out.print(name.get(sCourse.getStudentID())); %>
				</td>
				<td data-label="学生分数">
					<%out.print(sCourse.getStudentGrade()); %>
				</td>
				<td data-label="学生答案">
					<%out.print(sCourse.getStudentAnswer()); %>
				</td>
			</tr>
			<%}%>
		</tbody>
	</table>
</body>
</html>