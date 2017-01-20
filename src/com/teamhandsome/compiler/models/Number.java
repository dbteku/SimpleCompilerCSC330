package com.teamhandsome.compiler.models;

import com.teamhandsome.compiler.interfaces.ITokenType;

public class Number implements ITokenType{
	
	public Number() {
		
	}
	
	@Override
	public boolean isType(char[] chars) {
		return false;
	}

}
