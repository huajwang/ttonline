package com.troika.emall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.troika.emall.dao.FavouriteDao;
import com.troika.emall.model.TMallFavourite;
import com.troika.emall.model.TMallGood;
import com.troika.emall.service.FavouriteService;
import com.troika.emall.util.Constant;

@Service
@Transactional
public class FavouriteServiceImpl extends BaseServiceImpl implements FavouriteService{

	@Autowired
	private FavouriteDao favouriteDao;
	
	@Override
	public void addFavourite(long userId, long gId) {
		logger.info("查找商品ID为【"+gId+"】是否添加收藏");
		TMallFavourite faor = favouriteDao.findByUserIdAndGid(userId, gId);
		if(faor != null){
			favouriteDao.updateFavourite(userId, gId, "0");
		}else{
			favouriteDao.addFavourite(userId, gId);
		}
		logger.info("商品【"+gId+"】收藏成功");
	}

	@Override
	public void reduceFavourite(long userId, long gId) {
		favouriteDao.updateFavourite(userId, gId, "1");
		logger.info("商品【"+gId+"】取消收藏成功");
	}
	
	public void reduceFavourites(long userId, String gIds) {
		String[] gidArr = gIds.split(",");
		favouriteDao.updateFavourites(gidArr,userId);
		logger.info("商品【"+gIds+"】取消收藏成功");
	}
	
	@Override
	public void reduceFavourites(JSONObject ids) {
		favouriteDao.updateFavourites(ids);
		logger.info("商品取消收藏成功");
	}
	
//	public List<TMallGood> findFavoursByUserId(long userId){
//		logger.info("获取收用户【"+userId+"】的收藏列表");
//		return favouriteDao.findFavoursByUserId(userId);
//	}
	
	public List<Map<String,Object>> findFavouritesByUserId(long userId){
		logger.info("获取用户【"+userId+"】的收藏列表");
		List<Map<String,Object>> list = favouriteDao.findFavouritesByUserId(userId);
		for (Map<String, Object> fav : list) {
			fav.put("iconUrl", Constant.PHOTO_URL + fav.get("iconUrl"));
		}
		return list;
	}
	
	public Map<String,String> findGidIsFavourite(long userId,long gId){
		Map<String,String> result = new HashMap<String,String>();
		TMallFavourite faor = favouriteDao.findByUserIdAndGid(userId, gId);
		if(faor != null){
			if("0".equals(faor.getIsDel())){//已收藏
				result.put("isFavourite", "true");
				return result;
			}
		}
		result.put("isFavourite", "false");
		return result;
	}
}
