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
import com.troika.groupon.dao.GroupProjectImgDao;
import com.troika.groupon.model.TGrouponProjectImg;
@Repository
@Transactional
public class GroupProjectImgDaoImpl implements GroupProjectImgDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveOrUpdateProjectImg(List<TGrouponProjectImg> imgs) {
		for (int i = 0; i < imgs.size(); i++) {
			TGrouponProjectImg imgDetail = imgs.get(i);
			entityManager.persist(imgDetail);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Transactional
	public void delProjectImg(Integer projectId,Integer Icon){
		entityManager.createQuery("delete from TGrouponProjectImg t where t.projectId=? and t.id=?")
		.setParameter(1, projectId).setParameter(2, Icon).executeUpdate();
		entityManager.clear();//清空内存
		entityManager.close();//关闭
	}

	@Transactional(readOnly = true)
	public List<Map<String,Object>> findImgsByProjectId(Integer projectId){
		Query query =  entityManager.createNativeQuery(
				"select * from t_groupon_project_img t where t.projectId=?");
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		for(Map<String,Object> map : list){
			Object iconUrl = map.get("Icon");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("Icon", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}
}
