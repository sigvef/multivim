package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.Mode;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;
import org.ninjadev.multivim.server.Server;

import com.googlecode.lanterna.input.Key;

public class CtrlH extends NormalVisualCommand{
	
	public CtrlH(Key commandChar, EnumSet<NormalVisualFlag> flags, int arg) {
		super(commandChar, flags, arg);
	}

	public void executeCommand(User user) throws IOException {
		/* TODO: if visual mode stuff... se nv_ctrlh(..) in normal.c of vim */
		if(user.mode == Mode.VISUAL || user.mode == Mode.VISUAL_LINE){
			NotImplemented.warn();
		}
		new Left(commandKey, null, 0).executeCommand(user);
	}
}
