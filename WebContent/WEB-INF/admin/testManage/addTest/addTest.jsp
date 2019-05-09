<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加习题</title>
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
			var A = document.getElementById("testOption0").value;
			var B = document.getElementById("testOption1").value;
			if(A != "对" || B != "错") {
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
		action="${pageContext.request.contextPath}/TestManageServlet?operation=addTest"
		method="post" onSubmit="return check()">
		<table>
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 206px" required>
						<c:forEach items="${teachers}" var="teacher">
							<option>${teacher.tId}:${teacher.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>选择课程：</td>
				<td>
					<select name="course" id="course" style="width: 206px" required>
						<c:forEach items="${courses}" var="course">
							<option>${course.courseName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>习题类型：</td>
				<td><select name="type" id="type" style="width: 206px">
						<option value="0">判断题</option>
						<option value="1">单选题</option>
						<option value="2">多选题</option>
				</select></td>
			</tr>
			<tr>
				<td>习题题目：</td>
				<td><input name="testContent" id="testContent" style="width: 500px" required></td>
			</tr>
		</table>

		<table id = "selections">
			<tr>
				<td>习题选项：</td>
				<td>A:<input type="text" name="testOption" id="testOption0" style="width: 330px" required></td>
				<td>
					<input type="button" value="添加选项" onclick="addOneSelection()"/>
					&nbsp&nbsp
					<input type="button" value="删除选项" onclick="delLastSelection()"/>
				</td>
			</tr>
			<tr>
				<td>习题答案：</td>
				<td><input type="text" id="testAnswer" name="testAnswer" placeholder="AB······" style="width: 330px" required></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="添加">
					&nbsp&nbsp
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>