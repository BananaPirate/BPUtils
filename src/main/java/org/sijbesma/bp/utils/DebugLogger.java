package org.sijbesma.bp.utils;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DebugLogger {
	private static Logger logger;
	private static boolean debugFlag;
	
	public DebugLogger(Logger logger, boolean debugFlag) {
		DebugLogger.logger = logger;
		DebugLogger.debugFlag = debugFlag;
	}
	
	public static void debug(Object message) {
		if (debugFlag) {
			logger.log(Level.INFO, message.toString());
		}
	}

	public static void debug(Object message, boolean header) {
		debug(message.toString(), header, '-');
	}
	
	
	public static void debug(Object message, boolean header, char character) {
		String msg = message.toString();
		if (header) {
			int length = 20;
			int padLength = (length - msg.length() / 2);
			char[] padChar = new char[padLength];
			Arrays.fill(padChar, character);
			String padding = new String(padChar);
			if(msg.length() % 2 == 1) {
				debug(padding + msg + padding);
			}else {
				debug(padding + msg + padding + character);
			}
		} else {
			debug(message);
		}
	}

	public static void setDebugFlag(boolean debugFlag) {
		DebugLogger.debugFlag = debugFlag;
	}
	
	public static void printStackTrace() {
		new Exception().printStackTrace();
	}
	
	
}
