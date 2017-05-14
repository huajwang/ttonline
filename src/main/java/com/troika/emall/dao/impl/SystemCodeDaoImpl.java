package com.troika.emall.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.SystemCodeDao;
import com.troika.emall.model.TMallArea;
import com.troika.emall.model.TMallCity;
import com.troika.emall.model.TMallProvince;
@Repository
public class SystemCodeDaoImpl implements SystemCodeDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallProvince> findAllProvince() {
		return entityManager.createNamedQuery("findAllProvince").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallCity> findCityByProvinceId(String provinceId) {
		return entityManager.createQuery("select tc from TMallCity tc where tc.provinceId=?").setParameter(1, provinceId).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallArea> findAreaByCityId(String cityId) {
		return entityManager.createQuery("select ta from TMallArea ta where ta.cityId=?").setParameter(1, cityId).getResultList();
	}

}
