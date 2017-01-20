package com.teamhandsome.compiler.models;

import com.teamhandsome.compiler.interfaces.ITokenType;

public class Name implements ITokenType{
	
	public Name() {
		
	}
	
	@Override
	public boolean isType(char[] chars) {
		return false;
	}

}
