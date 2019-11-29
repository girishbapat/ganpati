package com.snehee.ganpati.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class BookingDates {

	private LocalDateTime fromDate;
	private LocalDateTime toDate;

	public BookingDates() {
		super();
	}

	public BookingDates(final LocalDateTime fromDate, final LocalDateTime toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	/**
	 * @return the fromDate
	 */
	public LocalDateTime getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(final LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public LocalDateTime getToDate() {
		return this.toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(final LocalDateTime toDate) {
		this.toDate = toDate;
	}

}
