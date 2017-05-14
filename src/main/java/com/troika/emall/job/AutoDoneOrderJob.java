package com.troika.emall.job;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.util.DateUtil;
import com.troika.emall.util.PropertyConstant;

@Component
public class AutoDoneOrderJob {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private OrderProcess orderProcess;
	/**
	 * 7天后自动完成订单。 每天凌晨两点启动该进程去扫描订单详情表里状态为“待收货”的记录， 把字段deliverTime(厂商发货时间)大于
	 * 11天的子订单自动标志为已完成(状态改为7)。7天的时间是大概的时间： 从厂商发货开始算，物流大概4天到。 买家收到物品大概7天后，如果
	 * 买家没有点击确认收货，系统自动把该订单完成。
	 * 自动完成订单.
	 */
	@Scheduled(cron="0 0 2 ? * *")
	public void execute() {
		logger.debug("系统自动7天后自动完成订单。 ");
		Date now = DateUtil.addHour(new Date(), -PropertyConstant.ORDER_DONE_LIMIT);
		// 获取订单详情表里所有待收货订单详情
		List<TMallOrderDetail> orderDetails = orderProcess.findOrderDetailsByStatus(OrderDetailStatus.TAKEIN);
		for(TMallOrderDetail orderDetail : orderDetails){
			if(orderDetail.getDeliverTime() != null && orderDetail.getDeliverTime().compareTo(now) < 0 ){//超过法定退货时间
				logger.debug("把订单详情状态为待收货且时间大于法定退货时间的订单详情标志位完成: " + orderDetail.getId());
				orderProcess.updateOrderDetailStatus(orderDetail.getId(), OrderDetailStatus.FINISH);
			}
		}
		// 下一步,获取状态为“已支付”的订单。遍历订单所属的订单详情，当所有的订单详情的状态都为完成时， 把总订单的状态设置成完成状态。
		List<TMallOrder> orders = orderProcess.getOrdersByStatus(OrderStatus.PAID);
		for (TMallOrder order : orders) {
			List<TMallOrderDetail> details = orderProcess.findOrderDetailsByOrderId(order.getId());
			boolean allDone = true;
			for (TMallOrderDetail detail : details) {
				if (!detail.getDetailStatus().equalsIgnoreCase(OrderDetailStatus.FINISH.getCode())) {
					allDone = false;
					break;
				}
			}
			if (allDone) {
				// 把总的订单状态改为已完成
				orderProcess.updateOrderStatus(order.getId(), OrderStatus.FINISH.getCode());
			}
		}
	}  
}
