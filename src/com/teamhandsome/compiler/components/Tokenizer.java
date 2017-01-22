package com.teamhandsome.compiler.components;

import java.util.ArrayList;
import java.util.List;

import com.teamhandsome.compiler.models.CharBuffer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenType;

public class Tokenizer {

	private final int LETTER_START_ONE = 65;
	private final int LETTER_END_ONE = 90;
	private final int LETTER_START_TWO = 97;
	private final int LETTER_END_TWO = 122;
	private final int ASCII_SYMBOL_ONE = 33;
	private final int ASCII_SYMBOL_TWO = 47;
	private final int ASCII_SYMBOL_THREE = 58;
	private final int ASCII_SYMBOL_FOUR = 64;
	private final int ASCII_SYMBOL_FIVE = 91;
	private final int ASCII_SYMBOL_SIX = 96;
	private final int ASCII_SYMBOL_SEVEN = 123;
	private final int ASCII_SYMBOL_EIGHT = 126;
	private final int SPACE = 32;
	private final int NEW_LINE = 10;
	private final int TAB = 11;
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
				char c = chars[index];
				if(!isNewLine(c)){
				//	System.out.println(c + 0);
					switch(state){
					case END:
						break;
					case ESCAPE:
						break;
					case NAME:
						nameState(c, chars, buffer, tokens);
						break;
					case NUMBER:
						break;
					case SPACE:
						spaceState(c, chars, buffer, tokens);
						break;
					case STRING:
						break;
					case SYMBOL:
						symbolState(c, chars, buffer, tokens);
						break;
					case TOKEN:
						break;
					default:
						break;
					}
				}else{
					state = TokenType.SPACE;
				}
				index++;
			}else{
				state = TokenType.END;
			}
		}
		return tokens;
	}

	private Token toToken(TokenType state, CharBuffer buffer){
		Token token = new Token("", TokenType.SPACE);
		if(!buffer.isEmpty()){
			token = new Token(buffer.toString(), state);
		}
		return token;
	}

	private char nextChar(char[] chars){
		char c = 0;
		if(index < chars.length){
			c = chars[index + 1];
		}
		return c;
	}

	private boolean isLetter(char c){
		//65 - 90 || 97 - 122
		return c >= LETTER_START_ONE && c <= LETTER_END_ONE || c >= LETTER_START_TWO && c<= LETTER_END_TWO;
	}

	private boolean isSymbol(char c){
		return c >= ASCII_SYMBOL_ONE && c <= ASCII_SYMBOL_TWO || 
				c >= ASCII_SYMBOL_THREE && c <= ASCII_SYMBOL_FOUR || 
				c >= ASCII_SYMBOL_FIVE && c <= ASCII_SYMBOL_SIX || 
				c >= ASCII_SYMBOL_SEVEN && c <= ASCII_SYMBOL_EIGHT;
	}

	private boolean isSpace(char c){
		return c == SPACE || c == TAB || isNewLine(c);
	}
	
	private boolean isNewLine(char c) {
		return c == NEW_LINE;
	}

	private void nameState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isLetter(c)){
			buffer.addChar(c);
			if(isSpace(nextChar(chars))){
				tokens.add(toToken(state, buffer));
				buffer.clear();
				state = TokenType.SPACE;
			}
			else if(isSymbol(nextChar(chars))){
				tokens.add(toToken(state, buffer));
				buffer.clear();
				state = TokenType.SYMBOL;
			}
		}
	}

	private void spaceState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isLetter(c)){
			buffer.addChar(c);
			state = TokenType.NAME;
			if(isSpace(nextChar(chars))){
				tokens.add(toToken(state, buffer));
				buffer.clear();
				state = TokenType.SPACE;
			}
			else if(isSymbol(nextChar(chars))){
				tokens.add(toToken(state, buffer));
				buffer.clear();
				state = TokenType.SYMBOL;
			}
		}
		else if(isSymbol(c)){
			buffer.addChar(c);
			state = TokenType.SYMBOL;
			tokens.add(toToken(state, buffer));
			buffer.clear();
		}
	}

	private void symbolState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isSymbol(c)){
			buffer.addChar(c);
			tokens.add(toToken(state, buffer));
			buffer.clear();
			if(isLetter(nextChar(chars))){
				state = TokenType.NAME;
			}
			else if(isSpace(nextChar(chars))){
				state = TokenType.SPACE;
			}
		}
	}
}
