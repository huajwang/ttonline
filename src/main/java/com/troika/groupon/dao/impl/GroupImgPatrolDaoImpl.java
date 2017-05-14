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

import com.troika.emall.util.Constant;
import com.troika.groupon.common.PatrolStatus;
import com.troika.groupon.dao.GroupImgPatrolDao;

@Repository
public class GroupImgPatrolDaoImpl implements GroupImgPatrolDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getImgsByStatus(PatrolStatus status) {
		Query query = entityManager
				.createNativeQuery("select CONCAT('"
						+ Constant.PHOTO_URL
						+ "',t.icon) as icon,t.url from t_groupon_img_patrol t where t.status=?");
		query.setParameter(1, status.getCode());
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
}
