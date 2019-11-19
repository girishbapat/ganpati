/**
 *
 */
package com.snehee.ganpati.service;

import java.time.LocalDate;
import java.util.List;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.exception.ResourceNotFoundException;

/**
 * @author Girish
 *
 */
public interface BookingService {
	List<Booking> getAllBookings();

	Booking getBookingById(final Integer bookingId) throws ResourceNotFoundException;

	List<BookingDTO> getBookingsWithCustomerNameLike(String attributeValue);

	List<BookingDTO> getBookingsWithPrimaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithSecondaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithLandlineLike(String attributeValue);

	List<BookingDTO> getBookingsWithAddressLike(String attributeValue);

	List<BookingDTO> getBookingsWithInfoLike(String attributeValue);

	List<BookingDTO> getBookingssWithCustomerCommentsLike(String attributeValue);

	List<Booking> getBookingsByBookingDate(LocalDate bookingDate);

}
