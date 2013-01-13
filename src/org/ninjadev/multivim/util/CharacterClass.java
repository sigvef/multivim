package org.ninjadev.multivim.util;

public enum CharacterClass {
	WHITESPACE(0), PUNCTUATION(1), KEYWORD(2);
	
	private final int ordinal;
	
	CharacterClass(int ordinal){
		this.ordinal = ordinal;
	}
	
	public int getOrdinal(){
		return ordinal;
	}
}
