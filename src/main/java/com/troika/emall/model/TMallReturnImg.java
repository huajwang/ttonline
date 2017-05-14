package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mall_return_img database table.
 * 
 */
@Entity
@Table(name="t_mall_return_img")
@NamedQuery(name="TMallReturnImg.findAll", query="SELECT t FROM TMallReturnImg t")
public class TMallReturnImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String imge;

	private long returnId;

	public TMallReturnImg() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImge() {
		return this.imge;
	}

	public void setImge(String imge) {
		this.imge = imge;
	}

	public long getReturnId() {
		return this.returnId;
	}

	public void setReturnId(long returnId) {
		this.returnId = returnId;
	}

}