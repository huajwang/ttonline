package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mall_user database table.
 * 
 */
@Entity
@Table(name="t_mall_user")
public class TMallUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String email;

	private String password;

	private String phone;

	private String realName;

	private BigDecimal rewards;

	private String sex;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String userId;

	private String userName;

	private BigDecimal withdrawable;
	
	private String wxwallet;
	
	private String alipaywallet;
	
	private String iconUrl;
	
	private String openId;
	
	private String subscribe;

	public TMallUser() {
	}
	
	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getRewards() {
		return this.rewards;
	}

	public void setRewards(BigDecimal rewards) {
		this.rewards = rewards;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getWithdrawable() {
		return this.withdrawable;
	}

	public void setWithdrawable(BigDecimal withdrawable) {
		this.withdrawable = withdrawable;
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}