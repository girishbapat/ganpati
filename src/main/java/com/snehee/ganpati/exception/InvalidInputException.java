
package com.snehee.ganpati.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Invalid input exception.
 *
 * @author Girish Bapat
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends Exception {

	/**
	 * Instantiates a Invalid input exception.
	 *
	 * @param message the message
	 */
	public InvalidInputException(final String message) {
		super(message);
	}
}
