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
import com.troika.groupon.dao.GroupProductDao;
import com.troika.groupon.model.TGrouponProduct;

@Repository
@Transactional
public class GroupProductDaoImpl implements GroupProductDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveOrUpdateProduct(List<TGrouponProduct> products) {
		for (int i = 0; i < products.size(); i++) {
			TGrouponProduct product = products.get(i);
			entityManager.persist(product);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Transactional
	public void delProduct(Integer productId) {
		entityManager.createQuery("delete from TGrouponProduct t where t.id=?")
				.setParameter(1, productId).executeUpdate();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findProductsByProjectId(Integer projectId) {
		Query query = entityManager
				.createNativeQuery("select *,(select count(*) from t_groupon_order_details a join t_groupon_order b on a.orderId=b.id where a.productId=t.id and b.`status`<>0 ) as orderNum,"
						+ "(select Surplus from v_productSurplus where id=t.id) as Surplus from t_groupon_product t where t.projectId=?");
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			Object iconUrl = map.get("Icon");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("Icon", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}
}
