package org.ninjadev.multivim.client;

import org.ninjadev.multivim.util.StringUtils;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal.Color;

public class OSD {
	String displayMessage;
	
	public OSD(){
		displayMessage = "";
	}
	
	public void displayMessage(String message){
		displayMessage = message;
	}
	
	public void render(Screen screen){
		screen.putString(0, 0, StringUtils.repeat(" ", screen.getTerminalSize().getColumns()), Color.WHITE, Color.MAGENTA, ScreenCharacterStyle.Bold);
		screen.putString(0, 0, displayMessage, Color.WHITE, Color.MAGENTA, ScreenCharacterStyle.Bold);
	}
}
