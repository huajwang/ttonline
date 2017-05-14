function doSubmit(){
	var alipay = $("#alipayAccount").val();
	var wx = $("#wxAccount").val();
	var r = $.ajax({
        type: "post",
        url: ctx + "/member/updateWallet",
        async: false,
        data: {
        	"alipay" : alipay,
        	"wx" : wx
        }
	 }).responseText;
	var json=eval("("+r+")");
	if(json["result"]=="success"){
		window.history.back(-1);
	}else{
		window.history.back(-1);
	};
}

function delWallet(one){
	if(confirm("确定删除？")){
		var delOne = one;
		var r = $.ajax({
	        type: "post",
	        url: ctx + "/member/deleteWallet",
	        async: false,
	        data: {
	        	"delOne":delOne
	        }
		 }).responseText;
		var json=eval("("+r+")");
		if(json["result"]=="success"){
			window.location.reload();
		}else{
			window.location.reload();
		};
	}
}

function withdrawal(){
	var payment = $("input[name='payment']:checked").val();//获取选中值
	var amount = $("#withdrawalAccount").val();
	var accountNo = '';
	var accountType ='';
	if(payment==null){
		alert("至少选择一种收款方式");
		return;
	}
	if(amount==""){
		alert("请输入金额");
		return;
	}
	if(payment=='zfb'){
		accountNo = $("#zfb").val();
		accountType = '1';
	}else{
		accountNo = $("#wx").val();
		accountType = '0';
	}
	var r = $.ajax({
        type: "post",
        url: ctx + "/member/doWithdrawal",
        async: false,
        data: {
        	"amount":amount,
        	"accountType":accountType,
        	"accountNo":accountNo
        }
	 }).responseText;
	var json=eval("("+r+")");
	if(json["result"]=="success"){
		window.location.reload();
	}else if(json["result"]=="failure"){
		alert("提现金额不足");
	}else{
		window.location.reload();
	};	
		
	
	
}
