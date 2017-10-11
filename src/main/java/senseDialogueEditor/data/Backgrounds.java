package senseDialogueEditor.data;

import java.util.ArrayList;
import java.util.List;

public class Backgrounds {

	public List<Background> backgrounds;
	private String[] names = {
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
	private String[] files = {
			"resources/images/pitchblack.png",
			"resources/images/TestBackground.jpg",
			"resources/images/free-space-textures.jpg",
			"resources/images/school.png",
			"resources/images/skyline-manhatten.png",
			"resources/images/DRbkgs/Classroom_normal.png",
			"resources/images/DRbkgs/Corridors.png",
			"resources/images/livingroom.jpg",
			"resources/images/city_train.jpg"
			};
	
	
	public Backgrounds(){
		backgrounds = new ArrayList<>();
		Background b;
		for(int i = 0; i < names.length; i++) {
			b = new Background(names[i], files[i]);
			backgrounds.add(b);
		}
	}
	
}
