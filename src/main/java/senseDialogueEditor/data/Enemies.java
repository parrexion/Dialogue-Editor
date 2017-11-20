package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class which contains all the people available in the game.
 *
 * @author Parrexion
 */
public class Enemies {
	
	public List<Enemy> enemyList;
	public List<Integer> indexList;
	public HashMap<Integer, String> names;
	
	
	public Enemies(){
		
		setupEnemies();
		
		enemyList = new ArrayList<>();
		Enemy c;
		int id;
		for(int i = 0; i < indexList.size(); i++) {
			id = indexList.get(i);
			c = new Enemy(id, names.get(id));
			enemyList.add(c);
		}
	}
	
	/**
	 * Fills the person list with all the different people
	 */
	private void setupEnemies(){
		names = new HashMap<>();
		indexList = new ArrayList<>();
	}
}
