package com.troika.groupon.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.util.Constant;
import com.troika.groupon.dao.GroupScheduleImgDao;
import com.troika.groupon.model.TGrouponScheduleImg;

@Repository
public class GroupScheduleImgDaoImpl implements GroupScheduleImgDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveOrUpdateProject(List<TGrouponScheduleImg> record) {
		// TODO Auto-generated method stub
		for (int i = 0; i < record.size(); i++) {
			TGrouponScheduleImg img = record.get(i);
			entityManager.persist(img);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	/**
	 * 获取进度图片数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TGrouponScheduleImg> getScheduleImg(Integer scheduleId) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createNamedQuery("TGrouponScheduleImg.getImgByScheduleId");
		query.setParameter("scheduleId", scheduleId);
		List<TGrouponScheduleImg> list = query.getResultList();
		for (TGrouponScheduleImg img : list) {
			String icon = img.getIcon();
			img.setIcon(Constant.PHOTO_URL + icon);
		}
		entityManager.clear();
		return list;
	}

}
