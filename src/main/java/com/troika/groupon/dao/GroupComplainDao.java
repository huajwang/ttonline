package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponComplain;
import com.troika.groupon.model.TGrouponComplainImg;

public interface GroupComplainDao {
	/**
	 * 保存投诉
	 * @param complain
	 * @return
	 */
	public TGrouponComplain saveOrUpdate(TGrouponComplain complain);
	/**
	 * 投诉图片
	 * @param imgs
	 */
	public void saveOrUpdateImgs(List<TGrouponComplainImg> imgs);
	/**
	 * 获取用户投诉
	 * @param userId
	 * @return
	 */
	public Map<Integer,List<Map<String,Object>>> getComplainList(Integer userId);
}
