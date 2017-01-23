package com.teamhandsome.compilter.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenType;

public class LoopsTest {

	@Test
	public void forLoop() {
		String code = "for(int i = 0; i < 1; i++){}";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("for", TokenType.NAME));
		expected.add(new Token("(", TokenType.SYMBOL));
		expected.add(new Token("int", TokenType.NAME));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("=", TokenType.SYMBOL));
		expected.add(new Token("0", TokenType.NUMBER));
		expected.add(new Token(";", TokenType.SYMBOL));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("<", TokenType.SYMBOL));
		expected.add(new Token("1", TokenType.NUMBER));
		expected.add(new Token(";", TokenType.SYMBOL));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("+", TokenType.SYMBOL));
		expected.add(new Token("+", TokenType.SYMBOL));
		expected.add(new Token(")", TokenType.SYMBOL));
		expected.add(new Token("{", TokenType.SYMBOL));
		expected.add(new Token("}", TokenType.SYMBOL));
		boolean equals = false;
		
		equals = actual.size() == expected.size();
		
		if(equals){
			for (int i = 0; i < actual.size(); i++) {
				Token toCompare = actual.get(i);
				equals = toCompare.equals(expected.get(i));
				if(!equals){
					i = actual.size();
				}
			}
		}
		
		Assert.assertTrue(equals);
	}
	
	@Test
	public void whileLoop() {
		String code = "while(true){ }";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("while", TokenType.NAME));
		expected.add(new Token("(", TokenType.SYMBOL));
		expected.add(new Token("true", TokenType.NAME));
		expected.add(new Token(")", TokenType.SYMBOL));
		expected.add(new Token("{", TokenType.SYMBOL));
		expected.add(new Token("}", TokenType.SYMBOL));
		
		
		boolean equals = false;
		
		equals = actual.size() == expected.size();
		
		if(equals){
			for (int i = 0; i < actual.size(); i++) {
				Token toCompare = actual.get(i);
				equals = toCompare.equals(expected.get(i));
				if(!equals){
					i = actual.size();
				}
			}
		}
		
		Assert.assertTrue(equals);
	}
	
	@Test
	public void doWhile() {
		String code = "do{}while(true);";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("do", TokenType.NAME));
		expected.add(new Token("{", TokenType.SYMBOL));
		expected.add(new Token("}", TokenType.SYMBOL));
		expected.add(new Token("while", TokenType.NAME));
		expected.add(new Token("(", TokenType.SYMBOL));
		expected.add(new Token("true", TokenType.NAME));
		expected.add(new Token(")", TokenType.SYMBOL));
		expected.add(new Token(";", TokenType.SYMBOL));
		
		boolean equals = false;
		
		equals = actual.size() == expected.size();
		
		if(equals){
			for (int i = 0; i < actual.size(); i++) {
				Token toCompare = actual.get(i);
				equals = toCompare.equals(expected.get(i));
				if(!equals){
					i = actual.size();
				}
			}
		}
		
		Assert.assertTrue(equals);
	}

}
