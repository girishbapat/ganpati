package com.snehee.ganpati.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@GetMapping(value = "/generateAndExportBookingReport")
	public String generateAndExportBookingReport() throws Exception {

		return this.reportService.generateAndExportBookingReport("pdf");
	}

	/**
	 * get totals
	 *
	 * @return
	 * @throws InvalidInputException
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/totalsForBookings")
	public ResponseEntity<List<TotalsDTO>> getTotalsForBookings() throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService.getTotals();
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	/**
	 * Get totals for particular booking date.
	 *
	 * @param particularBookingDate- d-MMM-yyyy- 04-Feb-2020
	 * @return
	 * @throws InvalidInputException
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/totalsForParticularBookingDate/{particularBookingDate}")
	public ResponseEntity<List<TotalsDTO>> getTotalsForParticularBookingDate(
			@PathVariable(value = "particularBookingDate") final String particularBookingDate)
					throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService
				.getTotalsForParticularBookingDate(particularBookingDate);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	/**
	 * Get totals for particular date for particular shift
	 *
	 * @param particularBookingDate- d-MMM-yyyy- 04-Feb-2020
	 * @param workShift-             MORNING/EVENING
	 * @return
	 * @throws InvalidInputException
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/totalsForParticularBookingDate/{particularBookingDate}/forParticularWorkShift/{workShift}")

	public ResponseEntity<List<TotalsDTO>> getTotalsForParticularBookingDateAndShift(
			@PathVariable(value = "particularBookingDate") final String particularBookingDate,
			@PathVariable(value = "workShift") final WorkShift workShift) throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService
				.getTotalsForParticularBookingDateAndShift(particularBookingDate, workShift);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	/**
	 * Get totals between from and to booking dates
	 *
	 * @param strFromBookingDate-01-FEB-2020
	 * @param strToBookingDate-04-FEB-2020
	 * @return
	 * @throws InvalidInputException
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/totalsByBookingDate/fromBookingDate/{strFromBookingDate}/toBookingDate/{strToBookingDate}")
	public ResponseEntity<List<TotalsDTO>> getTotalsByBookingDateBetween(
			@PathVariable(value = "strFromBookingDate") final String strFromBookingDate,
			@PathVariable(value = "strToBookingDate") final String strToBookingDate) throws InvalidInputException {

		final List<TotalsDTO> listOfTotalsDTO = this.reportService.getTotalsForBookingDateBetween(strFromBookingDate,
				strToBookingDate);
		return ResponseEntity.ok().body(listOfTotalsDTO);
	}

	/**
	 * Get totals by booking dates
	 *
	 * @param strFromBookingDate-01-FEB-2020
	 * @param fromWorkShift-MORNING
	 * @param strToBookingDate-04-FEB-2020
	 * @param toWorkShift-EVENING
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws InvalidInputException
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/totalsByBookingDate/fromBookingDate/{strFromBookingDate}/fromWorkShift/{fromWorkShift}/toBookingDate/{strToBookingDate}/toWorkShift/{toWorkShift}")
	public ResponseEntity<List<TotalsDTO>> getTotalsByBookingDateAndWorkShift(
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
