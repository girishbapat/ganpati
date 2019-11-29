package com.snehee.ganpati.dto;

import java.math.BigDecimal;

import com.snehee.ganpati.enums.PaymentMode;

import lombok.Data;

@Data
public class TotalsDTO {
	private PaymentMode paymentMode;
	private BigDecimal bookingAmount;
	private BigDecimal balanceAmount;
	private BigDecimal totalAmount;
	
	/**
	 * @param paymentMode
	 * @param bookingAmount
	 * @param balanceAmount
	 * @param totalAmount
	 */
	public TotalsDTO(PaymentMode paymentMode, BigDecimal bookingAmount, BigDecimal balanceAmount,
			BigDecimal totalAmount) {
		super();
		this.paymentMode = paymentMode;
		this.bookingAmount = bookingAmount;
		this.balanceAmount = balanceAmount;
		this.totalAmount = totalAmount;
	}

	/**
	 * 
	 */
	public TotalsDTO() {
		super();
	}
//pected arguments are: com.snehee.ganpati.enums.PaymentMode, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal 
}
