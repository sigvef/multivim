package org.ninjadev.multivim.commandparser;

public class CommandArguments {
	public OperatorArguments operatorArguments;
	public char prefixChar;
	public char cmdChar;
	public char nextChar;
	public char extraChar;
	public long opCount;
	public long count0;
	public long count1;
	public int arg;
	public int retval;
	public String searchPattern;
	
	public CommandArguments(){
		operatorArguments = new OperatorArguments();
		prefixChar = 0;
		cmdChar = 0;
		nextChar = 0;
		extraChar = 0;
		opCount = 0;
		count0 = 0;
		count1 = 1;
		arg = 0;
		retval = 0;
		searchPattern = null;
	}
}
