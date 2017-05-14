<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心-欢乐拼</title>
</head>
<style type="text/css">
    
</style>
<body style="padding:0;margin:0;background-color:#fff;">
<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
    <div class="specification_by_project"><strong>发起项目有何规范？</strong></div>
    <div class="specification_status"><strong>项目发起人资格</strong></div>
    <div class="specification_status_text">
        <span>18 周岁以上；</span><br/>
		<span>中华人民共和国公民；</span><br/>
		<span>拥有能够在中国地区接收人民币汇款的银行卡或者支付宝账户；</span><br/>
		<span>提供必要的身份认证和资质认证，根据项目内容，有可能包括但不限于：身份证、护照、</span>
		<span>学历证明等；</span><br/>
		<span>其他根据项目发起、执行需求相关的必须条件。</span>
		<span>18 周岁以下的中华人民共和国公民可经由符合以上条款具有资格的家长或老师代为发起和</span>
		<span>经营创意项目；</span><br/>
		<span>非中华人民共和国公民欲创建项目，需通过邮件方式递交申请，经批准可通过具有合法资质</span>
		<span>的代理机构进行项目发起。</span>
    </div>
    
     <div class="specification_status" style="margin-top:10px;"><strong>项目发布</strong></div>
      <div class="specification_status_text">
	        <span>根据相关法律法规，项目提交后，须经过网站工作人员审核后才能发布；</span><br/>
	        <span>根据项目的内容，工作人员会要求发起人提供相关材料，证明项目的可行性，以及发起人的</span>
	        <span>执行能力；</span><br/>
	        <span>网站对提交项目审核的项目是否拥有上线资格具有最终决定权（具备上线资格的项目的上线</span>
	        <span>时间由项目发起人自行决定）;</span><br/>
	        <span>项目在网站上线期间，不能在中国大陆其他类似的产品或项目孵化器平台同时发布。 无论项</span>
	        <span>目是否成功，一经发现将立即下线处理，其项目上线期间所得的金额将被立即退回给支持者</span>
	        <span>在网站上开立的账户中；因国家宏观政策以及法律法规、相关监管规定的调整，平台有权随</span>
	        <span>时对发布项目进行调整、下线且不承担任何相关责任。</span>        
      </div>
      
       <div class="specification_third" style="margin-top:10px;"><strong>项目内容规范（不符合以下内容规范的项目将被退回）</strong></div>
         <div class="specification_status_text">
		    <span>项目内容必须完整、合理、具有可行性；有完整的计划和执行能力，且图片、视频不能借用</span>
		    <span>  或盗用非本人/公司拍摄的内容。</span><br/>
			<span>项目应该有明确的目标，如制作一张专辑、出一本书或完成一件艺术品。可以完成的，有具</span>
			<span>体完成时间的才叫项目。创立一家公司就不是一种项目。</span><br/>
			<span>项目不得为个人公益项目。个人不得为慈善或某项事业筹资。例如，禁止为红十字会募资，</span>
			<span>禁止把筹集到的资金或未来利润转用于慈善或某项事业。</span><br/>
			<span>项目必须为有创意、有想法、有设计感的项目。</span><br/>
			<span>项目不得为网站类型的项目。由于网站需要长久经营，风险较大。无论是电子商务网站或社</span>
			<span>交网站的项目都将不被允许上线。</span><br/>
			<span>对于开发新硬件或新产品的设计和科技项目，发起人必须在项目页面上展示实物手板或原型</span>
			<span>机的照片，并提供发起人或团队在产品开发方面的相关经验的信息。</span><br/>
			<span>以下类别的项目和内容将不被允许在此发布：</span>
			<span>毒品、类似毒品的物质、吸毒用具、烟等相关</span>
			<span>枪支、武器和刀具相关</span><br/>
			<span>多级直销和传销类相关</span><br/>
			<span>令人反感的内容(仇恨言论、不适当内容等)
			<span>色情内容相关</span><br/>
			<span>支持或反对政治党派的项目</span><br/>
			<span>推广或美化暴力行为的项目</span><br/>
			<span>对奖、彩票和抽奖活动相关</span><br/>
			<span>* 申请提现时将扣除提现总额的 2%，</span>
			<span>该费用为平台服务费</span><br/>
			<br>
			<span>项目回报规范（不符合以下回报规范的项目将被退回）</span><br/>
			<br>
			<span>因平邮寄送会产生很多问题，故回报形式不得是平邮明信片(用快递寄送的套装明信片则不在此限)。</span><br/>
			
		    <span>项目不得以不可行、不合理的承诺作为回报。</span>
		    <span>项目回报必须是项目的衍生品，不得是与项目无关的回报内容。</span>
      </div>
      
       <div class="specification_status" style="margin-top:10px;"><strong>项目推荐标准：</strong></div>
      <div class="specification_status_text">
			     <span>举报：不符合《项目规范》</span> <br/>
				 <span>合格：符合《项目规范》</span> <br/>
			     <span>推荐：合格并且满足下列标准中的任意 1 - 3 项（含 3 项），视为推荐</span><br/>
			     <span>强烈推荐：合格并且满足下列标准中的任意 3 项以上，视为强烈推荐</span> <br/>
			     <span>项目内容逻辑性强、完整且条理清晰、可执行，有完整的计划和能力，有相关的图片和视频（图片、视频不能借用或盗用非本人/公司拍摄的）</span> <br/>
				 <span>项目内容有故事性、有热点，具备可传播性</span> 
				 <span>项目内容有创意、创新；非山寨、抄袭、跟风，产品在其他渠道买不到</span> <br/>
				 <span>科技或设计类项目的产品设计感好，有品质，符合大众审美喜好的要求</span> <br/>
				 <span>项目发起人有推广渠道、媒体资源、或在公众平台上有一定的影响力</span> <br/>
				 <span>总金额合理，达成率高，支持项及金额满足支持者多元化需求，性价比高，回报发送保证网站首发。   </span> 
        
      </div>
    
      
</body>
</html>