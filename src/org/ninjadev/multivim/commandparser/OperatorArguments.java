package org.ninjadev.multivim.commandparser;

import org.ninjadev.multivim.Position;
import org.ninjadev.multivim.Register;
import org.ninjadev.multivim.commandparser.normalvisualcommands.Operator;

/* see oparg_T in structs.h of vim */
public class OperatorArguments {
	Operator operator;
	public Register register;
	/* MotionType motionType; */
	int motionForce;
	boolean useRegisterOne;
	boolean inclusive;
	int end_adjusted;
	Position start;
	Position end;
	long lineCount;
	int empty;
	int is_VIsual;
	int block_mode;
	int start_vcol;
	int end_vcol;
}
