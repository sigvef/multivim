package org.ninjadev.multivim.unittests.golftests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.ninjadev.multivim.Buffer;
import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.User;

import com.googlecode.lanterna.input.*;


@RunWith(Parameterized.class)
public class GolfTests {
	
	public final static String PATH = "src/org/ninjadev/multivim/unittests/golftests/data/";
	
	@Parameters()
	public static Iterable<Object[]> data(){
		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles(); 
		
		HashSet<String> ids = new HashSet<String>();
		
		for(File file : listOfFiles){
			ids.add(file.getName().substring(0,file.getName().lastIndexOf('.')));
		}
		
		Object[][] args = new Object[ids.size()][];
				
		Object[] idsArray = ids.toArray();
		for(int i=0;i<ids.size();i++){
			args[i] = new Object[]{idsArray[i]};
		}
		
		return Arrays.asList(args);
	}
		
	private String testId;
	
	public GolfTests(String testId){
		this.testId = testId;
	}
	
	@Test
	public void test() throws FileNotFoundException, IOException{
		
		File input = new File(PATH + testId + ".input");
		
		ArrayList<Key> inputCommands = new ArrayList<Key>();
		
		for(Character c : new BufferedReader(new FileReader(input)).readLine().toCharArray()){
			inputCommands.add(new Key(c));
		}
		
		User user = new User();
		
		Buffer startBuffer = new Buffer().readFile(PATH + testId + ".start");
		Buffer endBuffer = new Buffer().readFile(PATH + testId + ".end");
		
		Cursor cursor = new Cursor(startBuffer, user);
		user.cursors.put(startBuffer.bufferId, cursor);
		user.getActiveViewPort().setBuffer(startBuffer);
		
		for(Key command : inputCommands){
			user.processCommand(command);
		}
				
		assertEquals(startBuffer.toString(), endBuffer.toString());
		
	}

}
