<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>我的拼团</title>
<%@ include file="/common/public.jsp"%>
	<style type="text/css">
		.returnup{
			display: none;
		}
	</style>
</head>
<body style=" background-color:RGB(245,246,250);margin:0;padding:0;">
		<!--弹出模态框  -->
		<div class="bk_kuang" id="beijing"></div>
		<div class="modal_activate">
		    <div class="activate_nickname">修改账号昵称</div>
		 <input id="username" class="the_input" type="text" name="" value="${user.userName}"/>
		 <div class="line_border"></div>
		 <div class="btn_bottom">
		     <div class="btn_bottom_left" id="cancel">取消</div>
		     <div class="btn_bottom_right" id="update">确定</div>
		 </div>
		</div>
		<div class="Head_portrait">
		     <div class="Head_portrait_left">头像</div>
		     <div class="Head_portrait_right">
		         <img class="Head_portrait_right_img" src="${user.iconUrl}"/>
		     </div>
		</div>

     <div class="nickname">
         <div class="nickname_left" style="width: 50%">账号昵称</div>
         <div class="nickname_right" style="width: 50%;text-align: right;line-height: 60px;" id="portraitid">
             <div class="san_jiao">
	             <span id='nick' style="margin-right: 5%;">${user.userName}</span>
             	<img  class="san_jiao_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
             </div>
         </div>
     </div>
     
     <div class="my_shopping">
     	<img  class="my_shopping_left_img" src="${ctx}/groupon/assets/img/pint/shopping.png"/>
     	<span>我 的 购 物</span>
		<img  class="my_shopping_sj" id="myshop" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
     
     <div class="feedback">
         <img  class="feedback_left_img" src="${ctx}/groupon/assets/img/pint/helps.png"/>
         <span>帮助与反馈</span>
         <img  class="feedback_sj" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
     <div class="joy">
         <img  class="joy_left_img" src="${ctx}/groupon/assets/img/pint/joys.png"/>
         <span style="margin-left:2px;">关于欢乐拼</span>
         <img  class="joy_sj" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
     <!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>
</body>
<script type="text/javascript">
$('#portraitid').click(function(){
    $('#beijing').css("display","block");
    $('.modal_activate').css("display","block");
});
$('#cancel').click(function(){
    $('#beijing').css("display","none");
    $('.modal_activate').css("display","none");
    
});
$('#update').click(function(){
    $.ajax({
		type : 'post',
		url : ctx + "/member/saveOrUpdateUser",
		data : {
			'userName' : $('#username').val(),
		},
		success : function(result) {
			var json = eval("(" + result + ")");
			if(json['status']=='0')	{
				$('#nick').text($('#username').val());
				$('#beijing').css("display","none");
			    $('.modal_activate').css("display","none");
			}		
		}
	});
    
});
</script>

<script type="text/javascript">
   /* . 我的购物    点击 .. */
	$('.my_shopping').on('click',function(){
		var inputs = '';
		   // inputs += '<input type="hidden" name="type" value="0" />';
		jQuery('<form action="${ctx}/groupon/personal/myshopping" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	});
   
	$('.identity').on('click',function(){
		var inputs = '';
		   // inputs += '<input type="hidden" name="type" value="0" />';
		jQuery('<form action="${ctx}/groupon/launch/authentication" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	});
	

	$('.feedback').on('click',function(){
		var inputs = '';
		   // inputs += '<input type="hidden" name="type" value="0" />';
		jQuery('<form action="${ctx}/groupon/launch/helpcenter" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	});
	

	$('.joy').on('click',function(){
		var inputs = '';
		   // inputs += '<input type="hidden" name="type" value="0" />';
		jQuery('<form action="${ctx}/groupon/launch/regard" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	});
	
	
</script>

</html>