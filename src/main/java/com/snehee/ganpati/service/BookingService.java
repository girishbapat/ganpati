/**
 *
 */
package com.snehee.ganpati.service;

import java.time.LocalDateTime;
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

	List<BookingDTO> getBookingsByBookingDateBetween(LocalDateTime fromBookingDate, LocalDateTime toBookingDate);

	List<BookingDTO> getBookingsForParticularBookingDate(String particularBookingDate);

	List<BookingDTO> getBookingsForParticularBookingDateAndShift(LocalDateTime fromBookingDate, WorkShift workShift);

}
