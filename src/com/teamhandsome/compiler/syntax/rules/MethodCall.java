package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MethodCall extends SyntaxRule{

	private static final int SIZE = 2;
	
	public MethodCall() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 &&
				nodes.get(nodes.size() - 1).getType() == NodeType.PARAMETER &&
				nodes.get(nodes.size() - 2).getType() == NodeType.NAME;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.METHOD_CALL, SIZE, nodes);
	}

}
