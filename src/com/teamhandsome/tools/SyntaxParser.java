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

	private final List<IGrammar> KEYWORDS;
	private final List<IGrammar> GRAMMAR;

	public SyntaxParser(){
		KEYWORDS = Arrays.asList(new IGrammar[]{new KeyWord("for"), new KeyWord("void"), new KeyWord("int"), new KeyWord("double"), new KeyWord("return")});
		GRAMMAR = new ArrayList<>();
		GRAMMAR.addAll(KEYWORDS);
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
			break;
		case NUMBER:
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
	
	private SyntaxNode nameToken(Token token){
		SyntaxNode node = new SyntaxNode(NodeType.NULL);
		return node;
	}

}
