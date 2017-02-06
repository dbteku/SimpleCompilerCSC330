package com.teamhandsome.exceptions;

public class InvalidSyntaxException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final String ERROR = "Invalid Syntax!";
	
	public InvalidSyntaxException() {
		super(ERROR);
	}

}
