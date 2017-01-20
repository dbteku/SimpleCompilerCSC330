package com.teamhandsome.compiler.interfaces;

import com.teamhandsome.compiler.models.TokenType;

public interface ITokenType {

	boolean isType(char[] chars, TokenType state);
	
}
