package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallEvaluation;
import com.troika.emall.model.TMallEvaluationImg;

public interface EvaluateDao {
	public void save(TMallEvaluation eval);

	public List<Map<String, Object>> evalListByGid(Long gId);

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
