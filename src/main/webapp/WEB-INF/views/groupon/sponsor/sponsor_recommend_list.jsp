<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>提现记录</title>
</head>
<style>
body {
	margin: 0;
	padding: 0;
}
</style>
</head>
<body>
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg"
			alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px; display: inline-block;">提现记录</span> <img
			style="float: right; margin-top: 1.05em; padding-right: 10px; display: none;"
			alt="" src="${ctx}/groupon/assets/img/pint/tb1.png">
	</div>
	<c:choose>
		<c:when test="${not empty list}">
			<c:forEach items="${list}" var="item" varStatus="vs">
				<div class="remlist">
					<div class="recommendrecord">
						<img class="remlist_img"
							src="${ctx}/groupon/assets/img/pint/jj1.png" />
						<div class="remlist_div" style="line-height: 2rem">
							<div class="remlist_div_pu">
								<span class="remlist_div_sp">项目名称：${item.name }</span><br> 
								<span class="gr-tx-bc gr-tx-bc1">项目实际结账：￥${item.actual }</span>
								
							</div>
							<div class="remlist_div_pu">
								<span class="remlist_div_sp1">金额：￥${item.actual }</span>
								<span class="gr-tx-bc gr-tx-bc2">项目总售：￥${item.total }</span><br>
								<span class="gr-tx-bc gr-tx-bc3">退货金额：￥${item.refund }</span> 
								<span class="gr-tx-bc gr-tx-bc4">平台服务费：￥${item.service }</span>
							</div>
							<div class="remlist_div_pu">
								<span class="gr-tx-bc gr-tx-bc3" style="text-align: center;">赔偿买家：￥${item.compensate }</span>
								<span class="remlist_div_sp1 remcolor" >状态： 
									<c:if test="${ fn:contains(item.status, '0') }">审核中</c:if>
									<c:if test="${ fn:contains(item.status, '1') }">审核通过</c:if>
									<c:if test="${ fn:contains(item.status, '2') }">审核失败</c:if>
									<c:if test="${ fn:contains(item.status, '3') }">付款成功</c:if>
								</span>
								<span class="gr-tx-bc gr-tx-bc4" style="text-align: left;width:90%">付款帐号： <c:if
										test="${ fn:contains(item.type, '0') }">支付宝</c:if> <c:if
										test="${ fn:contains(item.type, '1') }">微信帐号</c:if>
								</span>
								
							</div>
							<div class="remlist_div_pu">
								<span class="remlist_div_sp">时间：<fmt:formatDate type="time" value="${item.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span> 
								
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:when>
	</c:choose>


	<div class="remco_ts">
		<span>拓意客在线投诉：0592-2069229</span><br /> <span>Email:huawang088@163.com</span>
	</div>
</body>
</html>
