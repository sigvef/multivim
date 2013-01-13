package org.ninjadev.multivim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class Buffer implements Serializable{
	public LinkedList<String> lines;
	public int bufferId;
	public static int bufferIdCounter = 0;
	
	public Buffer() throws IOException{
		lines = new LinkedList<String>();
		bufferId = bufferIdCounter++;
		insertLine(0,"");
	}
	
	public void insertLine(int index, String line) throws IOException{
		lines.add(index, line);
	}

	public void deleteLine(int index) throws IOException{
		lines.remove(index);
	}
	
	public void replaceLine(int index, String line) throws IOException{
		lines.remove(index);
		lines.add(index, line);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String line : lines){
			sb.append(line);
			sb.append("\n");
		}
		
		if(sb.length() > 0){
			sb.delete(sb.length()-1, sb.length());
		}
		
		return sb.toString();
	}

	public String insert(String text, Cursor cursor) throws IOException {
		System.out.println("MY TEXT: " + text);
		String line = lines.get((int) cursor.data.position.row);
		line = line.substring(0,cursor.data.position.column) + text + line.substring(cursor.data.position.column);
		replaceLine(cursor.data.position.row, line);
		System.out.println(line);
		return line;
	}

	public void readFile(String pathname) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(new File(pathname)));
		int i=0;
		String line;
		while((line = file.readLine()) != null){
			insertLine(i++,line);
		}
		
		file.close();
	}

	public String getChar(int y, int x) {
		String character = null;
		try{
			character = ""+lines.get(y).charAt(x);
		}catch(Exception e){}
		return character;
	}

	public void setChar(String chr, int line, int character) {
		String str = lines.get(line);
		lines.set(line, str.substring(0, character) + chr + str.substring(character+1));
	}

	public void delete(Cursor cursor) throws IOException {
		String line = lines.get(cursor.data.position.row);
		if(cursor.data.position.column == 0){
			cursor.data.position.column = lines.get(cursor.data.position.row-1).length()+1;
			join(cursor.data.position.row-1, cursor.data.position.row);
			cursor.data.position.row--;
		}else{
			lines.set(cursor.data.position.row, line.substring(0, cursor.data.position.column-1) + line.substring(cursor.data.position.column));
		}
	}

	private void join(int i, int j) throws IOException {
		lines.set(i, lines.get(i) + lines.get(j));
		deleteLine(j);
	}

	public void delete(Cursor cursor, Cursor operatorCursor) throws IOException {
		while(operatorCursor.data.position.row != cursor.data.position.row ||
		      operatorCursor.data.position.column != cursor.data.position.column){
			System.out.println("delete");
			delete(cursor);
			operatorCursor.backward();
		}
	}
}
