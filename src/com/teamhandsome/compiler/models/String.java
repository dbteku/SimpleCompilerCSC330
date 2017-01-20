package com.teamhandsome.compiler.models;

import com.teamhandsome.compiler.interfaces.ITokenType;

public class String implements ITokenType{
	
	public String() {
		
	}
	
	@Override
	public boolean isType(char[] chars) {
		return false;
	}

}
