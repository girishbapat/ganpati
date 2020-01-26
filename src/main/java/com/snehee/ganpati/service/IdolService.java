package com.snehee.ganpati.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.expression.Operation;

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

	Idol updateIdol(final Idol idolDetailsTobeUpdated) throws ResourceNotFoundException;

	/**
	 * Update the quantity of idol to be updated. In the same API if this update
	 * trigger due to reparable or damaged quantity then provide that field in
	 * otherQuantityField
	 *
	 * @param otherQuantityField-  name of field which may be reduced or increased
	 *                             and due to which quantity will be updated
	 *                             e.g.Case 1-if the reparable idol quantity or
	 *                             damaged idol quantity is increased then quantity
	 *                             will be reduced. for normal booking this field
	 *                             will be null Case 2-if reparable quantity is
	 *                             repaired then reparable quantity will be reduced
	 *                             and quantity will be increased. -for booking
	 *                             cancellation or returned this field will be blank
	 * @param operation-           its wrt to quantity field if its ADDITION or
	 *                             SUBTRACTION
	 * @param quantityTobeUpdated- actual quantity to be updated
	 * @param idolId-              idol id for which this change would be done.
	 * @return-updated idol
	 * @throws ResourceNotFoundException
	 */
	Idol updateQuantityById(String otherQuantityField, Operation operation, int quantityTobeUpdated, int idolId)
			throws ResourceNotFoundException;

	Idol updateQuantityByIdol(String otherQuantityField, Operation operation, int quantityTobeUpdated,
			Idol idolQuantityTobeUpdated) throws ResourceNotFoundException;

	Boolean deleteIdol(final Integer idolId) throws ResourceNotFoundException;

}
