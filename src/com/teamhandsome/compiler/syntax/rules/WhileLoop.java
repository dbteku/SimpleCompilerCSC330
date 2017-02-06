package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class WhileLoop extends SyntaxRule{

	private static final int SIZE = 5;

	public WhileLoop() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 5 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.BODY 
				&& nodes.get(nodes.size() - 2).getType() == NodeType.TOKEN
				&& nodes.get(nodes.size() - 2).getToken().getValue().equals(")") 
				&& nodes.get(nodes.size() - 3).getType()== NodeType.BINARY_COMPARATOR 
				&& nodes.get(nodes.size() - 4).getType() == NodeType.TOKEN 
				&& nodes.get(nodes.size() - 4).getToken().getValue().equals("(") 
				&& nodes.get(nodes.size() - 5).getType() == NodeType.KEYWORD 
				&& nodes.get(nodes.size() - 5).getToken().getValue().equals("while");
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.WHILELOOP, SIZE, nodes);
	}

}
