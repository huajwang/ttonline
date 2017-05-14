package com.troika.emall.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the t_mall_cart_detail database table.
 * 
 */
@Entity
@Table(name="t_mall_cart_detail")
public class TMallCartDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private long cartId;

	private long gId;

	private int gQuantity;
	
	private String propertyTableName;
	private long propertyId;
	
	private BigDecimal price;

	public TMallCartDetail() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCartId() {
		return this.cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getGId() {
		return this.gId;
	}

	public void setGId(long gId) {
		this.gId = gId;
	}

	public int getGQuantity() {
		return this.gQuantity;
	}

	public void setGQuantity(int gQuantity) {
		this.gQuantity = gQuantity;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}