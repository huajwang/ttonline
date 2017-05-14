package com.troika.emall.restapi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.config.AppWxPayConfig;
import com.alipay.config.WxPayConfig;
import com.alipay.sign.RSA;
import com.google.gson.Gson;
import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.model.TMallAddress;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallUser;
import com.troika.emall.service.AddressService;
import com.troika.emall.service.CartService;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.FavouriteService;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/user/")
public class TMallUserController extends BaseController{
	@Autowired
	private TMallUserService tMallUserService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private FavouriteService favouriteService;
	@Autowired
	private EvaluateService evaluateService;

	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping("userRegister.api")
	@ResponseBody
	public String userRegister(HttpServletRequest request,String registerCode,String phone,String password){
		Object regObj = request.getSession().getAttribute(Constant.REGISTER_CODE);
		Map<String,Object> result = null;
		if(regObj != null && regObj.toString().equals(registerCode)){
			result = tMallUserService.register(phone, password);
		}else{
			result = new HashMap<String,Object>();
			result.put("msg", "没有注册码不能注册");
		}
		if(result.isEmpty()){
			return genSuccessResult();
		}else{
			return genFailResult(result);
		}
	}
	/**
	 * 重置密码
	 * @param request
	 * @param registerCode
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping("getPassword.api")
	@ResponseBody
	public String getPassword(HttpServletRequest request,String registerCode,String phone,String password){
		Object regObj = request.getSession().getAttribute(Constant.REGISTER_CODE);
		Map<String,Object> result = null;
		if(regObj != null && regObj.toString().equals(registerCode)){
			result = tMallUserService.reSetPassword(phone, password);
		}else{
			result = new HashMap<String,Object>();
			result.put("msg", "没有校验码不能使用此功能");
		}
		if(result.isEmpty()){
			return genSuccessResult();
		}else{
			return genFailResult(result);
		}
	}
	
	/**
	 * 更新保存地址
	 * @param address
	 * @return
	 */
	@RequestMapping("/ajaxSaveOrUpdateAddress.api")
	@ResponseBody
	public String ajaxSaveOrUpdateAddress(HttpServletRequest request,TMallAddress address){
		TMallUser user = CommonUtil.ValidateUser(request);
		address.setUserId(user.getId());
		addressService.saveOrUpdate(address);
		return genSuccessResult();
	}
	/**
	 * 获取用户下地址
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getAddrList.api")
	@ResponseBody
	public String ajaxGetAllAddr(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		List<Map<String,Object>> list = addressService.findAllAddress(user.getId());
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
	/**
	 * 获取单个地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAddress.api")
	@ResponseBody
	public String ajaxGetAddrById(long id){
		Map<String,Object> addr = addressService.findAddressById(id);
		String resultJson = genSuccessResult(addr);
		return resultJson;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxGetActiveAddress.api")
	@ResponseBody
	public String ajaxGetActiveAddress(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Object> addr = addressService.findActiveAddress(user.getId());
		String resultJson = genSuccessResult(addr);
		return resultJson;
	}
	/**
	 * 添加购物车
	 * @param request
	 * @param cartDetail
	 * @param total
	 * @return
	 */
	@RequestMapping("/addCartList.api")
	@ResponseBody
	public String ajaxAddCartList(HttpServletRequest request,String cartDetail,BigDecimal total){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Object> result = cartService.buildCartList(user.getId(),cartDetail,total);
		return genSuccessResult(result);
	}
	/**
	 * 添加购物车单个
	 * @param request
	 * @param cartDetail
	 * @param total
	 * @return
	 */
	@RequestMapping("/addCart.api")
	@ResponseBody
	public String ajaxAddCart(HttpServletRequest request,String cartDetail,BigDecimal total,String isOut){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Object> result = cartService.buildCart(user.getId(),cartDetail,total,isOut);
		return genSuccessResult(result);
	}
	
