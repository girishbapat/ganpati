package com.snehee.ganpati.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.entity.Size;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.IdolRepository;
import com.snehee.ganpati.service.IdolService;

@Service
public class IdolServiceImpl implements IdolService {

	@Autowired
	private IdolRepository idolRespository;

	@Override
	public List<Idol> getAllIdols() {
		return this.idolRespository.findAll();
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
		return this.idolRespository.save(idol);
	}

	@Override
	public Idol updateIdol(final Integer idolId, final Idol idolDetailsTobeUpdated) throws ResourceNotFoundException {
		final Idol currentIdolDetailsFromDb = this.idolRespository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolId));

		BeanUtils.copyProperties(idolDetailsTobeUpdated, currentIdolDetailsFromDb);
		final Idol updatedIdol = this.idolRespository.save(currentIdolDetailsFromDb);
		return updatedIdol;
	}

	@Override
	public Boolean deleteIdol(final Integer idolId) throws ResourceNotFoundException {
		final Idol currentIdolDetailsFromDb = this.idolRespository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolId));
		this.idolRespository.delete(currentIdolDetailsFromDb);
		return true;
	}

}
