<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加作业</title>
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
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/HomeworkManageServlet?operation=addHomework"
		method="post">
		<table>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 156px" required>
						<c:forEach items="${teachers}" var="teacher">
							<option>${teacher.tId}:${teacher.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>选择课程：</td>
				<td>
					<select name="courseName" id="course" style="width: 156px" required>
						<c:forEach items="${courses}" var="course">
							<option>${course.courseName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>作业标题：</td>
				<td><input name="hwTitle" style="width: 500px" required></td>
			</tr>
			<tr>
				<td>作业要求：</td>
				<td><textarea name="hwContent" rows="20" cols="80" required></textarea></td>
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