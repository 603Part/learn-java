<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

</head>


<body style="background-image: url(1.jpg);">

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    

    

    <div class="welinfo">
    <span><img src="images/sun.png" alt="天气" /></span>
    <b>欢迎回来，${sessionScope.teacher.name}老师</b>
    </div>
    
    <div class="welinfo">
    <span><img src="images/time.png" alt="时间" /></span>
    <i>您上次登录的时间：2019-4-22 15:22</i> 	
    </div>
   <div class="info"><b>click on the link below</b></div>
   
    <ul class="umlist">
    <li><a href="#">交大图书馆</a></li>
    <li><a href="https://www.baidu.com/">百度一下</a></li>  
    </ul>
    </div>
</body>
</html>
