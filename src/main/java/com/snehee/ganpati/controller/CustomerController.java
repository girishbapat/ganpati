package com.snehee.ganpati.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.snehee.ganpati.service.CustomerService;
import com.snehee.ganpati.util.Constants;

@RestController
class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/customers")
	List<Customer> getAllCustomers() {
		return this.customerService.getAllCustomers();
	}

	/**
	 * Gets customers by id.
	 *
	 * @param customerId the customer id
	 * @return the customers by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/customers/{id}")
	public ResponseEntity<Customer> getCustomersById(@PathVariable(value = "id") final Integer customerId)
			throws ResourceNotFoundException {
		final Customer customer = this.customerService.getCustomersById(customerId);
		return ResponseEntity.ok().body(customer);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/getCustomersWithNameLike/{name}")
	public ResponseEntity<List<Customer>> getCustomersByName(@PathVariable(value = "name") final String nameOfCustomer)
			throws ResourceNotFoundException {
		final List<Customer> customerListByName = this.customerService.getCustomersWithNameLike(nameOfCustomer);
		return ResponseEntity.ok().body(customerListByName);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/getCustomersWithNameLike/{name}/primaryMobileLike/{primaryMobile}")
	public ResponseEntity<List<Customer>> getCustomersWithNameAndPrimaryMobileLike(
			@PathVariable(value = "name") final String nameOfCustomer,
			@PathVariable(value = "primaryMobile") final String primaryMobile) throws ResourceNotFoundException {
		final List<Customer> customerListByName = this.customerService
				.getCustomersWithNameAndPrimaryMobileLike(nameOfCustomer, primaryMobile);
		return ResponseEntity.ok().body(customerListByName);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/getCustomersWithAttributeLike/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Customer>> getCustomersWithAttributeLike(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<Customer> customerListByType = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				customerListByType = this.customerService.getCustomersWithNameLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.PRIMARY_MOBILE)) {
				customerListByType = this.customerService.getCustomersWithPrimaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.SECONDARY_MOBILE)) {
				customerListByType = this.customerService.getCustomersWithSecondaryMobileLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				customerListByType = this.customerService.getCustomersWithLandlineLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.ADDRESS)) {
				customerListByType = this.customerService.getCustomersWithAddressLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.INFO)) {
				customerListByType = this.customerService.getCustomersWithInfoLike(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.COMMENTS)) {
				customerListByType = this.customerService.getCustomersWithCommentsLike(attributeValue);
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

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/getCustomersStartingWithAttribute/{attributeName}/{attributeValue}")
	public ResponseEntity<List<Customer>> getCustomersStartingWithAttribute(
			@PathVariable(value = "attributeName") @NotBlank final String attributeName,
			@PathVariable(value = "attributeValue", required = true) @NotBlank final String attributeValue)
			throws Exception {
		List<Customer> customerList = new ArrayList<>();
		try {
			if (attributeName.equalsIgnoreCase(Constants.NAME)) {
				customerList = this.customerService.getCustomersStartingWithCustomerName(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.PRIMARY_MOBILE)) {
				customerList = this.customerService.getCustomersStartingWithPrimaryMobile(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.SECONDARY_MOBILE)) {
				customerList = this.customerService.getCustomersStartingWithSecondaryMobile(attributeValue);
			} else if (attributeName.equalsIgnoreCase(Constants.LANDLINE)) {
				customerList = this.customerService.getCustomersStartingWithLandline(attributeValue);
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
		return ResponseEntity.ok().body(customerList);
	}

	/**
	 * Create customer customer.
	 *
	 * @param customer the customer
	 * @return the customer
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/customers")
	public Customer createCustomer(@Valid @RequestBody final Customer customer) {
		return this.customerService.createCustomer(customer);
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
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") final Integer customerId,
			@Valid @RequestBody final Customer customerDetailsTobeUpdated) throws ResourceNotFoundException {
		final Customer updatedCustomer = this.customerService.updateCustomer(customerId, customerDetailsTobeUpdated);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Delete customer map.
	 *
	 * @param customerId the customer id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/customers/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") final Integer customerId) throws Exception {

		final Boolean isCustomerDeleted = this.customerService.deleteCustomer(customerId);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", isCustomerDeleted);
		return response;
	}
}