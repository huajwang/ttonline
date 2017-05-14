package com.troika.emall.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.PayDao;
import com.troika.emall.model.TMallAlipayDetail;
@Repository
public class PayDaoImpl implements PayDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = true)
	public void aplipaySave(TMallAlipayDetail detail) {
		entityManager.merge(detail);
	}

}
