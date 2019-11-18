/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.BookingRepository;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.service.CustomerService;
import com.snehee.ganpati.service.IdolService;
import com.snehee.ganpati.util.Constants;

/**
 * @author Girish
 *
 */
@Service
public class BookingServiceimpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	CustomerService customerService;
	@Autowired
	IdolService idolService;

	@Override
	public List<Booking> getAllBookings() {
		return this.bookingRepository.findAll();
	}

	@Override
	public Booking getBookingByBookingId(final Integer bookingId) throws ResourceNotFoundException {
		final Booking booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id : " + bookingId));
		return booking;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerNameLike(final String nameOfCustomer) {
		final List<Customer> allCustomers = this.customerService.getAllCustomers();
		final List<Idol> allIdols = this.idolService.getAllIdols();
		/*
		 * First get list of customers with Name like given name
		 */
		final List<Customer> customersWithNameLike = this.customerService.getCustomersWithNameLike(nameOfCustomer);

		final List<Booking> bookingsForCustomersWithNameLike = new ArrayList<>();

		/*
		 * Now for each of that customer retrieve the bookings and Add Bookings in new
		 * array list of bookings for customer
		 */
		customersWithNameLike.forEach(customer -> {
			final List<Booking> bookingList = this.bookingRepository.findByCustomerId(customer.getId());
			bookingsForCustomersWithNameLike.addAll(bookingList);
		});

		/*
		 * Now we can update customer name and Idol name while traversing.
		 */
		final List<BookingDTO> bookingListofAllCustomers = bookingsForCustomersWithNameLike.stream()
				.map(bookingForSpecificCustomer -> {
					final BookingDTO booking = new BookingDTO();
					BeanUtils.copyProperties(bookingForSpecificCustomer, booking,
							new String[] { "customerName", "idolName" });
					final String customerName = Constants.CUSTOMER_LIST
							.get(bookingForSpecificCustomer.getCustomerId() - 1).getName();
					booking.setCustomerName(customerName);
					final String IdolName = Constants.IDOL_LIST.get(bookingForSpecificCustomer.getIdolId() - 1)
							.getName();
					booking.setIdolName(IdolName);//
					return booking;
				}).collect(Collectors.toList());

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerPrimaryMobileLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerSecondaryMobileLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerLandlineLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerAddressLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDTO> getBookingsWithCustomerInfoLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDTO> getBookingssWithCustomerCommentsLike(final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
