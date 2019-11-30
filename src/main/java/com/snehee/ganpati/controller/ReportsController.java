package com.snehee.ganpati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.service.ReportService;

@RestController
public class ReportsController {
	@Autowired
	private ReportService reportService;

	@GetMapping("/totalsForBookings")
	public ResponseEntity<List<TotalsDTO>> getTotalsForBookings() throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService.getTotals();
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	@GetMapping("/totalsForParticularBookingDate/{particularBookingDate}")
	public ResponseEntity<List<TotalsDTO>> getBookingsForParticularBookingDate(
			@PathVariable(value = "particularBookingDate") final String particularBookingDate)
			throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService
				.getTotalsForParticularBookingDate(particularBookingDate);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	@GetMapping("/totalsForParticularBookingDate/{strBookingDate}/forParticularWorkShift/{workShift}")

	public ResponseEntity<List<TotalsDTO>> getBookingsForParticularBookingDateAndShift(
			@PathVariable(value = "strBookingDate") final String strBookingDate,
			@PathVariable(value = "workShift") final WorkShift workShift) throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService
				.getTotalsForParticularBookingDateAndShift(strBookingDate, workShift);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	@GetMapping("/totalsByBookingDate/fromBookingDate/{strFromBookingDate}/toBookingDate/{strToBookingDate}")
	public ResponseEntity<List<TotalsDTO>> getBookingsByBookingDateBetween(
			@PathVariable(value = "strFromBookingDate") final String strFromBookingDate,
			@PathVariable(value = "strToBookingDate") final String strToBookingDate) throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService.getTotalsForBookingDateBetween(strFromBookingDate,
				strToBookingDate);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	@GetMapping("/totalsByBookingDate/fromBookingDate/{strFromBookingDate}/fromWorkShift/{fromWorkShift}/toBookingDate/{strToBookingDate}/toWorkShift/{toWorkShift}")
	public ResponseEntity<List<TotalsDTO>> getBookingsByBookingDateAndWorkShift(
			@PathVariable(value = "strFromBookingDate") final String strFromBookingDate,
			@PathVariable(value = "fromWorkShift") final WorkShift fromWorkShift,
			@PathVariable(value = "strToBookingDate") final String strToBookingDate,
			@PathVariable(value = "toWorkShift") final WorkShift toWorkShift)
			throws ResourceNotFoundException, InvalidInputException {
		final List<TotalsDTO> listOfTotalsDTO = this.reportService.getTotalsForBookingDatesAndShiftsBetween(
				strFromBookingDate, fromWorkShift, strToBookingDate, toWorkShift);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}
}
