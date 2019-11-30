package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.entity.Customer;
import com.snehee.ganpati.exception.ResourceNotFoundException;

public interface CustomerService {
	List<Customer> getAllCustomers();

	List<Customer> getCustomersWithNameLike(String nameOfCustomer);

	List<Customer> getCustomersWithNameAndPrimaryMobileLike(String nameOfCustomer, String primaryMobileNoOfCustomer);

	List<Customer> getCustomersStartingWithCustomerName(String nameOfCustomer);

	List<Customer> getCustomersStartingWithPrimaryMobile(String primaryMobileNoOfCustomer);

	List<Customer> getCustomersStartingWithSecondaryMobile(String secondaryMobileNoIfAnyOfCustomer);

	List<Customer> getCustomersStartingWithLandline(String landlineIfAnyOfCustomer);

	List<Customer> getCustomersWithPrimaryMobileLike(String primaryMobileNoOfCustomer);

	List<Customer> getCustomersWithSecondaryMobileLike(String secondaryMobileNoIfAnyOfCustomer);

	List<Customer> getCustomersWithLandlineLike(String landlineIfAnyOfCustomer);

	List<Customer> getCustomersWithAddressLike(String addressOfCustomer);

	List<Customer> getCustomersWithInfoLike(String infoAboutCustomerOrIdolsHePurchases);

	List<Customer> getCustomersWithCommentsLike(String commentsAboutCustomer);

	Customer getCustomersById(Integer customerId) throws ResourceNotFoundException;

	Customer createCustomer(Customer customer);

	Customer updateCustomer(final Integer customerId, final Customer customerDetailsTobeUpdated)
			throws ResourceNotFoundException;

	Boolean deleteCustomer(final Integer customerId) throws ResourceNotFoundException;

}
