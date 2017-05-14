package com.troika.emall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.TrueFalseType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.AddressDao;
import com.troika.emall.model.TMallAddress;
@Repository
public class AddressDaoImpl implements AddressDao{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveOrUpdate(TMallAddress addr) {
		entityManager.merge(addr);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String,Object>> findAllAddress(long userId) {
		Query query =  entityManager.createNativeQuery(
				"select dr.*,p.province,c.city,a.area from t_mall_address dr" +
				" left join t_mall_province p on p.provinceId=dr.provinceId"+
                " left join t_mall_city c on c.cityId=dr.cityId "+
                " left join t_mall_area a on a.areaId=dr.areaId "+
                " where dr.userId=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String,Object> findAddressById(long id) {
		Query query =  entityManager.createNativeQuery(
				"select dr.*,p.province,c.city,a.area from t_mall_address dr" +
				" left join t_mall_province p on p.provinceId=dr.provinceId"+
                " left join t_mall_city c on c.cityId=dr.cityId "+
                " left join t_mall_area a on a.areaId=dr.areaId "+
                " where dr.id=?");
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : new HashMap<String,Object>();
	}
	
	@Transactional(readOnly = true)
	public Map<String,Object> findAddressDetailByOrderId(long orderId){
		Query query =  entityManager.createNativeQuery(
				"select p.province,c.city,a.area,dr.streetAddr,dr.postcode,dr.contactName,dr.phone from t_mall_order o" +
				" left join t_mall_address dr on o.addrId = dr.id "+
                " left join t_mall_province p on dr.provinceId = p.provinceId "+
                " left join t_mall_city c on dr.cityId = c.cityId "+
                " left join t_mall_area a on dr.areaId = a.areaId where o.id=?");
		query.setParameter(1, orderId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : new HashMap<String,Object>();
	}
	
	@Transactional(readOnly = true)
	public Map<String,Object> findAddressByUserIdAndActive(long userId,int active) {
		Query query =  entityManager.createNativeQuery(
				"select dr.*,p.province,c.city,a.area from t_mall_address dr" +
				" left join t_mall_province p on p.provinceId=dr.provinceId"+
                " left join t_mall_city c on c.cityId=dr.cityId "+
                " left join t_mall_area a on a.areaId=dr.areaId "+
                " where dr.userId=? and dr.active=?");
		query.setParameter(1, userId).setParameter(2, active);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : new HashMap<String,Object>();
	}

	@Override
	@Transactional
	public int delAddressById(long id) {
		Query query =  entityManager.createNativeQuery(
				"delete from t_mall_address where id=?");
		query.setParameter(1, id);
		return query.executeUpdate();
	}


	@Override
	@Transactional
	public int setDefaultAddressByUserIdAndId(long userId, long id) {
		//设置该账号的所有地址都为非默认
		String sql = "update t_mall_address dr set dr.active = 0 where dr.userId=? and dr.active=1";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter(1, userId);
		int result = query.executeUpdate();
		
		//根据地址ID设置其为默认地址
		String defalut = "update t_mall_address dr set dr.active = 1 where dr.id = ?";
		Query queryD = entityManager.createNativeQuery(defalut);
		queryD.setParameter(1, id);
		result = queryD.executeUpdate();
		
		return result;
	}
}
