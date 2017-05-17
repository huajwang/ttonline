package com.troika.emall.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.config.WxPayConfig;
import com.alipay.util.AlipaySubmit;
import com.google.gson.Gson;
import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.OrderProcess;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.service.GroupOrderService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {

	@Autowired
	private OrderProcess orderProcess;

	@Autowired
	AsyncSearch asyncSearch;

	@Autowired
	private GroupOrderService groupOrderService;// 订单Service

	private WxPayConfig wpc = new WxPayConfig();

	/**
	 * 提交订单
	 * 
	 * @param model
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/subOrder")
	public String subOrder(Model model, Long id) {
		Map<String, Object> OrderDetail = orderProcess.getOrderDetailById(id);
		TMallOrder order = (TMallOrder) OrderDetail.get("order");
		Map<String, Object> address = (Map<String, Object>) OrderDetail
				.get("address");
		List<Map<String, Object>> list = (List<Map<String, Object>>) OrderDetail
				.get("orderDetail");
		model.addAttribute("detail", list);
		model.addAttribute("order", order);
		model.addAttribute("address", address);
		return "orders/subOrder";
	}

	/**
	 * 订单支付
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("Payment")
	public String Payment(HttpServletRequest request, Model model, Long id,
			HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			if (code != null) {
				String json = wpc.GetOpenidAndAccessTokenFromCode(code);
				JSONObject obj = JSONObject.fromObject(json);
				String OpenId = obj.getString("openid");
				String groupon = request.getParameter("groupon");// 拼团传参过来
				// 判断是否由拼团那边发起支付
				if (groupon == null)
					response.sendRedirect("PaymentByWX?id=" + id + "&OpenId="
							+ OpenId);
				else
					response.sendRedirect("PaymentByWX?id=" + id + "&OpenId="
							+ OpenId + "&groupon=" + groupon);
				return null;
			}
			Map<String, Object> OrderDetail = orderProcess
					.getOrderDetailById(id);
			TMallOrder order = (TMallOrder) OrderDetail.get("order");
			// 支付宝字符串
			{
				String sHtmlText = getsHtmlText(order);// 支付宝字符串
				model.addAttribute("sHtmlText", sHtmlText);
			}
			model.addAttribute("order", order);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "orders/Payment";
	}

	/**
	 * 获取支付宝支付Html字符串
	 * 
	 * @param order
	 * @return
	 */
	public String getsHtmlText(TMallOrder order) {
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = String.valueOf(order.getId());
		// 订单编号
		String subject = order.getOrderId();

		// 付款金额，必填
		String total_fee = order.getAmount().toString();
		// 收银台页面上，商品展示的超链接，必填
		String show_url = "";
		// 商品描述，可空
		String body = "";

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		// sParaTemp.put("app_pay","Y");//启用此参数可唤起钱包APP支付。
		sParaTemp.put("body", body);
		// 其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
		// 如sParaTemp.put("参数名","参数值");

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}

	/**
	 * 微信订单支付
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("PaymentByWX")
	public String PaymentByWX(HttpServletRequest request, Model model, Long id,
			String OpenId) {
		String groupon = request.getParameter("groupon");// 拼团传参过来
		if (groupon == null) {
			Map<String, Object> OrderDetail = orderProcess
					.getOrderDetailById(id);
			TMallOrder order = (TMallOrder) OrderDetail.get("order");
			model.addAttribute("order", order);
			model.addAttribute("orderId", order.getOrderId());// 订单编号
		} else {
			TGrouponOrder order = groupOrderService
					.findOrderById(id.intValue());
			model.addAttribute("order", order);
			model.addAttribute("orderId", order.getOrderno());// 订单编号
			model.addAttribute("groupon", groupon);// 拼团参数
		}
		model.addAttribute("OpenId", OpenId);
		return "orders/Payment";
	}

	/**
	 * 调用微信支付
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/WxPay.api")
	@ResponseBody
	public String WxPay(HttpServletRequest request, long id) {
		String groupon = request.getParameter("groupon");// 拼团传参过来
		String url = "";
		if (groupon == null)
			url = "http://www.xmtroika.com/ttmall/orders/Payment?id=" + id;
		else
			// 拼团
			url = "http://www.xmtroika.com/ttmall/orders/Payment?id=" + id
					+ "&groupon=" + groupon;
		String redirect_uri = URLEncoder.encode(url);
		String result = wpc.getCode(redirect_uri);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", result);
		String resultJson = new Gson().toJson(map);
		return resultJson;
	}

	/**
	 * 统一下单
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/OrderResult")
	@ResponseBody
	public String OrderResult(HttpServletRequest request, String OpenId,
			String orderId, double Money) {
		String resultJson = "";
		try {
			String url = request.getRequestURL().toString();// 获取当前路径
			String ip = request.getLocalAddr();// 获取URL地址
			String xmlResult = wpc.GetUnifiedOrderResult(OpenId, orderId, url,
					ip, Money);

			Document doc = DocumentHelper.parseText(xmlResult);
			Element root = doc.getRootElement();
			String return_code = root.elementText("return_code");
			String result_code = root.elementText("result_code");
			String return_msg = root.elementText("return_msg");
			String prepay_id = root.elementText("prepay_id");

			if (("SUCCESS").equals(result_code)) {

				Map<String, String> packageParams = new HashMap<String, String>();
				packageParams.put("appId", WxPayConfig.AppID);
				packageParams.put("timeStamp", System.currentTimeMillis()
						/ 1000 + "");
				packageParams.put("nonceStr", System.currentTimeMillis() + "");
				packageParams.put("package", "prepay_id=" + prepay_id);
				packageParams.put("signType", "MD5");
				String packageSign = wpc.MakeSign(packageParams);
				packageParams.put("paySign", packageSign);

				resultJson = new Gson().toJson(packageParams);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}

	/**
	 * 支付成功后，回调修改订单状态
	 * 
	 * @param request
	 * @param id
	 *            订单号
	 * @return
	 */
//	@RequestMapping("/updateOrder")
//	@ResponseBody
//	public String updateOrder(HttpServletRequest request, long id) {
//		String resultJson = "";
//		try {
//			logger.debug("订单支付完成，更新订单状态为已支付...");
//			orderProcess.updateOrderStatus(id, "1");
//			logger.debug("完成更新订单状态为已支付");
//			// 注意： 这里的orderId 是订单编号 。 例如 201607290415590008
//			String orderId = orderProcess.getOrderInfoById(id).getOrderId();
//			logger.debug("通过微信通知厂商,订单介绍人: " + orderId);
//			ListenableFuture<String> introducerFuture = asyncSearch
//					.asyncNotifyIntroducer(orderId);
//			asyncSearch.addCallback(introducerFuture, "通过微信通知邀请人成功",
//					"通过微信通知邀请人失败");
//			ListenableFuture<String> VendorsFuture = asyncSearch
//					.asyncNotifyVendors(orderId);
//			asyncSearch.addCallback(VendorsFuture, "通过微信通知厂商成功", "通过微信通知厂商失败");
//			logger.debug("async notification done!");
//			resultJson = genSuccessResult();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultJson;
//	}

	/**
	 * 修改订单详情状态
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateOrderDetailState")
	@ResponseBody
	public String updateOrderDetailState(HttpServletRequest request, long id) {
		String resultJson = "";
		try {
			TMallOrderDetail orderdetail = orderProcess.findOrderDetailById(id);
			orderdetail.setDetailStatus(OrderDetailStatus.EVALUATE.getCode());
			orderProcess.saveOrUpdateOneOrderDetail(orderdetail);
			resultJson = genSuccessResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}

	/**
	 * 支付宝支付成功后
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/alipaySuccess")
	public void alipaySuccess(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 订单主键Id
		Long id = Long.valueOf(request.getParameter("out_trade_no").toString());
		// 支付宝交易号
		String trade_no = request.getParameter("trade_no").toString();
		try {
			String orderId = orderProcess.getOrderInfoById(id).getOrderId();
			//修改订单状态并推送微信消息
			orderProcess.updateOrderWithPaid(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("../member/");
	}
}
