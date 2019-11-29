/**
 *
 */
package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Status;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;

/**
 * @author Girish
 *
 */
public interface BookingService {
	List<BookingDTO> getAllBookings();

	BookingDTO getBookingById(final Integer bookingId) throws ResourceNotFoundException;

	List<BookingDTO> getBookingsWithCustomerNameLike(String customerName);

	List<BookingDTO> getBookingsWithPrimaryMobileLike(String primaryMobile);

	List<BookingDTO> getBookingsWithSecondaryMobileLike(String secondaryMobile);

	List<BookingDTO> getBookingsWithLandlineLike(String landLine);

	List<BookingDTO> getBookingsWithAddressLike(String address);

	List<BookingDTO> getBookingsWithInfoLike(String info);

	List<BookingDTO> getBookingsWithCustomerCommentsLike(String customerComments);

	List<BookingDTO> getBookingsWithPaymentModeLike(PaymentMode paymentModeLike);

	List<BookingDTO> getBookingsWithStatusLike(Status status);

	List<BookingDTO> getBookingsWithCommentsLike(String comments);

	List<BookingDTO> getBookingsWithReasonLike(String reason);

	List<BookingDTO> getBookingsWithLocation(Location location);

	List<BookingDTO> getBookingsByBookingDateBetween(String strFromBookingDate, String strToBookingDate)
			throws InvalidInputException;

	List<BookingDTO> getBookingsByBookingDateBetween(String strFromBookingDate, WorkShift fromWorkShift,
			String strToBookingDate, WorkShift toWorkShift) throws InvalidInputException;

	/**
	 * Get Bookings for Particular date from Morning Shift 8 AM to next date by
	 * default add 24 hours
	 *
	 *
	 * @param particularBookingDate
	 * @return
	 */
	List<BookingDTO> getBookingsForParticularBookingDate(String particularBookingDate);

	/**
	 * Get Bookings for Particular date and particular shift by default shift is
	 * considered for 8 hours
	 *
	 * @param particularBookingDate
	 * @param workShift
	 * @return
	 */
	List<BookingDTO> getBookingsForParticularBookingDateAndShift(String particularBookingDate, WorkShift workShift);

	List<TotalsDTO> getTotalsForParticularBookingDate(String particularBookingDate) throws InvalidInputException;

	List<TotalsDTO> getTotalsForBookingDateBetween(String strFromBookingDate, String strToBookingDate)
			throws InvalidInputException;

	List<TotalsDTO> getTotalsForParticularBookingDateAndShift(String particularBookingDate, WorkShift workShift)
			throws InvalidInputException;

	List<TotalsDTO> getTotalsForBookingDatesAndShiftsBetween(final String strFromBookingDate, WorkShift fromWorkShift,
			final String strToBookingDate, WorkShift toWorkShift) throws InvalidInputException;

	List<TotalsDTO> getTotals();

}
