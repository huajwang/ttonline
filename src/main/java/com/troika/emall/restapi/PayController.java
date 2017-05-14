package com.troika.emall.restapi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.AlipayNotify;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.PayService;
import com.troika.groupon.controller.GrouponAsyncSearch;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.service.GroupOrderService;

@Controller
@RequestMapping("/pay/")
public class PayController extends BaseController {
	@Autowired
	private PayService payService;
	@Autowired
	private OrderProcess orderProcess;// 订单Service
	@Autowired
	GrouponAsyncSearch gasyncSearch;// 拼团异步推送消息

	@Autowired
	private GroupOrderService groupOrderService;// 订单Service

	@SuppressWarnings("rawtypes")
	@RequestMapping("/alipay_notify_url.api")
	@ResponseBody
	public String alipayNotify(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		if (AlipayNotify.verify(params)) {// 验证通过
			logger.info("支付成功,数据存库;");
			payService.aplipaySave(params, true);
			return "success";
		}
		logger.info("验证交易失败");
		payService.aplipaySave(params, false);
		return "fail";
	}

	/**
	 * 微信app支付返回
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wxpay_notify_url.api", method = RequestMethod.POST)
	@ResponseBody
	public String wxpayNotifyUrl(HttpServletRequest request) {
		try {
			ServletInputStream mRead = request.getInputStream();
			int len = request.getContentLength();
			byte[] Buf = new byte[len];
			mRead.read(Buf, 0, len);
			String xml = new String(Buf, 0, len, "utf-8");
			xml = xml.replace("\n", "").replace(" ", "");
			logger.debug("微信支付返回结果XML：" + xml);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String result_code = root.elementText("result_code");// 返回支付结果
			String orderId = root.elementText("out_trade_no");// 订单编号
			logger.debug("返回支付结果：" + result_code);
			logger.debug("订单编号：" + orderId);

			if (("SUCCESS").equals(result_code)) {
				// 更新订单信息
				if (orderId.indexOf("g") < 0) {
					// 商城支付
					EmallMsg(orderId);
				} else {
					// 拼团支付
					GrouponMsg(orderId);
				}

			}
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA["
					+ e.toString() + "]]></return_msg></xml>";
		}
	}

	/**
	 * 商城——订单状态修改与推送消息功能
	 * 
	 * @param orderId
	 *            订单编号
	 */
	private void EmallMsg(String orderId) {
		orderProcess.updateOrderWithPaid(orderId);
	}

	/**
	 * 拼团——订单状态修改与推送消息功能
	 * 
	 * @param orderNo
	 */
	private void GrouponMsg(String orderNo) {

		TGrouponOrder order = groupOrderService.findOrderByOrderNo(orderNo);
		order.setStatus("1");// 支付成功
		groupOrderService.saveOrUpdate(order);
		// 微信推送到项目发起人
		gasyncSearch.asyncNotifyVendors(order.getId());

	}
}
