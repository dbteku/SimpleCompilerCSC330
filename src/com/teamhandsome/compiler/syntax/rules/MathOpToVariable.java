package com.teamhandsome.compiler.syntax.rules;

import java.util.List;

import com.teamhandsome.compiler.models.NodeType;
import com.teamhandsome.compiler.models.SyntaxNode;

public class MathOpToVariable extends SyntaxRule{

	private static final int SIZE = 3;

	public MathOpToVariable() {

	}

	@Override
	public boolean isRule(List<SyntaxNode> nodes, SyntaxNode next) {
		return nodes.size() >= 3 
				&& nodes.get(nodes.size() - 3).getType() == NodeType.VARIABLE 
				&& nodes.get(nodes.size() - 2).getType() == NodeType.EQUALS 
				&& nodes.get(nodes.size() - 1).getType() == NodeType.MATH_OPERATION;
	}

	@Override
	public SyntaxNode applyRule(List<SyntaxNode> nodes) {
        NodeType type = NodeType.NULL;
        if (nodes.get(nodes.size() - 4).getType() == NodeType.TYPE)
        {
            type = NodeType.VARIABLE_NAME;
        }
        else
        {
            type = NodeType.VARIABLE;
        }

		return constructNode(type, SIZE, nodes);
	}

}
