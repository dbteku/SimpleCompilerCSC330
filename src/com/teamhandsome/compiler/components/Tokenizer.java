package com.teamhandsome.compiler.components;

import java.util.ArrayList;
import java.util.List;

import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenState;

public class Tokenizer {

	private final int CHAR_START = 65;
	private final int CHAR_END = 122;
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
				switchOnState(state, chars, index);
				index++;
			}else{
				state = TokenState.END;
			}
		}
		return tokens;
	}
	
	public boolean isLetter(char c){
		//65 - 122
		return c >= CHAR_START && c <= CHAR_END;
	}
	
	public TokenState switchOnState(TokenState state, char[] chars, int index){
		System.out.println("Char: " + chars[index]);
		TokenState newState = TokenState.SPACE;
		switch(state){
		case END:
			break;
		case ESCAPE:
			break;
		case NAME:
			break;
		case NUMBER:
			break;
		case SPACE:
			if(isLetter(chars[index])){
				System.out.println("HERE");
				newState = TokenState.NAME;
			}
			break;
		case STRING:
			break;
		case TOKEN:
			break;
		default:
			break;
		}
		return newState;
	}
	
	
}
