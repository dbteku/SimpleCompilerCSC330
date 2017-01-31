package com.teamhandsome.compiler.models;

public class SyntaxTree {

	private TreeNode root;
	
	public SyntaxTree() {
		root = null;
	}
	
	public void addNode(TreeNode node){
		if(root == null){
			root = node;
		}
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
}
