/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.entity.Customer;
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

	/**
	 * @param customers
	 * @return
	 */
	private List<BookingDTO> getBookingsForCustomers(final List<Customer> customers) {
		final List<Booking> bookingsForCustomersWithNameLike = new ArrayList<>();
		this.customerService.getAllCustomers();
		this.idolService.getAllIdols();

		/*
		 * Now for each of that customer retrieve the bookings and Add Bookings in new
		 * array list of bookings for customer
		 */
		customers.forEach(customer -> {
			final List<Booking> bookingList = this.bookingRepository.findByCustomerId(customer.getId());
			bookingsForCustomersWithNameLike.addAll(bookingList);
		});

		/*
		 * Now we can update customer name and Idol name while traversing.
		 */
		final List<BookingDTO> bookingListofAllCustomers = bookingsForCustomersWithNameLike.stream()
				.map(bookingForSpecificCustomer -> {
					final BookingDTO bookingDTO = new BookingDTO();
					BeanUtils.copyProperties(bookingForSpecificCustomer, bookingDTO,
							new String[] { "customerName", "primaryMobile", "idolName" });
					final Customer customer = Constants.CUSTOMER_LIST
							.get(bookingForSpecificCustomer.getCustomerId() - 1);
					bookingDTO.setCustomerName(customer.getName());
					bookingDTO.setPrimaryMobile(customer.getPrimaryMobile());
					final String IdolName = Constants.IDOL_LIST.get(bookingForSpecificCustomer.getIdolId() - 1)
							.getName();
					bookingDTO.setIdolName(IdolName);//
					return bookingDTO;
				}).collect(Collectors.toList());
		return bookingListofAllCustomers;
	}

	/*
	 * Apis related to customers
	 */

	@Override
	public List<BookingDTO> getBookingsWithCustomerNameLike(final String nameOfCustomer) {

		/*
		 * First get list of customers with Name like given name
		 */
		final List<Customer> customersWithNameLike = this.customerService.getCustomersWithNameLike(nameOfCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithPrimaryMobileLike(final String primaryMobileNoOfCustomer) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithPrimaryMobileLike(primaryMobileNoOfCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithSecondaryMobileLike(final String secondaryMobileNoOfCustomer) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithSecondaryMobileLike(secondaryMobileNoOfCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithLandlineLike(final String landlineNoOfCustomer) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithLandlineLike(landlineNoOfCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithAddressLike(final String addressOfCustomer) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithAddressLike(addressOfCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingsWithInfoLike(final String infoAboutCustomerOrIdolsHePurchases) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithInfoLike(infoAboutCustomerOrIdolsHePurchases);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<BookingDTO> getBookingssWithCustomerCommentsLike(final String commentsAboutCustomer) {
		final List<Customer> customersWithNameLike = this.customerService
				.getCustomersWithCommentsLike(commentsAboutCustomer);

		final List<BookingDTO> bookingListofAllCustomers = this.getBookingsForCustomers(customersWithNameLike);

		return bookingListofAllCustomers;
	}

	@Override
	public List<Booking> getAllBookings() {
		return this.bookingRepository.findAll();
	}

	@Override
	public Booking getBookingById(final Integer bookingId) throws ResourceNotFoundException {
		final Booking booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id : " + bookingId));
		return booking;
	}

	@Override
	public List<Booking> getBookingsByBookingDate(final LocalDate bookingDate) {
		final List<Booking> findAllByBookingDate = this.bookingRepository.findAllByBookingDate(bookingDate);
		return null;
	}

}
