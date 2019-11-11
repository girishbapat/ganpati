package com.snehee.ganpati.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.entity.Person;
import com.snehee.ganpati.exception.PersonNotFoundException;
import com.snehee.ganpati.repository.PersonRepository;

@RestController
class PersonController {

  private final PersonRepository repository;

  PersonController(PersonRepository repository) {
    this.repository = repository;
  }

  // Aggregate root

  @GetMapping("/persons")
  List<Person> all() {
    return repository.findAll();
  }

  @PostMapping("/persons")
  Person newPerson(@RequestBody Person newPerson) {
    return repository.save(newPerson);
  }

  // Single item

  @GetMapping("/persons/{id}")
  Person one(@PathVariable Long id) {

	  return repository.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
  }

  @PutMapping("/persons/{id}")
  Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
	  return repository.findById(id).
			  map(person ->
			  {
				  person.setFirstName(newPerson.getFirstName());
				  person.setLastName(newPerson.getLastName());
				  return repository.save(person);
			  }).orElseGet(()->
			  {
				  newPerson.setId(id);
				  return repository.save(newPerson);
				  
			  });

		/*
		 * return repository.findById(id) .map(person -> {
		 * 
		 * return repository.save(person); }) .orElseGet(() -> {
		 * 
		 * return repository.save(newPerson); });
		 */
  }

  @DeleteMapping("/persons/{id}")
  void deletePerson(@PathVariable Long id) {
    repository.deleteById(id);
  }
}