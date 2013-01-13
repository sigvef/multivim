package org.ninjadev.multivim.commandparser;

import java.util.EnumSet;
import java.util.HashMap;

import org.ninjadev.multivim.commandparser.normalvisualcommands.*;
import org.ninjadev.multivim.commandparser.normalvisualcommands.Error;
import org.ninjadev.multivim.commandparser.operators.Movement;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;

public class NormalVisualCommandTable{

	public static HashMap<Key, NormalVisualCommand> table;
	
	private static void entry(NormalVisualCommand normalVisualCommand){
		table.put(normalVisualCommand.commandKey, normalVisualCommand);
	}
	
	
	public static NormalVisualCommand get(Key key){
		NormalVisualCommand nvc = table.get(key);
		return nvc == null ? table.get(new Key('\0', false, false)) : nvc;
	}
	
	static{
		table = new HashMap<Key, NormalVisualCommand>();
		
		boolean F = false;
		boolean T = true;
		
		int FALSE = 0;
		int TRUE =  1;
		int BACKWARD = 3;
		int FORWARD = 4;
		int SEARCH_REV = 12;
		int BL_WHITE = 13;
		int BL_FIX = 14;
		
		/*      | command          | char | ctrl | alt  | flags | args |  */
		
		entry(new Error        (new Key( '\0' ,   F  ,  F  ),   null,   0          ));
		entry(new AddSub       (new Key(  'a' ,   T  ,  F  ),   null,   0          ));
		entry(new Page         (new Key(  'b' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayStopSelectionWithoutShiftModifier), BACKWARD));
		entry(new Esc          (new Key(  'c' ,   T  ,  F  ),   null,   TRUE       ));
		entry(new HalfPage     (new Key(  'd' ,   T  ,  F  ),   null,   0          ));
		entry(new ScrollLine   (new Key(  'e' ,   T  ,  F  ),   null,   TRUE       ));
		entry(new Page         (new Key(  'f' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayStopSelectionWithoutShiftModifier), FORWARD));
		entry(new CtrlG        (new Key(  'g' ,   T  ,  F  ),   null,   0          ));
		entry(new CtrlH        (new Key(  'h' ,   T  ,  F  ),   null,   0          ));
		entry(new PCMark       (new Key(  'i' ,   T  ,  F  ),   null,   0          ));
		entry(new Down         (new Key( Kind.Enter        ),   null,   FALSE      ));
		entry(new Error        (new Key(  'k' ,   T  ,  F  ),   null,   0          ));
		entry(new Clear        (new Key(  'l' ,   T  ,  F  ),   null,   0          ));
		entry(new Down         (new Key(  'm' ,   T  ,  F  ),   null,   TRUE       ));
		entry(new Down         (new Key(  'n' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayStopSelectionWithoutShiftModifier), FALSE));
		entry(new CtrlO        (new Key(  'o' ,   T  ,  F  ),   null,   0          ));
		entry(new Up           (new Key(  'p' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayStopSelectionWithoutShiftModifier), FALSE));
		entry(new Visual       (new Key(  'q' ,   T  ,  F  ),   null,   FALSE      ));
		entry(new Redo         (new Key(  'r' ,   T  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  's' ,   T  ,  F  ),   null,   0          ));
		entry(new TagPop       (new Key(  't' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.NotAllowedInCommandLineWindow), 0));
		entry(new HalfPage     (new Key(  'u' ,   T  ,  F  ),   null,   0          ));
		entry(new Visual       (new Key(  'v' ,   T  ,  F  ),   null,   FALSE      ));
		entry(new Visual       (new Key(  'V' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Visual       (new Key(  'v' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Window       (new Key(  'w' ,   T  ,  F  ),   null,   0          ));
		entry(new AddSub       (new Key(  'x' ,   T  ,  F  ),   null,   0          ));
		entry(new ScrollLine   (new Key(  'y' ,   T  ,  F  ),   null,   FALSE      ));
		entry(new Suspend      (new Key(  'z' ,   T  ,  F  ),   null,   0          ));
		entry(new Esc          (new Key(  Kind.Escape      ),   null,   FALSE      ));
		entry(new Normal       (new Key(  '\\',   T  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), 0));
		
		entry(new Ident        (new Key(  ']' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar), 0));
		entry(new Hat          (new Key(  '^' ,   T  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar), 0));
		entry(new Error        (new Key(  '_' ,   T  ,  F  ),   null,   0          ));
		entry(new Right        (new Key(  ' ' ,   F  ,  F  ),   null,   0          ));
		entry(new Operator     (new Key(  '!' ,   F  ,  F  ),   null,   0          ));
		entry(new Regname      (new Key(  '"' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharWhenNoOperatorPending,
			NormalVisualFlag.KeepRegisterName
		), 0));
		
		entry(new Ident        (new Key(  '#' ,   F  ,  F  ),   null,   0          ));
		entry(new Dollar       (new Key(  '$' ,   F  ,  F  ),   null,   0          ));
		entry(new Percent      (new Key(  '%' ,   F  ,  F  ),   null,   0          ));
		entry(new Optrans      (new Key(  '&' ,   F  ,  F  ),   null,   0          ));
		entry(new GoMark       (new Key(  '\'',   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), TRUE));
		
		entry(new Brace        (new Key(  '(' ,   F  ,  F  ),   null,   BACKWARD   ));
		entry(new Brace        (new Key(  ')' ,   F  ,  F  ),   null,   FORWARD    ));
		entry(new Ident        (new Key(  '*' ,   F  ,  F  ),   null,   0          ));
		entry(new Down         (new Key(  '+' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new CSearch      (new Key(  ',' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new Up           (new Key(  '-' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new Dot          (new Key(  '.' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName), 0));
		entry(new Search       (new Key(  '/' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new BeginLine    (new Key(  '0' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '1' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '2' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '3' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '4' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '5' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '6' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '7' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '8' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  '9' ,   F  ,  F  ),   null,   0          ));
		entry(new Colon        (new Key(  ':' ,   F  ,  F  ),   null,   0          ));
		entry(new CSearch      (new Key(  ';' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Operator     (new Key(  '<' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.RightLeftModifiesCommand), 0));
		entry(new Operator     (new Key(  '=' ,   F  ,  F  ),   null,   0          ));
		entry(new Operator     (new Key(  '>' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.RightLeftModifiesCommand), 0));
		entry(new Search       (new Key(  '?' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new At           (new Key(  '@' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharWhenNoOperatorPending
		), FALSE));
		
		entry(new Edit         (new Key(  'A' ,   F  ,  F  ),   null,   0          ));
		entry(new BackWord     (new Key(  'B' ,   F  ,  F  ),   null,   0          ));
		entry(new Abbrev       (new Key(  'C' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName), 0));
		entry(new Abbrev       (new Key(  'D' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName), 0));
		entry(new WordCommand  (new Key(  'E' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new CSearch      (new Key(  'F' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways,
			NormalVisualFlag.SecondCharNeedsLanguageAdjustment
		), BACKWARD));
		
		entry(new Goto         (new Key(  'G' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new Scroll       (new Key(  'H' ,   F  ,  F  ),   null,   0          ));
		entry(new Edit         (new Key(  'I' ,   F  ,  F  ),   null,   0          ));
		entry(new Join         (new Key(  'J' ,   F  ,  F  ),   null,   0          ));
		entry(new Ident        (new Key(  'K' ,   F  ,  F  ),   null,   0          ));
		entry(new Scroll       (new Key(  'L' ,   F  ,  F  ),   null,   0          ));
		entry(new Scroll       (new Key(  'M' ,   F  ,  F  ),   null,   0          ));
		entry(new Next         (new Key(  'N' ,   F  ,  F  ),   null,   SEARCH_REV ));
		entry(new Open         (new Key(  'O' ,   F  ,  F  ),   null,   0          ));
		entry(new Put          (new Key(  'P' ,   F  ,  F  ),   null,   0          ));
		entry(new ExMode       (new Key(  'Q' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar), 0));
		entry(new Replace      (new Key(  'R' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Subst        (new Key(  'S' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName), 0));
		entry(new CSearch      (new Key(  'T' ,   F  ,  F  ),   EnumSet.of(
				NormalVisualFlag.MayNeedSecondChar,
				NormalVisualFlag.NeedSecondCharAlways,
				NormalVisualFlag.SecondCharNeedsLanguageAdjustment
		), BACKWARD));
		
		entry(new Undo         (new Key(  'U' ,   F  ,  F  ),   null,   0          ));
		entry(new WordCommand  (new Key(  'W' ,   F  ,  F  ),   null,   TRUE       ));
		entry(new Abbrev       (new Key(  'X' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName)              ,   0          ));
		entry(new Abbrev       (new Key(  'Y' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName)              ,   0          ));
		entry(new Zet          (new Key(  'Z' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharWhenNoOperatorPending,
			NormalVisualFlag.NotAllowedInCommandLineWindow
		), 0));
		
		entry(new Brackets     (new Key(  '[' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), BACKWARD));
		
		entry(new Error        (new Key(  '\\',   F  ,  F  ),   null,   0          ));
		entry(new Brackets     (new Key(  ']' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), FORWARD));
		
		entry(new BeginLine    (new Key(  '^' ,   F  ,  F  ),   null,   BL_WHITE|BL_FIX));
		entry(new LineOp       (new Key(  '_' ,   F  ,  F  ),   null,   0          ));
		entry(new GoMark       (new Key(  '`' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), FALSE));
		
		entry(new Edit         (new Key(  'a' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar), 0));
		entry(new WordCommand  (new Key(  'b' ,   F  ,  F  ),   null,   0          ));
		entry(new Operator     (new Key(  'c' ,   F  ,  F  ),   null,   0          ));
		entry(new Operator     (new Key(  'd' ,   F  ,  F  ),   null,   0          ));
		entry(new WordCommand  (new Key(  'e' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new CSearch      (new Key(  'f' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways,
			NormalVisualFlag.SecondCharNeedsLanguageAdjustment
		), FORWARD));
		
		entry(new GCommand     (new Key(  'g' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways
		), FALSE));
		
		entry(new Left         (new Key(  'h' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.RightLeftModifiesCommand)                   ,   0          ));
		entry(new Edit         (new Key(  'i' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar)                  ,   0          ));
		entry(new Down         (new Key(  'j' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Up           (new Key(  'k' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Right        (new Key(  'l' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.RightLeftModifiesCommand)                   ,   0          ));
		entry(new Mark         (new Key(  'm' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharWhenNoOperatorPending
		), 0));
		
		entry(new Next         (new Key(  'n' ,   F  ,  F  ),   null,   0          ));
		entry(new Open         (new Key(  'o' ,   F  ,  F  ),   null,   0          ));
		entry(new Put          (new Key(  'p' ,   F  ,  F  ),   null,   0          ));
		entry(new Record       (new Key(  'q' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.MayNeedSecondChar)                  ,   0          ));
		entry(new Replace      (new Key(  'r' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharWhenNoOperatorPending,
			NormalVisualFlag.SecondCharNeedsLanguageAdjustment
		), 0));
		entry(new Subst        (new Key(  's' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName)              ,   0          ));
		entry(new CSearch      (new Key(  't' ,   F  ,  F  ),   EnumSet.of(
			NormalVisualFlag.MayNeedSecondChar,
			NormalVisualFlag.NeedSecondCharAlways,
			NormalVisualFlag.SecondCharNeedsLanguageAdjustment
		), FORWARD));
		
		entry(new Undo         (new Key(  'u' ,   F  ,  F  ),   null,   0          ));
		entry(new WordCommand  (new Key(  'w' ,   F  ,  F  ),   null,   FALSE      ));
		entry(new Abbrev       (new Key(  'x' ,   F  ,  F  ),   EnumSet.of(NormalVisualFlag.KeepRegisterName)              ,   0          ));
		entry(new Operator     (new Key(  'y' ,   F  ,  F  ),   null,   0          ));
		entry(new Zet          (new Key(  'z' ,   F  ,  F  ),   EnumSet.of(
				NormalVisualFlag.MayNeedSecondChar,
				NormalVisualFlag.NeedSecondCharAlways
				)              ,   0          ));
		entry(new FindPar      (new Key(  '{' ,   F  ,  F  ),   null,   BACKWARD   ));
		entry(new Pipe         (new Key(  '|' ,   F  ,  F  ),   null,   0          ));
		entry(new FindPar      (new Key(  '}' ,   F  ,  F  ),   null,   FORWARD    ));
		entry(new Tilde        (new Key(  '~' ,   F  ,  F  ),   null,   0          ));
		entry(new Ident        (new Key(  '£' ,   F  ,  F  ),   null,   0          ));
		entry(new Ignore       (new Key(  Kind.Ignore      ),   EnumSet.of(NormalVisualFlag.KeepRegisterName)              ,   0          ));
		entry(new Nop          (new Key(  Kind.Nop         ),   null,   0          ));
		entry(new Edit         (new Key(  Kind.Insert      ),   null,   0          ));
		entry(new CtrlH        (new Key(  Kind.Backspace   ),   null,   0          ));
		entry(new Up           (new Key(  Kind.ArrowUp     ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		), FALSE));
		
		entry(new Down         (new Key(  Kind.ArrowDown   ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		), FALSE));
		
		entry(new Left         (new Key(  Kind.ArrowLeft   ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		), 0));
		
		entry(new Right        (new Key(  Kind.ArrowRight  ),   EnumSet.of( 
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier,
			NormalVisualFlag.RightLeftModifiesCommand
		), 0));
		
		entry(new Page         (new Key(  Kind.PageUp      ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		),   BACKWARD   ));
		
		entry(new Page         (new Key(  Kind.PageDown    ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		),   FORWARD    ));
		
		entry(new End          (new Key(  Kind.End         ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		),   FALSE      ));
		
		entry(new Home         (new Key(  Kind.Home        ),   EnumSet.of(
			NormalVisualFlag.MayStartSelectionWithShiftModifier,
			NormalVisualFlag.MayStopSelectionWithoutShiftModifier
		),   0          ));
		
		entry(new Abbrev       (new Key(  Kind.Delete      ),   null,   0          ));
		entry(new Help         (new Key(  Kind.F1          ),   EnumSet.of(NormalVisualFlag.NotAllowedInCommandLineWindow), 0));
	}
}
