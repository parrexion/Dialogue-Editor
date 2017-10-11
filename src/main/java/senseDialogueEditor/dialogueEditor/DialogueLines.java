package senseDialogueEditor.dialogueEditor;

import java.util.ArrayList;

/**
 * Class representing each dialogue in the game.
 * @author Parrexion
 *
 */
public class DialogueLines {
	
	public String name;
	public int size;
	public ArrayList<DialogueScene> dataList;

	public DialogueLines(String name){
		this.name = name;
		dataList = new ArrayList<>();
		size = 0;
	}
	
	
	public void AddAction(DialogueScene data){
		dataList.add(data);
		size++;
		System.out.println("Added scene. " + size);
	}
	
	public void InsertAction(int index, DialogueScene data){
		dataList.add(index, data);
		size++;
		System.out.println("Inserted scene. " + index);
	}

	public void RemoveAction(int index){
		dataList.remove(index);
		size--;
		System.out.println("Remove scene. " + size);
	}
	
	public Object[] convertToTable(){
		return new Object[]{name,size};
	}
}
