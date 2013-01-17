package org.ninjadev.multivim.util;

public class StringUtils {
	
	public static boolean isWhitespace(String s){
		if(s == null) return true;
		for(char ch : s.toCharArray()){
			if(!Character.isWhitespace(ch)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isKeywordCharacter(String s){
		if(s == null) return false;
		return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".contains(s);
	}
	
	public static CharacterClass getCharacterClass(String s){
		if(isWhitespace(s)){
			return CharacterClass.WHITESPACE;
		}
		if(isKeywordCharacter(s)){
			return CharacterClass.KEYWORD;
		}
		return CharacterClass.PUNCTUATION;
	}

	public static CharacterClass getCharacterClass(String s, boolean bigWord) {
		if(!bigWord){
			return getCharacterClass(s);
		}
		if(isWhitespace(s)){
			return CharacterClass.WHITESPACE;
		}
		return CharacterClass.PUNCTUATION;
	}

	public static String repeat(String string, int columns) {
		StringBuilder sb = new StringBuilder();
		while(columns --> 0){
			sb.append(string);
		}
		return sb.toString();
	}
}
