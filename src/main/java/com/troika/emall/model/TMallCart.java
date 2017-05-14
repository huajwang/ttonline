package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mall_cart database table.
 * 
 */
@Entity
@Table(name="t_mall_cart")
public class TMallCart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long cartId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cartCreateTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cartProcessTime;

	private int cartStatus;

	private BigDecimal total;

	private long userId;
	
	private Integer introducer;//推荐人

	public TMallCart() {
	}

	public long getCartId() {
		return this.cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public Date getCartCreateTime() {
		return this.cartCreateTime;
	}

	public void setCartCreateTime(Date cartCreateTime) {
		this.cartCreateTime = cartCreateTime;
	}

	public Date getCartProcessTime() {
		return this.cartProcessTime;
	}

	public void setCartProcessTime(Date cartProcessTime) {
		this.cartProcessTime = cartProcessTime;
	}

	public int getCartStatus() {
		return this.cartStatus;
	}

	public void setCartStatus(int cartStatus) {
		this.cartStatus = cartStatus;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getIntroducer() {
		return introducer;
	}

	public void setIntroducer(Integer introducer) {
		this.introducer = introducer;
	}

}