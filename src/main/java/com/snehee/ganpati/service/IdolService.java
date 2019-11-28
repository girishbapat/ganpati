package com.snehee.ganpati.service;

import java.math.BigDecimal;
import java.util.List;

import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.enums.Size;
import com.snehee.ganpati.exception.ResourceNotFoundException;

public interface IdolService {
	List<Idol> getAllIdols();

	List<Idol> getIdolsWithNameLike(String nameOfIdol);

	List<Idol> getIdolsStartingWithIdolName(String nameOfIdol);

	List<Idol> getIdolsWithCommentsLike(String commentsAboutIdol);

	List<Idol> getIdolsWithTypeLike(String typeOfIdol);

	List<Idol> getIdolsWithSpecsLike(String specsOfIdol);

	List<Idol> getIdolsWithSizeLike(Size sizeOfIdol);

	List<Idol> getIdolsWithCostGreaterThanEqual(BigDecimal costOfIdol);

	List<Idol> getIdolsWithPriceGreaterThanEqual(BigDecimal priceOfIdol);

	List<Idol> getIdolsWithQuantityGreaterThanEqual(int quantityOfIdol);

	List<Idol> getIdolsWithReparableQtyGreaterThanEqual(int reparableQtyOfIdol);

	List<Idol> getIdolsWithDamagedQtyGreaterThanEqual(int damagedQtyOfIdol);

	Idol getIdolsById(Integer idolId) throws ResourceNotFoundException;

	Idol createIdol(final Idol idolToBeCreated);

	Idol updateIdol(final Integer idolId, final Idol idolDetailsTobeUpdated) throws ResourceNotFoundException;

	Boolean deleteIdol(final Integer idolId) throws ResourceNotFoundException;

}
