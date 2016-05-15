package com.design.imau.bean;

public class Person {

	private String name;
	private String sex;
	private String phone;

	public Person() {
		this.name = "";
		this.sex = "";
		this.phone = "";
	}

	public Person(String name, String sex, String phone) {
		this.name = name;
		this.sex = sex;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
