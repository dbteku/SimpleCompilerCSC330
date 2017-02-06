package com.teamhandsome.interfaces;

import java.util.List;

import com.teamhandsome.compiler.models.SyntaxNode;

public interface IRule {

	boolean isRule(List<SyntaxNode> nodes, SyntaxNode next);
	SyntaxNode applyRule(List<SyntaxNode> nodes);
	
}
