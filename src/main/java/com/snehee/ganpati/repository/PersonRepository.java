package com.snehee.ganpati.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.snehee.ganpati.entity.Person;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {
	/*
	 * List<Person> findByLastName(@Param("name") String name); List<Person>
	 * findByFirstName(@Param("fname") String fname); Person findById(long id);
	 */
}