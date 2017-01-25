package com.teamhandsome.compiler.models;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private List<TreeNode> children;

	public TreeNode() {
		children = new ArrayList<>();
	}

	public void addNode(TreeNode node){
		if(!children.contains(node)){
			this.children.add(node);
		}
	}	
	
	public void execute(){
		
	}
	
}
