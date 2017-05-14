package com.troika.emall.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_mall_return_exchange database table.
 * 
 */
@Entity
@Table(name="t_mall_return_record")
public class TMallReturnRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String cancelReason;

	private long orderDetailId;
	
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date processTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime;

	private String requestType;

	private String resultExplain;

	public TMallReturnRecord() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getResultExplain() {
		return this.resultExplain;
	}

	public void setResultExplain(String resultExplain) {
		this.resultExplain = resultExplain;
	}

	public long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}