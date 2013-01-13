package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.util.EnumSet;

import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.commandparser.operators.Lower;
import org.ninjadev.multivim.commandparser.operators.Upper;
import org.ninjadev.multivim.notimplemented.NotImplemented;

import com.googlecode.lanterna.input.Key;

public class GCommand extends NormalVisualCommand{
	
	public GCommand(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) {
		switch(commandKey.getCharacter()){
			case 'U':
				user.setOperator(new Upper());
				break;
			case 'u':
				user.setOperator(new Lower());
			default:
				NotImplemented.warn();
				break;
		}
	}
}
