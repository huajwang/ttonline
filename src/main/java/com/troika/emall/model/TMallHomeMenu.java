package com.troika.emall.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the t_mall_home_menu database table.
 * 
 */
@Entity
@Table(name="t_mall_home_menu")
@NamedQuery(name="findAllMenu", query="SELECT t FROM TMallHomeMenu t where t.status = '1' order by t.showOrder")
public class TMallHomeMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String logo;

	private String name;

	private int showOrder;

	private String status;

	public TMallHomeMenu() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}