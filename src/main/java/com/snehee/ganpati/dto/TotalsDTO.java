package com.snehee.ganpati.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.snehee.ganpati.enums.PaymentMode;

import lombok.Data;

@Data
public class TotalsDTO {
	private PaymentMode paymentMode;
	private BigDecimal bookingAmount;
	private BigDecimal balanceAmount;
	private BigDecimal totalAmount;
	private LocalDateTime bookingDate;
}
