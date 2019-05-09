<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加课程</title>
</head>

<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/ClassManageServlet?operation=addCourse"
		method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" style="width: 156px">
						<c:forEach items="${teachers}" var="teacher">
							<option>${teacher.tId}:${teacher.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>课程名称：</td>
				<td><input type="text" name="courseName" value="${requestScope.courseName}" style="width: 150px" required/></td>
			</tr>
			<tr>
				<td>课程简介：</td>
				<td><input type="text" name="courseAbstract" value="${requestScope.courseAbstract}" style="width: 150px" required/></td>
			</tr>
			<tr>
				<td>详细信息：</td>
				<td><textarea rows="10" cols="60" name="detailInfo" required>${requestScope.detailInfo}</textarea></td>
			</tr>

			<tr>
				<td>选择封面：</td>
				<td><input type="file" name="uphoto" accept="image/jpg, image/jpeg, image/gif, image/png" style="width: 500px" required/></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="添加" />
			</tr>
			<tr>
				<td></td>
				<td><font color="red">${requestScope.message}</font></td>
			</tr>
		</table>
	</form>
</body>
</html>