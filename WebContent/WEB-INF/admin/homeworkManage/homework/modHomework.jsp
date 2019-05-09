<%@page import="ujs.mlearn.entity.Homework"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
a {
	text-decoration: NONE
}
</style>
<title>编辑作业</title>
<script type="text/javascript">
    var xmlhttp = null;
    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else { // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    function findCourse(teacher) {
    	url = "TeacherQueryCourseServlet";
        xmlhttp.open("post", url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("teacher="+teacher);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4) { 
            	var jsonData =  xmlhttp.responseText;
            	var jsonObj = eval("{"+jsonData+"}");
            	document.getElementById("course").options.length = 0;
            	for(var i=0;i<jsonObj.length;i++){ 
            		var optionObj = new Option(jsonObj[i].courseName, jsonObj[i].courseName);
            		document.getElementById("course").options.add(optionObj); 
            	}
            }
        }
    }
</script>
</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form
		action="${pageContext.request.contextPath}/HomeworkManageServlet?operation=modHomework"
		method="post">
		<table>
			<%
				Homework homework = (Homework) request.getAttribute("homework");
			%>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 206px" required>
						<option><%=homework.getTeacherNumber()%>:<%=homework.getTeacherName() %></option>
						<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							for(Teacher teacher : teachers) {
								if(!teacher.gettId().equals(homework.getTeacherNumber())) {
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
				<td>选择课程：</td>
				<td>
					<select name="course" id="course" style="width: 206px" required>
						<option><%=homework.getCourseName() %></option>
						<%
							List<Course> courses = (List<Course>) request.getAttribute("courses");
							for(Course course : courses) {
								if(course.getCourseID() != homework.getCourseID()) {
						%>
							<option><%=course.getCourseName() %></option>
						<%
								}
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>作业标题：</td>
				<td><input name="hwTitle" style="width: 655px"
					value="${homework.hwTitle}"></td>
			</tr>
			<tr>
				<td>详细要求</td>
				<td><textarea rows="20" cols="80" name="hwContent">${homework.hwContent}</textarea></td>
			</tr>
			<tr>
				<td><input type="hidden" name="hwID" value="${homework.hwID}"></td>
				<td><input type="submit" value="重新提交" /></td>
			</tr>
		</table>
	</form>

</body>
</html>