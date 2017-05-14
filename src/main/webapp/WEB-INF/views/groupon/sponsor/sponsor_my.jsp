<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>我的发起</title>
</head>
<style type="text/css">
body {
	background-color: rgb(245, 246, 250);
	margin: 0;
	padding: 0;
}

.returnup {
	display: none;
}
</style>
</head>

<body>
	<!--
        	作者：787711932@qq.com
        	时间：2016-06-13
        	描述：头部切换菜单
        -->
        <div class="mobindextop ztbgcolor fontcolor1 fontsize25">
			<img onclick="self.location=document.referrer;" class="prodetalieimg"
				alt=""
				src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
			<span style="font-size: 18px; display: inline-block;">我的发起</span> <img
				style="float: right; margin-top: 1.05em; padding-right: 10px;display:none;"
				alt="" src="${ctx}/groupon/assets/img/pint/tb1.png">
		</div>
	<div class="mysptop">
		<div class="mysptop_zt">
			<div status="1" class="mysptop_zt_pu myspselect" data-menuid="shenhe"
				style="height: 4rem;">
				<div class="mysptop_zt_pu1">审核中</div>
			</div>
			<div status="2" class="mysptop_zt_pu" data-menuid="shenhetg"
				style="height: 4rem;">
				<div class="mysptop_zt_pu1 mysptop_zt_pu2">审核通过</div>
			</div>
			<div status="3" class="mysptop_zt_pu" data-menuid="shenhesb"
				style="height: 4rem;">
				<div class="mysptop_zt_pu1 mysptop_zt_pu2">审核失败</div>
			</div>
			<div status="4" class="mysptop_zt_pu" data-menuid="xiangmujs"
				style="height: 4rem;">
				<div class="mysptop_zt_pu1">项目结束</div>
			</div>
		</div>
	</div>
	<!--
        	作者：787711932@qq.com
        	时间：2016-06-13
        	描述：产品列表
        -->

	<!-- 审核中 -->
	<div id="shenhe" class="mysplist">
		<c:choose>
			<c:when test="${not empty list1}">
				<c:forEach items="${list1}" var="item" varStatus="vs">
					<div id="${item.id }" class="mysplist_pro">
						<img class="mysplist_pro_l" style="height: 4.2rem;"
							src="${ctx}/groupon/assets/img/pint/pros.PNG" />
						<div class="mysplist_pro_c" style="margin-top: 1.45rem;">
							<div class="mysplist_pro_c_d"
								style="font-size: 14px;; line-height: 20px">${item.name }</div>
							<div class="mysplist_pro_c_d"
								style="font-size: 12px; color: rgb(70, 172, 70);">目标金额：￥${item.amount }</div>
							<div class="mysplist_pro_c_d">
								<span style="font-size: 12px; color: gray;">剩余${item.day }天</span>
							</div>
						</div>
						<img class="mysplist_pro_r"
							src="${ctx}/groupon/assets/img/pint/jt.png" />
					</div>
				</c:forEach>
			</c:when>
		</c:choose>


	</div>


	<!-- 审核通过 -->
	<div id="shenhetg" class="mysplist disdown">

		<c:choose>
			<c:when test="${not empty list2}">
				<c:forEach items="${list2}" var="item" varStatus="vs">
					<div id="${item.id }" class="mysplist_pro">
						<img class="mysplist_pro_l" style="height: 4.2rem;"
							src="${ctx}/groupon/assets/img/pint/pros.PNG" />
						<div class="mysplist_pro_c" style="margin-top: 1.45rem;">
							<div class="mysplist_pro_c_d"
								style="font-size: 14px;text-overflow: ellipsis;overflow:hidden; line-height: 20px"><nobr>${item.name }</nobr></div>
							<div class="mysplist_pro_c_d"
								style="font-size: 12px; color: rgb(70, 172, 70);">目标金额：￥${item.amount }</div>
							<div class="mysplist_pro_c_d">
								<span style="font-size: 12px; color: gray;">剩余${item.day }天</span>
							</div>
						</div>
						<img class="mysplist_pro_r"
							src="${ctx}/groupon/assets/img/pint/jt.png" />
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>

	<!-- 审核失败 -->
	<div id="shenhesb" class="mysplist disdown">
		<c:choose>
			<c:when test="${not empty list3}">
				<c:forEach items="${list3}" var="item" varStatus="vs">
					<div id="${item.id }" class="mysplist_pro">
						<img class="mysplist_pro_l" style="height: 4.2rem;"
							src="${ctx}/groupon/assets/img/pint/pros.PNG" />
						<div class="mysplist_pro_c" style="margin-top: 1.45rem;">
							<div class="mysplist_pro_c_d"
								style="font-size: 14px; line-height: 20px">${item.name }</div>
							<div class="mysplist_pro_c_d"
								style="font-size: 12px; color: rgb(70, 172, 70);">目标金额：￥${item.amount }</div>
							<div class="mysplist_pro_c_d">
								<div style="widdth: 30%;">
									<span style="font-size: 12px; color: gray;">剩余${item.day }天</span>
								</div>
								<div
									style="float: right; margin-right: -20px; margin-top: -1.29em;">
									<span class="myspts">失败理由${item.opinion }</span>
								</div>
							</div>
						</div>
						<img class="mysplist_pro_r"
							src="${ctx}/groupon/assets/img/pint/jt.png" />
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>
	
	<!-- 项目结束 -->
	<div id="xiangmujs" class="mysplist disdown">
		<c:choose>
			<c:when test="${not empty list4}">
				<c:forEach items="${list4}" var="item" varStatus="vs">
					<div id="${item.id }" class="mysplist_pro">
						<img class="mysplist_pro_l" style="height: 4.2rem;"
							src="${ctx}/groupon/assets/img/pint/pros.PNG" />
						<div class="mysplist_pro_c" style="margin-top: 1.45rem;">
							<div class="mysplist_pro_c_d"
								style="font-size: 14px; line-height: 20px;overflow:hidden;text-overflow:ellipsis"><nobr>${item.name }</nobr></div>
							<div class="mysplist_pro_c_d"
								style="font-size: 12px; color: rgb(70, 172, 70);">目标金额：￥${item.amount }</div>
							<div class="mysplist_pro_c_d">
								<div style="widdth: 30%;">
									<span style="font-size: 12px; color: gray;">已结束</span>
								</div>
								<div
									style="float: right; margin-right: -20px; margin-top: -1.29em;">
									<span class="myspts">
									<c:if test="${item.Settlement=='' }">项目结算:未结算</c:if>
									<c:if test="${item.Settlement=='0' }">项目结算:审核中</c:if>
									<c:if test="${item.Settlement=='1' }">项目结算:审核成功</c:if>
									<c:if test="${item.Settlement=='2' }">项目结算:审核失败</c:if>
									<c:if test="${item.Settlement=='3' }">项目结算:付款成功</c:if>
									</span>
								</div>
							</div>
						</div>
						<img class="mysplist_pro_r"
							src="${ctx}/groupon/assets/img/pint/jt.png" />
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>

	<div class="bai"></div>

	<!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>

</body>
<script>
	$(function() {
		$('.mysptop_zt_pu').on('click', function() {
			$(this).siblings().removeClass('myspselect');
			$(this).addClass('myspselect');
			var menuid = $(this).data('menuid');
			$('.mysplist').css('display', 'none');
			$("#" + menuid + "").css('display', 'block');
		});

		//编辑
		$('.mysplist_pro').on(
				'click',
				function() {
					var id=$(this).attr("id");//项目主键Id
					window.location.href=ctx+"/groupon/project/projectedit?id="+id;
				});
	});
</script>
</html>
