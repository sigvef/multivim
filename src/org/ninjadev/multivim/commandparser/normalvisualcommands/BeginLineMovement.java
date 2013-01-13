package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;
import org.ninjadev.multivim.movement.IMovement;

public class BeginLineMovement implements IMovement{

	@Override
	public boolean move(Cursor cursor, int count, AbstractOperator operator) throws IOException {
		
		operator.before();
		
		Cursor operatorCursor = new Cursor(cursor.buffer, cursor.user);
		operatorCursor.set(cursor);
		
		while(operatorCursor.data.position.column != 0){
			operatorCursor.buffer.setChar(operator.transform(operatorCursor.getCharacter()), operatorCursor.data.position.row, operatorCursor.data.position.column);
			operatorCursor.backward();
		}
		
		operator.after(cursor, operatorCursor);
		cursor.user.setOperator(new Movement());
		return false;
	}

}
