/**
 *
 */
package com.snehee.ganpati.entity;

/**
 * @author Girish
 *
 */
public enum WorkShift {
	MORNING(8), EVENING(16);
	private int hours;

	private WorkShift(final int hours) {
		this.hours = hours;
	}

	public int getHours() {
		return this.hours;
	}
}
