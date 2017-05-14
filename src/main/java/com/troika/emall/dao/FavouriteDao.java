package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.troika.emall.model.TMallFavourite;

public interface FavouriteDao {
	public void addFavourite(long userId,long gId);
	public void updateFavourite(long userId,long gId,String isDel);
	public void updateFavourites(String[] ids,Long userId);
	public void updateFavourites(JSONObject ids);
	public TMallFavourite findByUserIdAndGid(long userId,long gId);
	// public List<TMallGood> findFavoursByUserId(long userId);
	public List<Map<String,Object>> findFavouritesByUserId(long userId);
}
