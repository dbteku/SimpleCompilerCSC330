package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MathOperatorToMathOperation extends SyntaxRule{

	private static final int SIZE = 2;
	
	public MathOperatorToMathOperation() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return  nodes.size() >= 2 
				&& nodes.get(nodes.size() - 2).getType() == NodeType.OPERATOR 
				&& nodes.get(nodes.size() - 2).getToken().getValue().equals("-")
                && nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.MATH_OPERATION, SIZE, nodes);
	}

}
