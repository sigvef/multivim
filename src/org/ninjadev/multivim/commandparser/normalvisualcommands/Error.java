package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.util.EnumSet;

import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;

import com.googlecode.lanterna.input.Key;

public class Error extends NormalVisualCommand{
	
	public Error(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand() {
		NotImplemented.warn();
	}
}
