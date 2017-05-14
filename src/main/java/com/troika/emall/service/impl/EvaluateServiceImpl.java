package com.troika.emall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.dao.EvaluateDao;
import com.troika.emall.dao.OrderDao;
import com.troika.emall.model.TMallEvaluation;
import com.troika.emall.model.TMallEvaluationImg;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.service.EvaluateService;

@Service
@Transactional
public class EvaluateServiceImpl extends BaseServiceImpl implements
		EvaluateService {

	@Autowired
	private EvaluateDao evaluateDao;
	@Autowired
	private OrderDao orderDao;

	/**
	 * 保存评价数据
	 */
	public Map<String, String> save(Long orderId, Long gId, String content,
			String rating, String Icon) {
		Map<String, String> result = new HashMap<String, String>();
		TMallOrderDetail detail = orderDao.findOrderDetailByoIdAndGid(orderId,
				gId);
		if (detail != null) {
			TMallEvaluation eval = new TMallEvaluation();
			eval.setOrderId(orderId);
			eval.setGId(gId);
			eval.setContent(content);
			eval.setRating(rating);
			eval.setCreateTime(new Date());
			evaluateDao.save(eval);
			detail.setDetailStatus(OrderDetailStatus.FINISH.getCode());
			orderDao.saveOrUpdateOneOrderDetail(detail);
			if(Icon.toString() != null && Icon.toString() != ""){
				for (String item : Icon.split(",")) {
					TMallEvaluationImg img = new TMallEvaluationImg();
					img.setEvaluationId((int) eval.getId());
					img.setIcon(item);
					evaluateDao.saveImg(img);
				}
			}
			logger.info("订单" + orderId + "--商品" + gId + "添加评价成功");
			result.put("msg", "添加评价成功");
		} else {
			result.put("msg", "找不到相应商品");
		}
		return result;
	}

	public List<Map<String, Object>> evalListByGid(Long gId) {
		return evaluateDao.evalListByGid(gId);
	}

	/**
	 * 保存评价图片
	 */
	@Override
	public void saveImg(TMallEvaluationImg record) {
		// TODO Auto-generated method stub
		evaluateDao.saveImg(record);
	}

	/**
	 * 获取评价图片
	 */
	@Override
	public List<TMallEvaluationImg> getImg(Integer evaluationId) {
		// TODO Auto-generated method stub
		return evaluateDao.getImg(evaluationId);
	}
}
