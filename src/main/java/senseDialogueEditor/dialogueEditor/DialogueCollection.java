package senseDialogueEditor.dialogueEditor;

import java.util.Arrays;

/**
 * Class containing all dialogues in the game as well as some other 
 * related settings and resources.
 * @author Parrexion
 *
 */
public class DialogueCollection {

	public Dialogue[] dialogues;

	
	public DialogueCollection(int size){
		dialogues = new Dialogue[size];
	}

	
	@Override
	public String toString() {
		return "DialogueCollection [dialogues=" + Arrays.toString(dialogues) + "]";
	}
	
	
}
