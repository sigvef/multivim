package org.ninjadev.multivim.movement;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;

public class LeftMovement implements IMovement {

	@Override
	public boolean move(Cursor cursor, int count, AbstractOperator operator) throws IOException {
		operator.before();
		Cursor operatorCursor = new Cursor(cursor.buffer, cursor.user);
		operatorCursor.set(cursor);
		
		if(operatorCursor.data.position.column != 0){
			operatorCursor.buffer.setChar(operator.transform(operatorCursor.getCharacter()), operatorCursor.data.position.row, operatorCursor.data.position.column);
			operatorCursor.data.position.column--;
			operatorCursor.desiredColumn = operatorCursor.data.position.column;
		}
		operator.after(cursor, operatorCursor);
		cursor.user.setOperator(new Movement());
		return true;
	}

}
