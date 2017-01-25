package com.teamhandsome.compiler.models;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	private Map<String, Variable> variables;
	
	public Scope() {
		variables = new HashMap<>();
	}
	
	public boolean containsVariable(String variableName){
		return variables.containsKey(variableName);
	}
	
	public void addVariable(Variable variable){
		
	}
	
	public void removeVariable(String variableName){
		
	}
	
}
