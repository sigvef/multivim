package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;

import com.googlecode.lanterna.input.Key;

public class BeginLine extends NormalVisualCommand{
	
	public BeginLine(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		new BeginLineMovement().move(user.activeCursor(), 1, user.getOperator());
	}
}
