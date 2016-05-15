package com.design.imau.bean;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

	private int id = 0;
	private String major;
	private String title;
	private String self = "";
	private String QQ = "";
	private String WX = "";
	private String date = "";
	private int stdCount = 0;
	private String imgURL = "";
	private boolean isFull = false;
	private List<String> stdno = new ArrayList<String>();
	private List<Design> desList = new ArrayList<Design>();

	public Teacher() {
		super();
		this.major = "";
		this.title = "";
	}

	public Teacher(String name, String sex, String major, String title,
			String phone) {
		super(name, sex, phone);
		this.major = major;
		this.title = title;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWX() {
		return WX;
	}

	public void setWX(String wX) {
		WX = wX;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStdCount() {
		return stdCount;
	}

	public void setStdCount(int stdCount) {
		this.stdCount = stdCount;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getStdno() {
		return stdno;
	}

	public void setStdno(List<String> stdno) {
		this.stdno = stdno;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public List<Design> getDesList() {
		return desList;
	}

	public void setDesList(List<Design> desList) {
		this.desList = desList;
	}

}
