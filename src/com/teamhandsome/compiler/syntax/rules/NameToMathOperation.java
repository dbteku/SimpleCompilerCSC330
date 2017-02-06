package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class NameToMathOperation extends SyntaxRule{

	private static final int SIZE = 1;
	
	public NameToMathOperation() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.NAME 
				&& !next.getToken().getValue().equals("(") 
				&& !(nodes.get(nodes.size() - 2).getType() == NodeType.TOKEN
				&& nodes.get(nodes.size() - 2).getToken().getValue().equals(",")) 
				&& next.getType() != NodeType.INCREMENTOR 
				&& next.getType() != NodeType.EQUALS;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.MATH_OPERATION, SIZE, nodes);
	}

}
