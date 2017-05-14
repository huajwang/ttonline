package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sms_check database table.
 * 
 */
@Entity
@Table(name="t_sms_check")
public class TSmsCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TSmsCheckPK id;

	private String code;

	private int countNum;

	@Temporal(TemporalType.DATE)
	private Date createDate;

	private String effective;

	public TSmsCheck() {
	}

	public TSmsCheckPK getId() {
		return this.id;
	}

	public void setId(TSmsCheckPK id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCountNum() {
		return this.countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEffective() {
		return this.effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

}