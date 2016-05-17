package com.ryanemitchell.exception;

import javax.servlet.ServletException;

public class ServerException extends ServletException {

	private static final long serialVersionUID = 2337115601945324055L;

	public ServerException(Throwable e) {
		super("A server error occurred. Please contact ryan.e.mitchell@gmail.com");
		System.out.println("Exception occurred: "+e.getMessage());
		e.printStackTrace();
		//Can add logging here
		
	}
}
