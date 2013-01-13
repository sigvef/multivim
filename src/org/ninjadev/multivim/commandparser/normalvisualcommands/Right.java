package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.movement.RightMovement;

import com.googlecode.lanterna.input.Key;

public class Right extends NormalVisualCommand{
	
	public Right(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		new RightMovement().move(user.activeCursor(), 1, user.operator);
	}
}