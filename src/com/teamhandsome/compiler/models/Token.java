package com.teamhandsome.compiler.models;

public class Token {

	private String value;
	private TokenType type;
	
	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;
	}
	
}
