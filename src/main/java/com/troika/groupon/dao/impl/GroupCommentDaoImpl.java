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

import com.troika.groupon.dao.GroupCommentDao;
import com.troika.groupon.model.TGrouponComment;
@Repository
public class GroupCommentDaoImpl implements GroupCommentDao {

	@PersistenceContext
	private EntityManager entityManager;
	@Transactional
	public TGrouponComment saveOrUpdate(TGrouponComment comment){
		return entityManager.merge(comment);
	}
	@Transactional
	public void delComment(Integer id){
		entityManager.createQuery("delete from TGrouponComment t where t.id=?")
		.setParameter(1, id).executeUpdate();
	}
	@Override
	@Transactional(readOnly = true)
	public List<Map<String,Object>> getComment(Integer projectId){
		Query query =  entityManager.createNativeQuery(
				"select t.*,u.userName from t_groupon_comment t "
				+" left join t_mall_user u on u.id=t.userId "
				+" where t.projectId=? order by t.createTime asc ");
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCommentByOrderId(Integer orderId) {
		Query query =  entityManager.createNativeQuery(
				"select t.*,u.userName from t_groupon_comment t "
				+" left join t_mall_user u on u.id=t.userId "
				+" where t.orderId=? order by t.createTime asc ");
		query.setParameter(1, orderId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCommentByScheduleId(Integer scheduleId) {
		Query query =  entityManager.createNativeQuery(
				"select t.*,u.userName from t_groupon_comment t "
				+" left join t_mall_user u on u.id=t.userId "
				+" where t.scheduleId=? order by t.createTime asc ");
		query.setParameter(1, scheduleId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}
	
	
}
