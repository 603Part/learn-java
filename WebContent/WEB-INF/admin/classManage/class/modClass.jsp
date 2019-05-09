<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>编辑课程</title>
</head>

<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/ClassManageServlet?operation=modCourse"
		method="post" enctype="multipart/form-data">
		<table>
			<%
			Course course = (Course) request.getAttribute("course");
			%>
			<tr>
				<td>课程名称：</td>
				<td><input type="text" name="courseName"
					value="${course.courseName}" style="width: 150px" required/></td>
			</tr>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 156px" required>
						<option><%=course.getTeacherNumber()%>:<%=course.getTeacherName() %></option>
						<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							for(Teacher teacher : teachers) {
								if(!teacher.gettId().equals(course.getTeacherNumber())) {
						%>
							<option><%=teacher.gettId()%>:<%=teacher.getName() %></option>
						<%
								}
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>课程简介：</td>
				<td><input type="text" name="courseAbstract"
					value="${course.courseAbstract}" style="width: 150px" required/></td>
			</tr>
			<tr>
				<td>详细信息：</td>
				<td><textarea rows="10" cols="60" name="detailInfo">${course.detailInfo}</textarea></td>
			</tr>
			<tr>
				<td>课程图片：</td>
				<td><img height = "150px" width = "150px" src="${pageContext.request.contextPath}/${course.courseUrl}"></td>
			</tr>
			<tr>
				<td>重新选择：</td>
				<td><input type="file" name="uphoto" accept="image/jpg, image/jpeg, image/gif, image/png" style="width: 500px" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="提交" /> <input type="reset" /></td>
			</tr>
			<tr>
				<td></td>
				<td><font color="red">${requestScope.message}</font></td>
			</tr>
			<tr>
				<td><input type="hidden" name="courseID"
					value="${course.courseID}"></td>
				<td><input type="hidden" name="courseUrl"
					value="${course.courseUrl}"></td>
			</tr>
		</table>
	</form>
</body>
</html>