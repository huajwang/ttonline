package com.troika.emall.service;

import java.util.List;
import java.util.Map;

public interface GoodsService {
	public List<Map<String,Object>> findGoodsByCategoryId(int subCategoryId);
	public List<Map<String, Object>> findGoodsByCategoryId(int subCategoryId,String type,String sort);
	public Map<String,Object> findGoodsById(long gId);
	public Map<String,Object> findGoodsByIdUser(long gId,long userId);
	public List<Map<String,Object>> findAllProperties(long gId,String propertyTableName);
	public List<Map<String,Object>> findAllProperties(long gId,int subCategoryId);
	public List<Map<String,Object>> findGoodsByName(String gName);
	public List<Map<String,Object>> findFrontGoods(int frontSubcategoryId);//获取首页商品数据
	public List<Map<String,Object>> findGoodsImg(int id);//获取商品详情图片
}
