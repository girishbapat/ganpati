package com.snehee.ganpati.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRespository;

	@Override
	public List<Customer> getCustomersWithNameLike(final String nameOfCustomer) {
		final List<Customer> customerListByName = this.customerRespository.findByNameContaining(nameOfCustomer);
		return customerListByName;
	}

	@Override
	public List<Customer> getCustomersStartingWithCustomerName(final String nameOfCustomer) {
		final List<Customer> customers = this.customerRespository.findByNameStartsWith(nameOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersStartingWithPrimaryMobile(final String primaryMobileNoOfCustomer) {
		final List<Customer> customers = this.customerRespository
				.findByPrimaryMobileStartsWith(primaryMobileNoOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersStartingWithSecondaryMobile(final String secondaryMobileNoIfAnyOfCustomer) {
		final List<Customer> customers = this.customerRespository
				.findBySecondaryMobileStartsWith(secondaryMobileNoIfAnyOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersStartingWithLandline(final String landlineIfAnyOfCustomer) {
		final List<Customer> customers = this.customerRespository.findByLandlineStartsWith(landlineIfAnyOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithPrimaryMobileLike(final String primaryMobileNoOfCustomer) {
		final List<Customer> customers = this.customerRespository
				.findByPrimaryMobileContaining(primaryMobileNoOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithSecondaryMobileLike(final String secondaryMobileNoIfAnyOfCustomer) {
		final List<Customer> customers = this.customerRespository
				.findBySecondaryMobileContaining(secondaryMobileNoIfAnyOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithLandlineLike(final String landlineIfAnyOfCustomer) {
		final List<Customer> customers = this.customerRespository.findByLandlineContaining(landlineIfAnyOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithAddressLike(final String addressOfCustomer) {
		final List<Customer> customers = this.customerRespository.findByAddressContaining(addressOfCustomer);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithInfoLike(final String infoAboutCustomerOrIdolsHePurchases) {
		final List<Customer> customers = this.customerRespository
				.findByInfoContaining(infoAboutCustomerOrIdolsHePurchases);
		return customers;
	}

	@Override
	public List<Customer> getCustomersWithCommentsLike(final String commentsAboutCustomer) {
		final List<Customer> customers = this.customerRespository.findByCommentsContaining(commentsAboutCustomer);
		return customers;
	}

	@Override
	public Customer getCustomersById(final Integer customerId) throws ResourceNotFoundException {
		final Customer customer = this.customerRespository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id : " + customerId));
		return customer;
	}

	@Override
	public Customer createCustomer(@Valid final Customer customer) {
		return this.customerRespository.save(customer);
	}

	@Override
	public Customer updateCustomer(final Integer customerId, final Customer customerDetailsTobeUpdated)
			throws ResourceNotFoundException {
		final Customer currentCustomerDetailsFromDb = this.customerRespository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id :: " + customerId));

		BeanUtils.copyProperties(customerDetailsTobeUpdated, currentCustomerDetailsFromDb);
		final Customer updatedCustomer = this.customerRespository.save(currentCustomerDetailsFromDb);
		return updatedCustomer;
	}

	@Override
	public Boolean deleteCustomer(final Integer customerId) throws ResourceNotFoundException {
		final Customer currentCustomerDetailsFromDb = this.customerRespository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer not found with customer id :: " + customerId));
		this.customerRespository.delete(currentCustomerDetailsFromDb);
		return true;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return this.customerRespository.findAll();
	}

}
