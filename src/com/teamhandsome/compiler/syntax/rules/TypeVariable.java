package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class TypeVariable extends SyntaxRule{

	private static final int SIZE = 2;

	public TypeVariable() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.VARIABLE_NAME
                && nodes.get(nodes.size() - 2).getType() == NodeType.TYPE;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.VARIABLE, SIZE, nodes);
	}

}
