package com.troika.emall.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_mall_front_goods")
public class TMallFrontGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**
	 * 商品id
	 */
	private long goodId;
	/**
	 * 类型Id
	 */
	private long frontSubcategoryId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGoodId() {
		return goodId;
	}

	public void setGoodId(long goodId) {
		this.goodId = goodId;
	}

	public long getFrontSubcategoryId() {
		return frontSubcategoryId;
	}

	public void setFrontSubcategoryId(long frontSubcategoryId) {
		this.frontSubcategoryId = frontSubcategoryId;
	}
	
	
}
