package com.teamhandsome.compiler.interfaces;

import com.teamhandsome.compiler.models.TokenState;

public interface ITokenType {

	boolean isType(char[] chars, TokenState state);
	
}
