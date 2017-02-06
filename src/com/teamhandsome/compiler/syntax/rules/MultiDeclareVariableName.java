package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MultiDeclareVariableName extends SyntaxRule{

	private static final int SIZE = 3;

	public MultiDeclareVariableName() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 3 && nodes.get(nodes.size() - 1).getType() == NodeType.VARIABLE_NAME
                && nodes.get(nodes.size() - 2).getType() == NodeType.TOKEN
                && nodes.get(nodes.size() - 2).getToken().getValue().equals(",")
                && nodes.get(nodes.size() - 3).getType() == NodeType.VARIABLE_NAME;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.VARIABLE_NAME, SIZE, nodes);
	}

}
