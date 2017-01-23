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
	private final int ASCII_NUMBER_START = 48;
	private final int ASCII_NUMBER_END = 57;
	private final int PERIOD = 46;
	private final int QUOTE = 34;
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
				if(isNewLine(c)){
					state = TokenType.SPACE;
				}else{
					switchOnState(c, chars, buffer, tokens);
				}
				index++;
			}else{
				state = TokenType.END;
			}
		}
		return tokens;
	}

	private void switchOnState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		switch(state){
		case ESCAPE:
			break;
		case NAME:
			nameState(c, chars, buffer, tokens);
			break;
		case NUMBER:
			numberState(c, chars, buffer, tokens);
			break;
		case SPACE:
			spaceState(c, chars, buffer, tokens);
			break;
		case STRING:
			stringState(c, chars, buffer, tokens);
			break;
		case SYMBOL:
			symbolState(c, chars, buffer, tokens);
			break;
		default:
			break;
		}
	}

	private void addTokenToList(CharBuffer buffer, List<Token> tokens){
		if(!buffer.isEmpty()){
			tokens.add(toToken(state, buffer));
			buffer.clear();
		}
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
		if(index < chars.length - 1){
			c = chars[index + 1];
		}
		return c;
	}
	
	private void skip(char[] chars){
		if(index < chars.length - 1){
			index ++;
			state = TokenType.SPACE;
		}
	}

	private boolean isLetter(char c){
		//65 - 90 || 97 - 122
		return c >= LETTER_START_ONE && c <= LETTER_END_ONE || c >= LETTER_START_TWO && c<= LETTER_END_TWO;
	}

	private boolean isNumber(char c){
		// 48 - 57
		return c >= ASCII_NUMBER_START && c <= ASCII_NUMBER_END;
	}

	private boolean isSymbol(char c){
		return c >= ASCII_SYMBOL_ONE && c <= ASCII_SYMBOL_TWO || 
				c >= ASCII_SYMBOL_THREE && c <= ASCII_SYMBOL_FOUR || 
				c >= ASCII_SYMBOL_FIVE && c <= ASCII_SYMBOL_SIX || 
				c >= ASCII_SYMBOL_SEVEN && c <= ASCII_SYMBOL_EIGHT;
	}
	
	private boolean isQuote(char c){
		return c == QUOTE;
	}
	
	private boolean isPeriod(char c){
		return c == PERIOD;
	}

	private boolean isSpace(char c){
		return c == SPACE || c == TAB || isNewLine(c);
	}

	private boolean isNewLine(char c) {
		return c == NEW_LINE;
	}
	
	private void stringState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(state == TokenType.STRING){
			buffer.addChar(c);
			if(isQuote(nextChar(chars))){
				addTokenToList(buffer, tokens);
				skip(chars);
			}
		}
	}

	private void nameState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isLetter(c)){
			buffer.addChar(c);
			if(isSpace(nextChar(chars))){
				addTokenToList(buffer, tokens);
				state = TokenType.SPACE;
			}
			else if(isSymbol(nextChar(chars))){
				addTokenToList(buffer, tokens);
				state = TokenType.SYMBOL;
			}
			else if(isNumber(nextChar(chars))){
				addTokenToList(buffer, tokens);
				state = TokenType.NUMBER;
			}
		}
	}

	private void spaceState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isLetter(c)){
			state = TokenType.NAME;
			nameState(c, chars, buffer, tokens);
		}
		else if(isSymbol(c)){
			state = TokenType.SYMBOL;
			symbolState(c, chars, buffer, tokens);
		}
		else if(isNumber(c)){
			state = TokenType.NUMBER;
			numberState(c, chars, buffer, tokens);
		}
	}

	private void symbolState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isSymbol(c)){
			if(isQuote(c)){
				state = TokenType.STRING;
			}else{
				buffer.addChar(c);
				tokens.add(toToken(state, buffer));
				buffer.clear();
				if(isLetter(nextChar(chars))){
					state = TokenType.NAME;
					addTokenToList(buffer, tokens);
				}
				else if(isSpace(nextChar(chars))){
					addTokenToList(buffer, tokens);
					state = TokenType.SPACE;
				}
				else if(isNumber(nextChar(chars))){
					addTokenToList(buffer, tokens);
					state = TokenType.NUMBER;
				}	
			}
		}
	}

	private void numberState(char c, char[] chars, CharBuffer buffer, List<Token> tokens){
		if(isNumber(c)){
			if(isSpace(nextChar(chars))){
				buffer.addChar(c);
				addTokenToList(buffer, tokens);
				state = TokenType.SPACE;
			}
			else if(isLetter(nextChar(chars))){
				buffer.addChar(c);
				addTokenToList(buffer, tokens);
				state = TokenType.NAME;
			}
			else if(isSymbol(nextChar(chars))){
				if(isPeriod(nextChar(chars))){
					buffer.addChar(c);
				}else{
					buffer.addChar(c);
					addTokenToList(buffer, tokens);
					state = TokenType.SYMBOL;	
				}
			}else if(isNumber(nextChar(chars))){	
				buffer.addChar(c);
			}
		}else if(isPeriod(c)){
			buffer.addChar(c);
		}
	}
}