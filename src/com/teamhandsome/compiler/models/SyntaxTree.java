package com.teamhandsome.compiler.models;

public class SyntaxTree {

	private SyntaxNode root;

	public SyntaxTree() {
		root = null;
	}

	public void setRoot(SyntaxNode root) {
		this.root = root;
	}
	
	public SyntaxNode getRoot() {
		return root;
	}

}
