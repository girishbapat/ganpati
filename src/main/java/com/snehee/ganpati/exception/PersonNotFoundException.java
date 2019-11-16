package com.snehee.ganpati.exception;

public class PersonNotFoundException extends RuntimeException {
	public PersonNotFoundException(Long id) {
		super("Could not find Customer with id"+id);
	}

}