	/**
	 * 获取购物车列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxCartList.api")
	@ResponseBody
	public String ajaxCartList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		List<Map<String,Object>> list = cartService.getCartListByUserId(user.getId());
		Integer cartId = null;
		for(int i=0;i<list.size();i++){
			Map<String,Object> tempMap = list.get(i);
			if(cartId == null){
				cartId = (Integer) tempMap.remove("cartId");
			}else{
				tempMap.remove("cartId");
			}
			if(!tempMap.containsKey("id") || tempMap.get("id") == null){
				list.remove(i);
				i--;
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(cartId != null){
			resultMap.put("cartId", cartId);
			resultMap.put("list", list);
		}
		String result = genSuccessResult(resultMap);
		logger.info("获取用户【"+user.getId()+"】的购物车列表: " + result);
		return result;
	}
	@RequestMapping("ajaxDelCartDetail.api")
	@ResponseBody
	public String ajaxDelCartDetail(String cartDetailIds){
		cartService.delDetail(cartDetailIds);
		return genSuccessResult();
	}
	/**
	 * 购物车商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxCartGoodsNum.api")
	@ResponseBody
	public String ajaxCartGoodsNum(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Long> map = cartService.getGoodsNum(user.getId());
		String result = genSuccessResult(map);
		return result;
	}
	
	/**
	 * @param request
	 * @param orderDetail
	 * @param orderSource
	 * @param amount
	 * @param addrId
	 * @param remark
	 * @param introducer 有introducer时代表该订单由购买人通过点击推荐人所发的链接来购买的， 没有表示该订单没有推荐人
	 * @param cartId 有cartId时代表从购物车提交的订单，没有时代表直接提交定单
	 * @return
	 */
	@RequestMapping("/addOrder.api")
	@ResponseBody
	public String addOrder(HttpServletRequest request,String orderDetail,String orderSource,
			BigDecimal amount,long addrId,String remark, @RequestParam(value="introducer",required=false) Long introducer, 
			@RequestParam(value="cartId",required=false) Long cartId){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Object> result = orderProcess.buildOrder(user.getId(), addrId, orderDetail, 
													orderSource, amount, remark, introducer == null ? 0 : introducer, cartId);
		if(result.containsKey("orderId")){
			return genSuccessResult(result);
		}
		return genResult(result, "1");
	}
	/**
	 * 删除定单（未支付状态才可删除）
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/deleteOrder.api")
	@ResponseBody
	public String deleteOrder(Long orderId){
		Map<String,Object> result =orderProcess.deleteOrderById(orderId);
		return genSuccessResult(result);
	}
	/**
	 * 订单列表-全部-除了已删除
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxOrderList.api")
	@ResponseBody
	public String ajaxOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getOrderListByUserId(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 订单列表-待支付
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxWaitPayOrderList.api")
	@ResponseBody
	public String ajaxWaitPayOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getWaitPayOrderList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 订单列表-待发货
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxWaitSendOrderList.api")
	@ResponseBody
	public String ajaxWaitSendOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getWaitSendOrderList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 订单列表-待收货
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxWaitGetOrderList.api")
	@ResponseBody
	public String ajaxWaitGetOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getWaitGetOrderList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 订单列表-已完成
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxDoneOrderList.api")
	@ResponseBody
	public String ajaxDoneOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getDoneOrderList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 订单列表-已完成
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxWaitEvalOrderList.api")
	@ResponseBody
	public String ajaxWaitEvalOrderList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getWaitEvalList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	
	/**
	 * 根据订单Id获取详情--包括订单所有商品，订单信息及地址
	 * @param oId
	 * @return
	 */
	@RequestMapping("ajaxOrderDetail.api")
	@ResponseBody
	public String ajaxOrderDetail(Long orderId){
		Map<String,Object> orderMap = orderProcess.getOrderDetailById(orderId);
		return genSuccessResult(orderMap);
	}
	/**
	 * 获取商品详情中的商品
	 * @param orderId
	 * @return
	 */
	@RequestMapping("ajaxOrderGoodsDetail.api")
	@ResponseBody
	public String ajaxOrderSingleDetail(Long orderId){
		List<Map<String,Object>> orderDetail = orderProcess.getOrderSingleDetailById(orderId);
		return genSuccessResult(orderDetail);
	}
	/**
	 * 获取订单相关属性
	 * @param orderId
	 * @return
	 */
	@RequestMapping("ajaxOrderInfo.api")
	@ResponseBody
	public String ajaxOrderInfo(Long orderId){
		//订单详情
	    TMallOrder order = orderProcess.getOrderInfoById(orderId);
	    return genSuccessResult(order);
	}
	/**
	 * 通过订单号获取订单所用地址
	 * @param orderId
	 * @return
	 */
	@RequestMapping("ajaxAddressInfo.api")
	@ResponseBody
	public String ajaxAddressInfo(Long orderId){
		Map<String,Object> result = orderProcess.getAddressInfoByOrderId(orderId);
		return genSuccessResult(result);
	}
	/**
	 * 添加收藏
	 * @param request
	 * @param gId
	 * @return
	 */
	@RequestMapping("addFavorite.api")
	@ResponseBody
	public String addFavorite(HttpServletRequest request,long gId){
		TMallUser user = CommonUtil.ValidateUser(request);
		favouriteService.addFavourite(user.getId(), gId);
		return genSuccessResult();
	}
	/**
	 * 取消收藏
	 * @param request
	 * @param gId
	 * @return
	 */
	@RequestMapping("reduceFavorite.api")
	@ResponseBody
	public String reduceFavorite(HttpServletRequest request,long gId){
		TMallUser user = CommonUtil.ValidateUser(request);
		favouriteService.reduceFavourite(user.getId(), gId);
		return genSuccessResult();
	}
	/**
	 * 取消收藏
	 * @param request
	 * @param gIds
	 * @return
	 */
	@RequestMapping("reduceFavorites.api")
	@ResponseBody
	public String reduceFavorite(HttpServletRequest request,String gIds){
		TMallUser user = CommonUtil.ValidateUser(request);
		favouriteService.reduceFavourites(user.getId(), gIds);
		return genSuccessResult();
	}
	/**
	 * 获取收藏商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxFavorites.api")
	@ResponseBody
	public String ajaxFavorites(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		List<Map<String, Object>> list = favouriteService.findFavouritesByUserId(user.getId());
		return genSuccessResult(list);
	}
	/**
	 * 查看是否收藏
	 * @param request
	 * @param gId
	 * @return
	 */
	@RequestMapping("ajaxIsFavorite.api")
	@ResponseBody
	public String ajaxIsFavorite(HttpServletRequest request,long gId){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,String> map = favouriteService.findGidIsFavourite(user.getId(),gId);
		return genSuccessResult(map);
	}
	
