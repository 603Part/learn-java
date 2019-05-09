<%@page import="ujs.mlearn.entity.CourseMaterial"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
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
	<div style="text-align : center; font-size : 30px"><b><label>《<%=request.getAttribute("courseName") %>》课程的资源列表</b></label></div>
	<table>
		<thead>
			<tr>
				<td><b>编号</b></td>
				<td><b>资源名称</b></front></td>
				<td><b>课程名称</b></td>
				<td><b>上传时间</b></td>
				<td><b>大小</b></td>
				<td><b>操作</b></td>
			</tr>
		</thead>
		<%
			List<CourseMaterial> mList = (List<CourseMaterial>) request.getAttribute("mList");
		%>
		<tbody>
			<%
			for(CourseMaterial resource : mList) {
				String resName = resource.getResTitle().substring(0, resource.getResTitle().lastIndexOf("."));
			%>
				<tr>
					<td data-label="编号">
						<b>&nbsp&nbsp
						<script type="text/javascript">
							document.write(i + ".");
							i++
						</script>&nbsp&nbsp
						</b>
					</td>
					<td data-label="资源名称"><%=resName %></td>
					<td data-label="课程名称"><%=resource.getCourseName() %></td>
					<td data-label="上传时间"><%=resource.getPublishTime() %></td>
					<td data-label="大小"><%=resource.getSize() %>B</td>
					<td data-lable="操作">
						<a
							href="${pageContext.request.contextPath }/WebMaterialServlet?action=editResource&resID=<%=resource.getResID()%>">编辑</a>
						<a
						href="${pageContext.request.contextPath }/WebMaterialServlet?action=downloadResource&resID=<%=resource.getResID()%>">下载</a>
						<a
						href="${pageContext.request.contextPath }/WebMaterialServlet?action=delResource&resID=<%=resource.getResID()%>">删除</a>
					</td>
				</tr>
			<%} %>
		</tbody>
	</table>
</body>
</html>