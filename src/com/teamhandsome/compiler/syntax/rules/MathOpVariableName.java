package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MathOpVariableName extends SyntaxRule{

	private static final int SIZE = 3;

	public MathOpVariableName() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 4 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION
	            && nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS 
	            && nodes.get(nodes.size() - 3).getType() == NodeType.NAME
	            && nodes.get(nodes.size() - 4).getType() == NodeType.TYPE;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.VARIABLE_NAME, SIZE, nodes);
	}

}
