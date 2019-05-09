<%@page import="ujs.mlearn.entity.CourseMaterial"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑文件</title>
</head>
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
<body  style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form action="${pageContext.request.contextPath}/ResourceManageServlet?operation=updateResource" 
		method="post">
		<table>
		<%
			CourseMaterial resource = (CourseMaterial) request.getAttribute("resource");
			String resName = resource.getResTitle().substring(0, resource.getResTitle().lastIndexOf("."));
			String type = resource.getResTitle().substring(resource.getResTitle().lastIndexOf("."), resource.getResTitle().length());
		%>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 156px" required>
						<option><%=resource.getTeacherNumber()%>:<%=resource.getTeacherName() %></option>
						<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							for(Teacher teacher : teachers) {
								if(!teacher.gettId().equals(resource.getTeacherNumber())) {
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
					<select name="course" id="course" style="width: 156px" required>
						<option><%=resource.getCourseName() %></option>
						<%
							List<Course> courses = (List<Course>) request.getAttribute("courses");
							for(Course course : courses) {
								if(course.getCourseID() != resource.getCourseID()) {
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
				<td>资源名称：</td>
				<td><input type="text" name="resTitle" id="resTitle" value=<%=resName %> style="width: 150px" required></td>
			</tr>
			<tr>
				<td><input type="hidden" name="resID" value=<%=resource.getResID()%> /></td>
				<td><input type="submit" value="重新提交" /></td>
				<td><input type="hidden" name="type" value=<%=type%> /></td>
			</tr>
			<tr>
				<td></td>
				<td><font color="red"><label id="message">${requestScope.message}</label></font></td>
			</tr>
		</table>
	</form>
</body>
</html>