<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>修改时间</title>
</head>
<script type="text/javascript">
	var id="${id}";//项目主键
	var createTime="${createTime}";//创建时间
</script>
<style type="text/css">
  
    
</style>
<body style="background-color: #fff;margin:0;padding:0;">
      <div class="Modify_time_top"></div>
      <div class="stop_date" style="background-color: #fff;border:1px solid RGB(233,233,233);">
              <div class="stop_wenzi">
		            <div class="stop_wenzi_left"><strong>截止日期</strong></div>
		            <div class="stop_wenzi_right"><span id="endTime" style="color:RGB(216,216,216);margin-left: 0.9em;">${endTime }</span> <span style="color:RGB(216,216,216)">共</span><span id="day"style="color:RGB(192,47,54);">7</span><span style="color:RGB(216,216,216)">天</span></div>          
             </div>
             <div class="gun_tiao">
                    <div class="gun_tiao_left">1天</div> 
               	    
			         	<div class='slider-example'>
				      		<div class="well">
								<input id="ex1" data-slider-id='ex1Slider' type="text" data-slider-min="1" data-slider-max="30" data-slider-step="1" data-slider-value="${dayNum }"/>
							</div>
					
			      	   </div>
      			    
                   <div class="gun_tiao_right">30天</div> 
               	 
             </div>
      </div>
      
      <div class="Modify_time_btn1">保存</div>
       <div class="Modify_time_btn2">返回</div>
</body>
  <!--滑动 -->
   <script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.js"></script>
   <script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.min.js"></script>
    <script type='text/javascript'>
    	var r=true;
    	$(document).ready(function() {
    		/* Example 1 */
	    	$('#ex1').slider({
	          	formatter: function(value) {
					$('#day').html(value+"");
	          		if(r){
	          			r=false;
	          		}
	          		else
	        			$("#endTime").html(CurentTime(value));//截止时间
	            	return '';
	          	}
	        });
    		
	    	//保存
			$(".Modify_time_btn1").click(function(){
				$.post(ctx + '/groupon/api/project/updateProjiectByEndTime', {
					id : id,
					endTime:$("#endTime").html()
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
			$(".Modify_time_btn2").click(function(){
				window.location.href = ctx + "/groupon/project/projectedit?id="+id;			
			});

    	});
    	
    	function CurentTime(i) {
    		var now   =   new   Date(createTime.replace(/-/g,   "/"));  
    		//var now = new Date();
    		if (i != null)
    			now.setDate(now.getDate() + i);
    		var year = now.getFullYear(); //年
    		var month = now.getMonth() + 1; //月
    		var day = now.getDate();
    		var hh = now.getHours(); //时
    		var mm = now.getMinutes(); //分
    		var clock = year + "-";
    		if (month < 10)
    			clock += "0";
    		clock += month + "-";
    		if (day < 10)
    			clock += "0";
    		clock += day + " ";
    		if (hh < 10)
    			clock += "0";
    		clock += hh + ":";
    		if (mm < 10)
    			clock += '0';
    		clock += mm;
    		return (clock);
    	}
    </script>
</html>