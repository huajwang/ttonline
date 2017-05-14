var dataForWeixin = {
	appId : "wx4c0e6c393432addd",
	img : "分享显示的图片",
	url : '分享地址的链接地址',
	title : '分享显示的标题',
	desc : '分享显示的描述',
	fakeid : "",
};
/**
 * 判断是否有登录
 */
function ajaxLogin() {
	var r = $.ajax({
		type : "post",
		url : ctx + "/detail/ajaxLogin",
		async : false
	}).responseText;
	var json = eval("(" + r + ")");
	if (json["status"] != "99") {
		dataForWeixin.url = window.location.href + "&userId="
				+ json["data"]["id"];
	}
}
(function() {
	var onBridgeReady = function() {
		// 发送给好友;
		ajaxLogin();
		WeixinJSBridge.on('menu:share:appmessage', function(argv) {
			WeixinJSBridge.invoke('sendAppMessage', {
				"appid" : dataForWeixin.appId,
				"img_url" : dataForWeixin.img,
				"img_width" : "120",
				"img_height" : "120",
				"link" : dataForWeixin.url,
				"desc" : dataForWeixin.desc,
				"title" : dataForWeixin.title
			}, function(res) {
				if (res["errMsg"] == "sendAppMessage:ok") {
					alert("分享成功！");
				}
			});
		});
		// 分享到朋友圈;
		WeixinJSBridge.on('menu:share:timeline', function(argv) {
			ajaxLogin();
			WeixinJSBridge.invoke('shareTimeline', {
				"img_url" : dataForWeixin.img,
				"img_width" : "120",
				"img_height" : "120",
				"link" : dataForWeixin.url,
				"desc" : dataForWeixin.desc,
				"title" : dataForWeixin.desc
			}, function(res) {
				if (res["errMsg"] == "shareTimeline:ok") {
					alert("分享成功！");
				}
			});
		});
	};
	if (document.addEventListener) {
		document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	} else if (document.attachEvent) {
		document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
		document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	}
})();