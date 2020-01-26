package com.snehee.ganpati.dto;

import java.math.BigDecimal;

import com.snehee.ganpati.enums.PaymentMode;

import lombok.Data;

@Data
public class TotalsDTO {
	private PaymentMode paymentMode;
	private BigDecimal bookingAmount;
	private BigDecimal discountAmount;
	private BigDecimal balanceAmount;
	private BigDecimal totalAmount;

	/**
	 * @param paymentMode
	 * @param bookingAmount
	 * @param balanceAmount
	 * @param totalAmount
	 */
	public TotalsDTO(PaymentMode paymentMode, BigDecimal bookingAmount,BigDecimal discountAmount, BigDecimal balanceAmount,
			BigDecimal totalAmount) {
		super();
		this.paymentMode = paymentMode;
		this.bookingAmount = bookingAmount;
		this.discountAmount=discountAmount;
		this.balanceAmount = balanceAmount;
		this.totalAmount = totalAmount;
	}

	/**
	 *
	 */
	public TotalsDTO() {
		super();
	}
}
