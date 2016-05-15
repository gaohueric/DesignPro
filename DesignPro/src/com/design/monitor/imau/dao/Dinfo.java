package com.design.monitor.imau.dao;

import java.util.Date;

/**
 * Dinfo entity. @author MyEclipse Persistence Tools
 */

public class Dinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer stdId;
	private Integer teaId;
	private String dname;
	private String dpath;
	private Integer supcount;
	private Integer tupcount;
	private Integer sdowncount;
	private Integer tdowncount;
	private Date uptime;
	private Date downtime;

	// Constructors

	/** default constructor */
	public Dinfo() {
	}

	/** full constructor */
	public Dinfo(Integer stdId, Integer teaId, String dname, String dpath,
			Integer supcount, Integer tupcount, Integer sdowncount,
			Integer tdowncount, Date uptime, Date downtime) {
		this.stdId = stdId;
		this.teaId = teaId;
		this.dname = dname;
		this.dpath = dpath;
		this.supcount = supcount;
		this.tupcount = tupcount;
		this.sdowncount = sdowncount;
		this.tdowncount = tdowncount;
		this.uptime = uptime;
		this.downtime = downtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStdId() {
		return this.stdId;
	}

	public void setStdId(Integer stdId) {
		this.stdId = stdId;
	}

	public Integer getTeaId() {
		return this.teaId;
	}

	public void setTeaId(Integer teaId) {
		this.teaId = teaId;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDpath() {
		return this.dpath;
	}

	public void setDpath(String dpath) {
		this.dpath = dpath;
	}

	public Integer getSupcount() {
		return this.supcount;
	}

	public void setSupcount(Integer supcount) {
		this.supcount = supcount;
	}

	public Integer getTupcount() {
		return this.tupcount;
	}

	public void setTupcount(Integer tupcount) {
		this.tupcount = tupcount;
	}

	public Integer getSdowncount() {
		return this.sdowncount;
	}

	public void setSdowncount(Integer sdowncount) {
		this.sdowncount = sdowncount;
	}

	public Integer getTdowncount() {
		return this.tdowncount;
	}

	public void setTdowncount(Integer tdowncount) {
		this.tdowncount = tdowncount;
	}

	public Date getUptime() {
		return this.uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public Date getDowntime() {
		return this.downtime;
	}

	public void setDowntime(Date downtime) {
		this.downtime = downtime;
	}

}