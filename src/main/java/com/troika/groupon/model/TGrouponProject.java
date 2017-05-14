package com.troika.groupon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_groupon_project database table.
 * 
 */
@Entity
@Table(name="t_groupon_project")
public class TGrouponProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal amount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private int createUser;

	private String ems;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private String name;

	private String remark;

	private String sendTime;

	private String status;
	
	private String opinion;//审核意见

	public TGrouponProject() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public String getEms() {
		return this.ems;
	}

	public void setEms(String ems) {
		this.ems = ems;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	
}