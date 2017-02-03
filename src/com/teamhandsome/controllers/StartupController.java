package com.teamhandsome.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.SyntaxTree;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.tools.FileReader;
import com.teamhandsome.tools.SyntaxParser;

public class StartupController {
	
	private Tokenizer tokenizer;
	private FileReader reader;
	
	public StartupController() {
		tokenizer = new Tokenizer();
		reader = new FileReader();
	}
	
	public void start(){
		try {
			String code = reader.readFileToString("squareRoot.cs2");
			List<Token> tokens = tokenizer.tokenize(code.toCharArray());
			SyntaxParser parser = new SyntaxParser();
			SyntaxTree tree = parser.toTree(tokens);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