	/**
	 * 支付宝签名
	 * @param signDetail
	 * @return
	 */
	@RequestMapping("/alipaySign.api")
	@ResponseBody
	public String alipaySign(HttpServletRequest request,String data){
		/*Type mapType=new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> param = new Gson().fromJson(signDetail, mapType);*/
		/*Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		String sign = AlipaySubmit.buildRequestMysign(params);
		
		*/
		logger.info("######内容"+data);
		Map<String,String> result = new HashMap<String,String>();
		String sign = RSA.sign(data, AlipayConfig.private_key, AlipayConfig.input_charset);
		result.put("sign", sign);
		result.put("sign_type", AlipayConfig.sign_type);
		result.put("notify_url", AlipayConfig.notify_url);
		return genSuccessResult(result);
	}
	/**
	 * 提交退货请求
	 * 买家提交包括原因和图片在内的退货请求。插入一条退款请求到t_mall_return_record表中， 并把订单详情
	 * 的状态改为退货申请中。
	 * @param request
	 * @param orderId
	 * @param gId
	 * @param propertyId
	 * @param type
	 * @param imgUrls
	 * @param reason
	 * @return
	 */
	@RequestMapping("/ajaxReturnGoods.api")
	@ResponseBody
	public String ajaxReturnGoods(HttpServletRequest request,Long orderId,Long gId,Long propertyId,
			@RequestParam(value="type",required=false) Integer type,String imgUrls,String reason){
		Map<String,String> result = orderProcess.requestReturnOrder(orderId, gId, propertyId,imgUrls, reason);
		return genSuccessResult(result);
	}
	/**
	 * 取消订单
	 * @param request
	 * @param orderId
	 * @param reason
	 * @return
	 */
	@RequestMapping("/ajaxCancelOrder.api")
	@ResponseBody
	public String ajaxCancelOrder(HttpServletRequest request,Long orderId,String reason){
		Map<String,String> result = orderProcess.candelOrder(orderId, reason);
		String status = result.remove("status");
		return "0".equals(status) ? genSuccessResult(result) : genFailResult(result);
	}
	/**
	 * 添加评价
	 * @param request
	 * @param orderId
	 * @param gId
	 * @param content
	 * @param rating
	 * @return
	 */
	@RequestMapping("/ajaxAddEvaluation.api")
	@ResponseBody
	public String ajaxAddEvaluation(HttpServletRequest request,Long orderId,Long gId,String content,String rating,String Icon){
		Map<String,String> result = evaluateService.save(orderId, gId, content, rating,Icon);
		return genSuccessResult(result);
	}
	/**
	 * 获取退货记录
	 * @param request
	 * @param orderId
	 * @param gId
	 * @param content
	 * @param rating
	 * @return
	 */
	@RequestMapping("/ajaxReturnList.api")
	@ResponseBody
	public String getReturnListByUserId(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		List<Map<String,Object>> result = orderProcess.getReturnListByUserId(user.getId());
		return genSuccessResult(result);
	}
	
