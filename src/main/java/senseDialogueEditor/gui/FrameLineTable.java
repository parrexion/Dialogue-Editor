package senseDialogueEditor.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import senseDialogueEditor.data.Dialogue;
import senseDialogueEditor.data.Frame;

public class FrameLineTable extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String[] columnNames;
	public ArrayList<Object[]> data;
	
	public FrameLineTable(){
		
		columnNames = new String[]{"Background","Characters","Poses","Talking","Text"};
		data = new ArrayList<>();
		Frame scene = new Frame();
		data.add(scene.convertToTable());
		scene.currentCharacters = new int[]{30,13,11,53};
		scene.currentPoses = new int[]{13,23,11,33};
		scene.background = 0;
		data.add(scene.convertToTable());
		scene.characterName = "NameIsSoLong Indeed";
		scene.talkingPosition = 1;
		scene.dialogueText = "This is some text. And then there are a lot more text which needs to fit in the box.";
		data.add(scene.convertToTable());
	}
	
	public FrameLineTable(Dialogue lines) {
		columnNames = new String[]{"Background","Characters","Poses","Talking","Text"};
		data = new ArrayList<>();
		for(Frame ds : lines.frames){
			data.add(ds.convertToTable());
		}
	}
	
	public void updateFrame(Frame scene,int selectedFrame){
		data.set(selectedFrame,scene.convertToTable());
		fireTableRowsUpdated(selectedFrame, selectedFrame);
	}
	
	public void insertFrame(int index, Frame scene){
		data.add(index, scene.convertToTable());
		fireTableRowsInserted(index, index);
	}
	
	public void removeFrame(int index){
		data.remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	@Override
	public int getColumnCount(){
		return columnNames.length;
	}
	
	@Override
	public int getRowCount(){
		return data.size();
	}

	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	

	@Override
	public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }

}
