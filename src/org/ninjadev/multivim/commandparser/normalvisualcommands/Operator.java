package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.commandparser.operators.Change;
import org.ninjadev.multivim.commandparser.operators.Delete;
import org.ninjadev.multivim.commandparser.operators.Yank;
import org.ninjadev.multivim.notimplemented.NotImplemented;
import org.ninjadev.multivim.server.Server;

import com.googlecode.lanterna.input.Key;

public class Operator extends NormalVisualCommand{
	
	public Operator(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		switch(commandKey.getCharacter()){
			case 'c':
				user.setOperator(new Change());
				break;
			case 'd':
				user.setOperator(new Delete());
				break;
			case 'y':
				user.setOperator(new Yank());
				break;
		}
	}
}
