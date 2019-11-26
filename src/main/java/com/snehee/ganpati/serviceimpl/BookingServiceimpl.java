/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.entity.WorkShift;
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
	 * This method will get the booking details for particular date with Workshift
	 * and difference of number of hours for next date
	 *
	 * @param strParticularBookingDate
	 * @return
	 */
	private List<Booking> getBookingsWithBookingDateWorkShiftAndDiffrence(final String strParticularBookingDate,
			final WorkShift workshift, final long addHours) {
		final LocalDateTime particularBookingDate = this
				.getLocalDateTimeForStrBookingDateAndWorkshift(strParticularBookingDate, workshift);
		final LocalDateTime nextBookingDate = particularBookingDate.plusHours(addHours);
		final List<Booking> findAllByBookingDate = this.bookingRepository
				.findAllByBookingDateBetween(particularBookingDate, nextBookingDate);
		return findAllByBookingDate;
	}

	/**
	 * @param strParticularBookingDate
	 * @param workshift
	 * @return
	 */
	private LocalDateTime getLocalDateTimeForStrBookingDateAndWorkshift(final String strParticularBookingDate,
			final WorkShift workshift) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		final LocalDate particularDate = LocalDate.parse(strParticularBookingDate, formatter);
		final LocalDateTime particularBookingDate = particularDate.atTime(workshift.getHours(), 0);
		return particularBookingDate;
	}

	/**
	 * Convert from Bookings to BookingDTO
	 *
	 * @param bookingsForCustomersWithNameLike
	 * @return
	 */
	private List<BookingDTO> getBookingDTOForBookings(final List<Booking> bookingsForCustomersWithNameLike) {
		this.customerService.getAllCustomers();
		this.idolService.getAllIdols();

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

		final List<BookingDTO> bookingListofAllCustomers = this
				.getBookingDTOForBookings(bookingsForCustomersWithNameLike);
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
	public List<BookingDTO> getAllBookings() {
		return this.getBookingDTOForBookings(this.bookingRepository.findAll());
	}

	@Override
	public Booking getBookingById(final Integer bookingId) throws ResourceNotFoundException {
		final Booking booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id : " + bookingId));
		return booking;
	}

	@Override
	public List<BookingDTO> getBookingsForParticularBookingDate(final String strParticularBookingDate) {
		// we will always consider booking starts 8 AM and all bookings till next 24
		// hours will be considered as for this date.
		final List<Booking> findAllByBookingDate = this
				.getBookingsWithBookingDateWorkShiftAndDiffrence(strParticularBookingDate, WorkShift.MORNING, 24);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsForParticularBookingDateAndShift(final String strParticularBookingDate,
			final WorkShift workShift) {
		// here we are considering shift of 8 hours
		final List<Booking> findAllByBookingDate = this
				.getBookingsWithBookingDateWorkShiftAndDiffrence(strParticularBookingDate, workShift, 8);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsByBookingDateBetween(final String strFromBookingDate,
			final String strToBookingDate) {

		final LocalDateTime fromBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate,
				WorkShift.MORNING);
		final LocalDateTime toBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate,
				WorkShift.MORNING);
		final List<Booking> findAllByBookingDate = this.bookingRepository.findAllByBookingDateBetween(fromBookingDate,
				toBookingDate);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsByBookingDateBetween(final String strFromBookingDate,
			final WorkShift fromWorkShift, final String strToBookingDate, final WorkShift toWorkShift) {
		final LocalDateTime fromBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate,
				fromWorkShift);
		final LocalDateTime toBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate,
				toWorkShift);
		final List<Booking> findAllByBookingDate = this.bookingRepository.findAllByBookingDateBetween(fromBookingDate,
				toBookingDate);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

}
