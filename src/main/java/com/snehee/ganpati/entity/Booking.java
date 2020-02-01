package com.snehee.ganpati.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Status;

import lombok.Data;

/**
 *
 * @author Girish
 *
 */
@Audited
@Data
@Entity
@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "booking_date")
	// @Temporal(TemporalType.TIMESTAMP)
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
	// @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime shipmentDate;

	@Column(name = "user_id")
	private int userId;

	private String comments;

	/**
	 *
	 */
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param bookingDate
	 * @param customerId
	 * @param idolId
	 * @param paymentMode
	 * @param bookingAmount
	 * @param balanceAmount
	 * @param totalAmount
	 * @param status
	 * @param reason
	 * @param location
	 * @param shipmentDate
	 * @param userId
	 * @param comments
	 */
	public Booking(int id,LocalDateTime bookingDate, int customerId, int idolId, PaymentMode paymentMode,
			BigDecimal bookingAmount, BigDecimal balanceAmount, BigDecimal discountAmount, BigDecimal totalAmount, Status status, String reason,
			Location location, LocalDateTime shipmentDate, int userId, String comments) {
		super();
		if(id>0) {
			this.id=id;
		}
		if (null == bookingDate) {
			this.bookingDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		} else {
			this.bookingDate = bookingDate;
		}

		this.customerId = customerId;

		this.idolId = idolId;

		if (null == paymentMode) {
			this.paymentMode =PaymentMode.CASH;
		}else {
			this.paymentMode = paymentMode;
		}

		this.bookingAmount = bookingAmount;
		this.discountAmount=discountAmount;
		this.totalAmount = totalAmount;
		if(null==balanceAmount) {
			balanceAmount=totalAmount.subtract(this.bookingAmount.add(this.discountAmount));
		}
		else {
			this.balanceAmount = balanceAmount;
		}

		if(null==status) {
			this.status =Status.BOOKED;
		}else {
			this.status = status;
		}
		this.reason = reason;
		this.location = location;
		this.shipmentDate = shipmentDate;
		if(0==userId) {
			this.userId = 1;
		}else {
			this.userId = userId;
		}
		this.comments = comments;
	}

	/**
	 *
	 * @param customerId
	 * @param idolId
	 * @param bookingAmount
	 * @param totalAmount
	 * @param discountAmount TODO
	 */
	public Booking(int customerId, int idolId, BigDecimal bookingAmount, BigDecimal totalAmount, BigDecimal discountAmount) {
		super();
		this.bookingDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		this.customerId = customerId;
		this.idolId = idolId;
		this.paymentMode = PaymentMode.CASH;
		this.bookingAmount = bookingAmount;
		this.discountAmount=discountAmount;
		this.totalAmount = totalAmount;
		this.balanceAmount = totalAmount.subtract(bookingAmount);
		this.status =Status.BOOKED;
		this.userId = 1;
	}

	/**
	 * @param customerId
	 * @param idolId
	 * @param bookingAmount
	 */
	public Booking(int customerId, int idolId, BigDecimal bookingAmount) {
		super();
		this.customerId = customerId;
		this.idolId = idolId;
		this.bookingAmount = bookingAmount;
	}




	public Booking(Booking bookingTobeSaved) {
		this( bookingTobeSaved.getId(),bookingTobeSaved.getBookingDate(), bookingTobeSaved.getCustomerId(), bookingTobeSaved.getIdolId(), bookingTobeSaved.getPaymentMode(),
				bookingTobeSaved.getBookingAmount(),bookingTobeSaved.getBalanceAmount(), bookingTobeSaved.getDiscountAmount(), bookingTobeSaved.getTotalAmount(),
				bookingTobeSaved.getStatus(), bookingTobeSaved.getReason(),bookingTobeSaved.getLocation(),bookingTobeSaved.getShipmentDate(), bookingTobeSaved.getUserId(), bookingTobeSaved.getComments());
	}




}
