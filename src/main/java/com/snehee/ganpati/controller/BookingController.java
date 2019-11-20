package com.snehee.ganpati.controller;

import java.time.LocalDateTime;
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
		final Booking booking = this.bookingService.getBookingById(bookingId);
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping("/getBookingsByBookingDate/{bookingDate}")
	public ResponseEntity<List<Booking>> getBookingsByBookingDate(
			@PathVariable(value = "bookingDate") final String strBookingDate) throws ResourceNotFoundException {
		// final DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("d-MMM-yyyy");
		// final String dateString = "2018-07-14"; // ISO date

		// string to date
		/*
		 * final LocalDate localDate = LocalDate.parse(dateString); // 2018-07-14 final
		 * DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		 */
		// convert String to LocalDate
		final LocalDateTime bookingDate = LocalDateTime.parse(strBookingDate);
		final List<Booking> listOfBookingsDTO = this.bookingService.getBookingsByBookingDate(bookingDate);
		return ResponseEntity.ok().body(listOfBookingsDTO);
	}

	/**
	 * Get booking details with multiple parameters
	 *
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBookingsWithCustomerAttributeLike/{attributeName}/{attributeValue}")
	public ResponseEntity<List<BookingDTO>> getBookingsWithAttributeLike(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<BookingDTO> bookingsListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				bookingsListByType = this.bookingService.getBookingsWithCustomerNameLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.PRIMARY_MOBILE)) {
				bookingsListByType = this.bookingService.getBookingsWithPrimaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.SECONDARY_MOBILE)) {
				bookingsListByType = this.bookingService.getBookingsWithSecondaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				bookingsListByType = this.bookingService.getBookingsWithLandlineLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.ADDRESS)) {
				bookingsListByType = this.bookingService.getBookingsWithAddressLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.INFO)) {
				bookingsListByType = this.bookingService.getBookingsWithInfoLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
				bookingsListByType = this.bookingService.getBookingssWithCustomerCommentsLike(attributeValue);
			} else {
				throw new InvalidInputException("Invalid customer attributeName:" + attributeName);
			}
		} catch (final InvalidInputException e) {
			throw new InvalidInputException("Invalid inputs for customer attributeName:" + attributeName
					+ " or attributeValue:" + attributeValue);
		} catch (final Exception e) {
			throw new Exception(
					"API called with customer attributeName:" + attributeName + " or attributeValue:" + attributeValue);
		}
		return ResponseEntity.ok().body(bookingsListByType);
	}

}