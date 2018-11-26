package com.codefans.concurrency.servercommon.util;

import java.util.regex.Pattern;

public class ValidateUtil {

	private static final Pattern XSS_PATTERN = Pattern
			.compile("(.*\\s*)((<\\s*script\\s*)|(<\\s*embed\\s*)|(<\\s*style\\s*)|(<\\s*img\\s*)|(<\\s*image\\s*)|(<\\s*frame\\s*)|(<\\s*object\\s*)|(<\\s*iframe\\s*)|(<\\s*a\\s*)|(<\\s*frameset\\s*)|(<\\s*meta\\s*)|(<\\s*xml\\s*)|(<\\s*applet\\s*)|(\\s*onmouse\\s*)|(<\\s*link\\s*)|(\\s*onload\\s*)|(\\s*onblur\\s*)|(\\s*onchange\\s*)|(\\s*onclick\\s*)|(\\s*ondblclick\\s*)|(\\s*onfocus\\s*)|(\\s*onkey\\s*)|(\\s*onselect\\s*)|(\\s*alert\\s*\\())(.*\\s*)");

	/**
	 * XSS校验
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isXSS(String content) {
		return XSS_PATTERN.matcher(content).matches();
	}

	/**
	 * 非空参数校验
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isNotEmpty(Object... objects) {
		if (objects == null || objects.length < 1) {
			return false;
		}
		for (Object obj : objects) {
			if (obj == null || obj.toString().length() < 1 || "null".equals(obj.toString())) {
				return false;
			}
		}
		return true;
	}


}
