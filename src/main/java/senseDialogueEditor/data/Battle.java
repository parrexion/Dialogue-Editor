package senseDialogueEditor.data;

import java.util.List;

public class Battle {

	public enum RemoveSide {NONE,LEFT,RIGHT};

	public String scenarioName = "";
	public RemoveSide removeSide = RemoveSide.NONE;
	public int backgroundHintLeft = -1;
	public int backgroundHintRight = -1;
	public int backgroundLeft = 0;
	public int backgroundRight = 0;
	public int numberOfEnemies = 1;
	public List<Integer> enemyTypes;
	public boolean escapeTextReq = false;

	public boolean storyBattle = false;
	public boolean escapeButtonEnabled = true;
	public boolean healthEnabled = true;

	public int[] equippedKanji;
}
