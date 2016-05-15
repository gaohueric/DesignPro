package com.design.imau.utils.other;

/**
 * 功能：推送消息标题和内容
 * 
 * @author WenC
 */
public class PushMessage {

	private String title;
	private String description;

	public PushMessage() {

	}

	public PushMessage(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
