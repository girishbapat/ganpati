package com.snehee.ganpati.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snehee.ganpati.entity.Idol;

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

	List<Idol> findBySizeContaining(String sizeOfIdol);

	List<Idol> findByCostGreaterThanEqual(String costOfIdol);

	List<Idol> findByPriceGreaterThanEqual(String priceOfIdol);

	List<Idol> findByQuantityGreaterThanEqual(String quantityOfIdol);

	List<Idol> findByReparableQtyGreaterThanEqual(String reparableQtyOfIdol);

	List<Idol> findByDamagedQtyGreaterThanEqual(String damagedQtyOfIdol);

	List<Idol> findByCommentsContaining(String commentsOfIdol);
}
