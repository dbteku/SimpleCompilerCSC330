package com.teamhandsome.compilter.tests;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.tools.FileReader;

public class OldToNewTests {

	@Test
	public void simpleTest() {
		FileReader reader = new  FileReader();
		String code;
		try {
			code = reader.readFileToString("squareRoot.cs2");
			Tokenizer tokenizer = new Tokenizer();
			List<Token> actual = tokenizer.tokenize(code.toCharArray());
			boolean equals = false;
			String actualString = actual.toString();
			String expectedString = "[NAME: void, NAME: Main, SYMBOL: (, NAME: string, SYMBOL: [, SYMBOL: ], NAME: args, SYMBOL: ), SYMBOL: {, NAME: RunSqrt, SYMBOL: (, SYMBOL: ), SYMBOL: ;, SYMBOL: }, NAME: private, NAME: void, NAME: RunSqrt, SYMBOL: (, SYMBOL: ), SYMBOL: {, NAME: double, NAME: A, SYMBOL: ,, NAME: B, SYMBOL: ;, NAME: double, NAME: E, SYMBOL: =, NUMBER: 0.01, SYMBOL: ;, NAME: for, SYMBOL: (, NAME: int, NAME: i, SYMBOL: =, NUMBER: 0, SYMBOL: ;, NAME: i, SYMBOL: <, SYMBOL: =, NUMBER: 100, SYMBOL: ;, NAME: i, SYMBOL: +, SYMBOL: +, SYMBOL: ), SYMBOL: {, NAME: if, SYMBOL: (, NAME: i, SYMBOL: <, NUMBER: 2, SYMBOL: ), SYMBOL: {, NAME: B, SYMBOL: =, NAME: i, SYMBOL: ;, SYMBOL: }, NAME: else, SYMBOL: {, NAME: A, SYMBOL: =, SYMBOL: (, NUMBER: 0.5, SYMBOL: *, NAME: i, SYMBOL: ), SYMBOL: ;, NAME: B, SYMBOL: =, SYMBOL: (, NAME: A, SYMBOL: *, NAME: A, SYMBOL: +, NAME: i, SYMBOL: ), SYMBOL: /, SYMBOL: (, NUMBER: 2, SYMBOL: *, NAME: A, SYMBOL: ), SYMBOL: ;, NAME: while, SYMBOL: (, NAME: Abs, SYMBOL: (, NAME: A, SYMBOL: -, NAME: B, SYMBOL: ), SYMBOL: >, NAME: E, SYMBOL: ), SYMBOL: {, NAME: A, SYMBOL: =, NAME: B, SYMBOL: ;, NAME: B, SYMBOL: =, SYMBOL: (, NAME: A, SYMBOL: *, NAME: A, SYMBOL: +, NAME: i, SYMBOL: ), SYMBOL: /, SYMBOL: (, NUMBER: 2, SYMBOL: *, NAME: A, SYMBOL: ), SYMBOL: ;, SYMBOL: }, SYMBOL: }, NAME: WriteLine, SYMBOL: (, NAME: B, SYMBOL: ), SYMBOL: ;, SYMBOL: }, SYMBOL: }, NAME: public, NAME: double, NAME: Abs, SYMBOL: (, NAME: double, NAME: input, SYMBOL: ), SYMBOL: {, NAME: double, NAME: toReturn, SYMBOL: =, NAME: input, SYMBOL: ;, NAME: if, SYMBOL: (, NAME: input, SYMBOL: <, NUMBER: 0, SYMBOL: ), SYMBOL: {, NAME: toReturn, SYMBOL: =, SYMBOL: -, NAME: input, SYMBOL: ;, SYMBOL: }, NAME: return, NAME: toReturn, SYMBOL: ;, SYMBOL: }]";
			equals = actualString.equals(expectedString);	
			Assert.assertTrue(equals);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
