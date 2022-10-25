package com.durgesh.exception;

import org.springframework.http.HttpStatus;

public class CandidateDoesntExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public CandidateDoesntExistsException(String message) {
		super();
		this.message = message;
	}

	public CandidateDoesntExistsException(HttpStatus badRequest, String string) {
		
		this.message = message;
	}
	

}
