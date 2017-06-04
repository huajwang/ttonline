<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢乐拼</title>
<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/sockjs-client/1.1.2/sockjs.min.js">
	</script>
<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/stomp-websocket/2.3.3-1/stomp.min.js">
	</script>
<%@ include file="/common/public.jsp"%>
</head>
<body class="bgcolor2">
	<script>
      var sock = new SockJS('/ttmall/spittr');
      var stomp = Stomp.over(sock);
      stomp.connect('guest', 'guest', function(frame) {
        stomp.subscribe("/topic/spittlefeed", handleSpittle);
        stomp.subscribe("/user/queue/notifications", handleNotification);
      });
      
      function handleSpittle(message) {
    	  alert('handle spittle: ' + JSON.parse(message.body).message);
    	  $('#output').append("<b>Received spittle: " + JSON.parse(message.body).message + "</b><br/>");
      }
      
      function handleNotification(message) {
          alert('handle Notification: ' + JSON.parse(message.body).message);
          $('#output').append("<b>Received: " + 
              JSON.parse(message.body).message + "</b><br/>")
        }

      function sendSpittle(text) {
          stomp.send("/app/spittle", {}, 
              JSON.stringify({ 'text': text }));
        }

      </script>

	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img class="mobintimg" alt=""
			src="${ctx}/groupon/assets/img/logo/log.PNG"> <span>欢乐拼</span>
	</div>

	<!-- 顶部菜单 -->
	<div class="topmenu bgcolor1">
		<div class="topmenu1 ztcolor">
			<img class="meimg" alt=""
				src="${ctx}/groupon/assets/img/index/01_index_03.png"> <span>欢乐拼首页</span>
		</div>
		<div class="topmenu1">
			<a href="${ctx}/groupon/launch"> <img class="meimg" alt=""
				src="${ctx}/groupon/assets/img/index/01_index_05.png"> <span>发起</span>
			</a>
		</div>
	</div>

	<c:choose>
		<c:when test="${not empty ImgList}">
			<!-- 图片轮播 -->
			<div data-am-widget="slider" class="am-slider am-slider-a2"
				data-am-slider='{&quot;directionNav&quot;:false}'>
				<ul class="am-slides">
					<c:forEach items="${ImgList}" var="item" varStatus="vs">
						<li><img src="${item.icon }"
							style="width: 100%; height: 180px"></li>
					</c:forEach>
				</ul>
			</div>
		</c:when>
	</c:choose>

	<!-- 菜单选项 -->
	<div class="menuselect bgcolor1 ztbordercolor ztcolor">
		<div id="selected" class="menuselect1 select">精选项目</div>
		<div id="new" class="menuselect1">最新项目</div>
	</div>

	<!-- 精选项目列表 -->
	<div id="SelectProject" class="recolist">
		<c:choose>
			<c:when test="${not empty SelectedProjectList}">
				<c:forEach items="${SelectedProjectList}" var="item" varStatus="vs">
					<div class="wybz onerecord bgcolor1" onclick="Submit(${item.id});">
						<div class="recolistzt">
							<!-- 用户信息 -->
							<div class="recolistzt_t">
								<img class="recolistzt_img" alt="" src="${item.iconUrl }">
								<span class="recolistzt_name fontcolor3">${item.username }</span>
								<span class="recolistzt_time fontcolor3"><fmt:formatDate
										type="time" value="${item.createTime }"
										pattern="yyyy-MM-dd hh:mm:ss" /></span>
							</div>
							<!-- 标题 -->
							<div class="recolistzt_titl">${item.name }</div>
							<!-- 内容 -->
							<div class="recolistzt_reco fontcolor3"
								style="display: -webkit-box; overflow: hidden; width: 100%; -webkit-line-clamp: 3; float: left; -webkit-box-orient: vertical;">${item.remark }</div>

							<!-- 图片 -->
							<div class="proimg">
								<c:forEach items="${item.ImgList}" var="IconList"
									varStatus="vs1">
									<img src="${IconList.icon }" />
								</c:forEach>
							</div>
							<!-- 已支持 -->
							<div class="zhichi">
								<div class="zhichi_l">
									<!-- <div class="zhichi_l_img">
										<img class="zhichi_l_imgs" alt=""
											src="${ctx}/groupon/assets/img/logo/log.PNG">
									</div>
									<div class="zhichi_l_img">
										<img class="zhichi_l_imgs" alt=""
											src="${ctx}/groupon/assets/img/logo/log.PNG">
									</div>
									<div class="zhichi_l_img">
										<img class="zhichi_l_imgs" alt=""
											src="${ctx}/groupon/assets/img/logo/log.PNG">
									</div> -->
								</div>
								<div class="zhichi_r fontcolor3">已获得${item.orderNum }次支持</div>
							</div>
						</div>
						<!-- 信息 -->
						<div class="reco bgcolor3 fontcolor3">
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>目标<span class="fontcolor4">${item.amount }</span>元
								</span>
							</div>
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>已拼<span class="fontcolor4">${item.orderMoney }</span>元
								</span>
							</div>
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>还剩<span class="fontcolor4">${item.day }</span>天
								</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>



	</div>
	<!-- 最新项目 -->
	<div id="NewProject" class="recolist">
		<c:choose>
			<c:when test="${not empty NewProjectList}">
				<c:forEach items="${NewProjectList}" var="item" varStatus="vs">
					<div class="wybz onerecord bgcolor1" onclick="Submit(${item.id});">
						<div class="recolistzt">
							<!-- 用户信息 -->
							<div class="recolistzt_t">
								<img class="recolistzt_img" alt="" src="${item.iconUrl}">
								<span class="recolistzt_name fontcolor3">${item.username }</span>
								<span class="recolistzt_time fontcolor3"><fmt:formatDate
										type="time" value="${item.createTime }"
										pattern="yyyy-MM-dd hh:mm:ss" /></span>
							</div>
							<!-- 标题 -->
							<div class="recolistzt_titl">${item.name }</div>
							<!-- 内容 -->
							<div class="recolistzt_reco fontcolor3"
								style="display: -webkit-box; overflow: hidden; width: 100%; -webkit-line-clamp: 3; float: left; -webkit-box-orient: vertical;">${item.remark }</div>

							<!-- 图片 -->
							<div class="proimg">
								<c:forEach items="${item.ImgList}" var="IconList"
									varStatus="vs1">
									<img src="${IconList.icon }" />
								</c:forEach>
							</div>
							<!-- 已支持 -->
							<div class="zhichi">
								<div class="zhichi_l">
									<!-- <div class="zhichi_l_img">
										<img class="zhichi_l_imgs" alt=""
											src="${item.iconUrl}">
									</div> -->
								</div>
								<div class="zhichi_r fontcolor3">已获得${item.orderNum }次支持</div>
								<div id="output">i am here</div>
							</div>
						</div>
						<!-- 信息 -->
						<div class="reco bgcolor3 fontcolor3">
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>目标<span class="fontcolor4">${item.amount }</span>元
								</span>
							</div>
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>已拼<span class="fontcolor4">${item.orderMoney }</span>元
								</span>
							</div>
							<div class="reco_w">
								<img alt="" src="${ctx}/groupon/assets/img/index/in1.PNG">
								<span>还剩<span class="fontcolor4">${item.day }</span>天
								</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>


	</div>
	<!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		$("#NewProject").hide();//最新项目列表隐藏
		$('.menuselect').on('click', 'div', function() {
			$('.menuselect div').removeClass('select');
			$(this).addClass('select');
			var id = $(this).attr("id");
			if (id == "new") {
				$("#NewProject").show();
				$("#SelectProject").hide();
			} else {
				$("#NewProject").hide();
				$("#SelectProject").show();
			}
		})
	});
	function Submit(id){
		window.location.href=ctx+"/groupon/homeprojectdetail?id="+id;
	}
</script>
</html>