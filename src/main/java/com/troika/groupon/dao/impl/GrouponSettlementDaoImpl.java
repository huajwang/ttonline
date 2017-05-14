package com.troika.groupon.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.groupon.dao.GrouponSettlementDao;
import com.troika.groupon.model.TGrouponSettlement;

@SuppressWarnings("unchecked")
@Repository
public class GrouponSettlementDaoImpl implements GrouponSettlementDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 保存数据
	 */
	@Override
	@Transactional
	public TGrouponSettlement saveOrUpdateRecord(TGrouponSettlement record) {
		// TODO Auto-generated method stub
		return entityManager.merge(record);
	}

	/**
	 * 通过项目Id获取结算数据
	 */
	@Override
	@Transactional(readOnly = true)
	public TGrouponSettlement getSettlement(Integer projectId, Integer status) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TGrouponSettlement t "
						+ " where t.projectid=? and t.status=?");
		query.setParameter(1, projectId);
		query.setParameter(2, status);
		List<TGrouponSettlement> list = query.getResultList();
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 计算结算数据
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> setSettlement(Integer projectId) {
		// TODO Auto-generated method stub
		String sql = "select a.*,b.realName,(a.Total-a.Refund-a.service) as Actual from v_settlement a join t_mall_user b on a.createUser=b.id"
				+ " where a.id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 通过项目Id获取结算数据
	 * 
	 * @param projectId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TGrouponSettlement> getSettlement(Integer projectId) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TGrouponSettlement t "
						+ " where t.projectid=? order by t.createtime desc");
		query.setParameter(1, projectId);
		List<TGrouponSettlement> list = query.getResultList();
		return list;
	}

	/**
	 * 获取用户的所有结算数据
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getSettlementByUser(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select a.name,a.createUser,b.* from t_groupon_project a join t_groupon_settlement b on a.id=b.projectId "
				+ "where a.createUser=? order by b.createTime desc";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	@Override
	public Boolean whetherSett(Integer projectId) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TGrouponSettlement t "
						+ " where t.projectid=? and t.status<>2");
		query.setParameter(1, projectId);
		List<TGrouponSettlement> list = query.getResultList();
		if (list.size() > 0)//已经结算或者在结算中
			return true;
		else
			return false;
	}
}
