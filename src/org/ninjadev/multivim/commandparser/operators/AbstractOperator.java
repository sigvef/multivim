package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public abstract class AbstractOperator {
	Key commandKey;
	Key secondCommandKey;
	boolean alwaysWorksOnLines;
	
	public abstract void before();
	public abstract String transform(String character);
	public abstract void after(Cursor cursor, Cursor operatorCursor) throws IOException;
}
