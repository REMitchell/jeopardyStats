package com.ryanemitchell.exception;

import javax.servlet.ServletException;

public class ClientException extends ServletException {

	private static final long serialVersionUID = 5434342386809673969L;

	public ClientException(String message) {
		super(message);
		//May want to add logging here
	}
}
