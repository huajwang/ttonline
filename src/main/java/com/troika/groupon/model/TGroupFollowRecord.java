package com.troika.groupon.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 项目关注记录
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_group_follow_record")
public class TGroupFollowRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer userid;

	private Integer prejectid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getPrejectid() {
		return prejectid;
	}

	public void setPrejectid(Integer prejectid) {
		this.prejectid = prejectid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}