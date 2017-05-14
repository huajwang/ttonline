<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1 ,user-scalable=no">
<title>广告册</title>
<%@ include file="/common/include.rec.ftl"%>
<link rel="stylesheet" href="resources/vtools/css/fre_color.css" />
<link rel="stylesheet" href="resources/vtools/css/amazeui.flat.css" />
<link rel="stylesheet" href="resources/vtools/css/zjw.css" />
<script type="text/javascript" src="resources/vtools/js/jquery.js"></script>
<script type="text/javascript" src="resources/vtools/js/amazeui.js"></script>
<style>
.onas td {
	padding: 2% 0;
}

.his_bc6 {
	width: 82%;
}

.his_bc4 {
	width: 18%;
}
</style>
</head>
<body class="fre_bg1" style="float: left;">
		<!--
        	作者：787711932@qq.com
        	时间：2016-09-08
        	描述：返回
        -->
        <div class="thene_bg his_bc10 fre_co1 fre_po1 fre_tc">
        	<img src="resources/vtools/img/personal/history/zr_01.png" class="fre_po2 his_bc12 fan" />
        	<span class="fre_fs7">朋友圈广告注入</span>
        	<span class="fre_po2 his_bc13 history">历史记录</span>
        </div>
        <!--
        	作者：787711932@qq.com
        	时间：2016-09-07
        	描述：用户信息
        -->
        <div class="fre_w1 per_top">
			<table class="fre_w1 theme_co">
				<tr>
					<td class="fre_tc">
						<img width="50px" height="50px" class="fre_w05" src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${user.iconUrl}" />
					</td>
					<td><strong>${user.userName}</strong></br>${user.phone}</td>
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
       	<form id="articleForm" action="vtools/advertise/uploadArticle" method="post">
        <div class="fre_w1 onas his_bc14">
        	<table class="fre_w1">
        		<tr>
        			<td class="fre_tc his_bc15 his_bc16">
        				文章地址
        			</td>
        			<td class="his_bc16">
        				<input type="text" id="article" name="articleUrl" class="his_bc17_1"/>
        			</td>
        		</tr>
        		<tr>
        			<td class="his_bc15 fre_tc">
        				文章类型
        			</td>
        			<td>
        				<select class="his_bc17" id="artType" name="artType">
        					<option value="1">选择</option>
        					<option value="2">财经</option>
        					<option value="3">娱乐</option>
        					<option value="4">体育</option>
        					<option value="5">时尚</option>
        					<option value="6">汽车</option>
        					<option value="7">房产</option>
        					<option value="8">社会</option>
        					<option value="9">军事</option>
        					<option value="0">其它</option>
        				</select>
        			</td>
        		</tr>
        		<tr>
        			<td class="his_bc15 fre_tc">
        				跳转地址
        			</td>
        			<td>
        				<select class='his_bc17' id="autoRedirect" name="autoRedirect">
        					<option value="0">阅读完不自动跳转</option>
		       				<c:forEach items="${list}" var="item" varStatus="vs">
		       					<option value="${item.id}">阅读完自动跳转至→${item.note}</option>
	        				</c:forEach>
        				</select>
        			</td>
        		</tr>
        	</table>
        </div>
        <input type="hidden" id="advertises" name="advertises" value=""/>
        </form>
        <div class="fre_fl his_bc19" style="height:40%">
        	<p style="margin:0">点击选择轮播广告：</p>
	        <div class="fre_w1 fre_bor his_bc2 fre_fl theme_bo" style="height:100%">
				<div class="fre_w1 per_menutd2 fre_fs5" style="padding-bottom: 2%;width:90% ;margin:20px auto;">
					<c:forEach items="${list}" var="item" varStatus="vs">
		            	<div class="theme_co fre_bor m_pbc4" id="${item.id}">
		            		<table class="fre_w1">
		            			<tr>
		            				<td style="width:25%">${item.note}:</td>
		            				<td><img src="${item.iconUrl}" style="height:20px;width:75%"/></td>
		            			</tr>
		            		</table>
		            	</div>
	            	</c:forEach>
	            </div>
			</div>
        </div>
        <div class="his_bc9 fre_tc theme_co his_bc8 his_bc24 zxinject" id="inject">执行注入</div>
        
        <!--
        	作者：787711932@qq.com
        	时间：2016-09-08
        	描述：加载状态
        -->
        <div class="his_bc23">
        	<div class="his_bc22"></div>
        	<div class="his_bc21">
				<i class="am-icon-spinner am-icon-pulse" style="color: #fff;font-size: 30px;"></i><br/>
				<span class="fre_co1 fre_fs3">正在加载中..</span>
        	</div>
		</div>
	</body>
	<script>
		$(function(){
			var advertises = "";
			//执行注入
			$('.zxinject').on('click',function(){
				var article = $("#article").val();
				var artType = $("#artType").val();
				if($('.mem_p_on').length > 0&&article!=""&&article!=null&&artType!=""&&artType!=null){
					$('.his_bc23').css('display','block')
					$('.mem_p_on').each(function(){
						advertises += $(this).attr("id")
						advertises +=" ";
					});
					$("#advertises").val(advertises);
					$("#articleForm").submit();
				}else{
					alert("请先完整填写内容！");
				}
				
			});
			//返回
			$('.fan').on('click',function(){
				window.location.href="vtools";
			});
			//历史记录
			$('.history').on('click',function(){
				 window.location.href="../personal/history/history.html"; 
			});
			//选择
			$('.m_pbc4').on('click',function(){
				//$('.mem_p_on').removeClass('mem_p_on');
				$(this).toggleClass('mem_p_on');
			});
		})
	</script>
</html>
