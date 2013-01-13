package org.ninjadev.multivim;

import java.io.IOException;

import org.ninjadev.multivim.client.Client;
import org.ninjadev.multivim.server.Server;

import com.googlecode.lanterna.input.Key;

public class Main {
	public static void main(String[]args) throws Exception{
		
		new Thread(new Runnable(){
			String[] args;
			public Runnable args(String[]args){
				this.args = args;
				return this;
			}
			public void run() {
				try {
					Server.main(args);
				} catch (IOException e) {}
			}
		}.args(args)).start();

		Client.main(args);
	}
}
