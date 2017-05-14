package com.troika.emall.dao.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.CashDao;
import com.troika.emall.model.TMallCommissionRecord;
import com.troika.emall.model.TMallMakerCommissionRecord;
import com.troika.emall.model.TMallUserCashRecord;

@Repository
public class CashDaoImpl implements CashDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取用户分享佣金
	 * @param userId - userID
	 * @param state - 0 未完成 1 已完成   2 订单取消
	 */
	@Transactional(readOnly = true)
	public List<TMallCommissionRecord> findShareCommissionByUserId(int userId, String state) {
		List<TMallCommissionRecord> list = new ArrayList<TMallCommissionRecord>();
		Query query = entityManager.createNamedQuery(
								"TMallCommissionRecord.findCommission");
		query.setParameter("introducer", userId);
		query.setParameter("state", state);
		list = query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 获取创客佣金
	 * @param makerId
	 * @param state - 0 未完成 1 已完成   2 订单取消
	 */
	@Transactional(readOnly = true)
	public List<TMallMakerCommissionRecord> findMakerCommissionByMakerId(String makerId, String state) {
		List<TMallMakerCommissionRecord> list = new ArrayList<TMallMakerCommissionRecord>();
		Query query = entityManager.createNamedQuery("TMallMakerCommissionRecord.findCommission");
		query.setParameter("makerId", makerId);
		query.setParameter("state", state);
		list = query.getResultList();
		return list;
	}

	@Override
	/**
	 * 保存用户提现请求
	 */
	@Transactional
	public void saveUserCashRecord(TMallUserCashRecord record) {
		entityManager.persist(record);
	}

	@Override
	/**
	 * 获取用户请求提现金额
	 * status = '0' 表示提现申请中
	 */
	@Transactional(readOnly = true)
	public BigDecimal findProcessCashSum(long userId,String status) {
		List<BigDecimal> list = entityManager.createNativeQuery("select sum(amount) from t_mall_user_cash_record  where userId=? and status=?")
				.setParameter(1, userId).setParameter(2, status).getResultList();
		return  (list.get(0)!=null ? list.get(0) : new BigDecimal("0.00"));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getUserCashRecords(long userId) {
		Query query = entityManager.createNativeQuery("select * from t_mall_user_cash_record where userId = ?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}
	
	

}
