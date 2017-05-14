package com.troika.emall.service.impl;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.common.CashStatus;
import com.troika.emall.common.CommissionState;
import com.troika.emall.dao.CashDao;
import com.troika.emall.model.TMallCommissionRecord;
import com.troika.emall.model.TMallMakerCommissionRecord;
import com.troika.emall.model.TMallUser;
import com.troika.emall.model.TMallUserCashRecord;
import com.troika.emall.service.CashService;

@Service
public class CashServiceImpl implements CashService {

	@Autowired
	private CashDao cashDao;
	
	@Override
	/**
	 * 获取用户分享部份的佣金
	 */
	public BigDecimal findShareCommissionByUserId(int userId, String state) {
		List<TMallCommissionRecord> list = cashDao.findShareCommissionByUserId(userId, state);
		BigDecimal sum = new BigDecimal(0);
		for (TMallCommissionRecord cr : list) {
			sum = sum.add(cr.getAmount());
		}
		sum = sum!=null?sum:new BigDecimal("0");
		return sum;
	}

	@Override
	public void saveUserCashRecord(TMallUserCashRecord record) {
		cashDao.saveUserCashRecord(record);
	}

	@Override
	/**
	 * 提现处理中 =  t_mall_user_cash_record里的提现处理中的总额；
	 */
	public BigDecimal findProcessCashSum(long userId,String status) {
		return cashDao.findProcessCashSum(userId,status);
	}

	@Override
	/**
	 * 获取创客部份的佣金
	 */
	public BigDecimal findMakerCommissionByMakerId(String makerId, String state) {
		List<TMallMakerCommissionRecord> list = cashDao.findMakerCommissionByMakerId(makerId, state);
		BigDecimal sum = new BigDecimal(0);
		for (TMallMakerCommissionRecord mcr : list) {
			sum = sum.add(mcr.getAmount());
		}
		sum = sum!=null? sum: new BigDecimal("0");
		return sum;
	}

	@Override
	/**
	 * 获取账户余额
	 * 账户余额 = （总的分享佣金 + 总的创客佣金) - t_mall_user_cash_record里的已完成的提现总额；
	 * TODO
	 */
	public BigDecimal findTotalBalance(TMallUser user) {
		BigDecimal total = new BigDecimal(0);
		BigDecimal share = findShareCommissionByUserId((int)user.getId(), CommissionState.COMPLETE.getCode());
		BigDecimal maker = findMakerCommissionByMakerId(user.getPhone(), CommissionState.COMPLETE.getCode());
		BigDecimal processed = findProcessCashSum(user.getId(), CashStatus.APPROVAL.getCode());
		total = share.add(maker).subtract(processed);
		return total;
	}

	@Override
	/**
	 * 获取可用余额
	 * 可用余额 = 账户余额 - t_mall_user_cash_record里的提现处理中的总额；
	 * TODO
	 */
	public BigDecimal findAvailableBalance(TMallUser user) {
		BigDecimal total = findTotalBalance(user);
		BigDecimal processing = findProcessCashSum(user.getId(), CashStatus.APPLY.getCode());
		BigDecimal available = total.subtract(processing);
		return available;
	}

	@Override
	/**
	 * 获取用户的历史提现请求
	 * @param userId 用户ID
	 */
	public List<Map<String, Object>> getUserCashRecords(long userId) {
		List<Map<String, Object>> records = cashDao.getUserCashRecords(userId);
		return records;
	}
}
