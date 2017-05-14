package com.troika.emall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.ActivityDao;
import com.troika.emall.model.TMallActivityGoods;
import com.troika.emall.model.TMallActivityGoodsImg;
import com.troika.emall.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;

	/**
	 * 获取活动产品
	 */
	@Override
	public TMallActivityGoods findActivityGoods() {
		// TODO Auto-generated method stub
		return activityDao.findActivityGoods();
	}

	/**
	 * 获取活动产品的图片
	 */
	@Override
	public List<TMallActivityGoodsImg> findActivityGoodsImg(
			Integer activityGoodsId, Integer type) {
		// TODO Auto-generated method stub
		return activityDao.findActivityGoodsImg(activityGoodsId, type);
	}

}
