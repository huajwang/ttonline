<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加新地址</title>
<%@ include file="/common/public.jsp"%>
</head>
<body style="background-color: #fff">
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg" alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px;display: inline-block;">添加新地址</span>
	</div>
	
	<!-- 收货人 -->
	<div class="newadress" style="padding-top: 2.5rem;">
		<div class="newadress_l fontcolor3">收货人：&nbsp;</div>
		<div class="newadress_r">
			<input class="inewadren" placeholder="请填写收货人姓名">
		</div>
	</div>
	
	<!-- 手机号码 -->
	<div class="newadress">
		<div class="newadress_l fontcolor3">手机号码：&nbsp;</div>
		<div class="newadress_r">
			<input class="inewadren" placeholder="请填写手机号码">
		</div>
	</div>
	
	<!-- 邮政编码 -->
	<div class="newadress">
		<div class="newadress_l fontcolor3">邮政编码：&nbsp;</div>
		<div class="newadress_r">
			<input class="inewadren" placeholder="请填写邮政编码">
		</div>
	</div>
	
	<div class="newadress">
		<fieldset id="element_id" class="sjld_wai" style="border: none;">
			<div class="sjld_wai_zt" >
				<div class="newadress_l fontcolor3">省份：&nbsp;</div>
				<select class="province sjld_wai_seel"  data-value="请选择"></select>
			</div>
			<div class="sjld_wai_zt" >
				<div class="newadress_l fontcolor3">城市：&nbsp;</div>
				<select class="city sjld_wai_seel" data-value="请选择"></select>
			</div>
			<div class="sjld_wai_zt" >
				<div class="newadress_l fontcolor3">区/县：&nbsp;</div>
				<select class="area sjld_wai_seel" data-value="请选择"></select>
			</div>
		</fieldset>
	</div>
	<!-- 省 -->
	<!-- 市 -->
	<!-- 区 -->
	
	<!-- 详细地址 -->
	<div class="newadress">
		<div class="newadress_l fontcolor3">详细地址：&nbsp;</div>
		<div class="newadress_r2">
			<textarea rows="" cols=""></textarea>
		</div>
	</div>
	
	<!-- 是否默认 -->
	<div class="newadress">
		<div class="newadress_l fontcolor3">是否默认：&nbsp;</div>
		<div class="newadress_r" style="padding-top: 1rem;">
			<select data-am-selected>
			  <option value="a" selected>否</option>
			  <option value="b" >是</option>
			</select>
		</div>
	</div>
	
</body>

<script src="${ctx}/groupon/assets/js/jquery.min.js"></script>
<script src="${ctx}/groupon/assets/js/cityselect/jquery.cxselect.js"></script>
<script type="text/javascript">
//三级联动
$(function() {
	$('#element_id').cxSelect({
	  url: '${ctx}/groupon/assets/js/cityselect/cityData.min.json',               // 如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
	  selects: ['province', 'city', 'area'],  // 数组，请注意顺序
	  emptyStyle: 'none',
	  jsonValue: ' ',
	});
});
</script>
</html>