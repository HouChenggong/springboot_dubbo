package cn.net.health.user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化工具类
 *
 * @author jun hu
 */
public class FormatUtil {

	private static final String format = "yyyy-MM-dd HH:mm:ss";

	public static String formatCalender(Calendar cal, String format) {
		if (format == null || format.equals(""))
			format = FormatUtil.format;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr = sdf.format(cal.getTime());
		return dateStr;

	}

	public static String formatDate(Date date, String format){
		if (format == null || format.equals(""))
			format = FormatUtil.format;
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDate(String time, String format) {
		if (format == null || format.equals(""))
			format = FormatUtil.format;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) {

		System.out.println(FormatUtil.formatCalender(Calendar.getInstance(), "yyyy/MM/dd HH:mm:ss"));

		System.out.println(FormatUtil.formatDate(new Date(), "yyyy/MM/dd HH:mm:ss"));

		System.out.println(FormatUtil.parseDate("2018/08/20 14:42:50", "yyyy/MM/dd HH:mm:ss"));

	}

}
