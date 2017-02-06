package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class BodyToForLoop extends SyntaxRule{

	private static final int SIZE = 7;

	public BodyToForLoop() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 7 
				&& nodes.get(nodes.size() - 7).getType() == NodeType.KEYWORD 
				&& nodes.get(nodes.size() - 7).getToken().getValue().equals("for")
                && nodes.get(nodes.size() - 6).getType() == NodeType.SYMBOL
                &&nodes.get(nodes.size() - 6).getToken().getValue().equals("(")
                && nodes.get(nodes.size() - 5).getType() == NodeType.STATEMENT
                && nodes.get(nodes.size() - 4).getType() == NodeType.BINARY_COMP_STATEMENT
                && nodes.get(nodes.size() - 3).getType() == NodeType.MODIFIED_ASSIGNMENT
                && nodes.get(nodes.size() - 2).getType() == NodeType.SYMBOL 
                && nodes.get(nodes.size() - 2).getToken().getValue().equals(")")
                && nodes.get(nodes.size() - 1).getType() == NodeType.BODY;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.FORLOOP, SIZE, nodes);
	}

}
