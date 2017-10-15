package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.List;

public class Backgrounds {

	public List<Background> backgrounds;
	private final String bkgFolder = "resources/Backgrounds/";
	private final String[] names = {
			"Nothing",
			"Graveyard",
			"Space",
			"School",
			"City",
			"Classroom",
			"Corridors",
			"Living Room",
			"City Train Line"
			};
	private final String[] files = {
			"pitchblack.png",
			"graveyard.jpg",
			"space.jpg",
			"school.png",
			"skyline.png",
			"classroom.png",
			"corridors.png",
			"livingroom.jpg",
			"city_train.jpg"
			};
	
	
	public Backgrounds(){
		backgrounds = new ArrayList<>();
		Background b;
		for(int i = 0; i < names.length; i++) {
			b = new Background(names[i], bkgFolder + files[i]);
			backgrounds.add(b);
		}
	}
	
}
