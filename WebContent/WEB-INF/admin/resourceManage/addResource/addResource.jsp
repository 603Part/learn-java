<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function addOneFile(obj) {
		document.getElementById("msg").innerHTML="";
		//1 找到表格
		var table = document.getElementById("one");
		//2 判断是否已经选择文件
		var file = document.getElementById("file"+(table.rows.length-5)).value;
		if(file == null || file == "") {
		 	alert("请先选择文件");
		 	return;
		}
		//3 创建出想要添加的行
		var tr = document.createElement("tr");
		tr.innerHTML = "<td></td><td><input type='file' name='fileURL' id='file"+(table.rows.length-4)+"' style='width: 215px' accept='aplication/zip'/></td><td><input type='button' value='删除文件' onclick='delOneFile(this)'  /></td>";
		//4 找到表格最后一行
		var lastRow = table.rows[table.rows.length-1-1];
		//5 insertBefore
		lastRow.parentNode.insertBefore(tr, lastRow);
	}
  	//参数: 要删除行中的删除按钮对象
	function delOneFile(obj) {
		document.getElementById("msg").innerHTML="";
		obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
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
		action="${pageContext.request.contextPath}/ResourceManageServlet?operation=addResource"
		method="post" enctype="multipart/form-data">
		<table id="one">
			<tr>
				<td>选择教师：</td>
				<td>
					<select name="teacher" id="teacher" onchange="findCourse(this.value)" style="width: 150px" required>
						<c:forEach items="${teachers}" var="teacher">
							<option>${teacher.tId}:${teacher.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>选择课程：</td>
				<td>
					<select name="course" id="course" style="width: 150px" required>
						<c:forEach items="${courses}" var="course">
							<option>${course.courseName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>上传资源包：</td>
				<td><input type="file" name="fileURL" id="file0"
					accept=".zip" style="width: 215px" required></td>
				<td><input type="button" value="添加文件"
					onclick="addOneFile(this)" /></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="提交" />
					<input type="reset" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td><font color="red"><label id="msg">${requestScope.msg}</label></font></td>
			</tr>
		</table>
	</form>
</body>
</html>