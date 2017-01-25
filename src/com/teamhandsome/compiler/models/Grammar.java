package com.teamhandsome.compiler.models;

public class Grammar {
	private GrammarType grammer;
	private String value;
	
	
	public Grammar(GrammarType grammer, String value){
		this.grammer = grammer;
		this.value = value;
	}


	public GrammarType getGrammer() {
		return grammer;
	}



	public String getValue() {
		return value;
	}


	

}
