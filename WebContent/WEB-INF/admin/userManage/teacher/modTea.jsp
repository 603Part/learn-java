<%@page import="ujs.mlearn.entity.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form
		action="${pageContext.request.contextPath}/UserManageServlet?operation=modTea"
		method="post"  enctype="multipart/form-data">
		<%Teacher teacher=(Teacher)request.getAttribute("teacher"); %>
		<table>
			<tr>
				<td>教师姓名：</td>
				<td><input type="text" style='width: 213px' name="userName" value="${teacher.name}" required/></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><select name="userSex"  style='width: 219px'>
						<%if (teacher.getSex().equals("男")) {%>
						<option selected="selected">男</option>
						<option>女</option>
						<%}else { %>
						<option>男</option>
						<option selected="selected">女</option>
						<%} %>
				</select></td>
			</tr>
			<tr>
				<td>工号：</td>
				<td><input placeholder="学号" type="text" value="${teacher.tId}" name="userNumber" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>学院：</td>
				<td><input placeholder="学院" type="text" value="${teacher.college}" name="userCollege" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>专业：</td>
				<td><input placeholder="专业" type="text" value="${teacher.specialty}" name="userSpecialty" style='width: 213px' required/></td>
			</tr>
			<tr>
				<td>输入密码：</td>
				<td><input placeholder="请输入密码，不输入默认不修改" type="password" name="userPass1" style='width: 213px'
				onblur="if(userPass2.value!=''&&userPass2.value!=null&&userPass1.value!=userPass2.value) alert('两次密码一致！');" /></td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input placeholder="再次输入密码，不输入默认不修改" type="password" name="userPass2" style='width: 213px'
				onblur="if(userPass1.value!=''&&userPass1.value!=null&&userPass1.value!=userPass2.value) alert('两次密码一致！');" /></td>
			</tr>
			<tr>
				<td>电话</td>
				<td><input placeholder="请输入联系方式" type="text" value="${teacher.phone}" name="userPhone" style='width: 213px' /></td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><input placeholder="请输入邮箱" type="text"  value="${teacher.email}" name="userEmail" style='width: 213px' /></td>
			</tr>
			<tr>
				<td>教师头像：</td>
				<td><img height = "150px" width = "150px" src="${pageContext.request.contextPath}/${teacher.photo}"></td>
			</tr>
			<tr>
				<td>选择头像：</td>
				<td><input type="file" name="userPhoto" id="userPhoto" accept="image/png, image/jpg, image/gif, image/jpeg"></td>
			</tr>
			<tr>
				<td>最后登陆时间：</td>
				<td><lable name="lastlogin">${teacher.logintime}</lable></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="修改" />
					<input type="reset" /></td>
			</tr>
			<tr>
				<td></td>
				<td><font color="red">${requestScope.message}</font></td>
			</tr>
		</table>
		<input type="hidden" name="userID" value="${teacher.userId}">
	</form>
</body>
</html>