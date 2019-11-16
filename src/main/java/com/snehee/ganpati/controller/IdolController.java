package com.snehee.ganpati.controller;

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
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.IdolRepository;
import com.snehee.ganpati.util.Constants;

/**
 * The type Idol controller.
 *
 * @author Girish Bapat
 */
@RestController
public class IdolController {

	@Autowired
	private IdolRepository idolRepository;

	/**
	 * Get all idols list.
	 *
	 * @return the list
	 */
	@GetMapping("/idols")
	public List<Idol> getAllIdols() {
		return this.idolRepository.findAll();
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
		final Idol idol = this.idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolId));
		return ResponseEntity.ok().body(idol);
	}

	@GetMapping("/getIdolsByName/{name}")
	public ResponseEntity<List<Idol>> getIdolsByName(@PathVariable(value = "name") final String nameOfIdol)
			throws ResourceNotFoundException {
		final List<Idol> idolListByName = this.idolRepository.findByNameContaining(nameOfIdol);
		return ResponseEntity.ok().body(idolListByName);
	}

	@GetMapping("/getIdolsByAttribute/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Idol>> getIdolsByAttribute(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws ResourceNotFoundException {
		List<Idol> idolListByType = new ArrayList<>();
		if (attributeName.equalsIgnoreCase(Constants.NAME)) {
			idolListByType = this.idolRepository.findByNameContaining(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.TYPE)) {
			idolListByType = this.idolRepository.findByTypeContaining(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.SPECS)) {
			idolListByType = this.idolRepository.findBySpecsContaining(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.SIZE)) {
			idolListByType = this.idolRepository.findBySizeContaining(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.COST)) {
			idolListByType = this.idolRepository.findByCostGreaterThanEqual(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.PRICE)) {
			idolListByType = this.idolRepository.findByPriceGreaterThanEqual(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.QUANTITY)) {
			idolListByType = this.idolRepository.findByQuantityGreaterThanEqual(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.REPARABLE_QTY)) {
			idolListByType = this.idolRepository.findByReparableQtyGreaterThanEqual(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.DAMAGED_QTY)) {
			idolListByType = this.idolRepository.findByDamagedQtyGreaterThanEqual(attributeValue);
		} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
			idolListByType = this.idolRepository.findByCommentsContaining(attributeValue);
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
		return this.idolRepository.save(idol);
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

		final Idol currentIdolFromDb = this.idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id :: " + idolId));

		BeanUtils.copyProperties(idolDetailsTobeUpdated, currentIdolFromDb);
		final Idol updatedIdol = this.idolRepository.save(currentIdolFromDb);
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
		final Idol idol = this.idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found with idol id  :: " + idolId));

		this.idolRepository.delete(idol);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
