/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.dto.BookingDates;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.entity.DailyBooking;
import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Reason;
import com.snehee.ganpati.enums.Status;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.BookingRepository;
import com.snehee.ganpati.repository.DailyBookingRepository;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.service.CustomerService;
import com.snehee.ganpati.service.IdolService;
import com.snehee.ganpati.util.Constants;

/**
 * @author Girish
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class BookingServiceimpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	DailyBookingRepository dailyBookingRepository;

	@Autowired
	CustomerService customerService;
	@Autowired
	IdolService idolService;

	/**
	 * @param strFromBookingDate
	 * @param fromWorkShift      TODO
	 * @param strToBookingDate
	 * @param toWorkShift        TODO
	 * @return
	 * @throws InvalidInputException
	 */
	private List<Booking> getBookingsBetweenBookingDates(final String strFromBookingDate, WorkShift fromWorkShift,
			final String strToBookingDate, WorkShift toWorkShift) throws InvalidInputException {
		// If from date is null throw exception
		if (StringUtils.isBlank(strFromBookingDate)) {
			throw new InvalidInputException("From Date cannot be null.");
		}
		List<Booking> listBookings = null;
		// if from date is not null and other 3 parameters are null
		if (null == fromWorkShift && StringUtils.isBlank(strToBookingDate) && null == toWorkShift) {
			fromWorkShift = WorkShift.MORNING;
			listBookings = this.getBookingsWithBookingDateWorkShiftAndDiffrence(strFromBookingDate, fromWorkShift, 24);
		}
		// if from date and fromWorkShift is not null and other 2 parameters are null
		else if (fromWorkShift != null && StringUtils.isBlank(strToBookingDate) && null == toWorkShift) {
			listBookings = this.getBookingsWithBookingDateWorkShiftAndDiffrence(strFromBookingDate, fromWorkShift, 8);
		}
		// if todate is present but fromWorkShift and toWorkshift is null then just
		// bothworkshifts to MORNING and proceed
		else if (null == fromWorkShift && StringUtils.isNotBlank(strToBookingDate) && null == toWorkShift) {
			fromWorkShift = WorkShift.MORNING;
			toWorkShift = WorkShift.MORNING;
		}
		// if from date and fromWorkShift and todate is not and only to workshift is
		// null then just set to workshift
		else if (fromWorkShift != null && StringUtils.isNotBlank(strToBookingDate) && null == toWorkShift) {
			toWorkShift = WorkShift.MORNING;
		}
		// if listBookings is null means above atleaset 2 conditions are false
		if (null == listBookings) {
			final LocalDateTime fromBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate,
					fromWorkShift);
			final LocalDateTime toBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate,
					toWorkShift);
			listBookings = this.bookingRepository.findAllByBookingDateBetween(fromBookingDate, toBookingDate);
		}
		return listBookings;
	}

	/**
	 * This method will get the booking details for particular date with Workshift
	 * and difference of number of hours for next date
	 *
	 * @param strParticularBookingDate
	 * @return
	 */
	private List<Booking> getBookingsWithBookingDateWorkShiftAndDiffrence(final String strParticularBookingDate,
			final WorkShift workshift, final long addHours) {
		final BookingDates bookingDates = this.getBookingDates(strParticularBookingDate, workshift, addHours);
		final List<Booking> findAllByBookingDate = this.bookingRepository
				.findAllByBookingDateBetween(bookingDates.getFromDate(), bookingDates.getToDate());
		return findAllByBookingDate;
	}

	/**
	 * Do the basic validations of bookings to be saved. The validations include if
	 * customer id, idol id, total amount, booking amount
	 *
	 * @param bookingTobeSaved
	 * @throws InvalidInputException
	 */
	private void performValidationsForBooking(final Booking bookingTobeSaved) throws InvalidInputException {
		if (bookingTobeSaved.getCustomerId() <= 0 || bookingTobeSaved.getIdolId() <= 0
				|| null == bookingTobeSaved.getBookingAmount()) {
			throw new InvalidInputException(
					"Not able to create/update booking due to invalid data. Values submitted are customerId:"
							+ bookingTobeSaved.getCustomerId() + ", Idol id:" + bookingTobeSaved.getIdolId()
							+ ",  invalid booking amount:" + bookingTobeSaved.getBookingAmount());
		}
		if (null == bookingTobeSaved.getTotalAmount() || bookingTobeSaved.getTotalAmount().floatValue() <= 0) {
			try {
				final Idol idolsById = this.idolService.getIdolsById(bookingTobeSaved.getIdolId());
				bookingTobeSaved.setTotalAmount(idolsById.getPrice());
				if (null == bookingTobeSaved.getDiscountAmount()
						|| bookingTobeSaved.getDiscountAmount().floatValue() <= 0) {
					bookingTobeSaved.setDiscountAmount(new BigDecimal(0));
				}
				bookingTobeSaved.setBalanceAmount(bookingTobeSaved.getTotalAmount()
						.subtract(bookingTobeSaved.getBookingAmount().add(bookingTobeSaved.getDiscountAmount())));
			} catch (final ResourceNotFoundException e) {
				throw new InvalidInputException(
						"Not able to create/update booking due to invalid idol Id. Cannot find idol with Idol id:"
								+ bookingTobeSaved.getIdolId());
			}
		}

	}

	/**
	 * @param strParticularBookingDate
	 * @param workshift
	 * @param addHours
	 * @return
	 */
	@Override
	public BookingDates getBookingDates(final String strParticularBookingDate, final WorkShift workshift,
			final long addHours) {
		final LocalDateTime particularBookingDate = this
				.getLocalDateTimeForStrBookingDateAndWorkshift(strParticularBookingDate, workshift);
		final LocalDateTime nextBookingDate = particularBookingDate.plusHours(addHours);
		final BookingDates bookingDates = new BookingDates(particularBookingDate, nextBookingDate);
		return bookingDates;
	}

	/**
	 * @param strParticularBookingDate
	 * @param workshift
	 * @return
	 */
	@Override
	public LocalDateTime getLocalDateTimeForStrBookingDateAndWorkshift(final String strParticularBookingDate,
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
							new String[] { "customerName", "primaryMobile", "idolName", "idolSpecs" });
					final Customer customer = Constants.CUSTOMER_LIST
							.get(bookingForSpecificCustomer.getCustomerId() - 1);
					bookingDTO.setCustomerName(customer.getName());
					bookingDTO.setPrimaryMobile(customer.getPrimaryMobile());
					final String idolName = Constants.IDOL_LIST.get(bookingForSpecificCustomer.getIdolId() - 1)
							.getName();
					bookingDTO.setIdolName(idolName);//
					final String idolSpecs = Constants.IDOL_LIST.get(bookingForSpecificCustomer.getIdolId() - 1)
							.getSpecs();
					bookingDTO.setIdolSpecs(idolSpecs);
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
	public List<BookingDTO> getBookingsWithCustomerCommentsLike(final String commentsAboutCustomer) {
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
	public BookingDTO getBookingById(final Integer bookingId) throws ResourceNotFoundException {
		final Booking booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id : " + bookingId));
		final BookingDTO bookingTobeReturned = this.getBookingDTOForBooking(booking);
		return bookingTobeReturned;

	}

	/**
	 * @param booking
	 * @return
	 * @throws ResourceNotFoundException
	 */
	private BookingDTO getBookingDTOForBooking(final Booking booking) throws ResourceNotFoundException {
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(Arrays.asList(booking));
		if (bookingDTOForBookings.isEmpty()) {
			throw new ResourceNotFoundException("Booking not found with booking id : " + booking.getId());
		}
		final BookingDTO bookingTobeReturned = bookingDTOForBookings.get(0);
		return bookingTobeReturned;
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
			final String strToBookingDate) throws InvalidInputException {

		final List<Booking> findAllByBookingDate = this.getBookingsBetweenBookingDates(strFromBookingDate, null,
				strToBookingDate, null);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsByBookingDateBetween(final String strFromBookingDate,
			final WorkShift fromWorkShift, final String strToBookingDate, final WorkShift toWorkShift)
					throws InvalidInputException {
		if (StringUtils.isBlank(strFromBookingDate)) {
			throw new InvalidInputException("from Booking date is not provided");
		}
		final LocalDateTime fromBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate,
				fromWorkShift);
		final LocalDateTime toBookingDate = this.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate,
				toWorkShift);
		final List<Booking> findAllByBookingDate = this.bookingRepository.findAllByBookingDateBetween(fromBookingDate,
				toBookingDate);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findAllByBookingDate);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsWithStatusLike(final Status status) {
		final List<Booking> findBookingsWithStatus = this.bookingRepository.findByStatusContaining(status);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findBookingsWithStatus);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsWithLocation(final Location location) {
		final List<Booking> findBookingsWithLocation = this.bookingRepository.findByLocationContaining(location);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findBookingsWithLocation);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsWithPaymentModeLike(final PaymentMode paymentModeLike) {
		final List<Booking> findBookingsWithPaymentMode = this.bookingRepository
				.findByPaymentModeContaining(paymentModeLike);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findBookingsWithPaymentMode);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsWithReasonLike(final String reason) {
		final List<Booking> findBookingsWithReason = this.bookingRepository.findByReasonContaining(reason);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findBookingsWithReason);
		return bookingDTOForBookings;
	}

	@Override
	public List<BookingDTO> getBookingsWithCommentsLike(final String comments) {
		final List<Booking> findBookingsWithComments = this.bookingRepository.findByCommentsContaining(comments);
		final List<BookingDTO> bookingDTOForBookings = this.getBookingDTOForBookings(findBookingsWithComments);
		return bookingDTOForBookings;
	}

	@Override
	public BookingDTO bookTheIdol(final BookingDTO currentlyBookedIdol, Booking bookingTobeSaved)
			throws InvalidInputException {
		this.performValidationsForBooking(bookingTobeSaved);
		bookingTobeSaved = new Booking(bookingTobeSaved);
		if (null != currentlyBookedIdol) {
			this.performUpdatesForChangedBooking(currentlyBookedIdol, bookingTobeSaved);
		}
		final BookingDTO bookedIdolDTO = this.performActualDBOperationForBooking(currentlyBookedIdol, bookingTobeSaved);
		return bookedIdolDTO;
	}

	private void performUpdatesForChangedBooking(final BookingDTO currentlyBookedIdol, final Booking bookingTobeSaved) {
		// Set customerId and id same as of currently booked idol
		bookingTobeSaved.setId(currentlyBookedIdol.getId());
		bookingTobeSaved.setCustomerId(currentlyBookedIdol.getCustomerId());
		bookingTobeSaved.setStatus(Status.CHANGED);
		if (StringUtils.isBlank(bookingTobeSaved.getReason())) {
			bookingTobeSaved.setReason(Reason.OTHER.toString());
		}
	}

	private BookingDTO performActualDBOperationForBooking(final BookingDTO currentlyBookedIdol,
			final Booking bookingTobeSaved) throws InvalidInputException {
		BookingDTO bookedIdolDTO = null;
		DailyBooking updatedEntrySaved=null;
		try {
			// as idol will be changed current idol quantity from shop will be added and for
			// the new idol the idol quantity will be reduced.
			if (null != currentlyBookedIdol) {
				this.idolService.updateQuantityById(null, Operation.ADD, 1, currentlyBookedIdol.getIdolId());
				updatedEntrySaved=this.prepareAndSaveDailyBooking(currentlyBookedIdol,bookingTobeSaved);
			}
			this.idolService.updateQuantityById(null, Operation.SUBTRACT, 1, bookingTobeSaved.getIdolId());
			this.bookingRepository.save(bookingTobeSaved);
			//new entry
			if(null==updatedEntrySaved) {
				this.prepareAndSaveDailyBooking(null,bookingTobeSaved);
			}
			bookedIdolDTO = this.getBookingDTOForBooking(bookingTobeSaved);
		} catch (final ResourceNotFoundException e) {
			throw new InvalidInputException(
					"Not able to create or update booking due to invalid data. Values submitted are :"
							+ bookingTobeSaved);
		} catch (final Exception e) {
			throw new InvalidInputException(
					"Not able to create or update booking due to invalid data. Values submitted are :"
							+ bookingTobeSaved);
		}
		return bookedIdolDTO;
	}


	/**
	 * This method saves daily booking details this is needed for all the daily totals.
	 * @param oldBookedEntry- existing entry in bookings in case of update, for new this will be null
	 * @param newOrUpdatedBookingEntryTobeSaved-changed entry
	 * @return
	 * @throws InvalidInputException
	 */
	private DailyBooking prepareAndSaveDailyBooking(BookingDTO oldBookedEntry, Booking newOrUpdatedBookingEntryTobeSaved) throws InvalidInputException {
		DailyBooking dailyBookingTobeSaved=null;
		//first create instance of Daily booking with new entry assuming old entry already saved.
		dailyBookingTobeSaved=new DailyBooking(newOrUpdatedBookingEntryTobeSaved);
		//check if old entry is present, if yes set the booking amount
		if(null!=oldBookedEntry) {
			if(null==oldBookedEntry.getBookingAmount()||null==newOrUpdatedBookingEntryTobeSaved.getBookingAmount()) {
				throw new InvalidInputException("Booking amount not present either for old or new entry. old booking:"+oldBookedEntry+", new entry:"+newOrUpdatedBookingEntryTobeSaved+". If booking amount is not specified at least 0 should be updated");
			}
			//we are just updating difference to get daily collection.
			dailyBookingTobeSaved.setBookingAmount(newOrUpdatedBookingEntryTobeSaved.getBookingAmount().subtract(oldBookedEntry.getBookingAmount()));
		}
		this.dailyBookingRepository.save(dailyBookingTobeSaved);
		return dailyBookingTobeSaved;//test
	}

	private BookingDTO performActualDBOperationForBookingCancellation(final Booking currentlyBookedIdolToBeCancelled)
			throws InvalidInputException {
		BookingDTO bookedIdolDTO = null;
		try {
			// as idol will be added back to idol quantity as the booking is cancelled.
			this.idolService.updateQuantityById(null, Operation.ADD, 1, currentlyBookedIdolToBeCancelled.getIdolId());
			this.bookingRepository.save(currentlyBookedIdolToBeCancelled);
			bookedIdolDTO = this.getBookingDTOForBooking(currentlyBookedIdolToBeCancelled);
		} catch (final ResourceNotFoundException e) {
			throw new InvalidInputException("Not able to cancel booking due to invalid data. Values submitted are :"
					+ currentlyBookedIdolToBeCancelled);
		} catch (final Exception e) {
			throw new InvalidInputException(
					"Not able to cancel booking. Values submitted are :" + currentlyBookedIdolToBeCancelled);
		}
		return bookedIdolDTO;
	}

	@Override
	public BookingDTO cancelTheBookedIdol(Booking bookingToCancel) throws InvalidInputException {
		if (bookingToCancel.getCustomerId() <= 0 || bookingToCancel.getIdolId() <= 0
				|| bookingToCancel.getBookingAmount() == null) {
			throw new InvalidInputException(
					"Not able to cancel booking. Need to provide mandatory values of customer id, booking amount (negative in case of refund or 0 in case of no refund)."
							+ bookingToCancel);
		}
		if (bookingToCancel.getTotalAmount() == null) {
			bookingToCancel.setTotalAmount(new BigDecimal(0));
		}
		if (bookingToCancel.getDiscountAmount() == null) {
			bookingToCancel.setDiscountAmount(new BigDecimal(0));
		}
		if (bookingToCancel.getBalanceAmount() == null) {
			bookingToCancel.setBalanceAmount(new BigDecimal(0));
		}
		bookingToCancel.setStatus(Status.CANCELLED);
		if (StringUtils.isBlank(bookingToCancel.getReason())) {
			bookingToCancel.setReason(Reason.CUSTOMER_CANCELLED_BOOKING.toString());
		}
		bookingToCancel = new Booking(bookingToCancel);
		return this.performActualDBOperationForBookingCancellation(bookingToCancel);
	}

	@Override
	public BookingDTO updateLocation(final int bookingId, final String location)
			throws ResourceNotFoundException, InvalidInputException {
		final Booking booking = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id : " + bookingId));
		Location loc = null;
		try {
			if (StringUtils.isNotBlank(location)) {
				loc = Location.valueOf(StringUtils.upperCase(location.trim()));
			} else {
				throw new InvalidInputException("Not able to update location, location shared is empty.");
			}
		} catch (final InvalidInputException e) {
			throw e;
		} catch (final Exception e) {
			throw new InvalidInputException("Not able to update location, invalid location shared:" + location);
		}

		booking.setLocation(loc);
		this.bookingRepository.save(booking);
		final BookingDTO bookedIdolDTO = this.getBookingDTOForBooking(booking);

		return bookedIdolDTO;
	}

}
