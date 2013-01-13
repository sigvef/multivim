package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import com.googlecode.lanterna.input.Key;

public class Dollar extends NormalVisualCommand{
	
	public Dollar(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		new EndLineMovement().move(user.activeCursor(), 1, user.getOperator());
	};
}
