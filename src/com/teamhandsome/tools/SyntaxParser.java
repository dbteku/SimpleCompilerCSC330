package com.teamhandsome.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;
import com.teamhandsome.compiler.models.SyntaxTree;
import com.teamhandsome.compiler.models.Token;
import com.teamhandsome.grammar.KeyWord;
import com.teamhandsome.interfaces.IGrammar;

public class SyntaxParser {

	private final List<IGrammar> TYPES;
	private final List<IGrammar> KEYWORDS;
	private final List<IGrammar> OPERATORS;
	private final List<IGrammar> MODIFIERS;
	private final List<IGrammar> COMPARATORS;

	public SyntaxParser(){
		TYPES = Arrays.asList(new IGrammar[]{ new KeyWord("int"), new KeyWord("double"), new KeyWord("void"), new KeyWord("String")});
		KEYWORDS = Arrays.asList(new IGrammar[]{new KeyWord("while"), new KeyWord("for"), new KeyWord("if"), new KeyWord("else"), new KeyWord("return")});
		OPERATORS = Arrays.asList(new IGrammar[]{new KeyWord("+"), new KeyWord("-"), new KeyWord("*"), new KeyWord("/")});
		MODIFIERS = Arrays.asList(new IGrammar[]{new KeyWord("++"), new KeyWord("--"), new KeyWord("+="), new KeyWord("-=")});
		COMPARATORS = Arrays.asList(new IGrammar[]{new KeyWord("=="), new KeyWord("<="), new KeyWord(">="), new KeyWord("!="), new KeyWord(">"), new KeyWord("<")});
	}

	public SyntaxTree toTree(List<Token> tokens){
		SyntaxTree tree = new SyntaxTree();
		List<SyntaxNode> nodes = new ArrayList<>();
		SyntaxNode currentNode = new SyntaxNode(NodeType.NULL);
		SyntaxNode nextNode = new SyntaxNode(NodeType.NULL);
		for (int i = 0; i < tokens.size(); i++) {
			currentNode = convertNode(tokens.get(i));
			if(i < tokens.size() - 1){
				nextNode = convertNode(tokens.get(i + 1));
			}

		}

		return tree;
	}

	private SyntaxNode convertNode(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		switch(token.getType()){
		case NAME:
			node = convertNameToken(token);
			break;
		case NUMBER:
			node = convertSymbol(token);
			break;
		case SPACE:
			break;
		case STRING:
			break;
		case SYMBOL:
			break;
		default:
			break;
		}
		return node;
	}
	
	private SyntaxNode convertNameToken(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		if(isType(token.getValue())){
			node = new SyntaxNode(NodeType.TYPE, token);
		}
		else if(isKeyWord(token.getValue())){
			node = new SyntaxNode(NodeType.KEYWORD, token);
		}else{
			node = new SyntaxNode(NodeType.NAME, token);
		}
		return node;
	}
	
	private SyntaxNode convertSymbol(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		if(isOperator(token.getValue())){
			
		}
		else if(token.getValue().equalsIgnoreCase("=")){
			
		}
        else if (token.getValue().equals(";")){

        }
        else if (isModifiedAssignment(token.getValue()))
        {

        }
        else if (isComparator(token.getValue()))
        {

        }
        else
        {

        }
		return node;
	}

	private boolean isType(String value){
		boolean isGrammar = false;
		for (int i = 0; i < TYPES.size(); i++) {
			IGrammar word = TYPES.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = TYPES.size();
			}
		}
		return isGrammar;
	}
	
	private boolean isKeyWord(String value){
		boolean isGrammar = false;
		for (int i = 0; i < KEYWORDS.size(); i++) {
			IGrammar word = KEYWORDS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = KEYWORDS.size();
			}
		}
		return isGrammar;
	}
	
	private boolean isOperator(String value){
		boolean isGrammar = false;
		for (int i = 0; i < OPERATORS.size(); i++) {
			IGrammar word = OPERATORS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = OPERATORS.size();
			}
		}
		return isGrammar;
	}
	
	private boolean isComparator(String value) {
		boolean isGrammar = false;
		for (int i = 0; i < COMPARATORS.size(); i++) {
			IGrammar word = COMPARATORS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = COMPARATORS.size();
			}
		}
		return isGrammar;
	}

	private boolean isModifiedAssignment(String value) {
		boolean isGrammar = false;
		for (int i = 0; i < MODIFIERS.size(); i++) {
			IGrammar word = MODIFIERS.get(i);
			isGrammar = word.isGrammar(value);
			if(isGrammar){
				i = MODIFIERS.size();
			}
		}
		return isGrammar;
	}

}
