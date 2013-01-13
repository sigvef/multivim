package org.ninjadev.multivim.movement;

import java.io.IOException;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;
import org.ninjadev.multivim.util.CharacterClass;
import org.ninjadev.multivim.util.StringUtils;

public class BackwardWord implements IMovement{

	@Override
	public boolean move(Cursor cursor, int count, AbstractOperator operator) throws IOException {
		operator.before();
		Cursor operatorCursor = new Cursor(cursor.buffer, cursor.user);
		operatorCursor.set(cursor);
		Buffer buffer = cursor.buffer;
		User user = cursor.user;
		
		boolean bigWord = false;
		boolean stop = false;
			
		counterLoop:
		while(count --> 0){
			CharacterClass startClass = StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord);
			if(operatorCursor.backward() == -1){
				return false;
			}
			
			if(!stop || startClass == StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord) || startClass == CharacterClass.WHITESPACE){
				while(StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord) == CharacterClass.WHITESPACE){
					if(operatorCursor.data.position.column == 0 && buffer.lines.get(operatorCursor.data.position.row).length() == 0){
						stop = false;
						continue counterLoop;
					}
					
					if(operatorCursor.backward() == -1){
						return true;
					}
				}
				
				CharacterClass cc = StringUtils.getCharacterClass(operatorCursor.getCharacter(), bigWord);
				while(StringUtils.getCharacterClass(operatorCursor.getCharacter(),bigWord) == cc){
					if(operatorCursor.backward() == -1) return true;
				}
			}
			operatorCursor.forward();
		}
		operator.after(cursor, operatorCursor);
		user.setOperator(new Movement());
		return true;
	}
}
