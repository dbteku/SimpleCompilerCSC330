package com.teamhandsome.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import com.teamhandsome.compiler.components.Tokenizer;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.tools.FileReader;

public class StartupController {
	
	private Tokenizer tokenizer;
	private FileReader reader;
	
	public StartupController() {
		tokenizer = new Tokenizer();
		reader = new FileReader();
	}
	
	public void start(){
		try {
			String code = reader.readFileToString("sample.cs2");
			List<Token> tokens = tokenizer.tokenize(code.toCharArray());
			System.out.println(tokens);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
