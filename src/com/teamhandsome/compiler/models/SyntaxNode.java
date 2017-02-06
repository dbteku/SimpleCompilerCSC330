package com.teamhandsome.compiler.models;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {

	private List<SyntaxNode> children;
	private NodeType type;
	private Token token;

	public SyntaxNode(NodeType type) {
		this(type, null);
	}

	public SyntaxNode(NodeType type, Token token){
		children = new ArrayList<>();
		this.type = type;
		this.token = token;
	}

	public void addNode(SyntaxNode node){
		if(!children.contains(node)){
			this.children.add(node);
		}
	}

	public Token getToken() {
		Token token = new Token("", TokenType.SPACE);
		if(this.token != null){
			token = this.token;
		}
		return token;
	}

	public NodeType getType() {
		return type;
	}

	public boolean isNull(){
		return type == NodeType.NULL;
	}

	@Override
	public String toString() {
		return toString(0);
	}

	private String toString(int indentLevel){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < indentLevel; i++)
		{
			builder.append("..");
		}

		builder.append(type.toString());
		builder.append(":");

		if (children.size() == 0){
			builder.append(token.toString());
			builder.append("\n");
		}
		else{
			builder.append("\n");
		}
		for (int i = 0; i < children.size(); i++)
		{
			builder.append(children.get(i).toString(indentLevel + 1));
		}

		return builder.toString();
	}

}
