/**
 *
 */
package com.snehee.ganpati.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Class provides common utility functions
 *
 * @author Bapat_G
 *
 */
public class CommonUtils {

	public static String getCurrentDateAndTime() {
		final DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("ddMMMyy_HH_mm");
		return timeStampPattern.format(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
	}

	public static String getCurrentDate() {
		final DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("ddMMMyy");
		return timeStampPattern.format(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
	}

	public static String getDayAndDate(final String date, final int addOrSubtractDays) {
		String returnDateStr = "";
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH);
			final Date parsedDate = sdf.parse(date);
			final Calendar c = Calendar.getInstance();
			c.setTime(parsedDate);
			c.add(Calendar.DATE, addOrSubtractDays);
			sdf.applyPattern("EEE, dd-MMM");
			returnDateStr = sdf.format(c.getTime());
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnDateStr;
	}
}
