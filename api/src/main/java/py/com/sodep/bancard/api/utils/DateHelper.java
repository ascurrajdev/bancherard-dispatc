package py.com.sodep.bancard.api.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateHelper {

	public static final int NUMBER_OF_MONTHS_IN_A_YEAR = 12;
	public static final int NUMBER_OF_DAYS_IN_A_YEAR = 365;
	public static final int ONE_DAY_IN_HOUR = 24;
	public static final long ONE_MINUTE_IN_MILLIS = 60000;
	public static final long ONE_HOUR_IN_MILLIS = 3600000;
	public static final long DAY_LONG = ONE_HOUR_IN_MILLIS * ONE_DAY_IN_HOUR;
	public static final String SIMPLE_DAY_FORMAT = "dd/MM/yyyy";
	public static final String SIMPLE_DAY_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String SIMPLE_DAY_FORMAT_IC_MOTOR = "yyyy-MM-dd";
	public static final String DEFAULT_DATE_TIME_MILL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String SIMPLE_DAY_TIME_FORMAT_TssZ = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String SIMPLE_DAY_TIME_FORMAT_TsssZ = "yyyy-MM-dd'T'HH:mm:sssZ";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SIMPLE_DAY_TIME_FORMAT_FOR_UQ_FILE = "yyyy-MM-dd-HHmmssS";
	public static final String TIME_FORMAT_HHMMSS = "HHmmss";
	public static final DateFormat sdf = new SimpleDateFormat(SIMPLE_DAY_FORMAT_IC_MOTOR);
	public static final DateFormat defaultDateTimeFormat = new SimpleDateFormat(DateHelper.DEFAULT_DATE_TIME_FORMAT);

	private DateHelper() {		
	}

	public static String yesterdayAsString() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return defaultDateTimeFormat.format(cal.getTime());
	}

	public static String formatDate(Date date) {
		return defaultDateTimeFormat.format(date);
	}

	public static String formatDate(XMLGregorianCalendar date) {
		Calendar c = date.toGregorianCalendar();
		sdf.setTimeZone(c.getTimeZone());
		return sdf.format(c.getTime());
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(String dateStr)
			throws ParseException, DatatypeConfigurationException {
		Date date = sdf.parse(dateStr);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	}

	public static String fromDate(final Date date) {
		return new SimpleDateFormat(SIMPLE_DAY_FORMAT_IC_MOTOR).format(date);
	}

	public static String fromDate(final Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date toDate(final String string) throws ParseException {
		return toDate(string, SIMPLE_DAY_FORMAT_IC_MOTOR);
	}

	public static Date toDate(final String string, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(string);
	}

	public static String now() {
		return fromDate(new Date());
	}

	public static String now(String format) {
		return fromDate(new Date(), format);
	}

	public static Date getDateWFirstHms(long datelong) {
		return new Date(setHmsFirst(datelong).getTimeInMillis());
	}

	public static Date getDateWLastHms(long datelong) {
		return new Date(setHmsLast(datelong).getTimeInMillis());
	}

	public static Calendar setHmsFirst(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		setCalendarHmsToFirst(calendar);
		return calendar;
	}

	public static Calendar setHmsLast(long dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		setCalendarHmsToLast(calendar);
		return calendar;
	}

	private static void setCalendarHmsToFirst(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	private static void setCalendarHmsToLast(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 59);
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static boolean isBeforeToday(Date date) {
		Date dateNow = getCurrentDateWOHms();
		return date.before(dateNow);
	}

	public static boolean isBefore(Date date, Date when) {
		if (date == null || when == null)
			return false;
		return getDateWFirstHms(date.getTime()).before(getDateWFirstHms(when.getTime()));
	}

	public static boolean isAfter(Date date, Date when) {
		if (date == null || when == null)
			return false;
		return getDateWFirstHms(date.getTime()).after(getDateWFirstHms(when.getTime()));
	}

	public static Date getCurrentDateWOHms() {
		return new Date(setHmsFirst(getCurrentDate().getTime()).getTime().getTime());
	}

	public static boolean daysBetweenDates(Date dateStart, Date dateEnd, int daysQuantity) {
		long dayDiff = (dateEnd.getTime() - dateStart.getTime()) / DAY_LONG;
		return (dayDiff < daysQuantity);
	}

	public static Date addMonth(Date date, int add) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, add);
		return calendar.getTime();
	}

	public static Date addDate(Date date, int add) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, add);
		return calendar.getTime();
	}
	
	public static Date addHour(Date date, int add) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, add);
		return calendar.getTime();
	}
	
	public static Date addMinute(Date date, int add) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, add);
		return calendar.getTime();
	}

	public static Integer daysBetweenDatesToInteger(Date startDate, Date endDate, int daysQuantity) {
		return Integer.valueOf(Math.round((endDate.getTime() - startDate.getTime()) / DAY_LONG) + 1);
	}

	public static Integer getMinuteBetweenDate(Date startDate, Date endDate) {
		long diferenciaMils = endDate.getTime() - startDate.getTime();
		long segundos = diferenciaMils / 1000;
		return Integer.valueOf(String.valueOf(segundos / 60));
	}

	public static String getMonthDescriptionByDate(Date date) {
		if (date == null)
			return "";
		return (new SimpleDateFormat("MMMM", new Locale("es", "ES"))).format(date);
	}

	public static double daysBetweenDate(Date initialDate, Date endDate) {
		return (endDate.getTime() - initialDate.getTime()) / DAY_LONG;
	}

	public static double monthsBetweenTwoDates(Date fechaInicio, Date fechaFin) {
		try {
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(fechaInicio);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(fechaFin);
			long startMes = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);
			long endMes = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);
			long diffMonth = endMes - startMes;
			return diffMonth;
		} catch (Exception e) {
			return 0;
		}
	}

	public static double yearsBetweenTwoDates(Date fechaInicio, Date fechaFin) {
		try {
			return monthsBetweenTwoDates(fechaInicio, fechaFin) / NUMBER_OF_MONTHS_IN_A_YEAR;
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getHourMinuteSecondBetweenDate(Date startDate, Date endDate) {
		long diferenciaMils = endDate.getTime() - startDate.getTime();
		long segundos = diferenciaMils / 1000;
		long horas = segundos / 3600;
		segundos -= horas * 3600;
		long minutos = segundos / 60;
		segundos -= minutos * 60;
		if (horas > ONE_DAY_IN_HOUR) {
			String vReturn = getNumWZero(2, (int) horas % ONE_DAY_IN_HOUR) + ":" + getNumWZero(2, (int) minutos) + ":"
					+ getNumWZero(2, (int) segundos);
			vReturn = String.valueOf((int) horas / ONE_DAY_IN_HOUR) + ":" + vReturn;
			return vReturn;
		}
		return getNumWZero(2, (int) horas) + ":" + getNumWZero(2, (int) minutos) + ":" + getNumWZero(2, (int) segundos);
	}

	public static String getNumWZero(int quantityZero, int number) {
		return String.format("%0" + String.valueOf(quantityZero) + "d", number);
	}
}