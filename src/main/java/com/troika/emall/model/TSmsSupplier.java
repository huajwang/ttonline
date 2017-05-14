package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_sms_supplier database table.
 * 
 */
@Entity
@Table(name="t_sms_supplier")
public class TSmsSupplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int keyId;

	private String name;

	private int status;

	private String supplier;

	public TSmsSupplier() {
	}

	public int getKeyId() {
		return this.keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}