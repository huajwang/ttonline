package com.troika.emall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.CategoryDao;
import com.troika.emall.dao.GoodsDao;
import com.troika.emall.model.TMallSubcategory;
import com.troika.emall.service.GoodsService;
import com.troika.emall.util.Constant;

/**
 * 商品服务
 * 
 * @author boa_cool
 * 
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private CategoryDao categoryDao;
	
	/**
	 * 通过子类别ID获取商品信息
	 */
	@Override
	public List<Map<String, Object>> findGoodsByCategoryId(int subCategoryId) {
		// 要先设置子类的id。这样才能从数据库读取正确的子类下的商品
		return goodsDao.findGoodsByCategoryId(subCategoryId);
	}
	
	public List<Map<String, Object>> findGoodsByCategoryId(int subCategoryId,String type,String sort){
		return goodsDao.findGoodsByCategoryId(subCategoryId, type, sort);
	}

	/**
	 * 通过商品ID获取详情
	 */
	@Override
	public Map<String, Object> findGoodsByIdUser(long gId,long userId) {
		Map<String, Object> goods = goodsDao.findGoodsByIdUser(gId,userId);
		goods = goods == null ? new HashMap<String, Object>() : goods;
		return goods;
	}

	/**
	 * 通过商品ID获取详情
	 */
	@Override
	public Map<String, Object> findGoodsById(long gId) {
		Map<String, Object> goods = goodsDao.findGoodsById(gId);
		goods = goods == null ? new HashMap<String, Object>() : goods;
		return goods;
	}
	
	/**
	 * 通过商品ID及商品所在二级类别获取相关弹出规则属性
	 */
	public List<Map<String, Object>> findAllProperties(long gId,
			int subCategoryId) {
		TMallSubcategory subCategory = categoryDao
				.getSubcategoryById(subCategoryId);
		if (subCategory != null) {
			String propertyTableName = subCategory.getPropertyTableName();
			if (StringUtils.isNotBlank(propertyTableName)) {
				return findAllProperties(gId, propertyTableName);
			}
		}
		return null;
	}

	/**
	 * 通过商品ID及商品属性名获取相关弹出规则属性
	 */
	public List<Map<String, Object>> findAllProperties(long gId,
			String propertyTableName) {
		List<Map<String, Object>> list = goodsDao.findAllProperties(gId,
				propertyTableName);
		return list;
	}

	/**
	 * 查询商品数据
	 */
	@Override
	public List<Map<String, Object>> findGoodsByName(String gName) {
		List<Map<String, Object>> list = goodsDao.findGoodsByName(gName);
		for (Map<String, Object> map : list) {
			map.put("iconUrl", Constant.PHOTO_URL + map.get("iconUrl"));
		}
		return list;
	}

	/**
	 * 获取首页商品数据
	 * @param frontSubcategoryId - 首页子类id
	 */
	@Override
	public List<Map<String, Object>> findFrontGoods(int frontSubcategoryId) {
		return goodsDao.findFrontGoods(frontSubcategoryId);
	}

	/**
	 * 获取商品详情图片
	 */
	@Override
	public List<Map<String, Object>> findGoodsImg(int id) {
		// 这是个耗时的查询操作。由于图片详情表变化并不大。 可以考虑放在缓存里。
		return goodsDao.findGoodsImg(id);
	}
}
