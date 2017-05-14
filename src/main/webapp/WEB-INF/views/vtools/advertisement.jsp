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
		<title>编辑广告</title>
		<%@ include file="/common/include.rec.ftl"%>
		<link rel="stylesheet" href="resources/vtools/css/fre_color.css" />
		<link rel="stylesheet" href="resources/vtools/css/amazeui.flat.css" />
		<link rel="stylesheet" href="resources/vtools/css/zjw.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/webuploader-portrait/webuploader_vtools.css">
		<script type="text/javascript" src="${ctx}/resources/js/webuploader-portrait/uploader_vtools.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/webuploader-portrait/webuploader.min.js"></script>
		<script type="text/javascript" src="resources/vtools/js/jquery.js" ></script>
		<script type="text/javascript" src="resources/vtools/js/amazeui.js" ></script>
		
		<style>
			.onas td{
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
        	<span class="fre_fs7">我的广告</span>
        	<span class="fre_po2 his_bc13 history"></span>
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
								<img width="50px" height="50px" class="fre_w05" src="resources/vtools/img/personal/my_personal_18.png" />
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
        <div class="fre_w1 onas his_bc14">
        	<table class="fre_w1">
        		<tr>
        			<td class="fre_tc his_bc15 his_bc16">
        				跳转链接
        			</td>
        			<td class="his_bc16">
        				<input type="text" id="redirectUrl" value="http://" name="redirectUrl" class="his_bc17_1"/>
        			</td>
        		</tr>
        		<tr>
        			<td class="his_bc15 fre_tc">
        				备注
        			</td>
        			<td>
        				<input type="text" id="note" name="note" class="his_bc17_1" maxlength="4"/>
        			</td>
        		</tr>
        		<tr>
        			<td class="fre_tc his_bc15">
        				<div id="filePicker" class="webuploader-pick" style="margin:0 2px">
        				<button type="button" class="webuploader-pick-button" style="min-width:65px;min-height:32px;border:0px solid white"><font size="1.5px" weight="bold">上传图片</font></button>
        				</div>
        			</td>
        			<td>
        				<div id="fileList" class="fre_tc fre_bor his_bc18">
        					<img id="adimage">
        				</div>
        			</td>
        		</tr>
        		<tr>
        			<td class="fre_tc his_bc15">
        			</td>
        			<td>
        				<span class="fre_fs5">图片规格：长*宽  1080*170</span>
        			</td>
        		</tr>
        	</table>
        </div>
        <div class="his_bc9 fre_tc theme_co his_bc8 create">生成</div>
        
        <div class="fre_fl his_bc19">
	        <div class="fre_w1 fre_bor his_bc2 fre_fl theme_bo">
	        <c:forEach items="${list}" var="item" varStatus="vs">
				<div class="fre_w1 fre_fl his_bc3">
					<div class="fre_fl his_bc4">
						<span class="his_bc5">${vs.index+1}</span>
					</div>
					<div class="fre_fl his_bc6 fre_po1">
						<img class="his_bc6" src="${item.iconUrl}" height="50px" />
						<img class="fre_po2 his_bc20 delete" id="${item.id}"  src="resources/vtools/img/inject/inj.png" />
					</div>
				</div>
			</c:forEach>
			</div>
        </div>
        
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
	var picname = '';
		$(function(){
			//执行注入
			$('.create').on('click',function(){
				$('.his_bc23').css('display','block');
				uploadPortrait(picname);
			});
			//返回
			$('.fan').on('click',function(){
				window.location="vtools";
			});
			$('.delete').on('click',function(){
				if(confirm("确定删除该记录？")){
					$.ajax({
					    type : 'post',
					    url : ctx + "/vtools/advertise/delete",
					    data : {
					          'id' : $(this).attr('id')
					         },
					    success : function(result) {
					    	window.location.reload();
					           //var json = eval(result);
					           //var status = json["status"];
					           //if(status == "0"){
					        	   //alert("删除成功");
					           //}
					           //else{ alert("删除失败");}
					    }  
					});
				}
			});
			//历史记录
			//$('.history').on('click',function(){
			//	 window.location.href="personal/history/history.html"; 
			//});
			
		})
	</script>
</html>
