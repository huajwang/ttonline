package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_sys_param database table.
 * 
 */
@Entity
@Table(name="t_sys_param")
public class TSysParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte hidden;

	private String name;

	@Column(name="param_value")
	private String paramValue;

	private String remark;

	public TSysParam() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getHidden() {
		return this.hidden;
	}

	public void setHidden(byte hidden) {
		this.hidden = hidden;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}