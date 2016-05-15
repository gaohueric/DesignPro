package com.design.monitor.imau.dao;

/**
 * Notification entity. @author MyEclipse Persistence Tools
 */

public class Notification implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer account;
	private String channelid;
	private String userid;

	// Constructors

	/** default constructor */
	public Notification() {
	}

	/** full constructor */
	public Notification(Integer account, String channelid, String userid) {
		this.account = account;
		this.channelid = channelid;
		this.userid = userid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccount() {
		return this.account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public String getChannelid() {
		return this.channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}