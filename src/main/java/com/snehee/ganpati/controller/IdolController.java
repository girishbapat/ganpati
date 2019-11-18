package com.snehee.ganpati.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.entity.Idol;
import com.snehee.ganpati.entity.Size;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.service.IdolService;
import com.snehee.ganpati.util.Constants;

/**
 * The type Idol controller.
 *
 * @author Girish Bapat
 */
@RestController
public class IdolController {

	@Autowired
	private IdolService idolService;

	/**
	 * Get all idols list.
	 *
	 * @return the list
	 */
	@GetMapping("/idols")
	public List<Idol> getAllIdols() {
		return this.idolService.getAllIdols();
	}

	/**
	 * Gets idols by id.
	 *
	 * @param idolId the idol id
	 * @return the idols by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/idols/{id}")
	public ResponseEntity<Idol> getIdolsById(@PathVariable(value = "id") final Integer idolId)
			throws ResourceNotFoundException {
		final Idol idol = this.idolService.getIdolsById(idolId);
		return ResponseEntity.ok().body(idol);
	}

	@GetMapping("/getIdolsByNameStartingWith/{name}")
	public ResponseEntity<List<Idol>> getIdolsByNameStartingWith(@PathVariable(value = "name") final String nameOfIdol)
			throws ResourceNotFoundException {
		final List<Idol> idolListByName = this.idolService.getIdolsStartingWithIdolName(nameOfIdol);
		return ResponseEntity.ok().body(idolListByName);
	}

	@GetMapping("/getIdolsByName/{name}")
	public ResponseEntity<List<Idol>> getIdolsByName(@PathVariable(value = "name") final String nameOfIdol)
			throws ResourceNotFoundException {
		final List<Idol> idolListByName = this.idolService.getIdolsWithNameLike(nameOfIdol);
		return ResponseEntity.ok().body(idolListByName);
	}

	@GetMapping("/getIdolsByAttribute/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Idol>> getIdolsByAttribute(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<Idol> idolListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				idolListByType = this.idolService.getIdolsWithNameLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.TYPE)) {
				idolListByType = this.idolService.getIdolsWithTypeLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.SPECS)) {
				idolListByType = this.idolService.getIdolsWithSpecsLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.SIZE)) {
				idolListByType = this.idolService.getIdolsWithSizeLike(Size.valueOf(attributeValue.toUpperCase()));
			} else if (attributeName.equalsIgnoreCase(Constants.COST)) {
				idolListByType = this.idolService.getIdolsWithCostGreaterThanEqual(new BigDecimal(attributeValue));
			} else if (attributeName.equalsIgnoreCase(Constants.PRICE)) {
				idolListByType = this.idolService.getIdolsWithPriceGreaterThanEqual(new BigDecimal(attributeValue));
			} else if (attributeName.equalsIgnoreCase(Constants.QUANTITY)) {
				idolListByType = this.idolService
						.getIdolsWithQuantityGreaterThanEqual(Integer.valueOf(attributeValue).intValue());
			} else if (attributeName.equalsIgnoreCase(Constants.REPARABLE_QTY)) {
				idolListByType = this.idolService
						.getIdolsWithReparableQtyGreaterThanEqual(Integer.valueOf(attributeValue).intValue());
			} else if (attributeName.equalsIgnoreCase(Constants.DAMAGED_QTY)) {
				idolListByType = this.idolService
						.getIdolsWithDamagedQtyGreaterThanEqual(Integer.valueOf(attributeValue).intValue());
			} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
				idolListByType = this.idolService.getIdolsWithCommentsLike(attributeValue);
			} else {
				throw new InvalidInputException("Invalid attributeName:" + attributeName);
			}
		} catch (final InvalidInputException e) {
			throw new InvalidInputException(
					"Invalid attributeName:" + attributeName + " or attributeValue:" + attributeValue);
		} catch (final Exception e) {
			throw new Exception(
					"API called with attributeName:\" + attributeName + \" and attributeValue:\" + attributeValue");
		}
		return ResponseEntity.ok().body(idolListByType);
	}

	/**
	 * Create idol idol.
	 *
	 * @param idol the idol
	 * @return the idol
	 */
	@PostMapping("/idols")
	public Idol createIdol(@Valid @RequestBody final Idol idol) {
		return this.idolService.createIdol(idol);
	}

	/**
	 * Update idol response entity.
	 *
	 * @param idolId                 the idol id
	 * @param idolDetailsTobeUpdated the idol details which needed to be updated.
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/idols/{id}")
	public ResponseEntity<Idol> updateIdol(@PathVariable(value = "id") final Integer idolId,
			@Valid @RequestBody final Idol idolDetailsTobeUpdated) throws ResourceNotFoundException {

		final Idol currentIdolFromDb = this.idolService.getIdolsById(idolId);

		BeanUtils.copyProperties(idolDetailsTobeUpdated, currentIdolFromDb);
		final Idol updatedIdol = this.idolService.createIdol(currentIdolFromDb);
		return ResponseEntity.ok(updatedIdol);
	}

	/**
	 * Delete idol map.
	 *
	 * @param idolId the idol id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/idols/{id}")
	public Map<String, Boolean> deleteIdol(@PathVariable(value = "id") final Integer idolId) throws Exception {
		final Boolean isIdolDeleted = this.idolService.deleteIdol(idolId);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", isIdolDeleted);
		return response;
	}
}
