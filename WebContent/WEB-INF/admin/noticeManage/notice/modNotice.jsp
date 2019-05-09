<%@page import="ujs.mlearn.entity.Notice"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="java.util.List"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import= "ujs.mlearn.entity.Notice"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>编辑通知</title>
</head>
<script type="text/javascript">
	function change(type) {
		if(type == "课程通知") {
        	document.getElementById("teacher").disabled=false;
        	document.getElementById("course").disabled=false;
        } else if(type == "全体通知") {
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
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg">

	<form
		action="${pageContext.request.contextPath}/NoticeManageServlet?operation=modNotice"
		method="post">
		<table>
			<tr>
				<td>通知标题：</td>
				<td><input type="text" name="noticeTitle"
					value="${notice.noticeTitle}" style="width: 200px" /></td>
			</tr>
			<tr>
				<%
					Notice notice = (Notice) request.getAttribute("notice");
					if(notice.getTypeString() == "课程通知") {
						request.setAttribute("anotherType", "全体通知");
					} else if(notice.getTypeString() == "全体通知") {
						request.setAttribute("anotherType", "课程通知");
					}
				%>
				<td>通知类型：</td>
				<td>
					<select name="noticeType" id="noticeType" onchange="change(this.value)" style="width: 206px">
						<option>${notice.typeString}</option>
						<option>${requestScope.anotherType}</option>
					</select>
				
				</td>
			</tr>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 206px">
						<%if(notice.getType() != Notice.ALL) {%>
							<option><%=notice.getTeacherNumber()%>:<%=notice.getTeacherName() %></option>
						<%} %>
						<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							for(Teacher teacher : teachers) {
								if(!teacher.gettId().equals(notice.getTeacherNumber())) {
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
					<select name="course" id="course" style="width: 206px">
						<%if(notice.getType() != Notice.ALL) {%>
							<option><%=notice.getCourseName() %></option>
						<%} %>
						<%
							List<Course> courses = (List<Course>) request.getAttribute("courses");
							for(Course course : courses) {
								if(course.getCourseID() != notice.getCourseID()) {
						%>
							<option><%=course.getCourseName() %></option>
						<%
								}
							}
						%>
					</select>
				</td>
			</tr>
			<script type="text/javascript">
				if(parseFloat(<%=notice.getType()%>) == <%=Notice.ALL%>) {
					document.getElementById("teacher").disabled=true;
		        	document.getElementById("course").disabled=true;
				}
			</script>
			<tr>
				<td>通知内容：</td>
				<td><textarea rows="10" cols="80" name="noticeContent" required>${notice.noticeContent}</textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="重新提交" />
			</tr>
			<tr>
				<td></td>
				<td><font color="red">${requestScope.message}</font></td>
			</tr>
			<tr>
				<td><input type="hidden" name="noticeID"
					value="${notice.noticeID}"></td>
			</tr>
		</table>
	</form>
</body>
</html>