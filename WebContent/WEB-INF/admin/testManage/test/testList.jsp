<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>习题列表</title>
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
	<div style="text-align : center; font-size : 30px"><b><label>习题列表</b></label></div>
	<table>
		<thead>
			<tr>
				<td><b>编号</b></td>
				<td><b>题目类型</b></front></td>
				<td><b>题目内容</b></front></td>
				<td><b>教师姓名</b></td>
				<td><b>教师工号</b></td>
				<td><b>课程名称</b></td>
				<td><b>操作</b></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tList}" var="test">
				<tr>
					<td data-label="编号">
						<b>&nbsp&nbsp
						<script type="text/javascript">
							document.write(i + ".");
							i++
						</script>&nbsp&nbsp
						</b>
					</td>
					<td data-label="题目类型">${test.getTypeString()}</td>
					<td data-label="题目内容">${test.testContent}</td>
					<td data-label="教师姓名">${test.teacherName}</td>
					<td data-label="教师工号">${test.teacherNumber}</td>
					<td data-label="课程名称">${test.courseName}</td>
					<td data-lable="操作">
						<a
						href="${pageContext.request.contextPath }/TestManageServlet?operation=editTest&testID=${test.testID}">编辑</a>
						<a
						href="${pageContext.request.contextPath }/TestManageServlet?operation=delTest&testID=${test.testID}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>