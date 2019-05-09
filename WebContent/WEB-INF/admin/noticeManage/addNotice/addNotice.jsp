<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加通知</title>
<script type="text/javascript">
        function change(obj) {
            if(obj.value == "class") {
            	document.getElementById("teacher").disabled=false;
            	document.getElementById("course").disabled=false;
            } else if(obj.value == "all") {
            	document.getElementById("teacher").disabled=true;
            	document.getElementById("course").disabled=true;
            }
        }
        
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
		action="${pageContext.request.contextPath}/NoticeManageServlet?operation=addNotice"
		method="post">
		<table>
			<tr>
				<td>通知类型</td>
				<td>
					<input type="radio" name="ra" onclick="change(this);" value="class"
						id="class" checked="checked" /><b>课程通知</b>
					<input type="radio" name="ra" onclick="change(this);" value="all"
						id="all" /><b>全体通知</b>
				</td>
			</tr>
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
					<select name="course" id="course" style="width: 156px" required>
						<c:forEach items="${courses}" var="course">
							<option>${course.courseName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>通知标题：</td>
				<td><input type="text" name="noticeTitle" value="${requestScope.noticeTitle}" style="width: 150px" required/></td>
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