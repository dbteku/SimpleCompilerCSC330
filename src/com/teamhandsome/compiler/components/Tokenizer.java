package com.teamhandsome.compiler.components;

import java.util.ArrayList;
import java.util.List;

import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenState;

public class Tokenizer {

	private final int LETTER_START = 65;
	private final int LETTER_END = 122;
	private TokenState state;
	private int index;
	
	public Tokenizer() {
		state = TokenState.SPACE;
		index = 0;
	}
	
	public List<Token> tokenize(char[] chars){
		List<Token> tokens = new ArrayList<>();
		while(state != TokenState.END){
			if(index < chars.length){
				// State switching.
				index++;
			}else{
				state = TokenState.END;
			}
		}
		return tokens;
	}
	
	public boolean isLetter(char c){
		//65 - 122
		return c >= LETTER_START && c <= LETTER_END;
	}
	
	
}
