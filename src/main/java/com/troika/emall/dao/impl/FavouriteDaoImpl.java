package com.troika.emall.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.troika.emall.dao.FavouriteDao;
import com.troika.emall.model.TMallFavourite;
import com.troika.emall.model.TMallGood;
import com.troika.emall.util.Constant;
@Repository
public class FavouriteDaoImpl implements FavouriteDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void addFavourite(long userId, long gId) {
		TMallFavourite favor = new TMallFavourite();
		favor.setUserId(userId);
		favor.setGId(gId);
		favor.setIsDel("0");
		favor.setCreateTime(new Date());
		entityManager.persist(favor);
	}

	@Override
	@Transactional
	public void updateFavourite(long userId, long gId,String isDel) {
		String sql = "update t_mall_favourite set isDel=? where userId=? and gId=?";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter(1, isDel);
		query.setParameter(2, userId);
		query.setParameter(3, gId);
		query.executeUpdate();
	}
	
	@Transactional
	public void updateFavourites(String[] ids,Long userId) {
		String condition = "(";
		for(String id : ids){
			condition +="'"+id + "',";
		}
		condition = condition.substring(0,condition.length()-1);
		condition += ")";
		String sql = "update t_mall_favourite set isDel=1 where gId in "+condition+" and userId='"+userId+"'";
		Query query =  entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}
	
	/**
	 * 批量删除商品收藏
	 * @param ids
	 */
	@Override
	@Transactional
	public void updateFavourites(JSONObject ids) {
		String condition = "("+ids.getInteger("0");
		for(int i=1;i<ids.size();i++){
			condition += ",";
			condition += ids.getString(""+i);
		}
		condition += ")";
		String sql = "update t_mall_favourite set isDel=1 where id in "+condition;
		Query query =  entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public TMallFavourite findByUserIdAndGid(long userId,long gId){
		List<TMallFavourite> list = entityManager.createQuery("select t from TMallFavourite t where t.userId=? and t.gId=?")
				.setParameter(1, userId).setParameter(2, gId).getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}
	
//	public List<TMallGood> findFavoursByUserId(long userId){
//		List<TMallGood> list = entityManager.createQuery(""
//				+ "select g from TMallFavourite t,TMallGood g where g.id=t.gId and t.userId=?")
//				.setParameter(1, userId).getResultList();
//		return list;
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Map<String,Object>> findFavouritesByUserId(long userId){
		Query query = entityManager.createNativeQuery("select t.id,t.gId,t.isDel,g.gName,g.price,g.iconUrl from t_mall_favourite t,t_mall_goods g where g.id=t.gId and t.isDel=0 and t.userId=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}

}
