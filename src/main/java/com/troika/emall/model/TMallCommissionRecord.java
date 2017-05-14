package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the t_mall_commission_record database table.
 * select t from TMallCommissionRecord t where userId=?
 */
@Entity
@Table(name="t_mall_commission_record")
@NamedQuery(name="TMallCommissionRecord.findCommission", 
	query="SELECT t FROM TMallCommissionRecord t where t.introducer = :introducer and t.state = :state")
public class TMallCommissionRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal amount;

	private Timestamp createTime;

	private int introducer;

	private int orderId;

	private String state;

	public TMallCommissionRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getIntroducer() {
		return this.introducer;
	}

	public void setIntroducer(int introducer) {
		this.introducer = introducer;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}