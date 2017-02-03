package com.teamhandsome.grammar;

import com.teamhandsome.interfaces.IGrammar;

public class KeyWord implements IGrammar {

    private final String WORD;

    public KeyWord(String word) {
        this.WORD = word;
    }

    @Override
    public boolean isGrammar(String word) {
        return WORD.equalsIgnoreCase(word);
    }

	@Override
	public IGrammar toGrammar(String word) {
		return new KeyWord(word);
	}
}
