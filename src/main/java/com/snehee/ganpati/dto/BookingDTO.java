package com.snehee.ganpati.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Status;

import lombok.Data;

@Data
public class BookingDTO {
	private int id;
	private LocalDateTime bookingDate;
	private int customerId;
	private String customerName;
	private String primaryMobile;
	private int idolId;
	private String idolName;
	private String idolSpecs;
	private PaymentMode paymentMode;
	private BigDecimal bookingAmount;
	private BigDecimal discountAmount;
	private BigDecimal balanceAmount;
	private BigDecimal totalAmount;
	private Status status;
	private String reason;
	private Location location;
	private LocalDateTime shipmentDate;
	private int userId;
	private String comments;
}
