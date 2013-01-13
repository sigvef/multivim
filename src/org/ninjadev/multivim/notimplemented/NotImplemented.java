package org.ninjadev.multivim.notimplemented;

public class NotImplemented {
	public static void warn(){
		try{ throw new NotImplementedException();}
		catch(NotImplementedException e){e.printStackTrace();}
	}
}
