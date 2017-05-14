package com.troika.groupon.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.util.Constant;
import com.troika.groupon.dao.GroupIdenDao;
import com.troika.groupon.model.TGrouponIdentification;
@Repository
public class GroupIdenDaoImpl implements GroupIdenDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public TGrouponIdentification saveOrUpdate(TGrouponIdentification iden){
		return entityManager.merge(iden);
	}
	/**
	 * 获取用户的身份验证信息
	 */
	@Transactional(readOnly = true)
	public TGrouponIdentification findById(Integer userId){
		Query query = entityManager
				.createQuery("select t from TGrouponIdentification t where t.userid=?");
		query.setParameter(1, userId);
		List<TGrouponIdentification> list = query.getResultList();
		TGrouponIdentification iden = list.size() > 0 ? list.get(0) : null;
		entityManager.clear();//清除内存
		//entityManager.close();
		if (iden != null) {
			iden.setIdcard_Icon(Constant.PHOTO_URL
					+ iden.getIdcard_Icon());
			iden.setLicense_Icon(Constant.PHOTO_URL
					+ iden.getLicense_Icon());
		}
		return iden;
	}

	@Transactional(readOnly = true)
	public List<TGrouponIdentification> findByStatus(String status){
		Query query = entityManager
				.createQuery("select t from TGrouponIdentification t where t.result=?");
		query.setParameter(1, status);
		List<TGrouponIdentification> list = query.getResultList();
		return list;
	}
}
