package com.troika.emall.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.ActivityDao;
import com.troika.emall.model.TMallActivityGoods;
import com.troika.emall.model.TMallActivityGoodsImg;

@SuppressWarnings("unchecked")
@Repository
public class ActivityDaoImpl implements ActivityDao {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 获取活动产品
	 */
	@Override
	@Transactional(readOnly = true)
	public TMallActivityGoods findActivityGoods() {
		// TODO Auto-generated method stub
		List<TMallActivityGoods> list = entityManager.createQuery(
				"select t from TMallActivityGoods t WHERE t.endDate>NOW() ")
				.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 获取活动产品的图片：0 轮播图片 1 产品详情图片
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TMallActivityGoodsImg> findActivityGoodsImg(
			Integer activityGoodsId, Integer type) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createQuery("select t from TMallActivityGoodsImg t where t.activityGoodsId=? and t.type=? ");
		query.setParameter(1, activityGoodsId);
		query.setParameter(2, type);
		List<TMallActivityGoodsImg> list = query.getResultList();
		return list;
	}
}
