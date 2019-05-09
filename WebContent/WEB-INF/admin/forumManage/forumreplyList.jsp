<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帖子回复列表</title>
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
				<td><b>回复人</b></td>
				<td><b>学号/工号</b></td>
				<td><b>身份</b></td>
				<td><b>回复内容</b></td>
				<td><b>回复时间</b></td>
				<td><b>操作</b></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rList}" var="reply">
				<tr>
					<td data-label="编号">
						<script type="text/javascript">
							document.write(i + ".  ");
							i++
						</script>
					</td>
					<td data-label="回复人">${reply.userName}</td>
					<td data-label="学号/工号">${reply.userNumber}</td>
					<td data-label="身份">${reply.userTypeString}</td>
					<td data-label="回复内容">${reply.replyContent}</td>
					<td data-label="回复时间">${reply.replyTime}</td>
					<td data-lable="操作">
						<a href="${pageContext.request.contextPath }/ForumManageServlet?operation=delReply&replyID=${reply.replyID}&userType=${reply.userType}&postID=${reply.postID}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>