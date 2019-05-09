<%@page import="ujs.mlearn.entity.Test"%>
<%@page import="ujs.mlearn.entity.Teacher"%>
<%@page import="ujs.mlearn.entity.Course"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑习题</title>
</head>
<script type="text/javascript">
	var sel=66;
</script>
<script type="text/javascript">
	function addOneSelection() {
		var table = document.getElementById("selections");
		var lastTestOption = document.getElementById("testOption"+(table.rows.length-3)).value;
		if(lastTestOption == null || lastTestOption =="") {
			alert("请先输入最后一个选项");
			return;
		}
		
		if(document.getElementById("type").value == '0') {
			if(table.rows.length > 3) {
				alert("判断题只能有两个选项");
				return;
			}
		}
		var tr = document.createElement("tr");
		tr.innerHTML = "<td></td><div><td>&#"+sel+":<input type='text' name='testOption' id='testOption"+(table.rows.length-2)+"' style='width: 330px' required></td></div>";
		var lastRow = table.rows[table.rows.length-1-1];
		lastRow.parentNode.insertBefore(tr, lastRow);
		sel++;
	}
	function delLastSelection() {
		var table = document.getElementById("selections");
		if(table.rows.length > 3) {
			var lastRow = table.rows[table.rows.length-1-2];
			lastRow.parentNode.removeChild(lastRow);
			sel--;
		}
	}
	function check() {
		var type = document.getElementById("type").value;
		var ans = document.getElementById("testAnswer").value.split('');
		for(var i = 0; i < ans.length; i++) {
			if(ans[i].charCodeAt()>=sel || ans[i].charCodeAt()<65) {
				alert("答案只能包含选项字母！");
				return false;
			}
		}
		var table = document.getElementById("selections");
		var lastTestOption = document.getElementById("testOption"+(table.rows.length-3)).value;
		if(lastTestOption == null || lastTestOption =="") {
			alert("请先输入最后一个选项");
			return false;
		}
		if(type == '0') {
			if(table.rows.length > 4) {
				alert("判断题只能有两个选项");
				return false;
			}
			if(ans[0] != "对" || ans[1] != "错") {
				alert("判断题选项必须是：A对，B错！");
				return false;
			}
			if(ans.length > 1 || (ans[0]!="A" && ans[0]!="B")) {
				alert("判断题答案只能有一个，且必须是A或B！");
				return false;
			}
		} else if(type == '1') {
			if(ans.length > 1) {
				alert("单选题答案只能有一个！");
				return false;
			}
		} else if(type == '2') {
			if(ans.length < 2) {
				alert("多选题答案不能只有一个！");
				return false;
			}
		}
		return true;
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
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form
		action="${pageContext.request.contextPath}/TestManageServlet?operation=modTest"
		method="post" onSubmit="return check()">
		<%
			Test test = (Test) request.getAttribute("test");
			int other1Int = -1;
			int other2Int = -1;
			String other1String = "";
			String other2String = "";
			if(test.getType() == Test.PAN_DUAN) {
				other1Int = Test.DAN_XUAN;
				other1String = "单选题";
				other2Int = Test.DUO_XUAN;
				other2String = "多选题";
			} else if(test.getType() == Test.DAN_XUAN) {
				other1Int = Test.PAN_DUAN;
				other1String = "判断题";
				other2Int = Test.DUO_XUAN;
				other2String = "多选题";
			} else if(test.getType() == Test.DUO_XUAN) {
				other1Int = Test.PAN_DUAN;
				other1String = "判断题";
				other2Int = Test.DAN_XUAN;
				other2String = "单选题";
			}
			request.setAttribute("other1Int", other1Int);
			request.setAttribute("other1String", other1String);
			request.setAttribute("other2Int", other2Int);
			request.setAttribute("other2String", other2String);
		%>
		<table>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 206px">
						<option><%=test.getTeacherNumber()%>:<%=test.getTeacherName() %></option>
						<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							for(Teacher teacher : teachers) {
								if(!teacher.gettId().equals(test.getTeacherNumber())) {
						%>
							<option><%=teacher.gettId()%>:<%=teacher.getName() %></option>
						<%		}
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>选择课程：</td>
				<td>
					<select name="course" id="course" style="width: 206px" required>
						<option><%=test.getCourseName() %></option>
						<%
							List<Course> courses = (List<Course>) request.getAttribute("courses");
							for(Course course : courses) {
								if(course.getCourseID() != test.getCourseID()) {
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
				<td>习题类型：</td>
				<td><select name="type" id="type" style="width: 206px">
						<option value="${test.getType()}">${test.getTypeString()}</option>
						<option value="${requestScope.other1Int}">${requestScope.other1String}</option>
						<option value="${requestScope.other2Int}">${requestScope.other2String}</option>
				</select></td>
			</tr>
			<tr>
				<td>习题题目：</td>
				<td><input name="testContent" id="testContent" value="<%=test.getTestContent() %>" style="width: 517px"></td>
			</tr>
		</table>
		<table id = "selections">
			<%
				String[] testOptions = test.getTestOption().trim().split(";");
				for(int i = 0; i < testOptions.length; i++) {
					if(i == 0) {
			%>
			<tr>
				<td>习题选项：</td>
				<td>A:<input type="text" name="testOption" id="testOption<%=i%>" value="<%=testOptions[i]%>" style="width: 330px" required></td>
				<td>
					<input type="button" value="添加选项" onclick="addOneSelection()"/>
					&nbsp&nbsp
					<input type="button" value="删除选项" onclick="delLastSelection()"/>
				</td>
			</tr>
			<%
					} else {
			%>
			<tr>
				<td></td>
				<td><script type="text/javascript">
					document.write("&#"+sel);
					sel++
				</script>:<input type="text" name="testOption" id="testOption<%=i%>" value="<%=testOptions[i]%>" style="width: 330px" required></td>
			</tr>
			<%		}
				}
			%>
			<tr>
				<td>习题答案：</td>
				<td><input type="text" id="testAnswer" name="testAnswer" value="<%=test.getTestAnswer() %>" placeholder="AB······" style="width: 330px" required></td>
			</tr>
			<tr>
				<td><input type="hidden" name="testID" value="${test.testID }"></td>
				<td><input type="submit" value="重新提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>