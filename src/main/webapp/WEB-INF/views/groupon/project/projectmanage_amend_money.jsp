<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>修改金额</title>
<script type="text/javascript">
	var id = "${id}";//项目主键
	$(function(){
		//保存
		$(".Modify_the_amount_btn1").click(function(){
			$.post(ctx + '/groupon/api/project/updateProjiectByAmount', {
				id : id,
				amount:$("#amount").val()
			}, function(result) {
				var json = JSON.parse(result);
				if (json["status"] == "0") {
					alert("修改成功！");
					window.location.href = ctx + "/groupon/project/projectedit?id="+id;
				}
				else{
					alert("修改失败！请与管理员联系");
				}
			});
		});
		//返回
		$(".Modify_the_amount_btn2").click(function(){
			window.location.href = ctx + "/groupon/project/projectedit?id="+id;			
		});
	});
</script>
</head>
<style type="text/css">
</style>
<body style="background-color: #fff; margin: 0; padding: 0;">
	<div class="Modify_the_amount_top"></div>
	<div class="Modify_the_amount_money" style="background-color: #fff;">
		<div class="Modify_the_amount_card">
			<strong class="Modify_the_amount_id">目标金额</strong>
		</div>
		<div class="Modify_the_amount_input_shuru">
			<input id="amount" value="${amount }" class="Modify_the_amount_input_jihuo"
				type="text" placeholder=" &nbsp;填写目标金额" />
		</div>
		<div class="Modify_the_amount_yuan">
			<strong>元</strong>
		</div>
	</div>

	<div class="Modify_the_amount_btn1">保存</div>
	<div class="Modify_the_amount_btn2">返回</div>
</body>
</html>