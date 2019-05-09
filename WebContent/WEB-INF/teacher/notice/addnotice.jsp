<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.*"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="java.util.List"%>
<%@page import="ujs.mlearn.dao.impl.CourseDaoImpl"%>
<%@page import="ujs.mlearn.dao.CourseDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加通知</title>

</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">
	<% 
CourseDao cou = new CourseDaoImpl();
Teacher teacher=(Teacher) request.getSession().getAttribute("teacher");
List<Course> courses = cou.findMyCourse(teacher.gettId());
%>
	<form
		action="${pageContext.request.contextPath}/WebNoticeServlet?action=addNotice"
		method="post">
		<table>
			<tr>
				<td>选择课程：</td>
				<td>
					<select name="course" id="course">
						<c:forEach items="${courses}" var="course">
							<option>${course.courseName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>通知标题：</td>
				<td><input type="text" name="noticeTitle" value="${requestScope.noticeTitle}" required/></td>
			</tr>
			<tr>
				<td>通知内容：</td>
				<td><textarea rows="10" cols="60" name="noticeContent" required>${requestScope.noticeContent}</textarea></td>
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