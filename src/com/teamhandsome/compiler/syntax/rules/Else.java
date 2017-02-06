package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class Else extends SyntaxRule{

	private static final int SIZE = 2;

	public Else() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 
				&& nodes.get(nodes.size() - 2).getType() == NodeType.KEYWORD 
				&& nodes.get(nodes.size() - 2).getToken().getValue().equals("else")
				&& nodes.get(nodes.size() - 1).getType() == NodeType.BODY;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.ELSE, SIZE, nodes);
	}

}
