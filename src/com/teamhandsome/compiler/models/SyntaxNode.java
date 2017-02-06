package com.teamhandsome.compiler.models;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {

	private List<SyntaxNode> children;
	private NodeType type;
	
	public SyntaxNode(NodeType type) {
		children = new ArrayList<>();
	}

	public void addNode(SyntaxNode node){
		if(!children.contains(node)){
			this.children.add(node);
		}
	}
	
	public NodeType getType() {
		return type;
	}
	
	public boolean isNull(){
		return type == NodeType.NULL;
	}
	
}
