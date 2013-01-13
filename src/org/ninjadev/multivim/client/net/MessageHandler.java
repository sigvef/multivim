package org.ninjadev.multivim.client.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.CursorData;
import org.ninjadev.multivim.IServer;
import org.ninjadev.multivim.Message;
import org.ninjadev.multivim.Position;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.UserData;
import org.ninjadev.multivim.ViewPort;
import org.ninjadev.multivim.client.Client;
import org.ninjadev.multivim.net.IMessageHandler;

import com.googlecode.lanterna.input.Key;

@SuppressWarnings("unchecked")
public class MessageHandler implements IMessageHandler{
	
	Client client;
	IServer server;
	
	public MessageHandler(Client client){
		this.client = client;
	}
	
	public void processMessage(Message message) throws Exception{
		System.out.println("Message: " + message.command);
		switch(message.command){
			case "setCursor": setCursor(message.payload); break;
			case "allBuffers": allBuffers(message.payload); break;
			case "allUsers": allUsers(message.payload); break;
			case "addUser": addUser(message.payload); break;
			case "removeUser": removeUser(message.payload);  break;
			case "identity": identity(message.payload); break;
			case "sendCommand": sendCommand(message.payload); break;
			case "render" : render(message.payload); break;
			case "addViewPort" : addViewPort(message.payload); break;
			case "setActiveViewPort" : setActiveViewPort(message.payload); break;
			default:break;
		}
	}

	private void render(Object payload) {
		client.render();
	}

	private void sendCommand(Object payload) throws IOException {
		Object[] o = (Object[]) payload;
		int userId = (int) o[0];
		Key command = (Key) o[1];
		client.users.get(userId).processCommand(command);
		client.render();
	}
	
	private void addViewPort(Object payload){
		Object[]o = (Object[]) payload;
		int userId = (int) o[0];
		int bufferId = (int) o[1];
		ViewPort viewPort = new ViewPort();
		viewPort.buffer = client.buffers.get(bufferId);
		client.users.get(userId).viewPorts.add(viewPort);
	}
	
	private void setActiveViewPort(Object payload){
		Object[]o = (Object[]) payload;
		int userId = (int) o[0];
		int viewPortId = (int) o[1];
		User user = client.users.get(userId);
		user.setActiveBuffer(user.viewPorts.get(viewPortId));
	}
	
	private void addUser(Object payload){
		Object[]o = (Object[]) payload;
		UserData userData = (UserData)o[0];
		if(userData.userId == client.selfUser.getUserId()) return;
		
		User user = new User();
		user.data = (UserData)o[0];
		for(Buffer buffer : client.buffers){
			if(!user.cursors.containsKey(buffer.bufferId)){
				user.cursors.put(buffer.bufferId, new Cursor(buffer, user));
			}
		}
		client.users.put(user.getUserId(), user);
		user.activeViewPort.setBuffer(client.buffers.getFirst());
		
		client.osd.displayMessage("User " + user + " has joined");
		client.render();
	}
	
	private void removeUser(Object payload){
		UserData userData = (UserData) payload;
		User user = client.users.get(userData.userId);
		client.users.remove(user.getUserId());
		
		client.osd.displayMessage("User " + user + " has disconnected");
		client.render();
	}
	
	private void identity(Object payload){
		UserData userData = (UserData)((Object[])payload)[0];
		client.selfUser = client.createUser(userData);
		client.users.put(client.selfUser.getUserId(), client.selfUser);
		client.osd.displayMessage("Connected!");
	}
	
	private void allBuffers(Object payload) throws ClassNotFoundException, IOException {
		client.buffers = (LinkedList<Buffer>) payload;
		client.selfUser.activeViewPort.setBuffer(client.buffers.getFirst());
		
		for(Integer userId : client.users.keySet()){
			User user = client.users.get(userId);
			for(Buffer buffer : client.buffers){
				if(!user.cursors.containsKey(buffer.bufferId)){
					user.cursors.put(buffer.bufferId, new Cursor(buffer, user));
				}
			}
			user.activeViewPort.setBuffer(client.buffers.getFirst());
		}
	}
	
	private void allUsers(Object payload) throws ClassNotFoundException, IOException {
		
		ArrayList<UserData> allUsers = (ArrayList<UserData>)payload;
		System.out.println("Allusers! " + allUsers);
		for(UserData ud : allUsers){
			if(!client.users.containsKey(ud.userId)){
				User user = client.createUser(ud);
				client.users.put(user.getUserId(), user);
			}
		}
		
		for(Integer userId : client.users.keySet()){
			User user = client.users.get(userId);
			for(Buffer buffer : client.buffers){
				if(!user.cursors.containsKey(buffer.bufferId)){
					user.cursors.put(buffer.bufferId, new Cursor(buffer, user));
				}
			}
		}
	}

	private void setCursor(Object payload) throws IOException {
		Object[] o = (Object[])payload;
		int userId = (int) o[0];
		int bufferId = (int) o[1];
		Position position = (Position) o[2];
		int desiredColumn = (int) o[3];
		Cursor cursor = client.users.get(userId).getCursor(bufferId);
		cursor.data.position = new Position(position);
		cursor.desiredColumn = desiredColumn;
		
		if(userId == client.selfUser.getUserId()){
			client.selfUser.activeViewPort.scroll(position);
		}
		client.render();
	}
}
