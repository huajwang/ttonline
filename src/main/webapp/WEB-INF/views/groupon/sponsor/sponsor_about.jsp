<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/common/public.jsp"%>
<title>关于欢乐拼</title>
</head>
<style>
		body{
			margin: 0;
			padding: 0;
			background-color: rgb(245,246,250);
		}
		
		</style>
<body>

		<div class="abouttop">
			<img class="abouttopim" src="${ctx}/groupon/assets/img/pint/jj1.png" />
			<div class="abouttoph"><strong>拓意客电子科技有限公司</strong></div>			
		</div>
		<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:-1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
		<div class="aboutreco">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;厦门拓意客网络科技有限公司于 2015 年 9 月成立。同年，基于社交圈的、面向广大网民日常生活的“欢乐拼”正式上线。<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢乐拼是中国最具影响力的、基于社交圈的平台。<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢乐拼上的项目大多聚焦在用户的日常生活领域，如一次私房菜的分享、一次说走就走的旅行、一场梦想中的画展等。这些拼团项目大多只是发起人的小愿望，支持者的支持金额通常较小，不会对支持者的生活带来很大的影响，容易等到朋友间的反馈和支持，所以更容易召集大家的参与。除此之外，我们还支持其他内容的拼团项目。<br>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在这里，不但聚集着众多有理想的人群，还聚集着众多年轻的、富有创新精神的、有情怀的创业者，正因为这些项目与生活密切相关，所以欢乐拼更适合在微信等具有社交属性的平台上进行传播。在欢乐拼，我们提供包括尝鲜预售（农鲜产品、私房菜等），30万+个项目，为平台 4000万+的注册用户提供了更多选择、更多低价、更多创新的个性化定制产品和服务。 
		</div>
		
		<div class="aboutphon aboutcol">
			<span>联系我们</span>
			<span style="font-size: 13px;">点击直接拨打电话</span>
		</div>
		<div class="aboutkf">
			客服电话：0592-2069229
		</div>
	</body>
</html>