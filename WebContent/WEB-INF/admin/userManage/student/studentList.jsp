<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base>
<style type="text/css">
body {
	font-family: arial;
}

table {
	border: 1px solid #ccc;
	width: 90%;
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
	<div style="text-align : center; font-size : 30px"><b>学生列表</b></label></div>
	<table>
		<thead>
			<tr>
				<td><b>编号</b></td>
				<td><b>姓名</b></front></td>
				<td><b>性别</b></td>
				<td><b>学号</b></td>
				<td><b>学院</b></td>
				<td><b>专业</b></td>
				<td><b>电话</b></td>
				<td><b>邮箱</b></td>
				<td><b>操作</b></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sList}" var="student">
				<tr>
					<td data-label="编号">
						<b>&nbsp&nbsp
						<script type="text/javascript">
							document.write(i + ".");
							i++
						</script>&nbsp&nbsp
						</b>
					</td>
					<td>${student.name}</td>
					<td data-label="性别">&nbsp&nbsp${student.sex}&nbsp&nbsp</td>
					<td data-label="学号">${student.sId}</td>
					<td data-label="学院">${student.college}</td>
					<td data-label="专业">${student.specialty}</td>
					<td data-label="电话">${student.phone}</td>
					<td data-label="邮箱">${student.email}</td>
					<td data-lable="操作">
						<a
						href="${pageContext.request.contextPath }/UserManageServlet?operation=editStu&userID=${student.userId}">编辑</a>
						<a
						href="${pageContext.request.contextPath }/UserManageServlet?operation=delStu&userID=${student.userId}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>