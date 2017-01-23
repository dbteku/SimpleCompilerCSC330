package com.teamhandsome.compilter.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenType;

public class DeclarationTests {

	@Test
	public void simpleDeclaration() {
		String code = "int i = 0;";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("int", TokenType.NAME));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("=", TokenType.SYMBOL));
		expected.add(new Token("0", TokenType.NUMBER));
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
	
	@Test
	public void simpleDoubleDeclaration() {
		String code = "int a,b;";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("int", TokenType.NAME));
		expected.add(new Token("a", TokenType.NAME));
		expected.add(new Token(",", TokenType.SYMBOL));
		expected.add(new Token("b", TokenType.NAME));
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
	
	@Test
	public void simpleString() {
		String code = "String s = \"Hello\";";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("String", TokenType.NAME));
		expected.add(new Token("s", TokenType.NAME));
		expected.add(new Token("=", TokenType.SYMBOL));
		expected.add(new Token("Hello", TokenType.STRING));
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
