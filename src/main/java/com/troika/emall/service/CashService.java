package com.troika.emall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallUser;
import com.troika.emall.model.TMallUserCashRecord;

public interface CashService {
	public BigDecimal findShareCommissionByUserId(int userId, String state);
	public BigDecimal findMakerCommissionByMakerId(String makerId, String state);
	public BigDecimal findTotalBalance(TMallUser user);
	public BigDecimal findAvailableBalance(TMallUser user);
	public void saveUserCashRecord(TMallUserCashRecord record);
	public BigDecimal findProcessCashSum(long userId,String status);
	public List<Map<String, Object>> getUserCashRecords(long userId);
}
