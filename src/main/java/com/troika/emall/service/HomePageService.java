package com.troika.emall.service;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallHomeMenu;
import com.troika.emall.model.TMallHomeTopPic;

public interface HomePageService {
	/**
	 * 获取首页菜单
	 * @return
	 */
	public List<TMallHomeMenu> findAllMenu();
	/**
	 * 获取轮播图片
	 * @return
	 */
	public List<Map<String, Object>> findAllPic();
	/**
	 * 获取特卖、优惠和热卖商品
	 * @return
	 */
	public List<Map<String,Object>> getTopGoods();
	/**
	 * 获取首页的大类和小类数据
	 * @return
	 */
	public List<Map<String,Object>> getFrontCategory();
	/**
	 * 获取首页热卖商品列表
	 */
	public List<Map<String, Object>> findBigHitAll();
}
