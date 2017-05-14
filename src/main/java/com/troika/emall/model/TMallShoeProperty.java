package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_shoe_property database table.
 * 
 */
@Entity
@Table(name="t_mall_shoe_property")
public class TMallShoeProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String color;

	private int gId;

	private int size;

	private int sold;

	private int stock;

	public TMallShoeProperty() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getGId() {
		return this.gId;
	}

	public void setGId(int gId) {
		this.gId = gId;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSold() {
		return this.sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}