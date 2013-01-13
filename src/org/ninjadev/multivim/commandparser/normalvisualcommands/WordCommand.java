package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.movement.BackwardBigWord;
import org.ninjadev.multivim.movement.BackwardWord;
import org.ninjadev.multivim.movement.ForwardBigWord;
import org.ninjadev.multivim.movement.ForwardWord;

import com.googlecode.lanterna.input.Key;

public class WordCommand extends NormalVisualCommand{
	
	public WordCommand(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		Cursor cursor = user.cursors.get(user.activeViewPort.getBuffer().bufferId);
		switch(commandKey.getCharacter()){
			case 'w':
				new ForwardWord().move(cursor, 1, user.getOperator());
				break;
			case 'b':
				new BackwardWord().move(cursor, 1, user.getOperator());
				break;
			case 'W':
				new ForwardBigWord().move(cursor, 1, user.getOperator());
				break;
			case 'B':
				new BackwardBigWord().move(cursor, 1, user.getOperator());
				break;
			default:
				break;
		}
	}
}
