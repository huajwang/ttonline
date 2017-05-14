package com.troika.emall.web;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.troika.emall.common.CashStatus;
import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.model.TMallFeedback;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.model.TMallReturnRecord;
import com.troika.emall.model.TMallUser;
import com.troika.emall.model.TMallUserCashRecord;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.CartService;
import com.troika.emall.service.CashService;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.FavouriteService;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;
import com.troika.emall.util.HtmlUtil;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	@Autowired
	private OrderProcess orderProcess;

	@Autowired
	private TMallUserService tMallUserService;

	@Autowired
	private FavouriteService favouriteService;

	@Autowired
	private CashService cashService;

	@Autowired
	private EvaluateService evaluateService;

	@Autowired
	private CartService cartService;

	@SuppressWarnings("unchecked")
	@RequestMapping
	public String index(HttpServletRequest request, Model model, Principal principal) {
		System.out.println("pricipal name = " + principal.getName());
		TMallUser user = tMallUserService.findUserByUserName(principal.getName());// CommonUtil.ValidateUser(request);
		if (user == null) {
			model.addAttribute("path", "/member/");
			return "login";
		} else {
			if (user.getIconUrl() != null) {
				System.out.println("icon url = " + user.getIconUrl());
				if(!user.getIconUrl().contains("http://wx")){
					String arr[] = user.getIconUrl().split("/");
					user.setIconUrl(Constant.PHOTO_URL + arr[arr.length - 1]);
				}
			}
			model.addAttribute("user", user);
			long id = user.getId();
			int count = 0;

			if (request.getSession().getAttribute(Constant.USER_BALANCE) == null) {
				BigDecimal total = cashService.findTotalBalance(user);
				request.getSession().setAttribute(Constant.USER_BALANCE, total);
			}
			model.addAttribute("balance",
					request.getSession().getAttribute(Constant.USER_BALANCE));
			// 待支付
			List<Map<String, Object>> list1 = orderProcess.WaitPayOrderList(id);
			model.addAttribute("order1", list1.size());
			// 待发货
			List<Map<String, Object>> list2 = orderProcess
					.WaitSendOrderList(id);
			for (Map<String, Object> map : list2) {
				List<Map<String, Object>> details = (List<Map<String, Object>>) map
						.get("details");
				count += details.size();
			}
			model.addAttribute("order2", count);
			// 待收货
			count = 0;
			List<Map<String, Object>> list3 = orderProcess.WaitGetOrderList(id);
			for (Map<String, Object> map : list3) {
				List<Map<String, Object>> details = (List<Map<String, Object>>) map
						.get("details");
				count += details.size();
			}
			model.addAttribute("order3", count);
			// 待评价
			count = 0;
			List<Map<String, Object>> list4 = orderProcess.WaitEvalList(id);
			for (Map<String, Object> map : list4) {
				List<Map<String, Object>> details = (List<Map<String, Object>>) map
						.get("details");
				count += details.size();
			}
			model.addAttribute("order4", count);

			// 收藏
			Long userId = user.getId();
			List<Map<String, Object>> list = favouriteService
					.findFavouritesByUserId(user.getId());
			model.addAttribute("goodsize", list.size());

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
			model.addAttribute("order5", returnNum);
			// 显示购物车商品数量
			// 判断用户是否登录
			if (user != null) {
				List<Map<String, Object>> list11 = cartService
						.getCartListByUserId(user.getId());
				model.addAttribute("cartsize", list11.size());
				model.addAttribute("carts", list11);
			}

			return "member/index";
		}
	}

	/**
	 * 待支付
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getWaitPayOrderList")
	public String getWaitPayOrderList(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.WaitPayOrderList(user
				.getId());
		model.addAttribute("order", mapList);
		return "member/WaitPayOrder";
	}

	/**
	 * 待发货
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getWaitSendOrder")
	public String getWaitSendOrder(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.WaitSendOrderList(user
				.getId());
		model.addAttribute("order", mapList);
		return "member/WaitSendOrder";
	}

	/**
	 * 待收货
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getWaitGetOrder")
	public String getWaitGetOrder(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.WaitGetOrderList(user
				.getId());
		for (Map<String, Object> map : mapList) {
			List<Map<String, Object>> details = (List<Map<String, Object>>) map
					.get("details");
			if (details.size() > 0) {// 判断商品是否存在数据
				model.addAttribute("order", mapList);
				break;
			}
		}
		return "member/WaitGetOrder";
	}

	/**
	 * 待评价
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getWaitEvalOrder")
	public String getWaitEvalOrder(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.WaitEvalList(user
				.getId());
		model.addAttribute("order", mapList);
		return "member/WaitEvalOrder";
	}

	/**
	 * 退换货
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getReturn")
	public String getReturn(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.WaitEvalList(user
				.getId());
		model.addAttribute("list", mapList);
		return "member/Return";
	}

	/**
	 * 申请退货
	 */
	@RequestMapping("applyReturn")
	public String applyReturn(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		model.addAttribute("orderDetailId", request.getParameter("detailId"));
		// model.addAttribute("orderId", request.getParameter("orderId"));
		// model.addAttribute("gId", request.getParameter("gId"));
		// model.addAttribute("propertyId", request.getParameter("propertyId"));
		return "member/submitReturnRequest";
	}

	/**
	 * 提交退换货申请
	 */
	@RequestMapping("setReturnRecord")
	public void setReturnRecord(TMallReturnRecord record,
			HttpServletRequest request, HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		String picStr = request.getParameter("picStr");
		Map<String, String> result = orderProcess.requestReturnOrderObject(
				picStr, record);
		// Map<String, String> result =
		// orderProcess.requestReturnOrder(orderId,gId,propertyId,picStr,record.getCancelReason());
		Map<String, Object> resultUtil = new HashMap<String, Object>();
		resultUtil.put("result", result.get("status"));
		HtmlUtil.writerJson(response, resultUtil);
	}

	/**
	 * 申请退货成功
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("submit")
	public String submit(HttpServletRequest request, Model model) {
		return "member/submit";
	}

	/**
	 * 退换货记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getReturnRecord")
	public String getReturnRecord(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> result = orderProcess
				.getReturnListByUserId(user.getId());
		model.addAttribute("list", result);
		return "member/ReturnRecord";
	}

	/**
	 * 分享交易记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getTrade")
	public String getTrade(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getShareTrades(user
				.getId());
		model.addAttribute("list", list);
		return "member/ShareTrades";
	}

	/**
	 * 余额提现
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getWithdrawals")
	public String getWithdrawals(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getUserWallets(user
				.getId());
		BigDecimal total = cashService.findTotalBalance(user);
		BigDecimal available = cashService.findAvailableBalance(user);
		BigDecimal processing = cashService.findProcessCashSum(user.getId(),
				CashStatus.APPLY.getCode());
		model.addAttribute("total", total);
		model.addAttribute("available", available);
		model.addAttribute("process", processing);
		model.addAttribute("wallets", list);
		return "member/Withdrawals";
	}

	/**
	 * 设置钱包账号
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("setPayMethod")
	public String setPayMethod(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		String paymethod = request.getParameter("paymethod") == null ? ""
				: request.getParameter("paymethod");
		model.addAttribute("mymethod", paymethod);
		List<Map<String, Object>> list = tMallUserService.getUserWallets(user
				.getId());
		model.addAttribute("alipaywallet", list.get(0).get("alipaywallet"));
		model.addAttribute("wxwallet", list.get(0).get("wxwallet"));
		return "member/Paymethod";
	}

	/**
	 * 修改钱包属性
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping("updateWallet")
	public void updateWallet(HttpServletRequest request,
			HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getUserWallets(user
				.getId());
		String alipay = request.getParameter("alipay") != null ? request
				.getParameter("alipay") : (String) list.get(0).get(
						"alipaywallet");
				String wx = request.getParameter("wx") != null ? request
						.getParameter("wx") : (String) list.get(0).get("wxwallet");

						tMallUserService.updateUserWallets(user.getId(), alipay, wx);
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("result", "success");

						// 更新用户缓存数据
						user.setAlipaywallet(alipay);
						user.setWxwallet(wx);
						String token = CommonUtil.buildToken();
						request.getSession().removeAttribute(Constant.LOGIN_USER);
						request.getSession().setAttribute(Constant.LOGIN_TOKEN, token);
						request.getSession().setAttribute(Constant.LOGIN_USER, user);
						request.getSession().setAttribute(token, new Date());// token放入的时间

						HtmlUtil.writerJson(response, result);
	}

	/**
	 * 删除钱包
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteWallet")
	public void deleteWallet(HttpServletRequest request,
			HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getUserWallets(user
				.getId());
		String one = request.getParameter("delOne");
		if (one.equals("zfb")) {
			tMallUserService.updateUserWallets(user.getId(), "", (String) list
					.get(0).get("wxwallet"));
		} else if (one.equals("wx")) {
			tMallUserService.updateUserWallets(user.getId(),
					(String) list.get(0).get("alipaywallet"), "");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "success");
		HtmlUtil.writerJson(response, result);
	}

	/**
	 * 提交提现请求
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "doWithdrawal")
	public void doWithdrawal(TMallUserCashRecord tucr,
			HttpServletRequest request, HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取可提现金额
		BigDecimal available = cashService.findAvailableBalance(user);
		if (tucr.getAmount().compareTo(available) > 0) {
			result.put("result", "failure");
		} else {
			tucr.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			tucr.setStatus("0");
			tucr.setUserId(user.getId());
			cashService.saveUserCashRecord(tucr);
			result.put("result", "success");
		}
		HtmlUtil.writerJson(response, result);
	}

	/**
	 * 提现记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getWithdrawalRecord")
	public String getWithdrawalRecord(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = cashService.getUserCashRecords(user
				.getId());
		if (list.size() <= 0)
			return "member/WithdrawalRecord";
		for (Map item : list) {
			item.put("statusText", CashStatus.APPLY.getTextbyCode(String
					.valueOf(item.get("status"))));
		}
		model.addAttribute("list", list);
		return "member/WithdrawalRecords";
	}

	/**
	 * 意见反馈
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("Feedback")
	public String Feedback(HttpServletRequest request, Model model) {

		return "member/Feedback";
	}

	/**
	 * 收益记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("EarningsRecord")
	public String EarningsRecord(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		long userId = user.getId();
		String makerId = user.getPhone();
		Map<String, Object> map = tMallUserService.getProfitRecord(userId,
				makerId);
		Map<String, Object> map1 = tMallUserService.getProfitSum(userId,
				makerId, 1);
		Map<String, Object> map2 = tMallUserService.getProfitSum(userId,
				makerId, 2);
		Map<String, Object> map3 = tMallUserService.getProfitSum(userId,
				makerId, 3);
		Map<String, Object> map4 = tMallUserService.getProfitSum(userId,
				makerId, 4);
		Map<String, Object> map5 = tMallUserService.getProfitSum(userId,
				makerId, 5);
		Map<String, Object> map6 = tMallUserService.getProfitSum(userId,
				makerId, 6);
		model.addAttribute("map", map);
		model.addAttribute("map1", map1);
		model.addAttribute("map2", map2);
		model.addAttribute("map3", map3);
		model.addAttribute("map4", map4);
		model.addAttribute("map5", map5);
		model.addAttribute("map6", map6);
		return "member/EarningsRecord";
	}

	/**
	 * 完善用户资料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("PerfectInfor")
	public String PerfectInfor(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		model.addAttribute("user", user);
		return "member/PerfectInfor";
	}

	/**
	 * 保存用户头像
	 */
	@RequestMapping("/replacePortrait")
	public void ReplacePortrait(String iconUrl, HttpServletRequest request,
			HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		tMallUserService.updateIconUrl(user.getId(), iconUrl);
		user.setIconUrl(iconUrl);
		request.getSession().setAttribute(Constant.LOGIN_USER, user);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "success");
		HtmlUtil.writerJson(response, result);
	}

	/**
	 * 列出用户的订单历史（所有订单）
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("orderHistory")
	public String orderHistory(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> mapList = orderProcess.getAllOrderList(user
				.getId());
		List<Map<String, Object>> result = new ArrayList<>();
		int count = 0;
		for (Map<String, Object> map : mapList) {
			if (count < 21) {
				result.add(map);
				count++;
			}
		}

		model.addAttribute("order", result);
		return "member/orderHistory";
	}

	/**
	 * 我的收藏（收藏的商品)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myFavorite")
	public String myFavorite(HttpServletRequest request, Model model) {
		TMallUser user = CommonUtil.ValidateUser(request);
		Long userId = user.getId();
		List<Map<String, Object>> list = favouriteService
				.findFavouritesByUserId(user.getId());
		model.addAttribute("goods", list);
		// model.addAttribute("ossurl", Constant.PHOTO_URL);
		return "member/myFavorite";
	}

	@RequestMapping(value = "delMyFavorite")
	public void delMyFavorite(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = request.getParameter("gdsJSON");
		JSONObject json = JSONObject.parseObject(jsonStr);
		favouriteService.reduceFavourites(json);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "success");
		HtmlUtil.writerJson(response, result);
	}

	/**
	 * 保存用户的反馈意见
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping("/saveOrUpdateFeedback")
	@ResponseBody
	public String saveOrUpdateFeedback(HttpServletRequest request,
			TMallFeedback record) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		record.setUserId(user.getId());
		record.setCreateTime(new Date());
		tMallUserService.saveOrUpdateFeedback(record);
		return genSuccessResult();
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateUser", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateUser(HttpServletRequest request, TMallUser record) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		String userName = record.getUserName();
		String email = record.getEmail();
		String phone = record.getPhone();
		TMallUser tmu1 = tMallUserService.findUserByUserName(userName);
		if (tmu1 != null) {// 如果数据不为空
			if (tmu1.getId() != user.getId()) {
				Map<String, Object> map = new HashMap<>();
				map.put("msg", "该登录名已经被使用过了！");
				return genFailResult(map);
			}
		}
		
		// 先判断手机号码是否为空
		if(!StringUtils.isNullOrEmpty(phone)){
			TMallUser tmu3 = tMallUserService.findUserByPhone(phone);
			if (tmu3 != null){
				if(tmu3.getId() != user.getId()){
					Map<String, Object> map = new HashMap<>();
					map.put("msg", "该手机号码已经被使用过了！");
					return genFailResult(map);
				}
			}
		}
		
		// 先判断电子邮件是否为空
		if (!StringUtils.isNullOrEmpty(email)) {
			TMallUser tmu2 = tMallUserService.findUserByEmail(email);
			if (tmu2 != null) {// 如果数据不为空
				if (tmu2.getId() != user.getId()) {
					Map<String, Object> map = new HashMap<>();
					map.put("msg", "该电子邮件已经被使用过了！");
					return genFailResult(map);
				}
			}
		}
		if (!StringUtils.isNullOrEmpty(user.getIconUrl())) {
			String arr[] = user.getIconUrl().split("/");
			user.setIconUrl(arr[arr.length - 1]);
		}
		user.setRealName(record.getRealName());
		user.setSex(record.getSex());// 性别
		user.setUserName(userName);// 登录名
		if (!StringUtils.isNullOrEmpty(email))
			user.setEmail(email);// 电子邮件
		if(!StringUtils.isNullOrEmpty(phone))
			user.setPhone(phone);
		user.setUpdateTime(new Date());
		tMallUserService.saveOrUpdateUser(user);
		return genSuccessResult();
	}

	/**
	 * 获取创客交易记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getMakerTrade")
	public String getMakerTrade(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getMakerTrades(user
				.getPhone());
		model.addAttribute("list", list);
		return "member/Trades";
	}

	/**
	 * 说明与反馈页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("ExplainFeedback")
	public String DescriptionFeedback(HttpServletRequest request, Model model) {

		return "member/ExplainFeedback";
	}

	/**
	 * 购物与退货说明
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("explain")
	public String explain(HttpServletRequest request, Model model) {
		return "member/Explain";
	}

	/**
	 * 推荐佣金说明
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("commission")
	public String commission(HttpServletRequest request, Model model) {
		return "member/commission";
	}

	/**
	 * 创客代理说明
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("maker")
	public String maker(HttpServletRequest request, Model model) {
		return "member/maker";
	}

	/**
	 * 厂家免费入驻说明
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("factory")
	public String factory(HttpServletRequest request, Model model) {
		return "member/factory";
	}

	/**
	 * 提现说明
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("money")
	public String money(HttpServletRequest request, Model model) {
		return "member/money";
	}

	/**
	 * 发表评价
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("Evaluate")
	public String Evaluate(HttpServletRequest request, Model model,
			long orderId, long gid) {
		System.out.println("orderId = " + orderId);
		List<Map<String, Object>> list = orderProcess
				.getOrderSingleDetailById(orderId);
		for (Map<String, Object> map : list) {
			if (gid == Long.valueOf(map.get("id").toString())) {// 获取当前商品的属性
				model.addAttribute("gName", map.get("gName"));
				model.addAttribute("iconUrl", map.get("iconUrl"));
				Map<String, Object> Property = (Map<String, Object>) map
						.get("goodsProperty");
				if (Property != null && Property.size() > 0) {
					if (Property.get("colour") != null)
						model.addAttribute("colour", Property.get("colour"));
					if (Property.get("size") != null)
						model.addAttribute("size", Property.get("size"));
					if (Property.get("style") != null)
						model.addAttribute("style", Property.get("style"));
				}
				break;
			}
		}
		return "member/Evaluate";
	}

	/**
	 * 去评价
	 * 
	 * @param request
	 * @param orderId
	 *            订单Id
	 * @param gId
	 *            商品Id
	 * @param content
	 *            评价内容
	 * @param rating
	 *            评价等级
	 * @param detailId
	 *            订单详情Id
	 * @return
	 */
	@RequestMapping("/ajaxAddEvaluation.api")
	@ResponseBody
	public String ajaxAddEvaluation(HttpServletRequest request, Long orderId,
			Long gId, String rating, long detailId, String Icon) {
		try {
			String content = URLDecoder.decode(request.getParameter("content"),
					"UTF-8");
			Map<String, String> result = evaluateService.save(orderId, gId,
					content, rating, Icon);
			// 修改订单详情状态数据：为完成
			TMallOrderDetail orderdetail = orderProcess
					.findOrderDetailById(detailId);
			orderdetail.setDetailStatus(OrderDetailStatus.FINISH.getCode());
			orderProcess.saveOrUpdateOneOrderDetail(orderdetail);
			// 判断所有商品详情数据状态已完成 修改订单数据状态
			Boolean r = true;
			List<Map<String, Object>> list = orderProcess
					.getOrderSingleDetailById(orderId);
			for (Map<String, Object> map : list) {
				int detailStatus = Integer.parseInt(map.get("detailStatus")
						.toString());
				if (detailStatus < 5) {
					r = false;
					break;
				}

			}
			// 修改订单状态数据 完成
			if (r) {
				TMallOrder order = orderProcess.getOrderInfoById(orderId);
				order.setOrderStatus("2");// 订单完成
				orderProcess.saveOrUpdateOrder(order);
			}
			return genSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return genFailResult();
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("retrievePassword")
	public String retrievePassword(Model model) {
		return "member/backpasswordtwo";
	}

	/**
	 * 短信验证通过，进入设置密码页面
	 * 
	 * @param phone
	 * @param request
	 * @param response
	 */
	@RequestMapping("resetPassword")
	public String resetPassword(String phone, HttpServletRequest request,
			Model model) {
		model.addAttribute("phone", phone);
		String registerCode = (String) request.getSession().getAttribute(
				Constant.REGISTER_CODE);
		model.addAttribute("registerCode", registerCode);
		return "member/backpassword";
	}
}
