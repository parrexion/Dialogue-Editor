package senseDialogueEditor.data;

import javax.swing.ImageIcon;

public class Enemy {
	
	private final int uuid;
	public String name;
	public ImageIcon picture;
	
	public Enemy(int id, String name){
		this.uuid = id;
		this.name = name;
	}
}
