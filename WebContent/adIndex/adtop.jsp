<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>移动学习平台管理员端</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>
<script type="text/javascript">
// $(function(){	
// 	//顶部导航切换
// 	$(".nav li a").click(function(){
// 		$(".nav li a.selected").removeClass("selected")
// 		$(this).addClass("selected");
// 	})	
// })	
</script>


</head>

<body style="background:url(images/topbg.gif) repeat-x;">

 
 <div
		style="height: 50px; font-size: 40px; width: 400px; margin-left:500px;margin-top:10px;color: #000000;">移动学习平台管理员端</div>
 
    
            
    <div class="topright">    
    <ul>
     <li style="margin-top: -12px;"><a href="adright.jsp" target="rightFrame">首页</a></li>
     <li style="margin-top: -12px;"><a href="${pageContext.request.contextPath}/AdminUserServlet?operation=logout"
				target="all">退出</a></li>
    </ul>
   
    </div>
</body>
</html>