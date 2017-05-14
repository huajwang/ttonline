package com.troika.groupon.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.config.WxPayConfig;
import com.alipay.util.AlipaySubmit;
import com.google.gson.Gson;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.util.Constant;
import com.troika.groupon.model.TGroupFollowRecord;
import com.troika.groupon.model.TGrouponComplain;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.service.GroupOrderService;
import com.troika.groupon.service.GroupService;

@Controller
@RequestMapping("/groupon")
public class GHomeController extends BaseController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private AddressService addressService;// 收货地址

	@Autowired
	private GroupOrderService groupOrderService;// 订单Service

	private WxPayConfig wpc = new WxPayConfig();// 微信接口

	@Autowired
	GrouponAsyncSearch asyncSearch;// 异步推送消息

	/**
	 * 拼团首页
	 * 
	 * */

	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		List<Map<String, Object>> list = groupService.getImg();// 获取首页轮播图片
		model.addAttribute("ImgList", list);
		// 获取最新的项目列表
		List<Map<String, Object>> NewProjectList = groupService
				.getNewProjectByList();
		model.addAttribute("NewProjectList", NewProjectList);

		// 获取精选项目列表
		List<Map<String, Object>> SelectedProjectList = groupService
				.getSelectProjectByList();
		model.addAttribute("SelectedProjectList", SelectedProjectList);

		return "groupon/home/index";
	}

	/**
	 * 项目拼团详情
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/homeprojectdetail")
	public String homeprojectdetail(int id, HttpServletRequest request,
			Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		Map<String, Object> project = groupService.getProject(id);
		// 获取项目进度
		List<Map<String, Object>> schedule = groupService
				.getScheduleAndComments(id);
		// 获取项目图片列表
		List<Map<String, String>> imgList = new ArrayList<>();
		for (Map<String, Object> img : (List<Map<String, Object>>) project
				.get("ImgList")) {
			Map<String, String> nimg = new HashMap<>();
			String IsDisplay = img.get("IsDisplay").toString();
			String Icon = img.get("Icon").toString();
			nimg.put("IsDisplay", IsDisplay);
			nimg.put("Icon", Icon);
			imgList.add(nimg);
		}
		model.addAttribute("ImgList", imgList);

		// 判断订单产品剩余份数
		for (Map<String, Object> product : (List<Map<String, Object>>) project
				.get("product")) {
			product.put("surplus", (int) product.get("num"));
			for (Map<String, Object> order : (List<Map<String, Object>>) project
					.get("OrderList")) {
				for (Map<String, Object> orderDetail : (List<Map<String, Object>>) order
						.get("productList")) {
					int dpid = (int) orderDetail.get("productId");
					int pid = (int) product.get("id");
					if (dpid == pid) {
						int surplus = (int) product.get("surplus");
						int sold = (int) orderDetail.get("num");
						product.put("surplus", surplus - sold);
					}
				}
			}
		}

		// 判断用户是否关注
		TGroupFollowRecord focus = new TGroupFollowRecord();
		try {
			focus = groupService.getRecord((int) user.getId(), id);
			model.addAttribute("userId", user.getId());//当前用户的主键Id
		} catch (Exception e) {
			focus = null;
		}
		model.addAttribute("focus", focus);
		model.addAttribute("schedule", schedule);
		model.addAttribute("project", project);

		return "groupon/home/index_project_detail";
	}

	/**
	 * 项目举报
	 * 
	 * */
	@RequestMapping(value = "/homeprojectreport")
	public String homeprojectreport(int id, HttpServletRequest request,
			Model model) {
		model.addAttribute("projectId", id);
		return "groupon/home/index_project_report";
	}

	/**
	 * 举报处理
	 */
	@RequestMapping(value = "/submitprojectreport")
	@ResponseBody
	public String submitprojectreport(TGrouponComplain complain, String images,
			HttpServletRequest request) {
		// try{
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		List<String> icons = new ArrayList<String>();
		if (!images.trim().equals("")) {
			String icon[] = images.trim().split(" ");
			for (String item : icon)
				icons.add(item);
		}
		boolean result = groupService.addComplain(complain.getProjectId(),
				(int) user.getId(), user.getPhone(), complain.getRealName(),
				complain.getIDCare(), complain.getContent(), icons);
		if (result)
			return genSuccessResult();
		else
			return genFailResult();
		// }catch(Exception e){
		// return genFailResult();
		// }

	}

	/**
	 * 支持 contactName province city area streetAddr
	 * */
	@RequestMapping(value = "/homeprojectsupport")
	public String homeprojectsupportt(int id, HttpServletRequest request,
			Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		int addrId = 0;
		if (request.getParameter("addrId") == null) {
			if (user != null) {
				Map<String, Object> map = addressService.findActiveAddress(user
						.getId());// 获取默认地址
				if (map.size() != 0) {// 判断没有默认地址
					addrId = Integer.valueOf(map.get("id").toString());
					model.addAttribute("addrInfor", map);// 地址信息
				}
			}
		} else {
			addrId = Integer.valueOf(request.getParameter("addrId"));// 重新选择地址
			Map<String, Object> map = addressService.findAddressById(addrId);
			model.addAttribute("addrInfor", map);// 地址信息
		}
		if (user != null) {
			model.addAttribute("userId", user.getId());// 当前用户Id
		}
		Map<String, Object> project = groupService.getProject(id);
		model.addAttribute("product", project.get("product"));// 产品列表
		model.addAttribute("projectId", id);// 项目主键
		model.addAttribute("addrId", addrId);// 收货地址

		return "groupon/home/index_project_support";
	}

	/**
	 * 收货人信息
	 */
	@RequestMapping(value = "/homeprojectadress")
	public String homeprojectadress(HttpServletRequest request, Model model) {
		return "groupon/home/index_project_adress";
	}

	/**
	 * 调用微信支付
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/WxPay.api")
	@ResponseBody
	public String WxPay(long orderId) {
		String url = "http://www.xmtroika.com/ttmall/groupon/homeprojectorderpay?orderId="
				+ orderId;
		String redirect_uri = URLEncoder.encode(url);
		String result = wpc.getCode(redirect_uri);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", result);
		String resultJson = new Gson().toJson(map);
		return resultJson;
	}

	/**
	 * 支付
	 */
	@RequestMapping(value = "/homeprojectorderpay")
	public String homeprojectorderpay(HttpServletResponse response,
			HttpServletRequest request, Model model, int orderId) {
		TGrouponOrder order = groupOrderService.findOrderById(orderId);
		model.addAttribute("order", order);
		try {
			String code = request.getParameter("code");
			if (code != null) {
				String json = wpc.GetOpenidAndAccessTokenFromCode(code);
				JSONObject obj = JSONObject.fromObject(json);
				String OpenId = obj.getString("openid");
				response.sendRedirect("WXPay?orderId=" + orderId + "&OpenId="
						+ OpenId);
				return null;
			}else{
				// 支付宝字符串
				{
					String sHtmlText = getsHtmlText(order);// 支付宝字符串
					model.addAttribute("sHtmlText", sHtmlText);
				}
				model.addAttribute("order", order);
			}
		} catch (Exception ex) {
		}
		return "groupon/home/index_project_orderpay";
	}
	/**
	 * 获取支付宝支付Html字符串
	 * 
	 * @param order
	 * @return
	 */
	public String getsHtmlText(TGrouponOrder order) {
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = String.valueOf(order.getId());
		// 订单编号
		String subject = order.getOrderno();

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
		sParaTemp.put("return_url", AlipayConfig.return_url1);
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
	@RequestMapping("WXPay")
	public String WXPay(HttpServletRequest request, Model model, int orderId,
			String OpenId) {
		TGrouponOrder order = groupOrderService.findOrderById(orderId);
		model.addAttribute("order", order);
		model.addAttribute("OpenId", OpenId);// 微信OpenId
		return "groupon/home/index_project_orderpay";
	}

	/**
	 * 添加新地址
	 */
	@RequestMapping(value = "/homeprojectaddadress")
	public String homeprojectaddadress(HttpServletRequest request, Model model) {
		return "groupon/home/index_project_addadress";
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
	 * @return
	 */
//	@RequestMapping("/updateOrder")
//	@ResponseBody
//	public String updateOrder(HttpServletRequest request, int orderId) {
//		String resultJson = "";
//		try {
//			TGrouponOrder order = groupOrderService.findOrderById(orderId);
//			order.setStatus("1");// 支付成功
//			groupOrderService.saveOrUpdate(order);
//			resultJson = genSuccessResult();
//			// 微信推送到项目发起人
//			asyncSearch.asyncNotifyVendors(order.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultJson;
//	}
	
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
		int id = Integer.valueOf(request.getParameter("out_trade_no").toString());
		// 支付宝交易号
		String trade_no = request.getParameter("trade_no").toString();
		try {
			TGrouponOrder order = groupOrderService.findOrderById(id);
			order.setStatus("1");// 支付成功
			groupOrderService.saveOrUpdate(order);
			// 微信推送到项目发起人
			asyncSearch.asyncNotifyVendors(order.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("../groupon/");
	}

}
