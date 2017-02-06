package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class ReturnStatement extends SyntaxRule{

	private static final int SIZE = 3;
	
	public ReturnStatement() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 3 &&
				nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON &&
				nodes.get(nodes.size() - 2).getType() == NodeType.MATH_OPERATION &&
				nodes.get(nodes.size() - 3).getType() == NodeType.KEYWORD &&
				nodes.get(nodes.size() - 3).getToken().getValue().equals("return");
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.STATEMENT, SIZE, nodes);
	}

}
