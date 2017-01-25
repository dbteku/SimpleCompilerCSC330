package com.teamhandsome.compiler.models;

public class Variable {

	private final String NAME;
	private final VariableType TYPE;
	private String value;
	
	public Variable(String name, VariableType type, String value) {
		this.NAME = name;
		this.TYPE = type;
		this.value = value;
	}
	
	public String getName() {
		return NAME;
	}
	
	public VariableType getType() {
		return TYPE;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
