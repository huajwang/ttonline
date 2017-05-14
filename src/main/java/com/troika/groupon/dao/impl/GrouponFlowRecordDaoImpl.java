package com.troika.groupon.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.groupon.dao.GrouponFlowRecordDao;
import com.troika.groupon.model.TGroupFollowRecord;

@SuppressWarnings("unchecked")
@Repository
public class GrouponFlowRecordDaoImpl implements GrouponFlowRecordDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 添加关注
	 * 
	 * @param project
	 * @return
	 */
	@Override
	@Transactional
	public TGroupFollowRecord saveOrUpdateRecord(TGroupFollowRecord record) {
		// TODO Auto-generated method stub
		return entityManager.merge(record);
	}

	/**
	 * 获取某个项目的关注数
	 * 
	 * @param projectId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public int getRecordCount(Integer projectId) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TGroupFollowRecord t where t.prejectid=?");
		query.setParameter(1, projectId);
		List<TGroupFollowRecord> list = query.getResultList();
		return list.size();
	}

	/**
	 * 判断用户是否已经关注过
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public TGroupFollowRecord getRecord(Integer userId, Integer projectId) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TGroupFollowRecord t where t.prejectid=? and t.userid=?");
		query.setParameter(1, projectId);
		query.setParameter(2, userId);
		List<TGroupFollowRecord> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

}
