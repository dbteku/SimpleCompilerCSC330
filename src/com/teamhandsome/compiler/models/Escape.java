package com.teamhandsome.compiler.models;

import com.teamhandsome.compiler.interfaces.ITokenType;

public class Escape implements ITokenType{
	
	public Escape() {
		
	}
	
	@Override
	public boolean isType(char[] chars) {
		return false;
	}

}
