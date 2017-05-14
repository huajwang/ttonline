<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心-欢乐拼</title>
</head>
<style type="text/css">
    
</style>
<body style="margin:0;padding:0;background-color: #fff;">
<img id="fan_hui" onclick="window.location.href='${ctx}/groupon/launch/helpcenter'" style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
     <div class="gestion_sale_project"><strong>项目管理相关问题</strong></div>
	 <div class="gestion_sale_status">如何进行提现操作？</div>
	      <div class="gestion_sales_text">
	    	 <span>在“共享家商城”我的中心页面，点击“账户余额" ，在里面输入金额，点击“提现”按钮。</span><br/>   
         </div>
     <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何进行提现操作？</div>
         <div class="gestion_sales_text">
	        <span>在欢乐拼主页点击发起，之后点击我的项目，在该页面就可以查看验证结果是否成功。</span><br/>
	      </div> 
	     
     <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何在微信里将项目分享到朋友圈</div>
          <div class="gestion_sales_text">
	        <span>点击微信页面右上角的“三个小点”跳出弹窗，接着点击“分享到到朋友圈”。</span><br/>
	      </div> 
	 
	  <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何绑定银行卡？</div>
          <div class="gestion_sales_text">
	        <span>在“共享家商城”我的中心页面点击“账户管理”，之后在该页面添加银行卡。</span><br/>
	      </div>   
	         
	  <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何修改项目金额？</div>
          <div class="gestion_sales_text">
	        <span>在“欢乐拼”页面点击页面底端“项目管理”图标，再弹出页面再次点击“修改金额”，来执行修改。</span><br/>
	      </div>  
	      
	   <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何修改项目时间？</div>
          <div class="gestion_sales_text">
	        <span>在“欢乐拼”页面点击页面底端“项目管理”图标，再弹出页面再次点击“间修改时间，来执行修改。</span><br/>
	      </div>  
	    
	   <div class="gestion_sale_status" style="border-top:1px solid RGB(239,239,239);margin-top:10px;">如何发布项目更新？</div>
          <div class="gestion_sales_text">
	        <span>在“欢乐拼”页面点击页面底端理“项目管理”图标，再弹出页面再次点击“编辑项目”，来执行修改。</span><br/>
	      </div>              
</body>
</html>