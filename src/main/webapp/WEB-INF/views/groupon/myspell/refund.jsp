<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/common/public.jsp"%>
<title>退货详情</title>
</head>
<style type="text/css">
   
</style>
<body style="margin:0;padding:0;background-color: #fff;">
      <div class="return_reason">退货原因</div>
       <div class="reason_what" style=""><textarea id="reason" class="reason_what_input"  placeholder="请如何告知退货原因，具体原因请和卖家联系." ></textarea></div>
       <div class="reason_btn1" id="makesure">确定</div>
       <div onclick="self.location=document.referrer;"  class="reason_btn2">返回</div>
       <input type="hidden" id="orderid" value="${orderid}"/>
</body>
<script type="text/javascript">
$('#makesure').click(function(){
	$.ajax({
		type : 'post',
		url : ctx + "/groupon/personal/applyReturn",
		data : {
			'id' : $('#orderid').val(),
			'reason' : $('#reason').val()
		},
		success : function(result) {
			var json = eval("(" + result + ")");
			if(json['status']=='0')	{
				self.location=document.referrer;
			}		
		}
	});
	
});
</script>
</html>