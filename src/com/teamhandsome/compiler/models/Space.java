package com.teamhandsome.compiler.models;

import com.teamhandsome.compiler.interfaces.ITokenType;

public class Space implements ITokenType{
	
	public Space() {
		
	}
	
	@Override
	public boolean isType(char[] chars) {
		return false;
	}

}
