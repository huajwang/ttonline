package com.troika.emall.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the t_mall_home_top_pic database table.
 * 
 */
@Entity
@Table(name="t_mall_home_top_pic")
@NamedQuery(name="findAllPic", query="SELECT t FROM TMallHomeTopPic t where t.status = '1' order by t.showOrder")
public class TMallHomeTopPic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String pic;

	private int showOrder;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public TMallHomeTopPic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}