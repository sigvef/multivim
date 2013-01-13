package org.ninjadev.multivim.commandparser.normalvisualcommands;

import java.io.IOException;
import java.util.EnumSet;

import org.ninjadev.multivim.IServer;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.NormalVisualFlag;
import org.ninjadev.multivim.notimplemented.NotImplemented;
import org.ninjadev.multivim.server.Server;

import com.googlecode.lanterna.input.Key;

public class NormalVisualCommand {
	public Key commandKey; /* first command character */
	public EnumSet<NormalVisualFlag> flags; /* from vim source code, not sure if we need this yet */
	public int arg; /* from vim source code, not sure if we need this yet */
	
	public void executeCommand(User user) throws IOException{
		NotImplemented.warn();
	}
	
	public NormalVisualCommand(Key commandKey, EnumSet<NormalVisualFlag> flags, int arg){
		this.commandKey = commandKey;
		this.flags = flags != null ? flags : EnumSet.noneOf(NormalVisualFlag.class);
		this.arg = arg;
	}
}