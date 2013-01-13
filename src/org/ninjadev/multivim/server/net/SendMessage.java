package org.ninjadev.multivim.server.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.Message;
import org.ninjadev.multivim.Mode;
import org.ninjadev.multivim.Position;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.UserData;
import org.ninjadev.multivim.ViewPort;

import com.googlecode.lanterna.input.Key;

public class SendMessage {

	public static void identity(User user, UserData data) throws IOException{
		user.os.writeObject(new Message("identity", new Object[]{user.data}));
		user.os.flush();
	}

	public static void allUsers(User user, HashMap<Integer, User> users) throws IOException {
		ArrayList<UserData> userDatas = new ArrayList<UserData>();
		for(User u : users.values()){
			userDatas.add(u.data);
		}
		user.os.writeObject(new Message("allUsers", userDatas));
		user.os.flush();
	}

	public static void allBuffers(User user, LinkedList<Buffer> buffers) throws IOException {
		user.os.writeObject(new Message("allBuffers", buffers));
		user.os.flush();
	}

	public static void addUser(HashMap<Integer, User> users, User user) throws IOException {
		for(User u : users.values()){
			u.os.writeObject(new Message("addUser", new Object[]{user.data}));
			u.os.flush();
		}
	}

	public static void addViewPort(HashMap<Integer, User> users, User user, ViewPort viewPort) throws IOException{
		for(User u : users.values()){
			u.os.writeObject(new Message("addViewPort", new Object[]{user.getUserId(), viewPort.buffer.bufferId}));
			u.os.flush();
		}
	}
	
	public static void setCursor(User user, HashMap<Integer, User> users) throws IOException {
		for(User u : users.values()){
			for(Cursor c : u.cursors.values()){
				setCursor(user, c);
			}
		}
	}

	public static void setCursor(User user, Cursor cursor) throws IOException{
		user.os.writeObject(new Message("setCursor",new Object[]{
				cursor.user.getUserId(),
				cursor.buffer.bufferId,
				new Position(cursor.data.position),
				cursor.desiredColumn
		}));
		user.os.flush();
	}

	public static void setCursor(HashMap<Integer, User> users, Cursor cursor) throws IOException{
		for(User user : users.values()){
			setCursor(user, cursor);
		}
	}

	public static void newBuffer(HashMap<Integer, User> users, Buffer buffer) throws IOException {
		for(User user : users.values()){
			user.os.writeObject(new Message("newBuffer", buffer));
			user.os.flush();
		}
	}

	public static void removeUser(HashMap<Integer, User> users, User user) throws IOException {
		for(User u: users.values()){
			if(u != user){
				u.os.writeObject(new Message("removeUser", user.data));
				u.os.flush();
			}
		}
	}

	public static void replaceLine(HashMap<Integer,User> users, int i, String line) throws IOException {
		for(User u: users.values()){
			u.os.writeObject(new Message("replaceLine", new Object[]{i,line}));
			u.os.flush();
		}
	}

	public static void setMode(User user, Mode mode) throws IOException {
		user.os.writeObject(new Message("setMode", mode));
		user.os.flush();
	}

	public static void sendCommand(HashMap<Integer, User> users, int userId, Key command) throws IOException {
		for(User u: users.values()){
			u.os.writeObject(new Message("sendCommand", new Object[]{userId,command}));
			u.os.flush();
		}
	}

	public static void render(User user) throws IOException {
		user.os.writeObject(new Message("render", null));
		user.os.flush();
	}
}
