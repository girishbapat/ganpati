package com.snehee.ganpati.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
	public List<Idol> getAllUsers() {
		return idolRepository.findAll();
	}

	/**
	 * Gets idols by id.
	 *
	 * @param idolId the idol id
	 * @return the idols by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/idols/{id}")
	public ResponseEntity<Idol> getUsersById(@PathVariable(value = "id") Integer idolId)
			throws ResourceNotFoundException {
		Idol idol = idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found on :: " + idolId));
		return ResponseEntity.ok().body(idol);
	}

	/**
	 * Create idol idol.
	 *
	 * @param idol the idol
	 * @return the idol
	 */
	@PostMapping("/idols")
	public Idol createUser(@Valid @RequestBody Idol idol) {
		return idolRepository.save(idol);
	}

	/**
	 * Update idol response entity.
	 *
	 * @param idolId      the idol id
	 * @param idolDetails the idol details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/idols/{id}")
	public ResponseEntity<Idol> updateIdol(@PathVariable(value = "id") Integer idolId,
			@Valid @RequestBody Idol idolDetails) throws ResourceNotFoundException {

		Idol idol = idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found on :: " + idolId));

		idol.setName(idolDetails.getName());
		idol.setType(idolDetails.getType());
		idol.setPrice(idolDetails.getPrice());
		idol.setCost(idolDetails.getCost());
		idol.setQuantity(idolDetails.getQuantity());
		idol.setReparable_qty(idolDetails.getReparable_qty());
		idol.setDamaged_qty(idolDetails.getDamaged_qty());
		idol.setSize(idolDetails.getSize());
		idol.setType(idolDetails.getType());
		/*
		 * idol.setUsername(idolDetails.getUsername());
		 * idol.setEmail(idolDetails.getEmail());
		 * idol.setPassword(idolDetails.getPassword());
		 */
		final Idol updatedIdol = idolRepository.save(idol);
		return ResponseEntity.ok(updatedIdol);
	}

	/**
	 * Delete idol map.
	 *
	 * @param idolId the idol id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/idol/{id}")
	public Map<String, Boolean> deleteIdol(@PathVariable(value = "id") Integer idolId) throws Exception {
		Idol idol = idolRepository.findById(idolId)
				.orElseThrow(() -> new ResourceNotFoundException("Idol not found on :: " + idolId));

		idolRepository.delete(idol);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
