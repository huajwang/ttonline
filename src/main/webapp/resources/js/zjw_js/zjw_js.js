var _taidu="";
$(function(){
	$('#pinj').on('click','li',function(){
		var s = $(this).attr('id');
		if(s=='1'){
			$('#1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#2').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='2') {
			$('#1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='3') {
			$('#1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='4') {
			$('#1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#4').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else{
			$('#pinj li').css('background',"url('../resources/images/xinixin2.png') no-repeat");
		}
	});
	
	
	
	
	$('.fuwu').on('click','li',function(){
		var s = $(this).attr('id');
		if(s=='fuwu1'){
			$('#fuwu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu2').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='fuwu2') {
			$('#fuwu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='fuwu3') {
			$('#fuwu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#fuwu5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else if (s=='fuwu4') {
			$('#fuwu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu4').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#fuwu5').css('background',"url('../resources/images/xininx.png') no-repeat");
		}else{
			$('.fuwu li').css('background',"url('../resources/images/xinixin2.png') no-repeat");
		}
	});
	
	
	
	$('.taidu').on('click','li',function(){
		var s = $(this).attr('id');
		if(s=='taidu1'){
			$('#taidu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu2').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu5').css('background',"url('../resources/images/xininx.png') no-repeat");
			_taidu="1";
		}else if (s=='taidu2') {
			$('#taidu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu3').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu5').css('background',"url('../resources/images/xininx.png') no-repeat");
			_taidu="2";
		}else if (s=='taidu3') {
			$('#taidu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu4').css('background',"url('../resources/images/xininx.png') no-repeat");
			$('#taidu5').css('background',"url('../resources/images/xininx.png') no-repeat");
			_taidu="3";
		}else if (s=='taidu4') {
			$('#taidu1').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu2').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu3').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu4').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			$('#taidu5').css('background',"url('../resources/images/xininx.png') no-repeat");
			_taidu="4";
		}else{
			$('.taidu li').css('background',"url('../resources/images/xinixin2.png') no-repeat");
			_taidu="5";
		}
	});
})