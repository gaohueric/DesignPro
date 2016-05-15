package com.design.monitor.imau.dao;

import java.util.HashSet;
import java.util.Set;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class Account implements java.io.Serializable {

	// Fields

	private Integer ids;
	private String passwd;
	private Boolean who;
	private Boolean astatus;
	private Set teainfos = new HashSet(0);
	private Set stdinfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(Integer ids, String passwd) {
		this.ids = ids;
		this.passwd = passwd;
	}

	/** full constructor */
	public Account(Integer ids, String passwd, Boolean who, Boolean astatus,
			Set teainfos, Set stdinfos) {
		this.ids = ids;
		this.passwd = passwd;
		this.who = who;
		this.astatus = astatus;
		this.teainfos = teainfos;
		this.stdinfos = stdinfos;
	}

	// Property accessors

	public Integer getIds() {
		return this.ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Boolean getWho() {
		return this.who;
	}

	public void setWho(Boolean who) {
		this.who = who;
	}

	public Boolean getAstatus() {
		return this.astatus;
	}

	public void setAstatus(Boolean astatus) {
		this.astatus = astatus;
	}

	public Set getTeainfos() {
		return this.teainfos;
	}

	public void setTeainfos(Set teainfos) {
		this.teainfos = teainfos;
	}

	public Set getStdinfos() {
		return this.stdinfos;
	}

	public void setStdinfos(Set stdinfos) {
		this.stdinfos = stdinfos;
	}

}