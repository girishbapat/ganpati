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

import lombok.Data;

/**
 * 
 * @author Girish
 *
 */
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

	@Column(name = "booking_amt")
	private BigDecimal bookingAmount;

	@Column(name = "balance_amt")
	private BigDecimal balanceAmount;

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
}
