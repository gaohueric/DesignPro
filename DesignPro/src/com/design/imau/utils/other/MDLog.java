package com.design.imau.utils.other;

public class MDLog {
	private static MDLog log;

	private MDLog() {
	}

	public static MDLog getLog() {
		if (log == null) {
			log = new MDLog();
		}
		return log;
	}

	public void debug(String tag, String funName, String content) {
		System.out.println("---->> && debug && <<----");
		System.out.println("-->> [" + tag + "] [" + funName + "] :" + content);
	}

	public void d(Class<?> c, String funName, String content) {
		System.out.println("---->> && debug && <<----");
		System.out.println("-->> [" + c.getSimpleName() + "] [" + funName
				+ "] :" + content);
	}

	public void error(String tag, String funName, Exception e) {
		System.out.println("---->> !! error !! <<----");
		e.printStackTrace();
	}

}
