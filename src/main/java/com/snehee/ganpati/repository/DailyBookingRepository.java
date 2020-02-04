package com.snehee.ganpati.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.dto.TotalsDTO;
import com.snehee.ganpati.entity.DailyBooking;

/**
 * The interface DailyBooking repository.
 * this is for daily booking totals
 *
 * @author Girish
 */
@Repository
public interface DailyBookingRepository extends JpaRepository<DailyBooking, Integer> {
	List<DailyBooking> findAllByBookingDateBetween(LocalDateTime fromBookingDate, LocalDateTime toBookingDate);

	@Query("SELECT new com.snehee.ganpati.dto.TotalsDTO( b.paymentMode , sum(b.bookingAmount) , sum(b.discountAmount) , sum(b.balanceAmount) ,  sum(b.totalAmount)) FROM DailyBooking as b where b.bookingDate between :fromBookingDate and :toBookingDate group by b.paymentMode")
	List<TotalsDTO> getTotals(@Param("fromBookingDate") LocalDateTime fromBookingDate,
			@Param("toBookingDate") LocalDateTime toBookingDate);

	@Query("SELECT new com.snehee.ganpati.dto.TotalsDTO( b.paymentMode , sum(b.bookingAmount) ,  sum(b.discountAmount) , sum(b.balanceAmount) ,  sum(b.totalAmount)) FROM DailyBooking as b group by b.paymentMode")
	List<TotalsDTO> getTotals();

}
