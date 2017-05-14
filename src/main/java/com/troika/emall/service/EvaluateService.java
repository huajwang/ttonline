package com.troika.emall.service;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallEvaluationImg;

public interface EvaluateService {
	/**
	 * 添加评价
	 * @param orderId
	 * @param gId
	 * @param content
	 * @param rating
	 * @return
	 */
	public Map<String,String> save(Long orderId,Long gId,String content,String rating,String Icon);
	/**
	 * 商品评价列表
	 * @param gId
	 * @return
	 */
	public List<Map<String,Object>> evalListByGid(Long gId);
	
	/**
	 * 保存评价图片
	 * 
	 * @param record
	 */
	public void saveImg(TMallEvaluationImg record);

	/**
	 * 获取评价图片
	 * 
	 * @param evaluationId
	 * @return
	 */
	public List<TMallEvaluationImg> getImg(Integer evaluationId);
}
