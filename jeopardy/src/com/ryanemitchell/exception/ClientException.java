package com.ryanemitchell.exception;

import javax.servlet.ServletException;

public class ClientException extends ServletException {
	
	public ClientException(String message) {
		super(message);
		//May want to add logging here
	}
}
