package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class IfStatement extends SyntaxRule{

	private static final int SIZE = 1;

	public IfStatement() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.get(nodes.size() - 1).getType() == NodeType.IF &&
				!(next.getType() == NodeType.KEYWORD && next.getToken().getValue().equals("else"));
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.STATEMENT, SIZE, nodes);
	}

}
