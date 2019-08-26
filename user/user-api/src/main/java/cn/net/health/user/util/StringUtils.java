package cn.net.health.user.util;

import java.io.UnsupportedEncodingException;

/**
 * 字符串操作工具类
 *
 * @author jun hu
 */
public class StringUtils {

	/**
	 * 首字母变小写
	 *
	 * @param str
	 * @return
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 *
	 * @param str
	 * @return
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 判断是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final String str) {
		return (str == null) || (str.length() == 0);
	}

	/**
	 * 判断是否不为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断是否空白
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否不是空白
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}

	/**
	 * 判断多个字符串全部是否为空
	 *
	 * @param strings
	 * @return
	 */
	public static boolean isAllEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (String str : strings) {
			if (isNotEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断多个字符串其中任意一个是否为空
	 *
	 * @param strings
	 * @return
	 */
	public static boolean isHasEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (String str : strings) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checkValue为 null 或者为 "" 时返回 defaultValue
	 *
	 * @param checkValue
	 * @param defaultValue
	 * @return
	 */
	public static String isEmpty(String checkValue, String defaultValue) {
		return isEmpty(checkValue) ? defaultValue : checkValue;
	}

	/**
	 * 字符串不为 null 而且不为 "" 并且等于other
	 *
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEmptyAndEquelsOther(String str, String other) {
		if (isEmpty(str)) {
			return false;
		}
		return str.equals(other);
	}

	/**
	 * 字符串不为 null 而且不为 "" 并且不等于other
	 *
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEmptyAndNotEquelsOther(String str, String... other) {
		if (isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < other.length; i++) {
			if (str.equals(other[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串不等于other
	 *
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEquelsOther(String str, String... other) {
		for (int i = 0; i < other.length; i++) {
			if (other[i].equals(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串不为空
	 *
	 * @param strings
	 * @return
	 */
	public static boolean isNotEmpty(String... strings) {
		if (strings == null) {
			return false;
		}
		for (String str : strings) {
			if (str == null || "".equals(str.trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 比较字符相等
	 *
	 * @param value
	 * @param equals
	 * @return
	 */
	public static boolean equals(String value, String equals) {
		if (isAllEmpty(value, equals)) {
			return true;
		}
		return value.equals(equals);
	}

	/**
	 * 比较字符串不相等
	 *
	 * @param value
	 * @param equals
	 * @return
	 */
	public static boolean isNotEquals(String value, String equals) {
		return !equals(value, equals);
	}

	/**
	 * 消除转义字符
	 *
	 * @param str
	 * @return
	 */
	public static String escapeXML(String str) {
		if (str == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			switch (c) {
			case '\u00FF':
			case '\u0024':
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				if (c >= '\u0000' && c <= '\u001F')
					break;
				if (c >= '\uE000' && c <= '\uF8FF')
					break;
				if (c >= '\uFFF0' && c <= '\uFFFF')
					break;
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static String substringBefore(final String s, final String separator) {
		if (isEmpty(s) || separator == null) {
			return s;
		}
		if (separator.isEmpty()) {
			return "";
		}
		final int pos = s.indexOf(separator);
		if (pos < 0) {
			return s;
		}
		return s.substring(0, pos);
	}

	public static String substringBetween(final String str, final String open, final String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		final int start = str.indexOf(open);
		if (start != -1) {
			final int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static String substringAfter(final String str, final String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		final int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 转换为字符串
	 *
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 *
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

}
