package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class VariableStatement extends SyntaxRule{

	private static final int SIZE = 2;
	
	public VariableStatement() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 &&
				nodes.get(nodes.size() - 1).getType() == NodeType.SEMICOLON &&
				nodes.get(nodes.size() - 2).getType() == NodeType.VARIABLE;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.STATEMENT, SIZE, nodes);
	}

}
