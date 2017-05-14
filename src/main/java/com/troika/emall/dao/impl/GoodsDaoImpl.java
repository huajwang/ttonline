package com.troika.emall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.GoodsDao;
import com.troika.emall.model.TMallGood;
import com.troika.emall.util.Constant;

@Repository
public class GoodsDaoImpl implements GoodsDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Map<String,Object>> findGoodsByCategoryId(int subCategoryId){
		return findGoodsByCategoryId(subCategoryId,"normal","desc");
	}
	/**
	 * 根据二级分类找商品
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findGoodsByCategoryId(int subCategoryId,String type,String sort) {
		String orderSql = "";
		type = StringUtils.isBlank(type) ? "normal" : type;
		switch(type){
		case "sales"://销量
			orderSql = " order by sales ";
			break;
		case "eval"://评价
			orderSql = " order by evalCount ";
			break;
		case "price"://价格
			orderSql = " order by g.price ";
			break;
			default:
				orderSql = " order by g.id ";
		}
		
		if("desc".equals(sort) || "asc".equals(sort)){
			orderSql += sort;
		}else{
			orderSql +="desc";
		}
		
		Query query = entityManager
				.createNativeQuery("select g.id,g.gName,g.iconUrl,g.price,g.inPrice,g.sold,count(e.id) as evalCount,g.isRecommend, g.upForSale,"
						+ "(select count(*) from t_mall_evaluation where gid=g.id) as evaluation,"
						+ "(select count(*) from t_mall_order_detail where detailStatus not in(-1,9) and gId=g.id) as sales"// 统计销售数量（过滤取消和退款的数据）
						+ " from t_mall_goods g "
						+ " left join t_mall_evaluation e on g.id=e.gId "
						+ " where g.subcategoryId=? and g.upForSale=1 group by g.id "+ orderSql);
		query.setParameter(1, subCategoryId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}

	/**
	 * 根据商品id找详情
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findGoodsById(long gId) {
		Query query = entityManager
				.createNativeQuery("select t.id,t.iconUrl,t.gId,t.gName,t.price,t.inPrice,t.description,t.subcategoryId,sb.propertyTableName,t.showPopup,"
						+ "t.stock,t.sold, t.upForSale, group_concat(p.photoUrl) as photoUrl "
						+ " from t_mall_goods t "
						+ " left join t_mall_goods_photos p on t.id = p.gId "
						+ " left join t_mall_subcategory sb on t.subcategoryId = sb.subcategoryId "
						+ " where t.id=? group by t.id");
		query.setParameter(1, gId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<String, Object> goodsMap = null;
		if (list.size() > 0) {
			goodsMap = list.get(0);
			Object photoUrl = goodsMap.get("photoUrl");
			if (photoUrl != null && !"".equals(photoUrl)) {
				String[] urlArr = photoUrl.toString().split(",");
				String resultUrl = "";
				for (String url : urlArr) {
					if (StringUtils.isNotBlank(url)) {
						resultUrl += Constant.PHOTO_URL + url + ",";
					}
				}
				resultUrl = "".equals(resultUrl) ? resultUrl : resultUrl
						.substring(0, resultUrl.length() - 1);
				goodsMap.put("photoUrl", resultUrl);
			}
		}
		return goodsMap;
	}
	
	/**
	 * 用户有登录时，进入商品详情，判断用户是否对该商品有收藏来显示图标
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findGoodsByIdUser(long gId,long userId) {
		Query query = entityManager
				.createNativeQuery("select t.id,t.gId,t.iconUrl,t.gName,t.price,t.inPrice,t.description,t.subcategoryId,sb.propertyTableName,t.showPopup,"
						+ "t.stock,t.sold, t.upForSale, group_concat(p.photoUrl) as photoUrl, "
						+ " (select isDel from t_mall_favourite f where f.gid = ? and f.userId =?) as isFavourite "
						+ " from t_mall_goods t "
						+ " left join t_mall_goods_photos p on t.id = p.gId "
						+ " left join t_mall_subcategory sb on t.subcategoryId = sb.subcategoryId "
						+ " where t.id=? group by t.id");
		query.setParameter(1, gId);
		query.setParameter(2, userId);
		query.setParameter(3, gId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<String, Object> goodsMap = null;
		if (list.size() > 0) {
			goodsMap = list.get(0);
			Object photoUrl = goodsMap.get("photoUrl");
			if (photoUrl != null && !"".equals(photoUrl)) {
				String[] urlArr = photoUrl.toString().split(",");
				String resultUrl = "";
				for (String url : urlArr) {
					if (StringUtils.isNotBlank(url)) {
						resultUrl += Constant.PHOTO_URL + url + ",";
					}
				}
				resultUrl = "".equals(resultUrl) ? resultUrl : resultUrl
						.substring(0, resultUrl.length() - 1);
				goodsMap.put("photoUrl", resultUrl);
			}
		}
		return goodsMap;
	}

	/**
	 * 展示商品弹出的属性
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllProperties(long gId,
			String propertyTableName) {
		String sql = "select p.* from t_mall_goods t " + " join t_mall_"
				+ propertyTableName + "_property p on t.id=p.gId"
				+ " where t.id=? order by p.id ";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, gId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	/**
	 * 获取属性值
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> getPropertyByPropertyNameAndId(
			String propertyTableName, long id) {
		String sql = "select *from t_mall_" + propertyTableName
				+ "_property where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : new HashMap<String, Object>();
	}

	@Override
	@Transactional(readOnly = true)
	public TMallGood findByGid(long gId) {
		return entityManager.find(TMallGood.class, gId);
	}

	/**
	 * 获取属性值及商品名
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> getPGByPropertyNameAndId(
			String propertyTableName, long id) {
		String sql = "select p.*,g.gName from t_mall_" + propertyTableName
				+ "_property p"
				+ " left join t_mall_goods g on p.gId=g.id where p.id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	@Transactional
	public void updateGoodsStock(long gId, int quantity, boolean isReduce) {
		String sql = "update t_mall_goods set stock=stock-?,sold=sold+? where id=?";
		if (!isReduce) {// 恢复销量，主要用于删除定单
			sql = "update t_mall_goods set stock=stock+?,sold=sold-? where id=?";
		}
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, quantity);
		query.setParameter(2, quantity);
		query.setParameter(3, gId);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateGoodsPStock(String propertyTableName, long id,
			int quantity, boolean isReduce) {
		String sql = "update t_mall_" + propertyTableName
				+ "_property set stock=stock-?,sold=sold+? where id=?";
		if (!isReduce) {
			sql = "update t_mall_" + propertyTableName
					+ "_property set stock=stock+?,sold=sold-? where id=?";
		}
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, quantity);
		query.setParameter(2, quantity);
		query.setParameter(3, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findGoodsByName(String gName) {
		String sql = "select * from t_mall_goods where upForSale=1 and gName like '%" + gName
				+ "%'";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		return result;
	}

	/**
	 * 获取首页商品数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findFrontGoods(int frontSubcategoryId) {
		String sql = "select a.*,(select count(*) from t_mall_evaluation where gid=a.id) as evaluation,"
				+ "(select count(*) from t_mall_order_detail where detailStatus not in(-1,9) and gId=a.id) as sales"// 统计销售数量（过滤取消和退款的数据）
				+ " from t_mall_goods a join t_mall_front_goods b on a.id=b.goodId where b.upForSale=1 and b.frontSubcategoryId=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, frontSubcategoryId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		for (Map<String, Object> map : result) {
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return result;
	}

	/**
	 * 获取商品详情图片
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findGoodsImg(int id) {
		String sql = "select * from t_mall_goods_detail_img where gid=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		for (Map<String, Object> map : result) {
			Object iconUrl = map.get("detailPhotoUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("detailPhotoUrl",
						Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return result;
	}

}
