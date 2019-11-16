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

import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.exception.InvalidInputException;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.CustomerRepository;
import com.snehee.ganpati.util.Constants;

@RestController
class CustomerController {

	@Autowired
	private CustomerRepository repository;

	@GetMapping("/customers")
	List<Customer> getAllCustomers() {
		return this.repository.findAll();
	}

	/**
	 * Gets customers by id.
	 *
	 * @param customerId the customer id
	 * @return the customers by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomersById(@PathVariable(value = "id") final Integer customerId)
			throws ResourceNotFoundException {
		final Customer customer = this.repository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id : " + customerId));
		return ResponseEntity.ok().body(customer);
	}

	@GetMapping("/getCustomersByName/{name}")
	public ResponseEntity<List<Customer>> getCustomersByName(@PathVariable(value = "name") final String nameOfCustomer)
			throws ResourceNotFoundException {
		final List<Customer> customerListByName = this.repository.findByNameContaining(nameOfCustomer);
		return ResponseEntity.ok().body(customerListByName);
	}

	@GetMapping("/getCustomersByAttribute/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Customer>> getCustomersByAttribute(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<Customer> customerListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				customerListByType = this.repository.findByNameContaining(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.MOBILE1)) {
				customerListByType = this.repository.findByMobile1Containing(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.MOBILE2)) {
				customerListByType = this.repository.findByMobile2Containing(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				customerListByType = this.repository.findByLandlineContaining(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.ADDRESS)) {
				customerListByType = this.repository.findByAddressContaining(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.INFO)) {
				customerListByType = this.repository.findByInfoContaining(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
				customerListByType = this.repository.findByCommentsContaining(attributeValue);
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
		return ResponseEntity.ok().body(customerListByType);
	}

	@GetMapping("/getCustomersStartsWithAttribute/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Customer>> getCustomersStartsWithAttribute(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<Customer> customerListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				customerListByType = this.repository.findByNameStartsWith(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.MOBILE1)) {
				customerListByType = this.repository.findByMobile1StartsWith(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.MOBILE2)) {
				customerListByType = this.repository.findByMobile2StartsWith(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				customerListByType = this.repository.findByLandlineStartsWith(attributeValue);
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
		return ResponseEntity.ok().body(customerListByType);
	}

	/**
	 * Create customer customer.
	 *
	 * @param customer the customer
	 * @return the customer
	 */
	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody final Customer customer) {
		return this.repository.save(customer);
	}

	/**
	 * Update customer response entity.
	 *
	 * @param customerId                 the customer id
	 * @param customerDetailsTobeUpdated the customer details which needed to be
	 *                                   updated.
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") final Integer customerId,
			@Valid @RequestBody final Customer customerDetailsTobeUpdated) throws ResourceNotFoundException {

		final Customer currentCustomerDetailsFromDb = this.repository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id :: " + customerId));

		BeanUtils.copyProperties(customerDetailsTobeUpdated, currentCustomerDetailsFromDb);
		final Customer updatedCustomer = this.repository.save(currentCustomerDetailsFromDb);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Delete customer map.
	 *
	 * @param customerId the customer id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/customers/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") final Integer customerId) throws Exception {
		final Customer customer = this.repository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id  :: " + customerId));

		this.repository.delete(customer);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}