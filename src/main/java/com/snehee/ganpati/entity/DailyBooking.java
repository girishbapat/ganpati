package com.snehee.ganpati.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Status;

import lombok.Data;

/**
 *This entity is only used for daily booking calculations
 * @author Girish
 *
 */
@Data
@Entity
@Table(name = "daily_booking")
public class DailyBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "booking_id")
	private int bookingId;

	@Column(name = "booking_date")
	private LocalDateTime bookingDate;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "idol_id")
	private int idolId;

	@Column(name = "payment_mode")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@Column(name = "booking_amt")
	private BigDecimal bookingAmount;

	@Column(name = "balance_amt")
	private BigDecimal balanceAmount;

	@Column(name = "discount_amt")
	private BigDecimal discountAmount;

	@Column(name = "total_amt")
	private BigDecimal totalAmount;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	private String reason;

	@Column(name = "location")
	@Enumerated(EnumType.STRING)
	private Location location;

	@Column(name = "shipment_date")
	private LocalDateTime shipmentDate;

	@Column(name = "user_id")
	private int userId;

	private String comments;

	/**
	 *
	 */
	public DailyBooking() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param bookingId
	 * @param bookingDate
	 * @param customerId
	 * @param idolId
	 * @param paymentMode
	 * @param bookingAmount
	 * @param balanceAmount
	 * @param discountAmount
	 * @param totalAmount
	 * @param status
	 * @param reason
	 * @param location
	 * @param shipmentDate
	 * @param userId
	 * @param comments
	 */
	public DailyBooking(Booking currentBooking) {
		super();
		this.bookingId = currentBooking.getId();
		this.bookingDate = currentBooking.getBookingDate();
		this.customerId = currentBooking.getCustomerId();
		this.idolId = currentBooking.getIdolId();
		this.paymentMode = currentBooking.getPaymentMode();
		this.bookingAmount = currentBooking.getBookingAmount();
		this.balanceAmount = currentBooking.getBalanceAmount();
		this.discountAmount = currentBooking.getDiscountAmount();
		this.totalAmount = currentBooking.getTotalAmount();
		this.status = currentBooking.getStatus();
		this.reason = currentBooking.getReason();
		this.location = currentBooking.getLocation();
		this.shipmentDate = currentBooking.getShipmentDate();
		this.userId = currentBooking.getUserId();
		this.comments = currentBooking.getComments();
	}

	/**
	 * @param id
	 * @param bookingId
	 * @param bookingDate
	 * @param customerId
	 * @param idolId
	 * @param paymentMode
	 * @param bookingAmount
	 * @param balanceAmount
	 * @param discountAmount
	 * @param totalAmount
	 * @param status
	 * @param reason
	 * @param location
	 * @param shipmentDate
	 * @param userId
	 * @param comments
	 */
	public DailyBooking(int id, int bookingId, LocalDateTime bookingDate, int customerId, int idolId,
			PaymentMode paymentMode, BigDecimal bookingAmount, BigDecimal balanceAmount, BigDecimal discountAmount,
			BigDecimal totalAmount, Status status, String reason, Location location, LocalDateTime shipmentDate,
			int userId, String comments) {
		super();
		this.id = id;
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.customerId = customerId;
		this.idolId = idolId;
		this.paymentMode = paymentMode;
		this.bookingAmount = bookingAmount;
		this.balanceAmount = balanceAmount;
		this.discountAmount = discountAmount;
		this.totalAmount = totalAmount;
		this.status = status;
		this.reason = reason;
		this.location = location;
		this.shipmentDate = shipmentDate;
		this.userId = userId;
		this.comments = comments;
	}






}
