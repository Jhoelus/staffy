package com.praxis.staffy.utilerias.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InvalidRequestTokenException extends UsernameNotFoundException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestTokenException(String msg) {
		super(msg);
	}
}
