<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>功能列表</div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>信息管理
    </div>
    	<ul class="menuson">
       <li><cite></cite>
       <a href="${pageContext.request.contextPath }/WebInfoServlet?action=goShowPage"
			target="rightFrame">我的信息</a></li>
<cite></cite>
        </ul>    
    </dd>
        
    
    <dd>
    <div class="title">
    <span><img src="images/leftico02.png" /></span>课程管理
    </div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebCourseServlet?action=goShowPage"
			target="rightFrame">开授新课</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebCourseServlet?action=findMyCourse" 
			target="rightFrame">我的课程</a><i></i></li>
        
        </ul>     
    </dd> 
    
    
    <dd><div class="title"><span><img src="images/leftico03.png" /></span>通知管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebNoticeServlet?action=goShowPage"
			target="rightFrame">发布课程通知</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebNoticeServlet?action=findMyCourseList"
			target="rightFrame">我的课程通知</a><i></i></li>
    </ul>    
    </dd>  
    
    
    <dd><div class="title"><span><img src="images/leftico04.png" /></span>作业管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebHomeworkServlet?action=goShowPage"
			target="rightFrame">发布课程作业</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebHomeworkServlet?action=findMyCourse"
			target="rightFrame">我的课程作业</a><i></i></li>
    </ul>
    
    </dd>   
    
    <dd><div class="title"><span><img src="images/leftico04.png" /></span>习题管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebTestServlet?action=goShowPage"
			target="rightFrame">添加课程习题</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebTestServlet?action=findMyCourse"
			target="rightFrame">我的课程习题</a><i></i></li>
    </ul>
    
    </dd>   
    <dd><div class="title"><span><img src="images/leftico04.png" /></span>师生交流</div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebBbsServlet?action=findQuestion"
			target="rightFrame">查看问题</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebBbsServlet?action=showReplyList"
			target="rightFrame">我的回答</a><i></i></li>
    </ul>
    
    </dd>   
     <dd><div class="title"><span><img src="images/leftico04.png" /></span>课程资源</div>
    <ul class="menuson">
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebMaterialServlet?action=goShowPage"
			target="rightFrame">上传课程资料</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath }/WebMaterialServlet?action=findMyCourseList"
			target="rightFrame">我的课程资料</a><i></i></li>
    </ul>
    
    </dd>  
    
    </dl>
</body>
</html>