	/**
	 * 可退货列表
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxCanReturnList.api")
	@ResponseBody
	public String ajaxCanReturnList(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<Long,List<Map<String,Object>>> mapList = orderProcess.getWaitEvalList(user.getId());
		String result = genSuccessOrderList(mapList);
		return result;
	}
	/**
	 * 退货详情
	 * @param request
	 * @param rId
	 * @return
	 */
	@RequestMapping("/ajaxReturnGoodDetail.api")
	@ResponseBody
	public String ajaxReturnGoodDetail(HttpServletRequest request,Long rId){
		TMallUser user = CommonUtil.ValidateUser(request);
		Map<String,Object> result = orderProcess.getReturnDetailId(rId);
		return genSuccessResult(result);
	}
	/**
	 * 确认收货
	 * @param request
	 * @param detailId
	 * @return
	 */
	@RequestMapping("/ajaxConfirmGoodDetail.api")
	@ResponseBody
	public String ajaxConfirmGoodDetail(HttpServletRequest request,Long detailId){
		boolean isSuc = orderProcess.ajaxConfirmGoodDetail(detailId);
		if(isSuc){
			return genSuccessResult();
		}
		return genFailResult();
	}
	/**
	 * 相关分类订单数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxOrderNum.api")
	@ResponseBody
	public String ajaxOrderNum(HttpServletRequest request){
		TMallUser user = CommonUtil.ValidateUser(request);
		long id = user.getId();
		int count = 0;
		Map<String,Integer> model = new HashMap<String,Integer>();
		// 待支付
		List<Map<String, Object>> list1 = orderProcess.WaitPayOrderList(id);
		model.put("waitPayCount", list1.size());
		// 待发货
		List<Map<String, Object>> list2 = orderProcess
				.WaitSendOrderList(id);
		for (Map<String, Object> map : list2) {
			List<Map<String, Object>> details = (List<Map<String, Object>>) map
					.get("details");
			count += details.size();
		}
		model.put("waitSendCount", count);
		// 待收货
		count = 0;
		List<Map<String, Object>> list3 = orderProcess.WaitGetOrderList(id);
		for (Map<String, Object> map : list3) {
			List<Map<String, Object>> details = (List<Map<String, Object>>) map
					.get("details");
			count += details.size();
		}
		model.put("waitGetCount", count);
		// 待评价
		count = 0;
		List<Map<String, Object>> list4 = orderProcess.WaitEvalList(id);
		for (Map<String, Object> map : list4) {
			List<Map<String, Object>> details = (List<Map<String, Object>>) map
					.get("details");
			count += details.size();
		}
		model.put("waitEvalCount", count);

		//收藏
		List<Map<String, Object>> list = favouriteService
				.findFavouritesByUserId(user.getId());
		model.put("favorCount", list.size());

		// 退换货
		int returnNum = 0;
		List<Map<String, Object>> list5 = orderProcess.getAllOrderList(id);
		for (int i = 0; i < list5.size(); i++) {
			List<Map<String, Object>> details = (List<Map<String, Object>>) list5
					.get(i).get("details");
			for (int e = 0; e < details.size(); e++) {
				String detalStatus = (String) details.get(e).get(
						"detailStatus");
				if (detalStatus.equals(OrderDetailStatus.RETURN_REQUEST
						.getCode())
						|| detalStatus
								.equals(OrderDetailStatus.RETURN_IN_PROGRESS
										.getCode())
						|| detalStatus
								.equals(OrderDetailStatus.CASHBACK_APPROVED
										.getCode()))
					returnNum++;
			}
		}
		model.put("returnCount", returnNum);
		return genSuccessResult(model);
	}
	
	/**
	 * 微信app支付签名
	 * @param request
	 * @param data
	 * @return
	 */
	@RequestMapping("/wxpaySign.api")
	@ResponseBody
	public String wxpaySign(HttpServletRequest request,String data){
		String resultJson = "";
		try {
			String url = request.getRequestURL().toString();// 获取当前路径
			String ip = request.getLocalAddr();// 获取URL地址
			String order_no = request.getParameter("order_no");
			String pay_amt = request.getParameter("pay_amt");
			if(!StringUtils.isNotBlank(pay_amt)){
				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("flog", "FAIL");
				packageParams.put("message", "金额错误请重新支付！");
				resultJson = new Gson().toJson(packageParams);
				return resultJson;
			}
			AppWxPayConfig wpc = new AppWxPayConfig();
			String xmlResult = wpc.GetUnifiedOrderResultApi(order_no, url,ip, Double.valueOf(pay_amt));
			// --------------------------------生成完成---------------------------------------------  
			Document doc = DocumentHelper.parseText(xmlResult);
			Element root = doc.getRootElement();
//			String return_code = root.elementText("return_code");
			String result_code = root.elementText("result_code");
//			String return_msg = root.elementText("return_msg");
			String prepay_id = root.elementText("prepay_id");
			if (("SUCCESS").equals(result_code)) {
	
				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("appid", AppWxPayConfig.AppID);
				packageParams.put("noncestr", System.currentTimeMillis() + "");
				packageParams.put("package", "Sign=WXpay");
				packageParams.put("partnerid", AppWxPayConfig.MCHID);
				packageParams.put("prepayid", prepay_id);
				packageParams.put("timestamp", System.currentTimeMillis()/ 1000 + "");
				String packageSign = wpc.MakeSign(packageParams);
				packageParams.put("sign", packageSign);
				packageParams.put("signType", "MD5");
				packageParams.put("flog", result_code);
				resultJson = new Gson().toJson(packageParams);
	
			}else{
				//支付失败
				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("flog", result_code);
				resultJson = new Gson().toJson(packageParams);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.println(resultJson);
		 return resultJson;
	}
}
