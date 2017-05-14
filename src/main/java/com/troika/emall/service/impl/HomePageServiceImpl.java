package com.troika.emall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.HomePageDao;
import com.troika.emall.model.TMallHomeMenu;
import com.troika.emall.service.HomePageService;
import com.troika.emall.util.Constant;

@Service
public class HomePageServiceImpl extends BaseServiceImpl implements HomePageService {

	@Autowired
	private HomePageDao homePageDao;

	/**
	 * 获取首页菜单
	 * 
	 * @return
	 */
	@Override
	public List<TMallHomeMenu> findAllMenu() {
		return homePageDao.findAllMenu();
	}

	@Override
	public List<Map<String, Object>> getTopGoods() {
		return homePageDao.getTopGoods();
	}

	/**
	 * 获取首页轮播图片
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findAllPic() {
		List<Map<String, Object>> list = homePageDao.findAllPic();
		for(Map<String, Object> map : list) {
			map.put("pic", Constant.PHOTO_URL + map.get("pic"));
		}
		return list;
	}

	/**
	 * 获取首页大类和小类数据
	 */
	@Override
	public List<Map<String, Object>> getFrontCategory() {
		return homePageDao.getFrontCategory();
	}

	@Override
	/**
	 * 从缓存或从数据库里获取首页商城推荐商品
	 */
	public List<Map<String, Object>> findBigHitAll() {
		return homePageDao.findBigHitAll();
	}
}
