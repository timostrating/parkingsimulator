package com.parkingtycoon.helpers;

import com.badlogic.gdx.Gdx;

/**
 * This Class helps with printing improved console massages
 */
public class Logger {

	public static void info(Object info) {

		if (info instanceof int[]) {
			String newInfo = "[";
			boolean first = true;
			for (int i : (int[]) info) {
				newInfo += first ? i : ", " + i;
				first = false;
			}
			info = newInfo + "]";
		}

		String string = info == null ? "null" : info.toString();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Gdx.app.log("INFO", string + "\n" + stackTrace[2]);
	}

}
