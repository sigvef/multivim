package org.ninjadev.multivim.server.net;

import org.ninjadev.multivim.Message;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.commandparser.CommandArguments;
import org.ninjadev.multivim.commandparser.NormalVisualCommandTable;
import org.ninjadev.multivim.commandparser.OperatorArguments;
import org.ninjadev.multivim.commandparser.normalvisualcommands.NormalVisualCommand;
import org.ninjadev.multivim.server.Server;

import com.googlecode.lanterna.input.Key;

public class MessageHandler {

	Server server;

	public MessageHandler(Server server){
		this.server = server;
	}

	public void processMessage(User user, Message message) throws Exception {

		switch(message.command){
			case "disconnect": disconnect(user, message.payload); break;
			case "sendCommand": sendCommand(user, message.payload); break;
			default:break;
		}
	}

	private void disconnect(User user, Object payload) {
	}

	private void sendCommand(User user, Object payload) throws Exception {
		Key command = (Key)payload;
		user.processCommand(command);
		SendMessage.sendCommand(server.users, user.getUserId(), command);
		
	}
}