package com.design.imau.utils.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

	public static String getDate() {
		String date = "";
		Date c = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		date = f.format(c);
		return date;
	}
}
