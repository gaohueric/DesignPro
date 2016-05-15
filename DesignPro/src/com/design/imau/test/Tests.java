package com.design.imau.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.design.imau.utils.other.PushMessage;
import com.design.monitor.imau.biz.NotificationBiz;

public class Tests {

	public static void main(String[] args) {
		String s = "啊啊啊表";
		try {
			System.out.println(new String(s.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void test() {
		PushMessage msg = new PushMessage("你好", "你好世界，hello world");
		NotificationBiz noti = NotificationBiz.getInstance();
		boolean flag = noti.sendNotification(
				Long.parseLong("4014552973884801196"), "1118910504953799415",
				msg);
		System.out.println(flag);
	}

}
