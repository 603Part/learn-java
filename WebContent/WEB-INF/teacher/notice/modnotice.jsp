<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body
	style="background-image: url(${pageContext.request.contextPath}/pic/body.jpg)">
	<form
		action="${pageContext.request.contextPath}/WebNoticeServlet?action=updateNotice"
		method="post">
		<table>
			<tr>
				<td>通知标题：</td>
				<td><input type="text" name="noticeTitle" id="noticeTitle" value="${notice.noticeTitle}" /></td>
			</tr>
			<tr>
				<td>通知内容：</td>
				<td><textarea rows="10" cols="40" name="noticeContent" id="noticeContent">${notice.noticeContent}</textarea>
			</tr>

			<tr>
				<td colspan="3" align="center"><input type="submit" value="提交" />
					<input type="reset" /></td>
			</tr>
		</table>
		<input type="hidden" name="courseID" value="${notice.courseID}">
		<input type="hidden" name="noticeID" value="${notice.noticeID}">
	</form>
</body>
</html>