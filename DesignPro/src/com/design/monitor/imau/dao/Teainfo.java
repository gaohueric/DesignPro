package com.design.monitor.imau.dao;

import java.util.Date;

/**
 * Teainfo entity. @author MyEclipse Persistence Tools
 */

public class Teainfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Account account;
	private String teaName;
	private String teaSex;
	private String teaMajor;
	private String teaTitle;
	private String teaPhone;
	private String teaQq;
	private String teaWx;
	private String teaSelf;
	private Date teaDate;
	private Integer teaStdcount;
	private String teaHead;

	// Constructors

	/** default constructor */
	public Teainfo() {
	}

	/** minimal constructor */
	public Teainfo(Account account, String teaName, String teaSex,
			String teaMajor, String teaTitle, String teaPhone, Date teaDate) {
		this.account = account;
		this.teaName = teaName;
		this.teaSex = teaSex;
		this.teaMajor = teaMajor;
		this.teaTitle = teaTitle;
		this.teaPhone = teaPhone;
		this.teaDate = teaDate;
	}

	/** full constructor */
	public Teainfo(Account account, String teaName, String teaSex,
			String teaMajor, String teaTitle, String teaPhone, String teaQq,
			String teaWx, String teaSelf, Date teaDate, Integer teaStdcount,
			String teaHead) {
		this.account = account;
		this.teaName = teaName;
		this.teaSex = teaSex;
		this.teaMajor = teaMajor;
		this.teaTitle = teaTitle;
		this.teaPhone = teaPhone;
		this.teaQq = teaQq;
		this.teaWx = teaWx;
		this.teaSelf = teaSelf;
		this.teaDate = teaDate;
		this.teaStdcount = teaStdcount;
		this.teaHead = teaHead;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTeaName() {
		return this.teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getTeaSex() {
		return this.teaSex;
	}

	public void setTeaSex(String teaSex) {
		this.teaSex = teaSex;
	}

	public String getTeaMajor() {
		return this.teaMajor;
	}

	public void setTeaMajor(String teaMajor) {
		this.teaMajor = teaMajor;
	}

	public String getTeaTitle() {
		return this.teaTitle;
	}

	public void setTeaTitle(String teaTitle) {
		this.teaTitle = teaTitle;
	}

	public String getTeaPhone() {
		return this.teaPhone;
	}

	public void setTeaPhone(String teaPhone) {
		this.teaPhone = teaPhone;
	}

	public String getTeaQq() {
		return this.teaQq;
	}

	public void setTeaQq(String teaQq) {
		this.teaQq = teaQq;
	}

	public String getTeaWx() {
		return this.teaWx;
	}

	public void setTeaWx(String teaWx) {
		this.teaWx = teaWx;
	}

	public String getTeaSelf() {
		return this.teaSelf;
	}

	public void setTeaSelf(String teaSelf) {
		this.teaSelf = teaSelf;
	}

	public Date getTeaDate() {
		return this.teaDate;
	}

	public void setTeaDate(Date teaDate) {
		this.teaDate = teaDate;
	}

	public Integer getTeaStdcount() {
		return this.teaStdcount;
	}

	public void setTeaStdcount(Integer teaStdcount) {
		this.teaStdcount = teaStdcount;
	}

	public String getTeaHead() {
		return this.teaHead;
	}

	public void setTeaHead(String teaHead) {
		this.teaHead = teaHead;
	}

}