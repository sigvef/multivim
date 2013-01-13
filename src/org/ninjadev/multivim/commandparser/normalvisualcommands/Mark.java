package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.util.EnumSet;

import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;

import com.googlecode.lanterna.input.Key;

public class Mark extends NormalVisualCommand{
	
	public Mark(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand() {
		NotImplemented.warn();
	}
}
