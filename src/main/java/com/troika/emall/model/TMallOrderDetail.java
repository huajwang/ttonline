package com.troika.emall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the t_mall_order_detail database table.
 * 
 */
@Entity
@Table(name="t_mall_order_detail")
public class TMallOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private long orderId;
	private long gId;
	private int quantity;
	private String propertyTableName;
	private long propertyId;
	private String detailStatus;
	private BigDecimal price;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliverTime;

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public TMallOrderDetail() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getgId() {
		return gId;
	}
	public void setgId(long gId) {
		this.gId = gId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPropertyTableName() {
		return propertyTableName;
	}

	public void setPropertyTableName(String propertyTableName) {
		this.propertyTableName = propertyTableName;
	}

	public long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}