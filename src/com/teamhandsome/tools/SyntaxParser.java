package com.teamhandsome.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teamhandsome.compiler.models.SyntaxTree;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.grammar.KeyWord;
import com.teamhandsome.interfaces.IGrammar;

public class SyntaxParser {

	private final List<IGrammar> KEYWORDS;
	private final List<IGrammar> GRAMMAR;
	
    public SyntaxParser(){
    	KEYWORDS = Arrays.asList(new IGrammar[]{new KeyWord("for"), new KeyWord("void"), new KeyWord("int"), new KeyWord("double"), new KeyWord("return")});
    	GRAMMAR = new ArrayList<>();
    	GRAMMAR.addAll(KEYWORDS);
    }

    public SyntaxTree toTree(List<Token> tokens){
    	SyntaxTree tree = new SyntaxTree();
    	
    	return tree;
    }
    
}
