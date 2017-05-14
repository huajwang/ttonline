package com.troika.emall.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallCommissionRecord;
import com.troika.emall.model.TMallMakerCommissionRecord;
import com.troika.emall.model.TMallUserCashRecord;

public interface CashDao {
	public void saveUserCashRecord(TMallUserCashRecord record);
	public BigDecimal findProcessCashSum(long userId,String status);
	public List<TMallCommissionRecord> findShareCommissionByUserId(int userId, String state);
	public List<TMallMakerCommissionRecord> findMakerCommissionByMakerId(String makerId, String state);
	public List<Map<String, Object>> getUserCashRecords(long userId);
}
