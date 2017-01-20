package com.teamhandsome.compiler.components;

import java.util.ArrayList;
import java.util.List;

import com.teamhandsome.compiler.models.CharBuffer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenType;

public class Tokenizer {

	private final int LETTER_START = 65;
	private final int LETTER_END = 122;
	private final int SPACE = 32;
	private TokenType state;
	private int index;
	
	public Tokenizer() {
		state = TokenType.SPACE;
		index = 0;
	}
	
	public List<Token> tokenize(char[] chars){
		List<Token> tokens = new ArrayList<>();
		CharBuffer buffer = new CharBuffer();
		while(state != TokenType.END){
			if(index < chars.length){
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
						buffer.addChar(chars[index]);
						state = TokenType.NAME;
						if(isSpace(nextChar(chars))){
							tokens.add(toToken(state, buffer));
							buffer.clear();
							state = TokenType.SPACE;
						}
					}
					break;
				case STRING:
					break;
				case TOKEN:
					break;
				default:
					break;
				}
				index++;
			}else{
				state = TokenType.END;
			}
		}
		return tokens;
	}
	
	public Token toToken(TokenType state, CharBuffer buffer){
		Token token = new Token("", TokenType.SPACE);
		if(!buffer.isEmpty()){
			token = new Token(buffer.toString(), state);
		}
		return token;
	}
	
	public char nextChar(char[] chars){
		char c = 0;
		if(index < chars.length){
			c = chars[index + 1];
		}
		return c;
	}
	
	public boolean isLetter(char c){
		//65 - 122
		return c >= LETTER_START && c <= LETTER_END;
	}
	
	public boolean isSpace(char c){
		return c == SPACE;
	}

	
}
