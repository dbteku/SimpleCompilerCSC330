package com.teamhandsome.exceptions;

public class FileNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final String ERROR = "File not found. Is the path correct?";
	
	public FileNotFoundException() {
		super(ERROR);
	}

}
