package com.teamhandsome.compiler.models;

public class SyntaxTree {

	private SyntaxNode root;

	public SyntaxTree() {
		this(null);
	}
	
	public SyntaxTree(SyntaxNode root) {
		this.root = root;
	}
	
	public SyntaxNode getRoot() {
		return root;
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
	@Override
	public String toString() {
		return root.toString();
	}

}
