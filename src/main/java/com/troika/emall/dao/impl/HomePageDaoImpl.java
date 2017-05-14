package com.troika.emall.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.HomePageDao;
import com.troika.emall.model.TMallHomeMenu;
import com.troika.emall.model.TMallHomeTopPic;
import com.troika.emall.util.Constant;

@SuppressWarnings("unchecked")
@Repository
public class HomePageDaoImpl implements HomePageDao {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 获取首页菜单
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TMallHomeMenu> findAllMenu() {
		return entityManager.createNamedQuery("findAllMenu").getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllPic() {
		System.out.println("kkkkkkkkkkkkkkkk query from DB...");
		Query query = entityManager
				.createNativeQuery("SELECT * FROM t_mall_home_top_pic t where t.status = '1' order by t.showOrder");
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> dataList = query.getResultList();
		return dataList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getTopGoods() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 热卖商品
		Map<String, Object> bigHitObj = new HashMap<String, Object>();
		Query query1 = entityManager
				.createNativeQuery("select a.gId,b.gName as name,b.iconUrl,"
						+ "b.price from t_mall_big_hit a join t_mall_goods b on a.gId=b.gId where b.upForSale=1");
		query1.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> bigHit = query1.getResultList();
		bigHitObj.put("type", "1");
		bigHitObj.put("good", bigHit);
		result.add(bigHitObj);
		// 优惠商品
		Map<String, Object> disObj = new HashMap<String, Object>();
		Query query2 = entityManager
				.createNativeQuery("select a.gId,b.gName as name,b.iconUrl,"
						+ "a.price from t_mall_discount a join t_mall_goods b on a.gId=b.gId where b.upForSale=1");
		query2.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> discount = query2.getResultList();
		disObj.put("type", "2");
		disObj.put("good", discount);
		result.add(disObj);
		// 特色商品
		Map<String, Object> speObj = new HashMap<String, Object>();
		Query query3 = entityManager
				.createNativeQuery("select a.gId,b.gName as name,b.iconUrl,"
						+ "b.price from t_mall_speciality a join t_mall_goods b on a.gId=b.gId where b.upForSale=1");
		query2.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> spe = query3.getResultList();
		speObj.put("type", "3");
		speObj.put("good", spe);
		result.add(speObj);
		return result;
	}

	/**
	 * 获取首页的大类和小类数据
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getFrontCategory() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Query query1 = entityManager
				.createNativeQuery("select * from t_mall_front_category order by showOrder");
		query1.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> dataList = query1.getResultList();
		for (Map<String, Object> data : dataList) {
			Map<String, Object> map = new HashMap<>();
			int id = (int) data.get("id");
			map.put("name", data.get("name"));
			Query query2 = entityManager
					.createNativeQuery("select * from t_mall_front_subcategory where parentId=? order by showOrder");
			query2.setParameter(1, id);
			query2.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> subcategory = query2.getResultList();
			for (Map<String, Object> temp : subcategory) {
				Object iconUrl = temp.get("textElement");
				if (iconUrl != null && !"".equals(iconUrl)) {
					temp.put("textElement", Constant.PHOTO_URL + iconUrl.toString());
				}
			}
			map.put("subcategory", subcategory);
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 获取首页热卖商品
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findBigHitAll() {
		Query query = entityManager.createNativeQuery("SELECT t.*,g.gName,g.price,g.iconUrl,(select count(*) from t_mall_order_detail where detailStatus not in(-1,9) and gId=g.id) as sales FROM t_mall_big_hit t join t_mall_goods g on t.gId=g.id where g.upForSale=1 order by showOrder");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}

}
