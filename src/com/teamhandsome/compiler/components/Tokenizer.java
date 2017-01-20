package com.teamhandsome.compiler.components;

import java.util.ArrayList;
import java.util.List;

import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenState;

public class Tokenizer {

	private TokenState state;
	
	public Tokenizer() {
		state = TokenState.NON;
	}
	
	public List<Token> tokenize(char[] chars){
		List<Token> tokens = new ArrayList<>();
		
		return tokens;
	}
	
}
