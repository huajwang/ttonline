package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_area database table.
 * 
 */
@Entity
@Table(name="t_mall_area")
public class TMallArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String areaId;

	private String area;

	private String cityId;

	public TMallArea() {
	}

	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}