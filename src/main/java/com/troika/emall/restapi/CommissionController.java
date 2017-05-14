package com.troika.emall.restapi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.service.CashService;

@Controller
@RequestMapping("/dollar/")
public class CommissionController extends BaseController {
	
	@Autowired
	private CashService cashService;

	@RequestMapping("/getShareCommission.api")
	@ResponseBody
	/**
	 * 获取用户的账户余额（分享佣金和创客佣金之和 - 已提现佣金）， 
	 * 
	 * @param userId
	 * @param state
	 * @return
	 */
	public String getCommission(String userId, char state) {
		Map<String, Object> commissionMap = new HashMap<String, Object>();
		// TODO 
		BigDecimal totalCommission = new BigDecimal(0);
		commissionMap.put("totalCommission", totalCommission);
		String resutJson = genSuccessResult(commissionMap);
		return resutJson;
	}
}
