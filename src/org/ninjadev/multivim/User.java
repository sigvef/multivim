package org.ninjadev.multivim;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.ninjadev.multivim.commandparser.NormalVisualCommandTable;
import org.ninjadev.multivim.commandparser.normalvisualcommands.NormalVisualCommand;
import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal.Color;

public class User {
	public UserData data;
	public HashMap<Integer,Cursor> cursors;
	String commandBuffer;
	public Mode mode;
	private String[] registers;
	int commandIndex;
	public ObjectInputStream is;
	public ObjectOutputStream os;
	public Socket socket;
	public ViewPort activeViewPort;
	public ArrayList<ViewPort> viewPorts;
	public AbstractOperator operator;
	
	public ArrayList<Key> commandString;
	
	static int userIdCounter = 0;

	public User(){
		data = new UserData();
		data.name = "Anonymous"+ (int)(Math.random()*1000);
		data.userId = userIdCounter++;
		data.color = getBGColor();
		
		commandString = new ArrayList<Key>();
		
		viewPorts = new ArrayList<ViewPort>();
		activeViewPort = new ViewPort();
		viewPorts.add(activeViewPort);
		
		operator = new Movement();
		
		cursors = new HashMap<Integer,Cursor>();
		commandBuffer = "";
		mode = Mode.NORMAL;
		registers = new String[256];
		for(int i=0;i<256;i++){
			registers[i] = "";
		}
		commandIndex = 0;
	}

	private Color getBGColor() {

		Color[] colors = new Color[]{
				Color.BLUE,
				Color.CYAN,
				Color.MAGENTA,
				Color.RED,
				Color.GREEN,
				Color.YELLOW,
				Color.WHITE,
		};
		
		return colors[getUserId()%colors.length];
	}

	public User(UserData ud) {
		this();
		this.data = ud;
	}

	public void processCommand(Key command) throws IOException{
		
		commandString.add(command);
		
		//command = commandString.get(0);
		
		switch(mode){
			case NORMAL:
				System.out.println("now doing nvc: "+command);
				NormalVisualCommand nvc = NormalVisualCommandTable.get(command);
				nvc.executeCommand(this);
			case COMMAND:
				if(command.equals(new Key(Kind.Escape))){
					setMode(Mode.NORMAL);
				}
				break;
			case INSERT:
				if(command.equals(new Key(Kind.Escape))){
					setMode(Mode.NORMAL);
					break;
				}
				if(command.equals(new Key(Kind.Backspace))){
					activeCursor().delete();
					break;
				}
				activeCursor().insert(command.getCharacter());
				break;
			case VISUAL:
				if(command.equals(new Key(Kind.Escape))){
					setMode(Mode.NORMAL);
				}
				break;
			case VISUAL_LINE:
				if(command.equals(new Key(Kind.Escape))){
					setMode(Mode.NORMAL);
				}
				break;
			default:
				break;
		}
	}

	public Cursor getCursor(int bufferId) {
		return cursors.get(bufferId);
	}

	public void setActiveBuffer(ViewPort viewPort) {
		activeViewPort = viewPort;
	}

	public ViewPort getActiveViewPort() {
		return activeViewPort;
	}
	
	public String toString(){
		return getUserId()+":"+getName();
	}

	public String getName() {
		return data.name;
	}

	public int getUserId() {
		return data.userId;
	}

	public void setUserId(int userId) {
		data.userId = userId;
	}

	public void setName(String name) {
		data.name = name;
	}

	public AbstractOperator getOperator() {
		return operator;
	}
	
	public void setOperator(AbstractOperator operator){
		this.operator = operator;
	}

	public Cursor activeCursor() {
		System.out.println(activeViewPort);
		System.out.println(activeViewPort.getBuffer());
		return getCursor(activeViewPort.getBuffer().bufferId);
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}
}
