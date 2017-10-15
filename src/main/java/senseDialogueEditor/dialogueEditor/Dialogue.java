package senseDialogueEditor.dialogueEditor;

import java.util.ArrayList;

/**
 * Class representing each dialogue in the game.
 * @author Parrexion
 *
 */
public class Dialogue {
	
	public String name;
	public int size;
	public ArrayList<Frame> frames;

	public Dialogue(String name){
		this.name = name;
		frames = new ArrayList<>();
		size = 0;
	}
	
	
	public void AddAction(Frame data){
		frames.add(data);
		size++;
		System.out.println("Added frame. " + size);
	}
	
	public void InsertAction(int index, Frame data){
		frames.add(index, data);
		size++;
		System.out.println("Inserted frame. " + index);
	}

	public void RemoveAction(int index){
		frames.remove(index);
		size--;
		System.out.println("Remove frame. " + size);
	}
	
	public Object[] convertToTable(){
		return new Object[]{name,size};
	}
}
