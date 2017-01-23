package com.teamhandsome.compiler.models;

public class Token {

	private String value;
	private TokenType type;
	
	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type + ": " + value;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		try {
			Token other = (Token) obj;
			equals = other.value.equalsIgnoreCase(value) && other.type == type;
		} catch (ClassCastException e) {
		}
		return equals;
	}
	
}
