package org.ninjadev.multivim.commandparser;

public enum NormalVisualFlag {
	MayNeedSecondChar,
	NeedSecondCharWhenNoOperatorPending,
	NeedSecondCharAlways,
	SecondCharNeedsLanguageAdjustment,
	
	MayStartSelection,
	MayStartSelectionWithShiftModifier,
	MayStopSelectionWithoutShiftModifier,
	RightLeftModifiesCommand,
	KeepRegisterName,
	NotAllowedInCommandLineWindow
}
