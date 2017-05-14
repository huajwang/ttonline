package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.troika.emall.model.TMallGood;

public interface GoodsDao {
	@Cacheable("goodsbysubcategoryid")
	public List<Map<String,Object>> findGoodsByCategoryId(int subCategoryId);
	@Cacheable("goodsbycategoryid")
	public List<Map<String, Object>> findGoodsByCategoryId(int subCategoryId,String type,String sort);
	@Cacheable("goodsbyid")
	public Map<String,Object> findGoodsById(long gId);
	public Map<String,Object> findGoodsByIdUser(long gId,long userId);
	public List<Map<String,Object>> findAllProperties(long gId,String propertyTableName);
	public Map<String,Object> getPropertyByPropertyNameAndId(String propertyTableName,long id);
	public TMallGood findByGid(long gId);
	public Map<String,Object> getPGByPropertyNameAndId(String propertyTableName,long id);
	public void updateGoodsStock(long gId,int quantity,boolean isReduce);
	public void updateGoodsPStock(String propertyTableName,long id,int quantity,boolean isReduce);
	public List<Map<String,Object>> findGoodsByName(String gName);
	@Cacheable("frontgoods")
	public List<Map<String,Object>> findFrontGoods(int frontSubcategoryId);//获取首页商品数据
	@Cacheable("goodsimg")
	public List<Map<String,Object>> findGoodsImg(int id);//获取商品详情图片
}
