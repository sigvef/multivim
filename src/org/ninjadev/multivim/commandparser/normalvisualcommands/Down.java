package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;
import org.ninjadev.multivim.server.Server;
import org.ninjadev.multivim.server.net.SendMessage;

import com.googlecode.lanterna.input.Key;

public class Down extends NormalVisualCommand{
	
	public Down(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		user.activeCursor().cursor_down(1);
	}
}
