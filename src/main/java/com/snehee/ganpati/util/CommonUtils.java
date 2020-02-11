/**
 *
 */
package com.snehee.ganpati.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Class provides common utility functions
 * @author Bapat_G
 *
 */
public class CommonUtils {

	public static String getCurrentDateAndTime() {
		final DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return timeStampPattern.format(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
	}
}
