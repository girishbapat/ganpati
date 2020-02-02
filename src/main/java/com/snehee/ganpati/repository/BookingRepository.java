package com.snehee.ganpati.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.entity.Booking;
import com.snehee.ganpati.enums.Location;
import com.snehee.ganpati.enums.PaymentMode;
import com.snehee.ganpati.enums.Status;

/**
 * The interface Booking repository.
 *
 * @author Girish
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByCustomerId(int customerId);

	List<Booking> findAllByBookingDateBetween(LocalDateTime fromBookingDate, LocalDateTime toBookingDate);

	List<Booking> findByStatusContaining(Status status);

	List<Booking> findByLocationContaining(Location location);

	List<Booking> findByPaymentModeContaining(PaymentMode paymentModeLike);

	List<Booking> findByReasonContaining(String reason);

	List<Booking> findByCommentsContaining(String comments);

	@Query("SELECT new com.snehee.ganpati.dto.TotalsDTO( b.paymentMode , sum(b.bookingAmount) , sum(b.discountAmount) , sum(b.balanceAmount) ,  sum(b.totalAmount)) FROM Booking as b where  UPPER( b.status ) IN ('BOOKED', 'DISPATCHED') and b.bookingDate between :fromBookingDate and :toBookingDate group by b.paymentMode")
	List<TotalsDTO> getTotals(@Param("fromBookingDate") LocalDateTime fromBookingDate,
			@Param("toBookingDate") LocalDateTime toBookingDate);

	@Query("SELECT new com.snehee.ganpati.dto.TotalsDTO( b.paymentMode , sum(b.bookingAmount) ,  sum(b.discountAmount) , sum(b.balanceAmount) ,  sum(b.totalAmount)) FROM Booking as b where UPPER( b.status ) IN ('BOOKED', 'DISPATCHED') group by b.paymentMode")
	List<TotalsDTO> getTotals();

}
