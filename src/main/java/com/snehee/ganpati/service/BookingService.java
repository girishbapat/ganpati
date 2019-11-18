/**
 *
 */
package com.snehee.ganpati.service;

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

	Booking getBookingByBookingId(final Integer bookingId) throws ResourceNotFoundException;

	List<BookingDTO> getBookingsWithCustomerNameLike(String attributeValue);

	List<BookingDTO> getBookingsWithCustomerPrimaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithCustomerSecondaryMobileLike(String attributeValue);

	List<BookingDTO> getBookingsWithCustomerLandlineLike(String attributeValue);

	List<BookingDTO> getBookingsWithCustomerAddressLike(String attributeValue);

	List<BookingDTO> getBookingsWithCustomerInfoLike(String attributeValue);

	List<BookingDTO> getBookingssWithCustomerCommentsLike(String attributeValue);

}
