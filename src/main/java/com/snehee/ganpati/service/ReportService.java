package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;

/**
 * Reporting service for all the reporting stuff
 *
 * @author Girish
 *
 */
public interface ReportService {

	List<TotalsDTO> getTotalsForParticularBookingDate(String particularBookingDate) throws InvalidInputException;

	List<TotalsDTO> getTotalsForBookingDateBetween(String strFromBookingDate, String strToBookingDate)
			throws InvalidInputException;

	List<TotalsDTO> getTotalsForParticularBookingDateAndShift(String particularBookingDate, WorkShift workShift)
			throws InvalidInputException;

	List<TotalsDTO> getTotalsForBookingDatesAndShiftsBetween(final String strFromBookingDate, WorkShift fromWorkShift,
			final String strToBookingDate, WorkShift toWorkShift) throws InvalidInputException;

	List<TotalsDTO> getTotals();

	String generateBookingRecordsReport(String reportFormat);

	String generateInvoice(int bookingId, String invoiceFormat, boolean printAsWell) throws ResourceNotFoundException;

	String generateInvoice(BookingDTO invoiceForCurrentBookedIdol, String invoiceFormat, boolean printAsWell);
}
