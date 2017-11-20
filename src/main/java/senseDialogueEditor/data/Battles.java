package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class which contains all the people available in the game.
 *
 * @author Parrexion
 */
public class Battles {
	
	public List<Person> personList;
	public List<Integer> indexList;
	public HashMap<Integer, String> names;
	public HashMap<Integer, String> colors;
	
	
	public Battles(){
		
		setupPersons();
		
		personList = new ArrayList<>();
		Person c;
		int id;
		for(int i = 0; i < indexList.size(); i++) {
			id = indexList.get(i);
			c = new Person(names.get(id), colors.get(id));
			personList.add(c);
		}
	}
	
	/**
	 * Fills the person list with all the different people
	 */
	private void setupPersons(){
		names = new HashMap<>();
		colors = new HashMap<>();
		indexList = new ArrayList<>();
		int id;
		
		//Shaman
		id = 1; indexList.add(id);
		names.put(id, "Shaman");
		colors.put(id, "yellow");

		//Pc
		id = 2; indexList.add(id);
		names.put(id, "Pc");
		colors.put(id, "green");
		
		//Instructor
		id = 3; indexList.add(id);
		names.put(id, "Instructor");
		colors.put(id, "orange");
		
		//Soldier
		id = 4; indexList.add(id);
		names.put(id, "Soldier");
		colors.put(id, "red");
		
		//Mc
		id = 5; indexList.add(id);
		names.put(id, "Mc");
		colors.put(id, "purple");
		
		//Monster
		id = 6; indexList.add(id);
		names.put(id, "Monster");
		colors.put(id, "black");
	}
}
