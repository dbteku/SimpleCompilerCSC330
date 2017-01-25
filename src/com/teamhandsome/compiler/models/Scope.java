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
		if(!variables.containsKey(variable.getName())){
			variables.put(variable.getName(), variable);
		}
	}
	
	public void removeVariable(String variableName){
		if(variables.containsKey(variableName)){
			variables.remove(variableName);
		}
	}
	
}
