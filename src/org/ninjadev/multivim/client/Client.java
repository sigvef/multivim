package org.ninjadev.multivim.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.Message;
import org.ninjadev.multivim.Mode;
import org.ninjadev.multivim.User;
import org.ninjadev.multivim.UserData;
import org.ninjadev.multivim.ViewPort;
import org.ninjadev.multivim.client.net.MessageHandler;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.ResizeListener;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class Client {

	public HashMap<Integer,User> users;
	public LinkedList<Buffer> buffers;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	public User selfUser;
	private SwingTerminal terminal;
	private Screen screen;
	
	public OSD osd;

	public MessageHandler messageHandler;
	private int screenWidth;

	public void startKeyboardHandlerThread(){
		new Thread(new Runnable(){
			ObjectOutputStream os;
			Terminal terminal;
			public Runnable args(Terminal terminal, ObjectOutputStream os){
				this.terminal = terminal;
				this.os = os;
				return this;
			}
			public void run() {
				while(true){
					Key key;
					while((key = terminal.readInput()) != null){
						try {
							os.writeObject(new Message("sendCommand", key));
							os.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.args(terminal, os)).start();
	}

	public void startMessageHandlerThread(){
		new Thread(new Runnable(){
			ObjectInputStream is;
			public Runnable args(ObjectInputStream is){
				this.is = is;
				return this;
			}
			public void run() {
				while(true){
					try {
						Message m = (Message)is.readObject();
						System.out.println(m);
						messageHandler.processMessage(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.args(is)).start();
	}

	public Buffer createBuffer(int bufferId) throws IOException{
		Buffer buffer = new Buffer();
		buffer.bufferId = bufferId;
		for(Integer i: users.keySet()){
			User u = users.get(i);
			u.cursors.put(buffer.bufferId, new Cursor(buffer, u));
		}
		return buffer;
	}
	
	public User createUser(ViewPort viewPort) {
		User user = new User();
		for(Buffer b: buffers){
			user.cursors.put(b.bufferId, new Cursor(b, user));
		}
		return user;
	}

	public void render(){
		
		osd.render(screen);
		for(ViewPort viewPort : selfUser.viewPorts){
			viewPort.scroll(selfUser.cursors.get(viewPort.buffer.bufferId).data.position);
			viewPort.render(users, screen);
		}
		
		/* render status line at bottom */
		screen.putString(0, screen.getTerminalSize().getColumns(), "-- "+selfUser.getMode()+" --                                                                                                                 ",
				Color.WHITE, Color.BLACK);
			
		screen.refresh();
	}

	public Client(Socket socket) throws IOException{

		terminal = TerminalFacade.createSwingTerminal();
		terminal.addResizeListener(new ResizeListener() {
			public void onResized(TerminalSize newSize) {
				for(ViewPort viewPort : selfUser.viewPorts){
					viewPort.setWidth(newSize.getColumns());
					viewPort.setHeight(newSize.getRows());
				}
				render();
			}
		});
		
		screen = TerminalFacade.createScreen(terminal);
		screen.startScreen();
		terminal.setCursorVisible(false);
		messageHandler = new MessageHandler(this);
		users = new HashMap<Integer, User>();
		
		osd = new OSD();

		buffers = new LinkedList<Buffer>();
		
		is = new ObjectInputStream(socket.getInputStream());
		os = new ObjectOutputStream(socket.getOutputStream());

		startMessageHandlerThread();
		startKeyboardHandlerThread();
	}


	public static void main(String[]args) throws Exception{
		Socket socket = new Socket("localhost", 9999);
		new Client(socket);
	}

	public User createUser(UserData ud) {
		User user = new User(ud);
		for(Buffer b: buffers){
			user.cursors.put(b.bufferId, new Cursor(b, user));
		}
		return user;
	}
}
