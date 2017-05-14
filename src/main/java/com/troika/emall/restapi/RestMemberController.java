package com.troika.emall.restapi;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.common.CashStatus;
import com.troika.emall.model.TMallUser;
import com.troika.emall.model.TMallUserCashRecord;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.CashService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/personal")
public class RestMemberController extends BaseController {

	@Autowired
	private TMallUserService tMallUserService;

	@Autowired
	private CashService cashService;

	/**
	 * 分享交易记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getShareTrades.api")
	@ResponseBody
	public String getTrade(HttpServletRequest request) {
		TMallUser user = CommonUtil.ValidateUser(request);
		List<Map<String, Object>> result = tMallUserService.getShareTrades(user
				.getId());
		return genSuccessResult(result);
	}

	/**
	 * 获取创客交易记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getMakerTrades.api")
	@ResponseBody
	public String getMakerTrade(HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> list = tMallUserService.getMakerTrades(user
				.getPhone());
		return genSuccessResult(list);
	}

	/**
	 * 获取用户钱包数据： 包括账户余额， 可用余额， 提现处理中金额； 以及微信， 支付宝账号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("wallet.api")
	@ResponseBody
	public String getWithdrawals(HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		BigDecimal total = cashService.findTotalBalance(user);
		BigDecimal available = cashService.findAvailableBalance(user);
		BigDecimal processing = cashService.findProcessCashSum(user.getId(), CashStatus.APPLY.getCode());
		// 钱包列表： 微信支付， 支付宝
		List<Map<String, Object>> wallets = tMallUserService.getUserWallets(user
				.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("available", available);
		map.put("process",processing);
		map.put("wallets", wallets);

		return genSuccessResult(map);
	}

	/**
	 * 修改微信或支付宝提现账号
	 * @param request
	 */
	@RequestMapping("updateWallet.api")
	@ResponseBody
	public String updateWallet(HttpServletRequest request, String alipay, String wx) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		tMallUserService.updateUserWallets(user.getId(), alipay, wx);
		return genSuccessResult();
	}

	/**
	 * 提交提现请求
	 * 
	 * @param request
	 * @param cashRecord 用户提现请求
	 */
	@RequestMapping(value = "requestWithdraw.api")
	@ResponseBody
	public String doWithdrawal(HttpServletRequest request, String accType, 
			String accNo, BigDecimal amount) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		// 获取可提现金额
		BigDecimal available = cashService.findAvailableBalance(user);
		if (amount.compareTo(available) > 0)
			return genFailResult();
		
		TMallUserCashRecord cashRecord = new TMallUserCashRecord();
		cashRecord.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		cashRecord.setStatus("0");
		cashRecord.setUserId(user.getId());
		cashService.saveUserCashRecord(cashRecord);
		return genSuccessResult();
	}

	/**
	 * 获取用户的提现记录 
	 * @param request
	 * @return
	 */
	@RequestMapping("getWithdrawalRecords.api")
	@ResponseBody
	public String getWithdrawalRecord(HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		List<Map<String, Object>> records = cashService.getUserCashRecords(user.getId());
		return genSuccessResult(records);
	}
	
	/**
	 * 完善用户资料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("perfectInfor.api")
	@ResponseBody
	public String perfectInfor(HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		return genSuccessResult(user);
	}
	
	/**
	 * 保存用户头像
	 */
	@RequestMapping("/replacePortrait.api")
	@ResponseBody
	public String ReplacePortrait(String iconUrl, HttpServletRequest request,
			HttpServletResponse response) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		tMallUserService.updateIconUrl(user.getId(), iconUrl);
		user.setIconUrl(iconUrl);
		request.getSession().setAttribute(Constant.LOGIN_USER, user);
		return genSuccessResult();
	}
	
	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping("/saveOrUpdateUser.api")
	@ResponseBody
	public String saveOrUpdateUser(HttpServletRequest request, TMallUser record) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		user.setRealName(record.getRealName());
		user.setSex(record.getSex());
		user.setUserName(record.getUserName());
		user.setEmail(record.getEmail());
		user.setUpdateTime(new Date());
		tMallUserService.saveOrUpdateUser(user);
		return genSuccessResult();
	}
	
	
	/**
	 * 收益记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("earningsRecord.api")
	@ResponseBody
	public String earningsRecord(HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		long userId = user.getId();
		String makerId = user.getPhone();
		Map<String, Object> profits = tMallUserService.getProfitRecord(userId,
				makerId);
		Map<String, Object> share1 = tMallUserService.getProfitSum(userId,
				makerId, 1);
		Map<String, Object> share7 = tMallUserService.getProfitSum(userId,
				makerId, 2);
		Map<String, Object> share30 = tMallUserService.getProfitSum(userId,
				makerId, 3);
		Map<String, Object> maker1 = tMallUserService.getProfitSum(userId,
				makerId, 4);
		Map<String, Object> maker7 = tMallUserService.getProfitSum(userId,
				makerId, 5);
		Map<String, Object> maker30 = tMallUserService.getProfitSum(userId,
				makerId, 6);
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", profits);
		result.put("share1", share1);
		result.put("share7", share7);
		result.put("share30", share30);
		result.put("maker1", maker1);
		result.put("maker7", maker7);
		result.put("maker30", maker30);
		return genSuccessResult(result);
	}

}
