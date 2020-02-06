/**
 *
 */
package com.snehee.ganpati.enums;

/**
 * @author Girish
 *
 */
public enum WorkShift {
	MORNING(8), EVENING(16), /*night shift is only for calculation purpose and should not be normally used*/NIGHT(23);
	private int hours;

	private WorkShift(final int hours) {
		this.hours = hours;
	}

	public int getHours() {
		return this.hours;
	}
}
