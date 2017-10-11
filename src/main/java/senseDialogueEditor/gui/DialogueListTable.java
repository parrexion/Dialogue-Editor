package senseDialogueEditor.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import senseDialogueEditor.dialogueEditor.DialogueDialogues;
import senseDialogueEditor.dialogueEditor.DialogueLines;

public class DialogueListTable extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String[] columnNames;
	public ArrayList<Object[]> data;
	
	@SuppressWarnings("unused")
	private DialogueListTable(){
	}
	
	public DialogueListTable(DialogueDialogues dialogues) {
		columnNames = new String[]{"Dialogue","Frames"};
		data = new ArrayList<>();
		for(DialogueLines dl : dialogues.lines){
			data.add(dl.convertToTable());
		}
	}
	
	public void updateFrame(DialogueLines lines,int selectedFrame){
		data.set(selectedFrame,lines.convertToTable());
		fireTableRowsUpdated(selectedFrame, selectedFrame);
	}
	
	public void addFrame(DialogueLines lines){
		data.add(lines.convertToTable());
		fireTableRowsInserted(getRowCount()-1, getRowCount());
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
