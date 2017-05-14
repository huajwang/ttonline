package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the wx_t_bus_qrcode database table.
 * 
 */
@Entity
@Table(name="wx_t_bus_qrcode")
@NamedQuery(name="WxTBusQrcode.findAll", query="SELECT w FROM WxTBusQrcode w")
public class WxTBusQrcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date createTime;

	private int gId;

	private int lifeTime;

	private int userId;

	public WxTBusQrcode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGId() {
		return this.gId;
	}

	public void setGId(int gId) {
		this.gId = gId;
	}

	public int getLifeTime() {
		return this.lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}