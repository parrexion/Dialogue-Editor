package senseDialogueEditor.data;

import javax.swing.ImageIcon;



public class Person {

	public String name;
	public String filename;
	public ImageIcon[] poses;
	public ImageIcon[] closeups;
	
	public String[] poseNames = {
			"neutral",
			"sad",
			"happy",
			"angry"
			};
	
	public Person(String name, String folder) {
		this.name = name;
		filename = folder + name + "/";

		poses = new ImageIcon[poseNames.length];
		closeups = new ImageIcon[poseNames.length];
		
		for (int i = 0; i < poseNames.length; i++) {
			
		    poses[i] = new ImageIcon(filename+poseNames[i]+".png");
		    closeups[i] = poses[i];
		}
	}
}
