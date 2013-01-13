package org.ninjadev.multivim;

import java.util.HashMap;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal.Color;

public class ViewPort {
	public Buffer buffer;
	int width;
	int height;
	int xOffset;
	int yOffset;
	int xPosition;
	public int yPosition;

	public ViewPort(){
		xPosition = 0;
		yPosition = 1;
		width = 100;
		height = 28;
		xOffset = 0;
		yOffset = 0;
		buffer = null;
	}

	public void scroll(int x, int y){
		xOffset += x;
		yOffset += y;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void render(HashMap<Integer, User> users, Screen screen){
		render(users, screen, 0,0);
	}

	public void render(HashMap<Integer, User> users, Screen screen, int xTranslation, int yTranslation){
		if(buffer == null) return;
		

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				
				Color fg = Color.WHITE;
				Color bg = Color.BLACK;
				
				int x = i+xOffset;
				int y = j+yOffset;
				
				for(Integer userId : users.keySet()){
					User u = users.get(userId);
					Cursor cursor = u.cursors.get(buffer.bufferId);
					if(cursor != null){
						if(cursor.data.position.row == y && cursor.data.position.column == x){
							fg = Color.BLACK;
							bg = u.data.color;
						}
					}
				}
				
				String s = buffer.getChar(y,x);
				if(s==null){
					s = " ";
					fg = Color.BLUE;
				}
				screen.putString(xPosition+xTranslation+i, yPosition+yTranslation+j, s, fg, bg);
			}
		}
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void setBuffer(Buffer buffer){
		this.buffer = buffer;
	}

	public void scroll(Position position) {
		if(position.row > height+yOffset-1){
			yOffset = position.row - height+1;
		}else if(position.row < yOffset){
			yOffset = position.row;
		}
	}
}
