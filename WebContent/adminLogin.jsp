<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>移动学习系统</title>
</head>
<body>
	<div
		style="height:190px;width:400px; background-image:url(${pageContext.request.contextPath}/aback.jpg);margin-left: 500px;margin-top: 150px;">
		<form
			action="${pageContext.request.contextPath}/AdminUserServlet?operation=login"
			method="post">
			<div style="height: 200px; width: 300px; margin-left: 50px;">
				<table align="center">
					<tr>
						<td></td>
						<td><h3>管理员登录</h3></td>
					</tr>
					<tr>
						<td>账号:</td>
						<td><input type="text" name="account" value="root" style="width:195px" required></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" name=password value = "123" style="width:195px" required></td>
					</tr>
					<tr>
						<td></td>
						<td><font color="red">${requestScope.error}</font></td>
					</tr>
					<tr>
						<td></td>
						<td>
							&nbsp&nbsp
							<input type="submit" name = "login" value="登录">
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							<input type="reset" value="重置">
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>

</body>
</html>