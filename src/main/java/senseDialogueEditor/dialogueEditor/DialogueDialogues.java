package senseDialogueEditor.dialogueEditor;

import java.util.Arrays;

/**
 * Class containing all dialogues in the game as well as some other 
 * related settings and resources.
 * @author Parrexion
 *
 */
public class DialogueDialogues {

	public DialogueLines[] lines;

	
	public DialogueDialogues(int size){
		lines = new DialogueLines[size];
	}

	
	@Override
	public String toString() {
		return "DialogueDialogues [lines=" + Arrays.toString(lines) + "]";
	}
	
	
}
