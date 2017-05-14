package com.troika.emall.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.annotations.Check;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.EvaluateDao;
import com.troika.emall.model.TMallCart;
import com.troika.emall.model.TMallEvaluation;
import com.troika.emall.model.TMallEvaluationImg;
import com.troika.emall.util.DateUtil;

@Repository
public class EvaluateDaoImpl implements EvaluateDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(TMallEvaluation eval) {
		entityManager.persist(eval);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> evalListByGid(Long gId) {
		Query query = entityManager
				.createNativeQuery("select e.id,e.content,e.rating,e.createTime,u.userName,u.iconUrl from t_mall_evaluation e  "
						+ " left join t_mall_order o on o.id = e.orderId"
						+ " left join t_mall_user u on u.id = o.userId "
						+ " where e.gId = ? order by e.createTime desc");
		query.setParameter(1, gId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			if (map.get("createTime") != null) {
				Date createTime = (Date) map.get("createTime");
				map.put("createTime", DateUtil.format2(createTime));
			}
			// 获取评价图片
			int id = Integer.valueOf(map.get("id").toString());
			List<TMallEvaluationImg> listImg = getImg(id);
			map.put("imgList", listImg);
		}
		return list;
	}

	/**
	 * 保存评价图片
	 */
	@Override
	public void saveImg(TMallEvaluationImg record) {
		// TODO Auto-generated method stub
		entityManager.merge(record);
	}

	/**
	 * 获取评价图片
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TMallEvaluationImg> getImg(Integer evaluationId) {
		// TODO Auto-generated method stub
		List<TMallEvaluationImg> list = entityManager
				.createQuery(
						"select t from TMallEvaluationImg t where t.evaluationId=? ")
				.setParameter(1, evaluationId).getResultList();
		return list;
	}

}
