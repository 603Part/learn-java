<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加用户</title>
</head>

<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/UserManageServlet?operation=addUser"
		method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>用户类型</td>
				<td>
					<select name="userType" style='width: 219px' required>
						<option>学生</option>
						<option>老师</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>用户姓名：</td>
				<td><input placeholder="用户姓名" type="text" name="userName" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>用户性别</td>
				<td><select name="userSex" style='width: 219px' required>
						<option>男</option>
						<option>女</option>
				</select></td>
			</tr>
			<tr>
				<td>用户账号：</td>
				<td><input placeholder="学号或工号" type="text" name="userNumber" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>用户学院：</td>
				<td><input placeholder="用户学院" type="text" name="userCollege" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>用户专业：</td>
				<td><input placeholder="用户专业" type="text" name="userSpecialty" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>用户密码：</td>
				<td><input placeholder="请输入密码" type="password" name="userPass1" style='width: 213px'
				onblur="if(userPass2.value!=''&&userPass2.value!=null&&userPass1.value!=userPass2.value) alert('两次密码一致！');" required/></td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input placeholder="再次输入密码" type="password" name="userPass2" style='width: 213px'
				onblur="if(userPass1.value!=''&&userPass1.value!=null&&userPass1.value!=userPass2.value) alert('两次密码一致！');" required/></td>
			</tr>
			<tr>
				<td>用户电话</td>
				<td><input placeholder="请输入联系方式" type="text" name="userPhone" style='width: 213px' /></td>
			</tr>
			<tr>
				<td>用户邮箱</td>
				<td><input placeholder="请输入邮箱" type="text" name="userEmail" style='width: 213px' /></td>
			</tr>
			<tr>
				<td>选择头像：</td>
				<td><input type="file" name="userPhoto" id="userPhoto" accept="image/png, image/jpg, image/gif, image/jpeg"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="注册" />
			</tr>
			<tr>
				<td></td>
				<td><font color="red">${requestScope.message}</font></td>
			</tr>
		</table>
	</form>
</body>
</html>