package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.troika.emall.model.TMallHomeMenu;
import com.troika.emall.model.TMallHomeTopPic;

/**
 * 首页类
 * 
 * @author Administrator
 * 
 */
public interface HomePageDao {
	/**
	 * 获取首页菜单
	 * @return
	 */
	public List<TMallHomeMenu> findAllMenu();
	/**
	 * 获取轮播图片
	 * @return
	 */
	@Cacheable("topPicCache")
	public List<Map<String, Object>> findAllPic();
	/**
	 * 获取特卖、优惠和热卖商品
	 * @return
	 */
	@Cacheable("topGoodsCache")
	public List<Map<String,Object>> getTopGoods();
	/**
	 * 获取首页的大类和小类数据
	 * @return
	 */
	@Cacheable("frontCategoryCache")
	public List<Map<String,Object>> getFrontCategory();
	/**
	 * 获取首页热卖商品列表
	 */
	@Cacheable("bitHitCache")
	public List<Map<String,Object>> findBigHitAll();
}
