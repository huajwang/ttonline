<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/common/public.jsp"%>
<title>消息-欢乐拼</title>
<style type="text/css">
   .returnup{
			display: none;
		}
   </style>
</head>
<body style="padding:0;margin:0;background-color: #fff;">
	<div class="news_top">
		<img class="news_img" src="${ctx}/groupon/assets/img/pint/jj1.png"/>
		<div class="news_wemzi">
		    <div class="news_wemzi_shang">
		       <span style="color:RGB(18,18,18);">欢乐拼</span>
		       <span style="color:RGB(205,24,23);">官方公告</span>
		    </div>
		    <div class="news_wemzi_xia" style="color:RGB(176,176,176);">5月27日</div>
		</div>
    </div>
    
	    <div class="news_conter">
	         <span>尊敬的用户：近期有不法分子冒充我公司员工</span>
		     <span>，已缴纳首页推广费用为名，通过电话微信骗</span>
		     <span>取客户资金。如遇此类事件，请拒绝一切方式</span>
	         <span>的转账付款，并记住对方详细信息(入姓名、</span>
	         <span>电话号码等)后，直接拨打拓意客商城电话(0592</span>
	         <span>-2069229)进行核实和举报.</span>    
	    </div>
	    <div class="news_xian"></div>
	    
	     <!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>
	  

     
	
</body>
</html>