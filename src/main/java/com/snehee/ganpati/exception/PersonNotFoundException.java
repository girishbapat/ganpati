package com.snehee.ganpati.exception;

public class PersonNotFoundException extends RuntimeException {
	public PersonNotFoundException(Long id) {
		super("Could not find Person with id"+id);
	}

}
