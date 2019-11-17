package com.snehee.ganpati.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.snehee.ganpati.entity.Sale;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.CustomerRepository;
import com.snehee.ganpati.repository.IdolRepository;
import com.snehee.ganpati.repository.SaleRepository;
import com.snehee.ganpati.repository.UserRepository;

@RestController
class SaleController {

	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private IdolRepository idolRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/sales")

	List<Sale> getAllSales() {
		return this.saleRepository.findAll();
	}

	/**
	 * Gets sales by id.
	 *
	 * @param saleId the sale id
	 * @return the sales by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/sales/{id}")
	public ResponseEntity<Sale> getSalesById(@PathVariable(value = "id") final Integer saleId)
			throws ResourceNotFoundException {
		final Sale sale = this.saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found with sale id : " + saleId));
		return ResponseEntity.ok().body(sale);
	}

	/**
	 * Create sale sale.
	 *
	 * @param sale the sale
	 * @return the sale
	 */
	@PostMapping("/sales")
	public Sale createSale(@Valid @RequestBody final Sale sale) {
		return this.saleRepository.save(sale);
	}

	/**
	 * Update sale response entity.
	 *
	 * @param saleId                 the sale id
	 * @param saleDetailsTobeUpdated the sale details which needed to be updated.
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/sales/{id}")
	public ResponseEntity<Sale> updateSale(@PathVariable(value = "id") final Integer saleId,
			@Valid @RequestBody final Sale saleDetailsTobeUpdated) throws ResourceNotFoundException {

		final Sale currentSaleDetailsFromDb = this.saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found with sale id :: " + saleId));

		BeanUtils.copyProperties(saleDetailsTobeUpdated, currentSaleDetailsFromDb);
		final Sale updatedSale = this.saleRepository.save(currentSaleDetailsFromDb);
		return ResponseEntity.ok(updatedSale);
	}

	/**
	 * Delete sale map.
	 *
	 * @param saleId the sale id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/sales/{id}")
	public Map<String, Boolean> deleteSale(@PathVariable(value = "id") final Integer saleId) throws Exception {
		final Sale sale = this.saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found with sale id  :: " + saleId));

		this.saleRepository.delete(sale);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}