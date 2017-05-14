// 按钮冷却倒计时
var wait = 0;
//获取验证码
function getVerificationCode(){
	if(wait<=60&&wait>0)
		return;
	var phone = $("#phone").val();
	if(phone!=''&&phone!=null){
		var r=$.ajax({
			type : 'post',
			url : ctx+'/web/code/smsCode',
			data : {
				'phone' : phone },
			success : function(result){
				var json = eval("(" + result + ")");
				var status = json["status"];
				if (status == "0") {
					alert("验证码已发送！");
					freezbtn(60);
				}
			}
		})
	}else {
		alert("手机号码不能为空");
	}
}

//验证短信验证码
function validateCode(){
	var valitationCode = $('#valicode').val();
	if(valitationCode!=''&&valitationCode!=null){
		var r=$.ajax({
			type : 'post',
			url : ctx+'/web/code/checkSmsCode',
			data : {
				'smsCode' : valitationCode },
			success : function(result){
				var json = eval("(" + result + ")");
				var status = json["status"];
				if (status == "0") {
					$('#resetpwd').submit();
				}
			}
		})
	}else {
		alert("验证码不能为空");
	}
}
		

freezbtn = function(time){
	wait = time;
	if(wait<=60 && wait>0){
		$("#sendSMS").css("background","RGB(164,164,164)");
		$("#sendSMS").html('('+wait+')秒后重新获取');
		$("#sendSMS").attr("disabled",true);
		wait--;
		setTimeout(function(){
			freezbtn(wait);
		},1000);
	}else{
		$("#sendSMS").css("background","RGB(239,46,47)");
		$("#sendSMS").html('获取短信验证码');
		$("#sendSMS").attr("disabled",false);
	}
}

