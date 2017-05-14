package com.troika.emall.dao.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.CartDao;
import com.troika.emall.dao.GoodsDao;
import com.troika.emall.model.TMallCart;
import com.troika.emall.model.TMallCartDetail;
import com.troika.emall.util.Constant;

@Repository
public class CartDaoImpl implements CartDao {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private GoodsDao goodsDao;

	private Set<String> noSet = null;// 一些不用的字段

	public CartDaoImpl() {
		noSet = new HashSet<String>();
		noSet.add("id");
		noSet.add("gId");
		noSet.add("stock");
		noSet.add("sold");
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallCart getCartByUserId(long userId) {
		List<TMallCart> list = entityManager
				.createQuery(
						"select t from TMallCart t where t.userId=? and t.cartStatus='0' ")
				.setParameter(1, userId).getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallCartDetail> getCartDetailByCartId(long cartId) {
		List<TMallCartDetail> list = entityManager
				.createQuery("select t from TMallCartDetail t where t.cartId=?")
				.setParameter(1, cartId).getResultList();
		return list;
	}

	@Override
	@Transactional
	public void saveOrUpdateDetail(List<TMallCartDetail> list) {
		for (TMallCartDetail detail : list) {
			entityManager.merge(detail);
		}
	}

	@Transactional
	public TMallCartDetail saveOrUpdateOneDetail(TMallCartDetail detail) {
		TMallCartDetail tempDetail = entityManager.merge(detail);
		return tempDetail;
	}

	@Override
	@Transactional
	public TMallCart saveOrUpdateCart(TMallCart cart) {
		TMallCart result = entityManager.merge(cart);
		return result;
	}

	@Override
	@Transactional
	public void deleteCartDetail(List<TMallCartDetail> list) {
		for (TMallCartDetail detail : list) {
			entityManager
					.createQuery("delete from TMallCartDetail t where t.id=?")
					.setParameter(1, detail.getId()).executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCartListByUserId(long userId) {// 这里将price和inprice都换成详情里的
		Query query = entityManager
				.createNativeQuery("select cd.id as cartDetailId,g.id,g.gName,g.iconUrl,cd.price,cd.price as inPrice,g.upForSale,cd.gQuantity,"
						+ "cd.propertyTableName,cd.propertyId,c.cartId from t_mall_cart c "
						+ " left join t_mall_cart_detail cd on c.cartId=cd.cartId "
						+ " left join t_mall_goods g on cd.gId=g.id where c.cartStatus='0' and c.userId=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			Object propertyName = map.get("propertyTableName");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				Object propertyId = map.get("propertyId");
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}

			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)&&!iconUrl.toString().contains("http:")) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}

	@Transactional(readOnly = true)
	public TMallCartDetail queryByProperties(long cartId, long gId,
			String propertyTableName, long propertyId) {
		String sql = "select t from TMallCartDetail t where t.cartId=? and t.gId=?";
		if (StringUtils.isNotBlank(propertyTableName)) {
			sql += " and t.propertyTableName=? and t.propertyId=?";
		}
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, cartId);
		query.setParameter(2, gId);
		if (StringUtils.isNotBlank(propertyTableName)) {
			query.setParameter(3, propertyTableName);
			query.setParameter(4, propertyId);
		}
		List<TMallCartDetail> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Transactional
	public void delDetail(List<Long> list) {
		for (Long detailId : list) {
			entityManager
					.createQuery("delete from TMallCartDetail t where t.id=?")
					.setParameter(1, detailId).executeUpdate();
		}
	}

	@Transactional(readOnly = true)
	public Map<String, Long> getGoodsNum(long userId) {
		Query query = entityManager
				.createNativeQuery("select count(*) as num from t_mall_cart c,t_mall_cart_detail d "
						+ "where c.cartId = d.cartId and c.userId=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Long> map = (Map<String, Long>) query.getSingleResult();
		return map;
	}

	/**
	 * 获取购物主表数据
	 */
	@Override
	@Transactional(readOnly = true)
	public TMallCart getCartByCartId(long cartId) {
		List<TMallCart> record = entityManager
				.createQuery("select t from TMallCart t where t.cartId=?")
				.setParameter(1, cartId).getResultList();
		return record.size() > 0 ? record.get(0) : null;
	}
}
