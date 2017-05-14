package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_province database table.
 * 
 */
@Entity
@Table(name="t_mall_province")
@NamedQuery(name="findAllProvince", query="SELECT t FROM TMallProvince t")
public class TMallProvince implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String provinceId;

	private String province;

	public TMallProvince() {
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}