package com.troika.emall.service;

import java.util.List;

import com.troika.emall.model.TMallActivityGoods;
import com.troika.emall.model.TMallActivityGoodsImg;

public interface ActivityService {
	// 获取活动产品
	public TMallActivityGoods findActivityGoods();

	// 获取活动产品的图片：0 轮播图片 1 产品详情图片
	public List<TMallActivityGoodsImg> findActivityGoodsImg(
			Integer activityGoodsId, Integer type);
}
