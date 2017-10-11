package senseDialogueEditor.dialogueEditor;

import java.util.Arrays;

/**
 * Class representing each frame in the dialogue.
 * @author Parrexion
 *
 */
public class DialogueScene {

	public int background;
	public int[] positions;
	public int[] currentPoses;
	/**
	 * The name of the extra character in the middle.
	 */
	public String characterName;
	public String dialogue;
	public int talkingPosition;
	public int talkingCharacter;
	public int talkingPose;
	
	
	public DialogueScene(){
		background = 0;
		positions = new int[]{-1,-1,-1,-1,-1};
		currentPoses = new int[]{-1,-1,-1,-1,-1};
		characterName = "";
		dialogue = "";
		talkingPosition = -1;
		talkingCharacter = -1;
		talkingPose = 0;
	}
	
	public DialogueScene(DialogueScene ds){
		background = ds.background;
		positions = ds.positions;
		currentPoses = ds.currentPoses;
		characterName = ds.characterName;
		dialogue = ds.dialogue;
		talkingPosition = ds.talkingPosition;
		talkingCharacter = ds.talkingCharacter;
		talkingPose = ds.talkingPose;
	}
	
	public Object[] convertToTable(){
		
		String pos = "";
		String poses = "";
		for (int i = 0; i < 4; i++) {
			if (positions[i] != -1){
				pos += positions[i];
			}
			if (currentPoses[i] != -1)
				poses += currentPoses[i];
			
			if (i == 3)
				break;
			pos += " , ";
			poses += " , ";
		}
		String talk = talkingPosition + " , " + characterName.split(" ")[0] + " , " + talkingPose;
		
		return new Object[]{background,pos,poses,talk,dialogue};
	}

	
	
	@Override
	public String toString() {
		return "DialogueScene [background=" + background + ", positions=" + Arrays.toString(positions)
				+ ", currentPoses=" + Arrays.toString(currentPoses) + ", characterName=" + characterName + ", dialogue="
				+ dialogue + ", talkingPosition=" + talkingPosition + ", talkingCharacter=" + talkingCharacter
				+ ", talkingPose=" + talkingPose + "]";
	}

	
}
