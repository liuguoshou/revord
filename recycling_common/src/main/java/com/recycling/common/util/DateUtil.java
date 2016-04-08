package com.recycling.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	private static final Calendar _CALENDAR = Calendar.getInstance();
	private static final SimpleDateFormat _DATE_FORMAT = new SimpleDateFormat();
	static GregorianCalendar cldr = new GregorianCalendar();

	private static Calendar _getCalendar(Date date) {
		if (date == null) {
			date = getCurrentDate();
		}
		Calendar cal = (Calendar) _CALENDAR.clone();
		cal.setTime(date);
		return cal;
	}

	private static SimpleDateFormat _getDateFormater(String pattern) {
		if (pattern == null || pattern.trim().length() == 0) {
			return null;
		}
		SimpleDateFormat sdf = (SimpleDateFormat) _DATE_FORMAT.clone();
		sdf.applyPattern(pattern);
		return sdf;
	}

	public static String getLastDayOfMonth(String date, String datePattern)
			throws ParseException {

		return DateUtil.getLastDayOfMonth(
				DateUtil.parseToDate(date, datePattern), datePattern);
	}

	public static String getLastDayOfMonth(Date date, String datePattern) {

		return DateUtil.formatDate(DateUtil.getLastDayOfMonth(date),
				datePattern);
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		int dom = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dom);

		return c.getTime();
	}

	/**
	 * ���ָ������������µĵ�һ��
	 * 
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static String getFirstDayOfMonth(String date, String datePattern)
			throws ParseException {

		return DateUtil.getFirstDayOfMonth(
				DateUtil.parseToDate(date, datePattern), datePattern);
	}

	public static String getFirstDayOfMonth(Date date, String datePattern) {

		return DateUtil.formatDate(DateUtil.getFirstDayOfMonth(date),
				datePattern);
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dom = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dom + 1);

		return c.getTime();
	}

	static public int getRptDate(Date date) {
		Day d = new Day(date);
		int lastDay = d.getYear() * 10000 + (d.getMonth() + 1) * 100
				+ d.getDayOfMonth();

		return lastDay;
	}

	static public Date getNextDate(Date date) {
		return getDate(date, 1);
	}

	static public Date getDate(Date date, int days) {
		Day d = new Day(date);
		d.addDays(days);

		return d.getDate();
	}

	static public Date getDateOfMonths(Date date, int months) {
		Day d = new Day(date);
		d.addMonths(months);

		return d.getDate();
	}

	static public void clearTime(Date dt) {
		dt.setHours(0);
		dt.setMinutes(0);
		dt.setSeconds(0);
	}
	
	public static boolean isInDateRange(Date curDate, Date startDate,
			Date endDate) {
		if (startDate == null || curDate == null) {
			return false;
		}

		if (curDate.compareTo(startDate) >= 0) {
			if (endDate == null) {
				return true;
			} else if (curDate.compareTo(endDate) <= 0) {
				return true;
			}
		}
		return false;
	}

	public static Date getDate(String sDate) {
		int nYear = Integer.parseInt(sDate.substring(0, 4));
		int nMonth = Integer.parseInt(sDate.substring(4, 6));
		int nDay = Integer.parseInt(sDate.substring(6, 8));

		int nHour = 0;
		if (sDate.length() >= 10) {
			nHour = Integer.parseInt(sDate.substring(8, 10));

		}
		int nMinute = 0;
		if (sDate.length() >= 12) {
			nMinute = Integer.parseInt(sDate.substring(10, 12));

		}
		int nSecond = 0;
		if (sDate.length() >= 14) {
			nSecond = Integer.parseInt(sDate.substring(12, 14));

		}
		cldr.set(nYear, nMonth - 1, nDay, nHour, nMinute, nSecond);
		return new Date(cldr.getTime().getTime());
	}

	public static String formatDate(Date d, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(d);
	}

	public static String formatDate(Date d) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}

	public static int getIntDate(Date d) {
		return new Integer(formatDate(d, "yyyyMMdd")).intValue();
	}

	public static String formatDate(Timestamp d, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(d);
	}

	public static Date parseToDate(String sDate, String pattern)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.parse(sDate);
	}

	public static Date parseToDateWithyyyy_MM_dd(String sDate)
			throws ParseException {
		return parseToDate(sDate, "yyyy-MM-dd");
	}

	public static String parseToDateString(String sDate) throws ParseException {
		StringBuffer date = new StringBuffer(sDate);
		date.insert(4, '-');
		date.insert(7, '-');
		return date.toString();

	}

	public static String parseToDateString(int sDate) throws ParseException {
		return parseToDateString(sDate + "");

	}

	public static Timestamp parseToTimestampWithyyyy_MM_dd(String dateString)
			throws ParseException {
		return new Timestamp(parseToDateWithyyyy_MM_dd(dateString).getTime());
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static int compare(Date dt1, int days, Date dt2) {
		// return getIntDate(getDate(dt1, days)) - getIntDate(dt2);
		int betweenDays = new Day(getDate(dt1, days)).daysBetween(new Day(dt2));
		if (dt1.after(dt2))
			return betweenDays;
		else
			return -betweenDays;
	}

	public static Date getMinTime(Date dt) {
		Date dt1 = null;
		try {
			dt1 = DateUtil.parseToDate(DateUtil.formatDate(dt, "yyyyMMdd"),
					"yyyyMMdd");
		} catch (ParseException e) {
			System.out.println("date formate error ��" + dt + ".   "
					+ e.getMessage());
		}
		return dt1;
	}

	public static Date getMaxTime(Date dt) {
		Date dt1 = null;
		try {
			dt1 = DateUtil.getNextDate(dt);
			dt1 = DateUtil.parseToDate(DateUtil.formatDate(dt1, "yyyyMMdd"),
					"yyyyMMdd");
			dt1 = new Date(dt1.getTime() - 1);
		} catch (ParseException e) {
			System.out.println("date formate error " + dt + ".   "
					+ e.getMessage());
		}
		return dt1;
	}

	public static int getDay(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.MONTH);
	}

	public static int getIntMonth(Date dt) {
		return new Integer(formatDate(dt, "yyyyMM")).intValue();
	}

	public static Date getDayBeforeSeconds(Date date, Long seconds) {

		Date newDate = (Date) date.clone();
		newDate.setTime((date.getTime() / 1000 - seconds) * 1000);
		return newDate;
	}

	public static Date getBeforeDate(Date date) {
		return getDate(date, -1);
	}

	public static int daysOfTwo(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		cal2.setTime(date2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);

		long DAY = 24L * 60L * 60L * 1000L;

		return (int) ((cal2.getTime().getTime() - cal1.getTime().getTime()) / DAY);
	}

	public static int minuteOfTwo(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		cal2.setTime(date2);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);

		long MINUTE = 60L * 1000L;

		return (int) ((cal2.getTime().getTime() - cal1.getTime().getTime()) / MINUTE);
	}
	
	/**
	 * 获取当前时间
	 * @return yyyyMMddHHmmss
	 */
	public static String getNowToString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 获取当前时间
	 * @return yyyyMMddHHmmss
	 */
	public static String getNowToStringStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static void main(String[] args) {
		System.out.println(getNowToStringStr()+" 00:00:00");
	}
}
