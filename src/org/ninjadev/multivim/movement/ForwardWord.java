package org.ninjadev.multivim.movement;

import java.io.IOException;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;
import org.ninjadev.multivim.util.CharacterClass;
import org.ninjadev.multivim.util.StringUtils;

public class ForwardWord implements IMovement{

	@Override
	public boolean move(Cursor cursor, int count, AbstractOperator operator) throws IOException {
		operator.before();
		Cursor operatorCursor = new Cursor(cursor.buffer, cursor.user);
		operatorCursor.set(cursor);
		Buffer buffer = cursor.buffer;
		User user = cursor.user;
		
		boolean bigWord = false;
		boolean EOL = false;
		
		while(count --> 0){
			
			CharacterClass startClass = StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord);
			
			boolean lastLine = operatorCursor.data.position.row == (buffer.lines.size()-1);
			int i = operatorCursor.forward();
			if(i == -1 || i >= 1 && lastLine){
				return false;
			}
			if(i >= 1 && EOL && count == 0){
				return true;
			}
			
			if(startClass != CharacterClass.WHITESPACE){
				while(startClass == StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord)){
					i = operatorCursor.forward();
					if(i == -1 || (i >= 1 && EOL && count == 0)){
						return true;
					}
				}
			}
			
			while(StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord) == CharacterClass.WHITESPACE){
				if(operatorCursor.data.position.column == 0 && buffer.lines.get(operatorCursor.data.position.row).length() == 0){
					break;
				}
				
				i = operatorCursor.forward();
				if(i == -1 || ( i>=1  && EOL && count == 0)){
					return true;
				}
			}
		}
		operator.after(cursor, operatorCursor);
		user.setOperator(new Movement());
		return true;
	}
}
