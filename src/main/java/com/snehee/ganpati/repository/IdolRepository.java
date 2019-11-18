package com.snehee.ganpati.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.entity.Size;

/**
 * The interface Idol repository.
 *
 * @author Girish
 */
@Repository
public interface IdolRepository extends JpaRepository<Idol, Integer> {

	List<Idol> findByNameContaining(String nameOfIdol);

	List<Idol> findByTypeContaining(String typeOfIdol);

	List<Idol> findBySpecsContaining(String specsOfIdol);

	List<Idol> findBySizeLike(Size sizeOfIdol);

	List<Idol> findByCostGreaterThanEqual(BigDecimal costOfIdol);

	List<Idol> findByPriceGreaterThanEqual(BigDecimal priceOfIdol);

	List<Idol> findByQuantityGreaterThanEqual(int quantityOfIdol);

	List<Idol> findByReparableQtyGreaterThanEqual(int reparableQtyOfIdol);

	List<Idol> findByDamagedQtyGreaterThanEqual(int damagedQtyOfIdol);

	List<Idol> findByCommentsContaining(String commentsOfIdol);

	List<Idol> findByNameStartsWith(String nameOfIdol);
}
