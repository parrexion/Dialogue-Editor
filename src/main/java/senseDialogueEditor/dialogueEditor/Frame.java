package senseDialogueEditor.dialogueEditor;

import java.util.Arrays;

/**
 * Class representing each frame in the dialogue.
 * @author Parrexion
 *
 */
public class Frame {

	public int background;
	public int[] currentCharacters;
	public int[] currentPoses;
	/**
	 * The name of the extra character in the middle.
	 */
	public String characterName;
	public String dialogueText;
	public int talkingPosition;
	
	
	public Frame(){
		background = 0;
		currentCharacters = new int[]{-1,-1,-1,-1,-1};
		currentPoses = new int[]{-1,-1,-1,-1,-1};
		characterName = "";
		dialogueText = "";
		talkingPosition = -1;
	}
	
	public Frame(Frame ds){
		background = ds.background;
		currentCharacters = new int[]{-1,-1,-1,-1,-1};
		currentPoses = new int[]{-1,-1,-1,-1,-1};
		for(int i = 0; i < 5; i++){
			currentCharacters[i] = ds.currentCharacters[i];
			currentPoses[i] = ds.currentPoses[i];
		}
		characterName = ds.characterName;
		dialogueText = ds.dialogueText;
		talkingPosition = ds.talkingPosition;
	}
	
	public Object[] convertToTable(){
		
		String pos = "";
		String poses = "";
		for (int i = 0; i < 4; i++) {
			if (currentCharacters[i] != -1){
				pos += currentCharacters[i];
			}
			if (currentPoses[i] != -1)
				poses += currentPoses[i];
			
			if (i == 3)
				break;
			pos += " , ";
			poses += " , ";
		}
		int talkingPose = (talkingPosition >= 0) ? currentPoses[talkingPosition] : -1;
		String talk = talkingPosition + " , " + characterName.split(" ")[0] + " , " + talkingPose;
		
		return new Object[]{background,pos,poses,talk,dialogueText};
	}

	
	
	@Override
	public String toString() {
		return "Frame [background=" + background + ", positions=" + Arrays.toString(currentCharacters)
				+ ", currentPoses=" + Arrays.toString(currentPoses) + ", characterName=" + characterName + ", dialogue="
				+ dialogueText + ", talkingPosition=" + talkingPosition + "]";
	}

	
}
