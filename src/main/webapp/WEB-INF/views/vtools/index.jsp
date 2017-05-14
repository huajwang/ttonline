<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
	<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1 ,user-scalable=no">
		<title>微广工具箱</title>
		<%@ include file="/common/include.rec.ftl"%>
		<link rel="stylesheet" href="resources/vtools/css/fre_color.css" />
		<link rel="stylesheet" href="resources/vtools/css/amazeui.flat.css" />
		<link rel="stylesheet" href="resources/vtools/css/zjw.css" />
		<script type="text/javascript" src="resources/vtools/js/jquery.js" ></script>
		<script type="text/javascript" src="resources/vtools/js/amazeui.js" ></script>
	</head>
	<body class="fre_bg1">
		<div class="fre_w1" style="margin-bottom: 8rem;">
			<!--
	        	作者：787711932@qq.com
	        	时间：2016-09-08
	        	描述：广告图
	        -->
	        <div class="fre_w1">
	        	<img class="fre_w1" src="resources/vtools/img/index/gg.png" />
	        </div>
			<!--
	        	作者：787711932@qq.com
	        	时间：2016-09-07
	        	描述：用户信息
	        -->
	        <div class="fre_w1 per_top">
				<table class="fre_w1 theme_co">
					<tr>
						<c:choose>
							<c:when test="${not empty user}">
								<td class="fre_tc">
									<img width="50px" height="50px" class="fre_w05" src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${user.iconUrl}" />
								</td>
								<td><strong>${user.userName}</strong></br>${user.phone}</td>
							</c:when>
							<c:otherwise>
								<td class="fre_tc">
									<img class="fre_w05" src="resources/vtools/img/personal/my_personal_18.png" />
								</td>
								<td><strong>未登录</strong></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty vip}">
								<td>${vip.name} </td>
								<td>剩余${vip.day}天</td>
							</c:when>
							<c:otherwise>
								<td>游客</td>
								<td>无vip特权</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>        	
	        </div>
	        <!--
	        	作者：787711932@qq.com
	        	时间：2016-09-08
	        	描述：菜单
	        -->
	        <div class="fre_w1 per_menu">
	        	<table class="fre_w1" style="border-collapse:collapse;color: black;">
	        		<tr>
	        			<td class="fre_w03-3 fre_tc inject">
	    					<img src="resources/vtools/img/personal/my_personal_24.png" />
	        				<br/>
	        				<span>广告注入</span>
	        			</td>
	        			<td class="fre_w03-3 fre_tc per_menutd wgmember">
	    					<img src="resources/vtools/img/personal/my_personal_21.png" />
	        				<br/>
	        				<span>微广会员</span>
	        			</td>
	        			<td class="fre_w03-3 fre_tc jjkf">
	    					<img src="resources/vtools/img/personal/my_personal_09.png" />
	        				<br/>
	        				<span>即将开放...</span>
	        			</td>
	        		</tr>
	        		<tr>
	        			<td class="fre_w03-3 fre_tc per_menutd3">
	        			</td>
	        			<td class="fre_w03-3 fre_tc per_menutd per_menutd3">
	        			</td>
	        			<td class="fre_w03-3 fre_tc per_menutd3">
	        			</td>
	        		</tr>
	        		<tr>
	        			<td class="fre_w03-3 fre_tc per_menutd2 per_menutd3">
	        			</td>
	        			<td class="fre_w03-3 fre_tc per_menutd per_menutd2 per_menutd3">
	        			</td>
	        			<td class="fre_w03-3 fre_tc per_menutd2 per_menutd3">
	        			</td>
	        		</tr>
	        	</table>
	        	
	        </div>
		</div>
		<!--
        	作者：787711932@qq.com
        	时间：2016-09-07
        	描述：尾部导航
        -->
		<div class="fre_w1 fre_foo fre_bg2">
			<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc cluster">
				<span>
					<img src="resources/vtools/img/personal/my_personal_24.png" />
				</span><br/>
				<span class="theme_co fre_fs4">广告册</span>
			</div>
			<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc wgmember">
				<span>
					<img src="resources/vtools/img/personal/my_personal_21.png" style="width: 19%" />
				</span><br/>
				<span class="theme_co fre_fs4">微广会员</span>
			</div>
			<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc personal">
				<span>
					<img src="resources/vtools/img/personal/my_personal_18.png" style="width: 16%" />
				</span><br/>
				<span class="theme_co fre_fs4">我的中心</span>
			</div>
		</div>
	</body>
	<script>
		$(function(){
			//我的中心
			$('.personal').on('click',function(){
				  window.location.href="vtools/vmember"; 
			});
			//广告注入
			$('.inject').on('click',function(){
				  window.location.href="vtools/advertise/editPage"; 
			});
			//广告册
			$('.cluster').on('click',function(){
				  window.location.href="vtools/advertise/curd"; 
			});
			//微广会员
			$('.wgmember').on('click',function(){
				  window.location.href="vtools/vmember/pay"; 
			});
		})
	</script>
</html>
