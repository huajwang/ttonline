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
		<title>会员历史记录</title>
		<%@ include file="/common/include.rec.ftl"%>
		<link rel="stylesheet" href="resources/vtools/css/fre_color.css" />
		<link rel="stylesheet" href="resources/vtools/css/amazeui.flat.css" />
		<link rel="stylesheet" href="resources/vtools/css/zjw.css" />
		<script type="text/javascript" src="resources/vtools/js/jquery.js" ></script>
		<script type="text/javascript" src="resources/vtools/js/amazeui.js" ></script>
	</head>
	<body class="body_bgc">
		<div class="fre_con" style="margin-bottom: 8rem;">
			<!--
            	作者：787711932@qq.com
            	时间：2016-09-07
            	描述：头部
            -->
			<div class="theme_co fre_bg1 fre_top fre_fs8">
				订单记录
			</div>
			<div class="fre_w1 fre_cen">
				<table>
					<tbody>
						<tr>
							<td>
								用户136xxxx666
							</td>
							<td>普通版</td>
							<td>20160906</td>
						</tr>
						<tr>
							<td>
								用户136xxxx666
							</td>
							<td>高级版</td>
							<td>20160906</td>
						</tr>
						<tr>
							<td>
								用户136xxxx666
							</td>
							<td>企业版</td>
							<td>20160906</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--
        	作者：787711932@qq.com
        	时间：2016-09-07
        	描述：尾部导航
        -->
		<div class="fre_w1 fre_foo fre_bg2">
			<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc inject">
				<span>
					<img src="resources/vtools/img/personal/my_personal_24.png" />
				</span><br/>
				<span class="theme_co fre_fs4">广告注入</span>
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
			//微广会员
			$('.wgmember').on('click',function(){
				  window.location.href="vtools/vmember/pay"; 
			});
		})
	</script>
</html>
