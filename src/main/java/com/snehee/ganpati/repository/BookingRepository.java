package com.snehee.ganpati.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.Booking;

/**
 * The interface Booking repository.
 *
 * @author Girish
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByCustomerId(int customerId);

	List<Booking> findAllByBookingDateBetween(LocalDateTime fromBookingDate, LocalDateTime toBookingDate);

}
