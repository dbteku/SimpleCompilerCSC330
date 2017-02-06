package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MathOperationToBinaryComparison extends SyntaxRule{

	private static final int SIZE = 3;

	public MathOperationToBinaryComparison() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 3 &&
				nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION &&
				nodes.get(nodes.size() - 2).getType() == NodeType.COMPARATOR &&
				nodes.get(nodes.size() - 3).getType() == NodeType.MATH_OPERATION;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.BINARY_COMPARATOR, SIZE, nodes);
	}

}
