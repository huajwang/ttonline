package com.troika.emall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the t_mall_order database table.
 * 
 */
@Entity
@Table(name="t_mall_order")
public class TMallOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private long addrId;

	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private long introducer;

	private String logisticsNo;

	private String orderId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderProcessTime;

	private String orderSource;

	private String orderStatus;

	private String payNo;

	private String payType;

	private String remark;

	private long userId;

	public TMallOrder() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAddrId() {
		return this.addrId;
	}

	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getIntroducer() {
		return this.introducer;
	}

	public void setIntroducer(long introducer) {
		this.introducer = introducer;
	}

	public String getLogisticsNo() {
		return this.logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderProcessTime() {
		return this.orderProcessTime;
	}

	public void setOrderProcessTime(Date orderProcessTime) {
		this.orderProcessTime = orderProcessTime;
	}

	public String getOrderSource() {
		return this.orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayNo() {
		return this.payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}