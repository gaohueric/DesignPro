package com.design.monitor.imau.dao;

import java.util.Date;

/**
 * TControl entity. @author MyEclipse Persistence Tools
 */

public class TControl implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer teaId;
	private Integer cycle;
	private Integer reminder;
	private Date deadline;

	// Constructors

	/** default constructor */
	public TControl() {
	}

	/** minimal constructor */
	public TControl(Integer teaId) {
		this.teaId = teaId;
	}

	/** full constructor */
	public TControl(Integer teaId, Integer cycle, Integer reminder,
			Date deadline) {
		this.teaId = teaId;
		this.cycle = cycle;
		this.reminder = reminder;
		this.deadline = deadline;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeaId() {
		return this.teaId;
	}

	public void setTeaId(Integer teaId) {
		this.teaId = teaId;
	}

	public Integer getCycle() {
		return this.cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getReminder() {
		return this.reminder;
	}

	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}