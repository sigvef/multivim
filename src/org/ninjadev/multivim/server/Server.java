package org.ninjadev.multivim.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.CursorData;
import org.ninjadev.multivim.IServer;
import org.ninjadev.multivim.Message;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.UserData;
import org.ninjadev.multivim.ViewPort;
import org.ninjadev.multivim.server.net.MessageHandler;
import org.ninjadev.multivim.server.net.SendMessage;

public class Server implements IServer {
	
	public HashMap<Integer, User> users;
	public LinkedList<Buffer> buffers;
	
	public Server() throws IOException{
		users = new HashMap<Integer,User>();
		buffers = new LinkedList<Buffer>();
		Buffer buffer = createBuffer();
		buffer.readFile("README.txt");
		buffers.add(buffer);
	}
	
	public Buffer createBuffer() throws IOException{
		Buffer buffer = new Buffer();
		for(User u : users.values()){
			u.cursors.put(buffer.bufferId, new Cursor(buffer, u));
		}
		SendMessage.newBuffer(users, buffer);
		return buffer;
	}
	
	public void addConnection(Socket socket) throws IOException{
		System.out.println("Connected: "+socket);
		User user = new User();
		for(Buffer b : buffers){
			user.cursors.put(b.bufferId, new Cursor(b, user));
		}
		user.socket = socket;
		users.put(user.getUserId(), user);
		new ServerThread(socket, this, user).start();
	}
	
	public void removeUser(User user) throws IOException{
		users.remove(user.getUserId());
		SendMessage.removeUser(users, user);
		System.out.println("Disonnected: "+user);
	}
	
	public class ServerThread extends Thread {
		
		private Socket socket;
		private Server server;
		private User user;
		ObjectOutputStream os;
	    ObjectInputStream is;
		
		public ServerThread(Socket socket, Server server, User user) throws IOException{
			super("ServerThread");
			this.socket = socket;
			this.server = server;
			this.user = user;
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			user.is = is;
			user.os = os;
		}
		
		public void run(){
		    MessageHandler messageHandler = new MessageHandler(server);
		    
		    try {
				SendMessage.identity(user, user.data);
				SendMessage.allUsers(user, users);
				SendMessage.allBuffers(user, buffers);
				user.activeViewPort.setBuffer(buffers.getFirst());
				SendMessage.addUser(users, user);
				SendMessage.setCursor(user, users);
				SendMessage.render(user);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Message inputMessage = null;
		    try {
				while ((inputMessage = (Message)is.readObject()) != null) {
					System.out.println("Message received: "+inputMessage.command);
					messageHandler.processMessage(user, inputMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    try {
				server.removeUser(user);
				os.close();
				is.close();
				socket.close();
			} catch (IOException e) {e.printStackTrace(); }
		}
	}
	
	
	public static void main(String[]args) throws IOException{
		Server server = new Server();
		ServerSocket serverSocket = new ServerSocket(9999);
		while(true){
			Socket sock = serverSocket.accept();
			server.addConnection(sock);
		}
	}
}

