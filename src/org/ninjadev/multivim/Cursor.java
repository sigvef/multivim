package org.ninjadev.multivim;

import java.io.IOException;

import org.ninjadev.multivim.commandparser.operators.AbstractOperator;
import org.ninjadev.multivim.commandparser.operators.Movement;
import org.ninjadev.multivim.notimplemented.NotImplemented;
import org.ninjadev.multivim.util.CharacterClass;
import org.ninjadev.multivim.util.StringUtils;

public class Cursor implements Comparable {
	public CursorData data;
	public int desiredColumn;
	public Buffer buffer;
	public User user;
	
	public Cursor(Buffer buffer, User user){
		data = new CursorData();
		this.buffer = buffer;
		this.user = user;
	}
	
	public void set(Cursor cursor){
		data.position = new Position(cursor.data.position);
		desiredColumn = cursor.desiredColumn;
	}

	/* returns 1 when going to the next line
	 * returns 2 when moving forward onto a null at the end of a line
	 * returns -1 when at the end of a file
	 * returns 0 otherwise
	 */
	public int forward(){
		String character = getCharacter();
		if(character != null){ /* still within line, move to next char (may be null) */
			data.position.column++;
			desiredColumn = data.position.column;
			return getCharacter() == null ? 2 : 0;
		}
		if(data.position.row != buffer.lines.size()-1){
			data.position.column = 0;
			data.position.row++;
			desiredColumn = data.position.column;
			return 1;
		}
		return -1;
	}
	
	public int backward(){
		if(data.position.column > 0){
			data.position.column--;
			desiredColumn = data.position.column;
			return 0;
		}
		if(data.position.row > 0){
			data.position.row--;
			data.position.column = buffer.lines.get(data.position.row).length();
			desiredColumn = data.position.column;
			return 1;
		}
		return -1;
	}

	private void setCharacter(String character) {
		buffer.setChar(character, data.position.row, data.position.column);
	}

	public boolean backwardWord(long count, boolean bigWord, boolean stop){
		counterLoop:
		while(count --> 0){
			CharacterClass startClass = StringUtils.getCharacterClass(getCharacter(), bigWord);
			if(backward() == -1){
				return false;
			}
			
			if(!stop || startClass == StringUtils.getCharacterClass(getCharacter(), bigWord) || startClass == CharacterClass.WHITESPACE){
				while(StringUtils.getCharacterClass(getCharacter(), bigWord) == CharacterClass.WHITESPACE){
					if(data.position.column == 0 && buffer.lines.get(data.position.row).length() == 0){
						stop = false;
						continue counterLoop;
					}
					
					if(backward() == -1){
						return true;
					}
				}
				
				CharacterClass cc = StringUtils.getCharacterClass(getCharacter(), bigWord);
				while(StringUtils.getCharacterClass(getCharacter(),bigWord) == cc){
					if(backward() == -1) return true;
				}
			}
			forward();
		}
		return true;
	}

	public String getCharacter() {
		return buffer.getChar(data.position.row, data.position.column);
	}
	
	private Object getNextCharacter() {
		return buffer.getChar(data.position.row, data.position.column+1);
	}

	public boolean oneleft() {
		if(data.position.column == 0){
			return false;
		}
		
		data.position.column--;
		desiredColumn = data.position.column;
		return true;
	}
	
	public boolean oneright(){
		if(getCharacter() == null){
			return false;
		}
		if(getNextCharacter() == null){
			return false;
		}
		data.position.column++;
		desiredColumn = data.position.column;
		return true;
	}
	
	public boolean cursor_up(int n){
		if(n > 0){
			int row = data.position.row;
			if(row-n < 0){
				row = n;
			}
			
			row -= n;
			data.position.row = row;
		}
		coladvance(desiredColumn);
		return true;
	}
	
	public boolean cursor_down(int n){
		if(n > 0){
			int row = data.position.row;
			if(row+n > buffer.lines.size()-1){
				row = buffer.lines.size()-1;
			}
			
			row += n;
			data.position.row = row;
		}
		coladvance(desiredColumn);
		return true;
	}

	private boolean coladvance(int column) {
		try{
			data.position.column = Math.min(column, buffer.lines.get(data.position.row).length());
		}catch(IndexOutOfBoundsException e){
			data.position.column = 0;
		}
		return data.position.column == column;
	}

	public void insert(char character) throws IOException {
		buffer.insert(""+character, this);
		System.out.println("trying to insert yo");
		forward();
	}

	public void delete() throws IOException {
		buffer.delete(this);
		backward();
	}

	@Override
	public int compareTo(Object o) {
		
		Cursor cursor = (Cursor) o;
		
		int rowDifference = cursor.data.position.row - data.position.row;
		if(rowDifference != 0){ 
			return rowDifference;
		}
		
		int columnDifference = cursor.data.position.column - data.position.column;
		return columnDifference;
	}

}
