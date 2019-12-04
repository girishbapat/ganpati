/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.snehee.ganpati.dto.BookingDates;
import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.repository.BookingRepository;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.service.ReportService;

/**
 * @author Girish
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public List<TotalsDTO> getTotalsForBookingDatesAndShiftsBetween(final String strFromBookingDate,
			WorkShift fromWorkShift, final String strToBookingDate, WorkShift toWorkShift)
			throws InvalidInputException {
		// If from date is null throw exception
		if (!StringUtils.hasText(strFromBookingDate)) {
			throw new InvalidInputException("From Date cannot be null.");
		}
		List<TotalsDTO> listOfTotalsDTO = null;
		BookingDates bookingDates = null;

		// if from date is not null and other 3 parameters are null
		if ((null == fromWorkShift) && !StringUtils.hasText(strToBookingDate) && (null == toWorkShift)) {
			fromWorkShift = WorkShift.MORNING;
			bookingDates = this.bookingService.getBookingDates(strFromBookingDate, fromWorkShift, 24);
		}
		// if from date and fromWorkShift is not null and other 2 parameters are null
		else if ((fromWorkShift != null) && !StringUtils.hasText(strToBookingDate) && (null == toWorkShift)) {
			bookingDates = this.bookingService.getBookingDates(strFromBookingDate, fromWorkShift, 8);
		}
		// if from date and fromWorkShift and todate is not and only to workshift is
		// null then just set to workshift
		else if ((fromWorkShift != null) && StringUtils.hasText(strToBookingDate) && (null == toWorkShift)) {
			toWorkShift = WorkShift.MORNING;
			final LocalDateTime fromBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate, fromWorkShift);
			final LocalDateTime toBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate, toWorkShift);
			bookingDates = new BookingDates(fromBookingDate, toBookingDate);

		}
		listOfTotalsDTO = this.bookingRepository.getTotals(bookingDates.getFromDate(), bookingDates.getToDate());
		return listOfTotalsDTO;
	}

	@Override
	public List<TotalsDTO> getTotalsForBookingDateBetween(final String strFromBookingDate,
			final String strToBookingDate) throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(strFromBookingDate, null, strToBookingDate, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotalsForParticularBookingDate(final String particularBookingDate)
			throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(particularBookingDate, null, null, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotalsForParticularBookingDateAndShift(final String particularBookingDate,
			final WorkShift workShift) throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(particularBookingDate, workShift, null, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotals() {
		final List<TotalsDTO> totals = this.bookingRepository.getTotals();
		return totals;
	}

}
