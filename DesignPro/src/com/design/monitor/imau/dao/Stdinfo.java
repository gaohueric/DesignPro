package com.design.monitor.imau.dao;

import java.util.Date;

/**
 * Stdinfo entity. @author MyEclipse Persistence Tools
 */

public class Stdinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Account account;
	private String stdName;
	private String stdSex;
	private String stdMajor;
	private String stdGrade;
	private String stdClass;
	private String stdPhone;
	private String stdQq;
	private String stdSelf;
	private Date stdDate;
	private Boolean isselect;
	private Boolean hasTea;
	private Integer teaNum;
	private String stdHead;

	// Constructors

	/** default constructor */
	public Stdinfo() {
	}

	/** minimal constructor */
	public Stdinfo(Account account, String stdName, String stdSex,
			String stdMajor, String stdGrade, String stdClass, String stdPhone,
			Date stdDate) {
		this.account = account;
		this.stdName = stdName;
		this.stdSex = stdSex;
		this.stdMajor = stdMajor;
		this.stdGrade = stdGrade;
		this.stdClass = stdClass;
		this.stdPhone = stdPhone;
		this.stdDate = stdDate;
	}

	/** full constructor */
	public Stdinfo(Account account, String stdName, String stdSex,
			String stdMajor, String stdGrade, String stdClass, String stdPhone,
			String stdQq, String stdSelf, Date stdDate, Boolean isselect,
			Boolean hasTea, Integer teaNum, String stdHead) {
		this.account = account;
		this.stdName = stdName;
		this.stdSex = stdSex;
		this.stdMajor = stdMajor;
		this.stdGrade = stdGrade;
		this.stdClass = stdClass;
		this.stdPhone = stdPhone;
		this.stdQq = stdQq;
		this.stdSelf = stdSelf;
		this.stdDate = stdDate;
		this.isselect = isselect;
		this.hasTea = hasTea;
		this.teaNum = teaNum;
		this.stdHead = stdHead;
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

	public String getStdName() {
		return this.stdName;
	}

	public void setStdName(String stdName) {
		this.stdName = stdName;
	}

	public String getStdSex() {
		return this.stdSex;
	}

	public void setStdSex(String stdSex) {
		this.stdSex = stdSex;
	}

	public String getStdMajor() {
		return this.stdMajor;
	}

	public void setStdMajor(String stdMajor) {
		this.stdMajor = stdMajor;
	}

	public String getStdGrade() {
		return this.stdGrade;
	}

	public void setStdGrade(String stdGrade) {
		this.stdGrade = stdGrade;
	}

	public String getStdClass() {
		return this.stdClass;
	}

	public void setStdClass(String stdClass) {
		this.stdClass = stdClass;
	}

	public String getStdPhone() {
		return this.stdPhone;
	}

	public void setStdPhone(String stdPhone) {
		this.stdPhone = stdPhone;
	}

	public String getStdQq() {
		return this.stdQq;
	}

	public void setStdQq(String stdQq) {
		this.stdQq = stdQq;
	}

	public String getStdSelf() {
		return this.stdSelf;
	}

	public void setStdSelf(String stdSelf) {
		this.stdSelf = stdSelf;
	}

	public Date getStdDate() {
		return this.stdDate;
	}

	public void setStdDate(Date stdDate) {
		this.stdDate = stdDate;
	}

	public Boolean getIsselect() {
		return this.isselect;
	}

	public void setIsselect(Boolean isselect) {
		this.isselect = isselect;
	}

	public Boolean getHasTea() {
		return this.hasTea;
	}

	public void setHasTea(Boolean hasTea) {
		this.hasTea = hasTea;
	}

	public Integer getTeaNum() {
		return this.teaNum;
	}

	public void setTeaNum(Integer teaNum) {
		this.teaNum = teaNum;
	}

	public String getStdHead() {
		return this.stdHead;
	}

	public void setStdHead(String stdHead) {
		this.stdHead = stdHead;
	}

}