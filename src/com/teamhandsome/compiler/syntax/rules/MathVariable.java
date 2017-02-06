package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MathVariable extends SyntaxRule{

	private static final int SIZE = 3;

	public MathVariable() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 3 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION
                && nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS 
                && nodes.get(nodes.size() - 3).getType() == NodeType.NAME 
                && next.getType() != NodeType.OPERATOR;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.VARIABLE, SIZE, nodes);
	}

}
