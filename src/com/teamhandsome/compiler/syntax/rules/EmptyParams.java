package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class EmptyParams extends SyntaxRule{

	private static final int SIZE = 2;
	
	public EmptyParams() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= SIZE && 
				nodes.get(nodes.size() - 1).getType() == NodeType.SYMBOL && 
				nodes.get(nodes.size() - 1).getToken().getValue().equals(")") && 
				nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL &&
				nodes.get(nodes.size() - 2).getToken().getValue().equals("(");
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.PARAMETER, SIZE, nodes);
	}

}
