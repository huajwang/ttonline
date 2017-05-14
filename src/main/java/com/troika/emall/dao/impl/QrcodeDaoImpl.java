package com.troika.emall.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.QrcodeDao;
import com.troika.emall.model.WxTBusQrcode;

@Repository
public class QrcodeDaoImpl implements QrcodeDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public WxTBusQrcode saveOrUpQrcode(WxTBusQrcode qrcode) {
		return entityManager.merge(qrcode);
	}

	@Override
	public WxTBusQrcode findQrcodeById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
