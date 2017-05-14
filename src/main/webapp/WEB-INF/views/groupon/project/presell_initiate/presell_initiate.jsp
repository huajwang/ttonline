<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心</title>
<!-- 帮助_发起项目的首页 - -->
<style type="text/css">
      
   
</style>
</head>
<body style="margin:0;padding:0; background-color: #fff;">
<img id="fan_hui" onclick="window.location.href='${ctx}/groupon/launch/helpcenter'"  style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
      <div class="advance_sale_project"><strong>尝鲜预售相关问题</strong></div>
     <div class="advance_sale_status" style="margin-bottom:5px;line-height:30px;">什么样的场频适合欢乐拼？</div>
     <div class="advance_sales_text">
	     <span>农副产品，文化创意类。</span><br/>   
	     <span>原创类手工艺品：书画，剪纸，木工，布艺，皮艺，编织，刺绣等。</span><br/>
	     <span>自主品牌产品，好吃，好看，好玩。有专属于自己的个性特色，性价比高，价格优于市场。</span>
     </div>
     
     <div class="advance_sale_status text_recommended">·不给推荐：</div>
     <div class="advance_sales_text">
          <span>1.保健品、药品类、无资质酒类、无产地证明的茶叶类中药材类等不推荐。</span><br/>
          <br>
          <span>2.微商，直销，化妆品类等其他。</span>
     </div>
     
      <div class="advance_sale_status text_recommended">你可以试着发起一个项目</div>
     <div class="advance_sales_text">
         <span> 1.金额不建议写太高，这样容易达成目标。金额大小可自己调整。 </span><br/>
         <br>
          <span>2.截止日期：滑动图中小圆按钮可设定众筹时间。3-30天时间任选。</span><br/>
          <br>
		  <span>3.设为隐私：项目设为隐私后，则不会被搜索到［默认是关闭状态］</span><br/>
		  <br>
		  <span>4.是否需要支持者收件地址［默认是需要支持者收获地址状态］ </span><br/>
		  <br>
		  <span>5.发货时间：最好在支持者支持项目48小时以内发货。</span><br/>
		  <br>
	      <span>用户收到产品后，给出的好评，推荐，都可以达到二次购买，提高销量的效果。</span><br/>
	      <br>
		  <span>6.标题跟详细介绍产品内容页很重要，后面我们会详细介绍，</span><br/>
		  <br>
		  <span>详细介绍里最好用电脑端图文混排，方便直观观看。 </span>
     </div>
     
      <div class="advance_sale_status text_recommended">关于电脑图文混排</div>
        <div class="advance_sale_status ">内容介绍怎么写</div>
         <div class="advance_sales_text">
	        <span>建议上传5张左右清晰图片（宽700、高不限，文</span><br/>
	        <span>件格式不要太大，否则不易显示，影响客户体验</span>
	        <span>。）给出的图片保证内容干净、不凌乱，保证照明光线合理，避免出现比例失调、模糊严重的图</span>
	        <span>片。如没有美工基础，尽可能不要修饰图片。欢</span>
	        <span>乐拼拥有页面图文使用权，并有权对信息进行修改;</span><br/>
	        <span>配合文字来简洁生动介绍你的产品，产品的产地</span>
	        <span>，特色，功能，使用说明，特殊功效，保存方法</span>
	        <span>等。让支持者对你的产品或者要做的事一目了然，并充满兴趣。</span><br/>
	        <span>增加一些生活场景，现实生活照，以及你做此产</span>
	        <span>品的经历，付出的努力等，会增加你项目的可信</span>
	        <span>度并由此提高拼团的成功率。为你自己和你的产</span>
	        <span>品讲一个生动合理不煽情故事（不能带公益导向的内容）</span>
     
         </div>
     
     <div class="advance_sale_status text_recommended">8添加图片</div>
         <div class="advance_sales_text">
			<span>手机显示端的四张图</span><br/>
			<br>
			<span>手机端显示的是页面编辑时，添加的前四张图，</span>
			<span>图片的大小请限制在1080*980像素内，</span>
			<span>这四张图是手机用户对你产品的直观印象。除了</span>
			<span>要满足上面说的选头图的要求：清晰直观美图以</span>
			<span>外，一定要四张图分工明确，尽可能展示更多，</span>
			<span>更详细的内容！例如：全景，细节，包装等～</span><br/>
         </div>
          
     <div class="advance_sale_status text_recommended">电脑版添加图片</div>
         <div class="advance_sales_text">
             <span>注意：上传图库第一张图尽量用横版，以保证网页页面图片显示正常；</span><br/>
				<br>
		     <span>上传图库前四张图片将在移动端以正方形显示，应保证产品出现在图片中央位置。
			  请使用自己的图片，严禁盗图。一经发现撤销推荐并追究责任！ </span><br/>
			  <br>
             <span style="color:RGB(180,26,26);">请使用自己的图片，严禁盗图。一经发现撤销推荐并追究责任！</span>
         </div>
     
</body>
</html>