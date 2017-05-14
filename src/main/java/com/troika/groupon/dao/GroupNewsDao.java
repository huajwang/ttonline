package com.troika.groupon.dao;

import java.util.List;

import com.troika.groupon.model.TGrouponNews;
import com.troika.groupon.model.TGrouponNewsRead;

public interface GroupNewsDao {
	/**
	 * 根据ID获取消息
	 * @param userId
	 * @return
	 */
	public List<TGrouponNews> findNewsByUserIdAndStatus(Integer userId,String status);
	/**
	 * 修改消息状态
	 * @param id
	 * @param userId
	 * @param status
	 * @return
	 */
	public boolean updNewsStatus(Integer id,Integer userId,String status);
	/**
	 * 保存更新新闻
	 * @param news
	 * @return
	 */
	public TGrouponNews saveOrUpdate(TGrouponNews news);
	/**
	 * 保存读取标记
	 * @param reads
	 */
	public void saveOrUpdateNewsRead(List<TGrouponNewsRead> reads);
}
