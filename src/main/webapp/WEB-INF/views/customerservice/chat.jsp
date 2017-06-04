<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/include.rec.ftl"%>
<title>CustomerService</title>
<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/sockjs-client/1.1.2/sockjs.min.js">
	</script>
<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/stomp-websocket/2.3.3-1/stomp.min.js">
	</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	var sock = new SockJS('/ttmall/spittr');
	var stomp = Stomp.over(sock);
	stomp.connect('guest', 'guest', function(frame) {
		alert("connected")
	    stomp.subscribe("/topic/customerservice", handleSpittle);
	});
});

function handleSpittle(message) {
	$('#msg').text(JSON.parse(message.body).chatMessage)
}

</script>
</head>
<body>
	<div>
		<p id="msg"></p>
		<p>
			<input type="text" id="content" placeholder="请输入信息 ">
		</p>
		<section id="reply">确认</section>
	</div>
</body>

<script>
	$(function() {
		//提交
		$("#reply").on(
				'click',
				function() {
					var msg = $("#content").val();
					$.post(ctx + "/customerservice/ajaxReply", {
						chatMessage : msg,
					}, function(result) {
						var status = result["status"];
						if (status == "0") {
						} else {
							alert("失败！");
						}
					}, 'json');
				});
	})
</script>
</html>
