/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.snehee.ganpati.dto.BookingDTO;
import com.snehee.ganpati.dto.BookingDates;
import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.enums.WorkShift;
import com.snehee.ganpati.exception.BusinessException;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.repository.DailyBookingRepository;
import com.snehee.ganpati.service.BookingService;
import com.snehee.ganpati.service.ReportService;
import com.snehee.ganpati.util.CommonUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//test
/**
 * @author Girish
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private DailyBookingRepository dailyBookingRepository;

	@Value("${report.output.path}")
	private String reportPath;

	@Override
	public List<TotalsDTO> getTotalsForBookingDatesAndShiftsBetween(final String strFromBookingDate,
			WorkShift fromWorkShift, final String strToBookingDate, WorkShift toWorkShift)
					throws InvalidInputException {
		// If from date is null throw exception
		if (StringUtils.isBlank(strFromBookingDate)) {
			throw new InvalidInputException("From Date cannot be null.");
		}
		List<TotalsDTO> listOfTotalsDTO = null;
		BookingDates bookingDates = null;

		// if from date is not null and other 3 parameters are null
		if (null == fromWorkShift && StringUtils.isBlank(strToBookingDate) && null == toWorkShift) {
			fromWorkShift = WorkShift.MORNING;
			bookingDates = this.bookingService.getBookingDates(strFromBookingDate, fromWorkShift, 24);
		}
		// if from date and fromWorkShift are not null and other 2 parameters are null
		else if (fromWorkShift != null && StringUtils.isBlank(strToBookingDate) && null == toWorkShift) {
			bookingDates = this.bookingService.getBookingDates(strFromBookingDate, fromWorkShift, 8);
		}
		// if from date and fromWorkShift and to date is not and only to workshift is
		// null then just set to workshift
		else if (fromWorkShift != null && StringUtils.isNotBlank(strToBookingDate) && null == toWorkShift) {
			toWorkShift = WorkShift.MORNING;
			final LocalDateTime fromBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate, fromWorkShift);
			final LocalDateTime toBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate, toWorkShift);
			bookingDates = new BookingDates(fromBookingDate, toBookingDate);

		} else if (null == fromWorkShift && StringUtils.isNotBlank(strToBookingDate) && null == toWorkShift) {
			fromWorkShift = WorkShift.MORNING;
			toWorkShift = WorkShift.NIGHT;// used for special purpose
			final LocalDateTime fromBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate, fromWorkShift);
			final LocalDateTime toBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate, toWorkShift);
			bookingDates = new BookingDates(fromBookingDate, toBookingDate);
		} else if (fromWorkShift != null && StringUtils.isNotBlank(strToBookingDate) && toWorkShift != null) {
			final LocalDateTime fromBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strFromBookingDate, fromWorkShift);
			final LocalDateTime toBookingDate = this.bookingService
					.getLocalDateTimeForStrBookingDateAndWorkshift(strToBookingDate, toWorkShift);
			bookingDates = new BookingDates(fromBookingDate, toBookingDate);
		}
		listOfTotalsDTO = this.dailyBookingRepository.getTotals(bookingDates.getFromDate(), bookingDates.getToDate());
		return listOfTotalsDTO;
	}

	@Override
	public List<TotalsDTO> getTotalsForBookingDateBetween(final String strFromBookingDate,
			final String strToBookingDate) throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(strFromBookingDate, null, strToBookingDate, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotalsForParticularBookingDate(final String particularBookingDate)
			throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(particularBookingDate, null, null, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotalsForParticularBookingDateAndShift(final String particularBookingDate,
			final WorkShift workShift) throws InvalidInputException {
		final List<TotalsDTO> bookingsBetweenBookingDates = this
				.getTotalsForBookingDatesAndShiftsBetween(particularBookingDate, workShift, null, null);
		return bookingsBetweenBookingDates;
	}

	@Override
	public List<TotalsDTO> getTotals() {
		final List<TotalsDTO> totals = this.dailyBookingRepository.getTotals();
		return totals;
	}

	@Override
	public String generateAndExportBookingReport(final String reportFormat)  {

		String returnString="";
		try {
			final List<BookingDTO> bookings = this.bookingService.getAllBookings();
			final File file = ResourceUtils.getFile("classpath:ganpatiBookingRecords.jrxml");

			final JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			final JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(bookings);
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("createdBy", "Girish Bapat");
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
			String  ganpatiBookingReportFilePath = "";
			if (reportFormat.equalsIgnoreCase("html")) {
				ganpatiBookingReportFilePath = this.reportPath+"/Ganpati_Booking_" + CommonUtils.getCurrentDateAndTime() + ".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint, ganpatiBookingReportFilePath);
			} else { // default is pdf
				ganpatiBookingReportFilePath = this.reportPath+"/Ganpati_Booking_" + CommonUtils.getCurrentDateAndTime() + ".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, ganpatiBookingReportFilePath);
			}
			returnString="report exported at:" + ganpatiBookingReportFilePath;
		} catch (FileNotFoundException | JRException e) {
			returnString="Problem generating report."+e.getMessage();
			throw new BusinessException(e.getMessage());
		}

		return returnString;

	}

}
