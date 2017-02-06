package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class TypedVariableName extends SyntaxRule{

	private static final int SIZE = 1;

	public TypedVariableName() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 2 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.NAME
                && nodes.get(nodes.size() - 2).getType() == NodeType.TYPE 
                && !(next.getType() == NodeType.TOKEN && next.getToken().getValue().equals("("));
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.VARIABLE_NAME, SIZE, nodes);
	}

}
