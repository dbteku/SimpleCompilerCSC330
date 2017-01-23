package com.teamhandsome.compilter.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.compiler.models.TokenType;

public class FunctionTests {

	@Test
	public void simpleFunction() {
		String code = "void testMethod(int i){\n int number = 0; i = i + 1; }";
		Tokenizer tokenizer = new Tokenizer();
		List<Token> actual = tokenizer.tokenize(code.toCharArray());
		List<Token> expected = new ArrayList<>();
		expected.add(new Token("void", TokenType.NAME));
		expected.add(new Token("testMethod", TokenType.NAME));
		expected.add(new Token("(", TokenType.SYMBOL));
		expected.add(new Token("int", TokenType.NAME));
		expected.add(new Token("number", TokenType.NAME));
		expected.add(new Token(")", TokenType.SYMBOL));
		expected.add(new Token("{", TokenType.SYMBOL));
		expected.add(new Token("int", TokenType.NAME));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("=", TokenType.SYMBOL));
		expected.add(new Token("0", TokenType.NUMBER));
		expected.add(new Token(";", TokenType.SYMBOL));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("=", TokenType.SYMBOL));
		expected.add(new Token("i", TokenType.NAME));
		expected.add(new Token("+", TokenType.SYMBOL));
		expected.add(new Token("1", TokenType.NUMBER));
		expected.add(new Token(";", TokenType.SYMBOL));
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

}
