/**
 *
 */
package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.entity.WorkShift;
import com.snehee.ganpati.exception.ResourceNotFoundException;

/**
 * @author Girish
 *
 */
public interface BookingService {
	List<BookingDTO> getAllBookings();

	Booking getBookingById(final Integer bookingId) throws ResourceNotFoundException;

	List<BookingDTO> getBookingsWithCustomerNameLike(String attributeValue);

	List<BookingDTO> getBookingsWithPrimaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithSecondaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithLandlineLike(String attributeValue);

	List<BookingDTO> getBookingsWithAddressLike(String attributeValue);

	List<BookingDTO> getBookingsWithInfoLike(String attributeValue);

	List<BookingDTO> getBookingssWithCustomerCommentsLike(String attributeValue);

	List<BookingDTO> getBookingsByBookingDateBetween(String strFromBookingDate, String strToBookingDate);

	List<BookingDTO> getBookingsByBookingDateBetween(String strFromBookingDate, WorkShift fromWorkShift,
			String strToBookingDate, WorkShift toWorkShift);

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

}
