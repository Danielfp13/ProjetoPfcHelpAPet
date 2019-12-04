package com.projeto.helpapet.resources.execepton;

import java.io.Serializable;

public class StandardError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;
	private String msg;
	private Long TimeStamp;
	public StandardError(Integer status, String msg, Long timeStanp) {
		super();
		this.status = status;
		this.msg = msg;
		TimeStamp = timeStanp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTimeStanp() {
		return TimeStamp;
	}
	public void setTimeStanp(Long timeStanp) {
		TimeStamp = timeStanp;
	}
	
	
}
