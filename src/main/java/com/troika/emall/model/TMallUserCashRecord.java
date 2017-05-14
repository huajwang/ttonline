package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the t_mall_user_cash_record database table.
 * 
 */
@Entity
@Table(name="t_mall_user_cash_record")
@NamedQuery(name="TMallUserCashRecord.findAllByUserId", 
	query="SELECT t FROM TMallUserCashRecord t WHERE t.userId = :userId")
public class TMallUserCashRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String accountNo;

	private String accountType;

	private BigDecimal amount;

	private Timestamp createdTime;

	private Timestamp processTime;

	private String result;

	private String status;

	private long userId;

	public TMallUserCashRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Timestamp processTime) {
		this.processTime = processTime;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}