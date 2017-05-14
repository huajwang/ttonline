package com.troika.groupon.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.groupon.dao.GroupNewsDao;
import com.troika.groupon.model.TGrouponNews;
import com.troika.groupon.model.TGrouponNewsRead;
@Repository
public class GroupNewsDaoImpl implements GroupNewsDao{
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public TGrouponNews saveOrUpdate(TGrouponNews news){
		return entityManager.merge(news);
	}
	@Transactional
	public void saveOrUpdateNewsRead(List<TGrouponNewsRead> reads) {
		for (int i = 0; i < reads.size(); i++) {
			TGrouponNewsRead read = reads.get(i);
			entityManager.persist(read);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TGrouponNews> findNewsByUserIdAndStatus(Integer userId,String status){
		Query query = entityManager
				.createQuery("select t from TGrouponNews t,TGrouponNewsRead r " +
						" where t.id=r.newsid and r.isread=? and r.userid=?");
		query.setParameter(1, status);
		query.setParameter(2, userId);
		List<TGrouponNews> list = query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public boolean updNewsStatus(Integer id,Integer userId,String status){
		Query query = entityManager
				.createQuery("update TGrouponNewsRead r set r.isread=?,r.readtime=? " +
						" where r.newsid=? and r.userid=?");
		query.setParameter(1, status);
		query.setParameter(2, new Date());
		query.setParameter(3, id);
		query.setParameter(4, userId);
		query.executeUpdate();
		return true;
	}
}
