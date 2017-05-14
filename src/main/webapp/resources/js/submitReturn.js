function doApply(){
		var orderDetailId = $("#orderDetailId").val();
		var requestType = $("input[type='radio']:checked").val();
		var cancelReason = $("#reason").val();
		//var orderId=$("#orderId").val();
		//var gId = $("#gId").val();
		//var propertyId = $("#propertyId").val();
		var picStr = "";
		if(requestType==null){
			alert("请选择退换货方式！");
			return;
		}
		if(picArr.length>0)
			picStr = picArr[0];
		for(var i=1;i<picArr.length;i++)
		{
			picStr = picStr + ','+ picArr[i];//picArr[]图片名称存储数组，定义在uploader.js中
		}
		var r = $.ajax({
	        type: "post",
	        url: ctx + "/member/setReturnRecord",
	        async: false,
	        data: {
	        	//"orderId" : orderId,
	        	//"gId" : gId,
	        	//"propertyId" : propertyId,
	        	"picStr" : picStr,
	        	"orderDetailId" : orderDetailId,
	        	"requestType" : requestType,
	        	"cancelReason" : cancelReason
	        }
		 }).responseText;
		var json=eval("("+r+")");
		if(json["result"]=="0"){
			window.location.href=ctx+"/member/submit/"; 
		}else{
			alert("如果您要换货，请联系我们售后客服QQ或电话：0592-2069229！");
			window.history.go(-1);
		};
	}