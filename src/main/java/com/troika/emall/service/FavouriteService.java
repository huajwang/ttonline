package com.troika.emall.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface FavouriteService {
	/**
	 * 添加收藏
	 * @param userId
	 * @param gId
	 */
	public void addFavourite(long userId,long gId);
	/**
	 * 取消收藏
	 * @param userId
	 * @param gId
	 */
	public void reduceFavourite(long userId,long gId);
	public void reduceFavourites(long userId,String gIds);
	public void reduceFavourites(JSONObject ids);
	// public List<TMallGood> findFavoursByUserId(long userId);
	public Map<String,String> findGidIsFavourite(long userId,long gId);
	public List<Map<String,Object>> findFavouritesByUserId(long userId);
}
