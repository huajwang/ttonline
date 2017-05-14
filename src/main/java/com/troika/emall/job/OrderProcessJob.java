package com.troika.emall.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.troika.emall.common.OrderStatus;
import com.troika.emall.dao.OrderDao;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.util.DateUtil;
import com.troika.emall.util.PropertyConstant;

@Component
public class OrderProcessJob {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderProcess orderProcess;
	/**
	 * 每小时处理一次
	 */
	@Scheduled(cron="0 0 1 ? * *")
    public void execute() {//由于spring加载多次暂时注释
		System.out.println("running scheduled....");
		Date now = DateUtil.addHour(new Date(), -PropertyConstant.ORDER_LIMIT);
		// 获取所有待支付订单
		List<TMallOrder> list = orderDao.findAllWaitPay();
		for(TMallOrder order : list){
			if(order.getCreateTime() != null && order.getCreateTime().compareTo(now) < 0 ){//超过24小时
				logger.debug("物理删除订单: " + order.getId());
				orderProcess.deleteOrderById(order.getId());
			}
		}
    }  
	
}
