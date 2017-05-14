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

import com.troika.groupon.dao.GroupScheduleDao;
import com.troika.groupon.model.TGrouponSchedule;
@Repository
public class GroupScheduleDaoImpl implements GroupScheduleDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public TGrouponSchedule saveOrUpdateProject(TGrouponSchedule schedule){
		return entityManager.merge(schedule);
	}

	@Transactional(readOnly = true)
	public List<Map<String,Object>> findAllByProjectId(Integer projectId){
		Query query =  entityManager.createNativeQuery(
				"select t.*,u.userName,u.iconUrl from t_groupon_schedule t "
				+ " left join t_mall_user u on u.id=t.userId "
				+ "  where t.projectId=? order by t.createTime desc ");
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}
}
