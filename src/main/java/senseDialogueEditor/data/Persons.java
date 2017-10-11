package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.List;

public class Persons {
	
	public List<Person> personList;
	private String[] names = {
			"Shaman",
			"Pc",
			"Instructor",
			"Soldier"
			};
	private String folder = "resources/Characters/";
	
	public Persons(){
		
		personList = new ArrayList<>();
		Person c;
		for(int i = 0; i < names.length; i++) {
			c = new Person(names[i], folder);
			personList.add(c);
		}
	}
}
