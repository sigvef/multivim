package org.ninjadev.multivim.movement;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;

public interface IMovement {
	public boolean move(Cursor cursor, int count, AbstractOperator operator) throws IOException;
}
