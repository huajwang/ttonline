<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/common/public.jsp"%>
<!-- 添加拼团产品 -->
<title>发布产品</title>
<script type="text/javascript">
	function test(){
		
	}
</script>
</head>
<style type="text/css">
     
</style>
<body>
   <div  class="issued_money" style="background-color: #fff;">
       <div class="issued_money_card"><strong class="issued_money_id">拼售价格</strong></div>
       <div class="issued_money_input_shuru"><input class="issued_money_input_jihuo" type="text"  placeholder=" &nbsp;填写金额"/></div>	
       <div class="issued_money_yuan"><strong>元</strong></div>
   </div>
   <div class="issued_what" style=""><textarea  class="issued_what_input"  placeholder=" &nbsp;填写产品详情" ></textarea></div>
   <div class="uploading_img">
	   <img  style="width:25%;margin-top:15px;margin-left:20px;margin-bottom:5px;" src="${ctx}/groupon/assets/img/pint/mm1.png"/>
   </div>
   
   <div  class="issued_money" style="background-color: #fff;">
       <div class="issued_money_card"><strong class="issued_money_id">数量限制</strong></div>
       <div class="issued_money_input_shuru"><input class="issued_money_input_jihuo" type="text"  placeholder=" &nbsp;默认不限制"/></div>	
       <div class="issued_money_yuan"><strong>份</strong></div>
   </div>
   <div class="issued_btn" onclick="test()">添加保存</div>
   
</body>
</html>