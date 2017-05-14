package com.troika.groupon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_groupon_complain_Img database table.
 * 
 */
@Entity
@Table(name="t_groupon_complain_Img")
public class TGrouponComplainImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int complainId;

	private String icon;

	public TGrouponComplainImg() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getComplainId() {
		return this.complainId;
	}

	public void setComplainId(int complainId) {
		this.complainId = complainId;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}