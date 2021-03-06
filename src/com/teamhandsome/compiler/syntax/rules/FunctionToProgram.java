package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class FunctionToProgram extends SyntaxRule{

	private static final int SIZE = 1;
	
	public FunctionToProgram() {
		
	}
	
	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.get(nodes.size() - 1).getType() == NodeType.FUNCTION && next.getType() == NodeType.NULL;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
		return constructNode(NodeType.PROGRAM, SIZE, nodes);
	}

}
