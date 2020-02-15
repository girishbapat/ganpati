
package com.snehee.ganpati.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Invalid input exception.
 *
 * @author Girish Bapat
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2084036870382678078L;

	/**
	 * Business exception.
	 *
	 * @param message the message
	 */
	public BusinessException(final String message) {
		super(message);
	}
}
