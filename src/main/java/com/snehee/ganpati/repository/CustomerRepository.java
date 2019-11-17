package com.snehee.ganpati.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snehee.ganpati.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findByNameContaining(String nameOfCustomer);

	List<Customer> findByNameStartsWith(String nameOfCustomer);

	List<Customer> findByPrimaryMobileContaining(String primaryMobileNoOfCustomer);

	List<Customer> findByPrimaryMobileStartsWith(String primaryMobileNoOfCustomer);

	List<Customer> findBySecondaryMobileContaining(String secondaryMobileNoIfAnyOfCustomer);

	List<Customer> findBySecondaryMobileStartsWith(String secondaryMobileNoIfAnyOfCustomer);

	List<Customer> findByLandlineContaining(String landlineIfAnyOfCustomer);

	List<Customer> findByLandlineStartsWith(String landlineIfAnyOfCustomer);

	List<Customer> findByAddressContaining(String addressOfCustomer);

	List<Customer> findByInfoContaining(String infoAboutCustomerOrIdolsHePurchases);

	List<Customer> findByCommentsContaining(String commentsAboutCustomer);
}