package com.teamhandsome.compiler.models;

import java.util.ArrayList;
import java.util.List;

public class CharBuffer {

	private List<Character> buffer;
	
	public CharBuffer() {
		buffer = new ArrayList<>();
	}
	
	public void addChar(char c){
		buffer.add(c);
	}
	
	public void removeLast(){
		if(!buffer.isEmpty()){
			buffer.remove(buffer.size() - 1);	
		}
	}
	
	public void clear(){
		buffer.clear();
	}
	
	public boolean isEmpty(){
		return buffer.isEmpty();
	}
	
	public char[] toCharArray(){
		char[] array = new char[buffer.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = buffer.get(i);
		}
		return array;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < buffer.size(); i++) {
			builder.append(buffer.get(i));
		}
		return builder.toString();
	}
	
}
