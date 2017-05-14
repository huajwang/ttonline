package com.troika.emall.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_mall_front_subcategory")
public class TMallFrontSubcategory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**
	 * 类别名称
	 */
	private String name;
	/**
	 * 大类Id
	 */
	private long parentId;
	/**
	 * 显示顺序
	 */
	private int showOrder;
	/**
	 * 小类图文存储地址
	 */
	private String textElement;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getTextElement() {
		return textElement;
	}

	public void setTextElement(String textElement) {
		this.textElement = textElement;
	}
	
	
}
