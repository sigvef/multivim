package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.util.EnumSet;

import org.ninjadev.multivim.commandparser.NormalVisualFlag;

import com.googlecode.lanterna.input.Key;

public class Redo extends NormalVisualCommand{

	public Redo(Key commandKey, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandKey, flags, arg);
	}

}
