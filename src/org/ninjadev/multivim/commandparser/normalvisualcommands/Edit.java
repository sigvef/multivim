package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.Mode;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;

import com.googlecode.lanterna.input.Key;

public class Edit extends NormalVisualCommand{
	
	public Edit(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		user.setMode(Mode.INSERT);
		
		switch(commandKey.getCharacter()){
		case 'a':
			user.activeCursor().forward();
			break;
		case 'I':
			break;
		case 'A':
			break;
		}
	}
}
