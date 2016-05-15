package com.design.imau.bean;

public class Student extends Person {

	private int id = 0;
	private String major;
	private String grade;
	private String sclass;
	private String self = "";
	private String QQ = "";
	private String date = "";
	private String imgURL = "";
	private boolean isSelect = false;
	private boolean hasTea = false;
	private String teaNum = "";
	private String belong = "";

	public Student() {
		super();
		this.major = "";
		this.grade = "";
		this.sclass = "";
	}

	public Student(String name, String sex, String major, String grade,
			String sclass, String phone) {
		super(name, sex, phone);
		this.major = major;
		this.grade = grade;
		this.sclass = sclass;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSclass() {
		return sclass;
	}

	public void setSclass(String sclass) {
		this.sclass = sclass;
	}

	public String getSelf() {
		return self;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qq) {
		QQ = qq;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public boolean isHasTea() {
		return hasTea;
	}

	public void setHasTea(boolean hasTea) {
		this.hasTea = hasTea;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeaNum() {
		return teaNum;
	}

	public void setTeaNum(String teaNum) {
		this.teaNum = teaNum;
	}

}
