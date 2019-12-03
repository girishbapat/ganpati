package com.snehee.ganpati.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.enums.Size;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.IdolRepository;
import com.snehee.ganpati.service.IdolService;
import com.snehee.ganpati.util.Constants;

@Transactional
@Service
public class IdolServiceImpl implements IdolService {

	@Autowired
	private IdolRepository idolRespository;

	@Override
	public List<Idol> getAllIdols() {
		final List<Idol> allIdols = this.idolRespository.findAll();
		Constants.refreshIdolList(allIdols);
		return allIdols;
	}

	@Override
	public Idol getIdolsById(final Integer idolId) throws ResourceNotFoundException {
		final Idol idol = this.idolRespository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id : " + idolId));
		return idol;
	}

	@Override
	public List<Idol> getIdolsStartingWithIdolName(final String nameOfIdol) {
		final List<Idol> idols = this.idolRespository.findByNameStartsWith(nameOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithNameLike(final String nameOfIdol) {
		final List<Idol> idolListByName = this.idolRespository.findByNameContaining(nameOfIdol);
		return idolListByName;
	}

	@Override
	public List<Idol> getIdolsWithCommentsLike(final String commentsAboutIdol) {
		final List<Idol> idols = this.idolRespository.findByCommentsContaining(commentsAboutIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithTypeLike(final String typeOfIdol) {
		final List<Idol> idols = this.idolRespository.findByTypeContaining(typeOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithSpecsLike(final String specsOfIdol) {
		final List<Idol> idols = this.idolRespository.findBySpecsContaining(specsOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithSizeLike(final Size sizeOfIdol) {
		final List<Idol> idols = this.idolRespository.findBySizeLike(sizeOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithCostGreaterThanEqual(final BigDecimal costOfIdol) {
		final List<Idol> idols = this.idolRespository.findByCostGreaterThanEqual(costOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithPriceGreaterThanEqual(final BigDecimal priceOfIdol) {
		final List<Idol> idols = this.idolRespository.findByPriceGreaterThanEqual(priceOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithQuantityGreaterThanEqual(final int quantityOfIdol) {
		final List<Idol> idols = this.idolRespository.findByQuantityGreaterThanEqual(quantityOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithReparableQtyGreaterThanEqual(final int reparableQtyOfIdol) {
		final List<Idol> idols = this.idolRespository.findByReparableQtyGreaterThanEqual(reparableQtyOfIdol);
		return idols;
	}

	@Override
	public List<Idol> getIdolsWithDamagedQtyGreaterThanEqual(final int damagedQtyOfIdol) {
		final List<Idol> idols = this.idolRespository.findByDamagedQtyGreaterThanEqual(damagedQtyOfIdol);
		return idols;
	}

	@Override
	public Idol createIdol(final Idol idol) {
		final Idol savedIdol = this.idolRespository.save(idol);
		this.getAllIdols();
		return savedIdol;
	}

	@Override
	public Boolean deleteIdol(final Integer idolId) throws ResourceNotFoundException {
		final Idol currentIdolDetailsFromDb = this.idolRespository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolId));
		this.idolRespository.delete(currentIdolDetailsFromDb);
		this.getAllIdols();
		return true;
	}

	@Override
	public Idol updateQuantityById(final String otherQuantityField, final Operation operation,
			final int quantityTobeUpdated, final int idolId) throws ResourceNotFoundException {
		final Idol idolQuantityTobeUpdated = this.getIdolsById(idolId);
		return this.updateQuantityByIdol(otherQuantityField, operation, quantityTobeUpdated, idolQuantityTobeUpdated);
	}

	@Override
	public Idol updateQuantityByIdol(final String otherQuantityField, final Operation operation,
			final int quantityTobeUpdated, final Idol idolQuantityTobeUpdated) throws ResourceNotFoundException {
		if (Operation.ADD.equals(operation)) {
			/*
			 * Case 1- if the booking is cancelled or something other than repaired just
			 * increase quantity.
			 */
			idolQuantityTobeUpdated.setQuantity(idolQuantityTobeUpdated.getQuantity() + quantityTobeUpdated);
			/*
			 * Case 2- If quantity is repaired that means repairable quantity will be
			 * reduced.
			 */
			if (Constants.REPARABLE_QTY.equals(otherQuantityField)) {
				idolQuantityTobeUpdated
						.setReparableQty(idolQuantityTobeUpdated.getReparableQty() - quantityTobeUpdated);
			}
		} else if (Operation.SUBTRACT.equals(operation)) {

			/*
			 * Case 1- if the idol is booked then just reduce quantity.
			 */
			idolQuantityTobeUpdated.setQuantity(idolQuantityTobeUpdated.getQuantity() - quantityTobeUpdated);

			/*
			 * Case 2- if the idol will be repaired or damaged increase that quantity as
			 * well.
			 */
			if (Constants.REPARABLE_QTY.equals(otherQuantityField)) {
				idolQuantityTobeUpdated
						.setReparableQty(idolQuantityTobeUpdated.getReparableQty() + quantityTobeUpdated);
			} else if (Constants.DAMAGED_QTY.equals(otherQuantityField)) {
				idolQuantityTobeUpdated.setDamagedQty(idolQuantityTobeUpdated.getDamagedQty() + quantityTobeUpdated);
			}

		}
		return this.updateIdol(idolQuantityTobeUpdated);
	}

	@Override
	public Idol updateIdol(final Idol idolTobeUpdated) throws ResourceNotFoundException {

		final Idol currentIdolDetailsFromDb = this.idolRespository.findById(idolTobeUpdated.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolTobeUpdated.getId()));

		BeanUtils.copyProperties(idolTobeUpdated, currentIdolDetailsFromDb);
		final Idol updatedIdol = this.idolRespository.save(currentIdolDetailsFromDb);
		this.getAllIdols();
		return updatedIdol;

	}

}
