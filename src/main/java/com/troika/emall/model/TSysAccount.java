package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sys_account database table.
 * 
 */
@Entity
@Table(name="t_sys_account")
public class TSysAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int operatorId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private byte isLocked;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	private String operatorName;

	private String pwd;

	public TSysAccount() {
	}

	public int getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getIsLocked() {
		return this.isLocked;
	}

	public void setIsLocked(byte isLocked) {
		this.isLocked = isLocked;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}