package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_supplier_detail database table.
 * 
 */
@Entity
@Table(name="t_mall_supplier_detail")
public class TMallSupplierDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String arearId;

	private String cityId;

	private String contact;

	private String fax;

	private String name;

	private String phone;

	private String postalCode;

	private String provinceId;

	private String street;
	
	private String wxwallet;

	private String alipaywallet;

	private String image;

	private String openId;
	
	private String user;
	
	private String password;

	private String status;


	public TMallSupplierDetail() {
	}

	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setUser(String user) {
		this.user = user;
	}



	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getWxwallet() {
		return wxwallet;
	}


	public void setWxwallet(String wxwallet) {
		this.wxwallet = wxwallet;
	}


	public String getAlipaywallet() {
		return alipaywallet;
	}


	public void setAlipaywallet(String alipaywallet) {
		this.alipaywallet = alipaywallet;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArearId() {
		return this.arearId;
	}

	public void setArearId(String arearId) {
		this.arearId = arearId;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}