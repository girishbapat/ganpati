package com.snehee.ganpati.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.enums.Status;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.BookingRepository;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.util.Constants;

@RestController
class BookingController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	BookingRepository bookingRepository;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/bookings")
	public List<BookingDTO> getAllBookings() {
		return this.bookingService.getAllBookings();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/bookings")

	public BookingDTO createBooking(@Valid @RequestBody final Booking bookingTobeSaved) throws InvalidInputException {
		return this.bookingService.bookTheIdol(null, bookingTobeSaved);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/bookings/{id}")

	public BookingDTO changeBooking(@PathVariable(value = "id") final Integer bookingId,
			@Valid @RequestBody final Booking changedBookingTobeUpdated)
			throws InvalidInputException, ResourceNotFoundException {
		BookingDTO rebookedIdol = null;
		try {
			final BookingDTO currentlyBookedIdol = this.bookingService.getBookingById(bookingId);
			if (changedBookingTobeUpdated.getCustomerId() != currentlyBookedIdol.getCustomerId()) {
				throw new InvalidInputException(
						"Not able to update booking, due to invalid data. Booking customer id in the request is:"
								+ changedBookingTobeUpdated.getCustomerId() + " and customer id in db is:"
								+ currentlyBookedIdol.getCustomerId());
			}
			rebookedIdol = this.bookingService.bookTheIdol(currentlyBookedIdol, changedBookingTobeUpdated);
		} catch (final ResourceNotFoundException resourceNotFound) {
			throw resourceNotFound;
		}
		return rebookedIdol;
	}

	@PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookings/{id}/location/{location}")

	public BookingDTO updateLocationForBooking(@PathVariable(value = "id") final Integer bookingId,
			@PathVariable(value = "location") final String location)
			throws InvalidInputException, ResourceNotFoundException {
		BookingDTO updatedLocationIdol = null;
		updatedLocationIdol = this.bookingService.updateLocation(bookingId, location);
		return updatedLocationIdol;
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/bookings/cancel/{id}")

	public BookingDTO cancelBooking(@PathVariable(value = "id") final Integer bookingId,
			@Valid @RequestBody final Booking cancelledBookingTobeUpdated)
			throws InvalidInputException, ResourceNotFoundException {
		BookingDTO cancelledIdol = null;
		try {
			final BookingDTO currentlyBookedIdol = this.bookingService.getBookingById(bookingId);
			if (cancelledBookingTobeUpdated.getCustomerId() != currentlyBookedIdol.getCustomerId()) {
				throw new InvalidInputException(
						"Not able to cancel booking, due to invalid data. current booking customer id in the request is:"
								+ cancelledBookingTobeUpdated.getCustomerId() + " and customer id in db is:"
								+ currentlyBookedIdol.getCustomerId());
			}
			if (Status.CANCELLED.equals(currentlyBookedIdol.getStatus())) {
				throw new InvalidInputException(
						"Not able to cancel booking, Booking already cancelled:" + currentlyBookedIdol);
			}
			cancelledBookingTobeUpdated.setId(currentlyBookedIdol.getId());
			cancelledBookingTobeUpdated.setIdolId(currentlyBookedIdol.getIdolId());
			cancelledIdol = this.bookingService.cancelTheBookedIdol(cancelledBookingTobeUpdated);
		} catch (final ResourceNotFoundException resourceNotFound) {
			throw resourceNotFound;
		}
		return cancelledIdol;
	}

	/**
	 * Gets bookings by BookingId.
	 *
	 * @param bookingId the booking id
	 * @return the bookings by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookings/{id}")
	public ResponseEntity<BookingDTO> bookingByBookingId(@PathVariable(value = "id") final Integer bookingId)
			throws ResourceNotFoundException {
		final BookingDTO booking = this.bookingService.getBookingById(bookingId);
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookingsForParticularBookingDate/{strBookingDate}")
	public ResponseEntity<List<BookingDTO>> getBookingsForParticularBookingDate(
			@PathVariable(value = "strBookingDate") final String strBookingDate) throws ResourceNotFoundException {

		final List<BookingDTO> listOfBookingsDTO = this.bookingService
				.getBookingsForParticularBookingDate(strBookingDate);
		return ResponseEntity.ok().body(listOfBookingsDTO);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookingsForParticularBookingDate/{strBookingDate}/forParticularWorkShift/{workShift}")
	public ResponseEntity<List<BookingDTO>> getBookingsForParticularBookingDateAndShift(
			@PathVariable(value = "strBookingDate") final String strBookingDate,
			@PathVariable(value = "workShift") final WorkShift workShift) throws ResourceNotFoundException {

		final List<BookingDTO> listOfBookingsDTO = this.bookingService
				.getBookingsForParticularBookingDateAndShift(strBookingDate, workShift);
		return ResponseEntity.ok().body(listOfBookingsDTO);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookingsByBookingDate/fromBookingDate/{strFromBookingDate}/toBookingDate/{strToBookingDate}")
	public ResponseEntity<List<BookingDTO>> getBookingsByBookingDateBetween(
			@PathVariable(value = "strFromBookingDate") final String strFromBookingDate,
			@PathVariable(value = "strToBookingDate") final String strToBookingDate)
			throws ResourceNotFoundException, InvalidInputException {

		final List<BookingDTO> listOfBookingsDTO = this.bookingService
				.getBookingsByBookingDateBetween(strFromBookingDate, strToBookingDate);
		return ResponseEntity.ok().body(listOfBookingsDTO);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookingsByBookingDate/fromBookingDate/{strFromBookingDate}/fromWorkShift/{fromWorkShift}/toBookingDate/{strToBookingDate}/toWorkShift/{toWorkShift}")
	public ResponseEntity<List<BookingDTO>> getBookingsByBookingDateAndWorkShift(
			@PathVariable(value = "strFromBookingDate") final String strFromBookingDate,
			@PathVariable(value = "fromWorkShift") final WorkShift fromWorkShift,
			@PathVariable(value = "strToBookingDate") final String strToBookingDate,
			@PathVariable(value = "toWorkShift") final WorkShift toWorkShift)
			throws ResourceNotFoundException, InvalidInputException {
		final List<BookingDTO> listOfBookingsDTO = this.bookingService
				.getBookingsByBookingDateBetween(strFromBookingDate, fromWorkShift, strToBookingDate, toWorkShift);
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
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/bookingsWithCustomerAttributeLike/{attributeName}/{attributeValue}")
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
				bookingsListByType = this.bookingService.getBookingsWithCustomerCommentsLike(attributeValue);
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