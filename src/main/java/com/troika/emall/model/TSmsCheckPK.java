package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_sms_check database table.
 * 
 */
@Embeddable
public class TSmsCheckPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String phone;

	private int type;

	public TSmsCheckPK() {
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TSmsCheckPK)) {
			return false;
		}
		TSmsCheckPK castOther = (TSmsCheckPK)other;
		return 
			this.phone.equals(castOther.phone)
			&& (this.type == castOther.type);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.phone.hashCode();
		hash = hash * prime + this.type;
		
		return hash;
	}
}