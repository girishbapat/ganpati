package com.snehee.ganpati.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.util.Constants;

@RestController
class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/bookings")
	List<Booking> getAllBookings() {
		return this.bookingService.getAllBookings();
	}

	/**
	 * Gets bookings by BookingId.
	 *
	 * @param bookingId the booking id
	 * @return the bookings by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/bookings/{id}")
	public ResponseEntity<Booking> getBookingByBookingId(@PathVariable(value = "id") final Integer bookingId)
			throws ResourceNotFoundException {
		final Booking booking = this.bookingService.getBookingByBookingId(bookingId);
		return ResponseEntity.ok().body(booking);
	}

	/**
	 * Get booking details with multiple parameters
	 *
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBookingsWithAttributeLike/{attributeName}/{attributeValue}")
	public ResponseEntity<List<BookingDTO>> getBookingsWithAttributeLike(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<BookingDTO> bookingsListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.CUSTOMER_NAME)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerNameLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.CUSTOMER_PRIMARY_MOBILE)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerPrimaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.CUSTOMER_SECONDARY_MOBILE)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerSecondaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerLandlineLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.ADDRESS)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerAddressLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.INFO)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerInfoLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
				bookingsListByType = this.bookingService.getBookingssWithCustomerCommentsLike(attributeValue);
			} else {
				throw new InvalidInputException("Invalid attributeName:" + attributeName);
			}
		} catch (final InvalidInputException e) {
			throw new InvalidInputException(
					"Invalid attributeName:" + attributeName + " or attributeValue:" + attributeValue);
		} catch (final Exception e) {
			throw new Exception(
					"API called with attributeName:\" + attributeName + \" and attributeValue:\" + attributeValue");
		}
		return ResponseEntity.ok().body(bookingsListByType);
	}

}