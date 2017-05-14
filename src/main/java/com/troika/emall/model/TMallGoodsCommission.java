package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the t_mall_goods_commission database table.
 * 
 */
@Entity
@Table(name="t_mall_goods_commission")
public class TMallGoodsCommission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal commissions;

	private int gId;

	private BigDecimal rate;

	public TMallGoodsCommission() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCommissions() {
		return this.commissions;
	}

	public void setCommissions(BigDecimal commissions) {
		this.commissions = commissions;
	}

	public int getGId() {
		return this.gId;
	}

	public void setGId(int gId) {
		this.gId = gId;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}