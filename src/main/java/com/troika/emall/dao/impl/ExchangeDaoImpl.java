package com.troika.emall.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.ExchangeDao;
import com.troika.emall.model.TMallReturnImg;
import com.troika.emall.model.TMallReturnRecord;

@Repository
public class ExchangeDaoImpl implements ExchangeDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Override
	/**
	 * 保存顾客传上来的退货原因图片
	 * @param list - 最多三张的退货原因图片
	 */
	@Transactional
	public void saveExchangeImgs(List<TMallReturnImg> list) {
		for(TMallReturnImg exchange : list){
			entityManager.merge(exchange);
		}
	}

	
	@Override
	/**
	 * 插入一条退货请求到t_mall_return_record
	 * @param returnRecord - 退货记录，包括原因和图片的退货记录
	 */
	@Transactional
	public void saveReturnRecord(TMallReturnRecord returnRecord) {
		logger.debug("重复记录的插入排查: " + returnRecord);
		entityManager.persist(returnRecord);
	}

	
	
}
