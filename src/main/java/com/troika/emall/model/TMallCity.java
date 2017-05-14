package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_city database table.
 * 
 */
@Entity
@Table(name="t_mall_city")
public class TMallCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String cityId;

	private String city;

	private String provinceId;

	public TMallCity() {
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

}