package com.design.imau.bean;

public class Design {

	private int ID;
	private String name;
	private int tea_id;
	private String std_id;
	private int canchoice;
	private int isselect;
	private String description;
	private String date;
	
	public Design() {
		this.ID = 0;
		this.name = "";
		this.tea_id = 0;
		this.std_id = "";
		this.isselect = 0;
		this.description = "";
		this.date = "";
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTea_id() {
		return tea_id;
	}

	public void setTea_id(int tea_id) {
		this.tea_id = tea_id;
	}

	public String getStd_id() {
		return std_id;
	}

	public void setStd_id(String std_id) {
		this.std_id = std_id;
	}

	public int getIsselect() {
		return isselect;
	}

	public void setIsselect(int isselect) {
		this.isselect = isselect;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCanchoice() {
		return canchoice;
	}

	public void setCanchoice(int canchoice) {
		this.canchoice = canchoice;
	}
	
}
