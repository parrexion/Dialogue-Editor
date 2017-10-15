package senseDialogueEditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import senseDialogueEditor.dialogueEditor.DialogueCollection;

public class DialogueListPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	public JTable dialogueTable;
	public DialogueListTable data;
	public DialogueCollection dialogueDialogues;
	
	
	public DialogueListPanel(ListSelectionListener listlis, DialogueCollection dialogues) {
		super();
		this.setLayout(new BorderLayout());
		
		dialogueDialogues = dialogues;
		
		this.setPreferredSize(new Dimension(200, 500));
		this.add(createDialogueButtons(),BorderLayout.NORTH);
		this.add(createDialogueTable(),BorderLayout.CENTER);
	}
	
	private JPanel createDialogueButtons(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Lines"));
		panel.setLayout(new BorderLayout());
		
		
		return panel;
	}
	
	private JScrollPane createDialogueTable(){

		data = new DialogueListTable(dialogueDialogues);
		dialogueTable = new JTable(data);
		dialogueTable.setFont(new Font("Arial",Font.PLAIN,16));
		
		DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
		rend.setHorizontalAlignment(SwingConstants.CENTER);
		dialogueTable.setDefaultRenderer(Object.class, rend);
		rend = (DefaultTableCellRenderer) dialogueTable.getTableHeader().getDefaultRenderer();
		rend.setHorizontalAlignment(SwingConstants.CENTER);
		JTableHeader head = dialogueTable.getTableHeader();
		head.setDefaultRenderer(rend);
		dialogueTable.setTableHeader(head);
		dialogueTable.setRowSelectionInterval(dialogueDialogues.dialogues.length-1, dialogueDialogues.dialogues.length-1);
		dialogueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumnModel model = dialogueTable.getColumnModel();
		model.getColumn(0).setMinWidth(120);
		model.getColumn(1).setMinWidth(60);
		
		JScrollPane scroll = new JScrollPane(dialogueTable);
		scroll.setBorder(BorderFactory.createTitledBorder("Dialogues"));
		dialogueTable.setFillsViewportHeight(true);
		return scroll;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent e) {
		JTextField tField = (JTextField) e.getSource();
		String text = tField.getText();
		if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
			int pos = tField.getCaretPosition();
			String text1 = text.substring(0, pos);
			String text2 = text.substring(pos);
			text = text1 + e.getKeyChar() + text2;
		}
		dialogueDialogues.dialogues[0].name = text;
		data.updateFrame(dialogueDialogues.dialogues[0], 0);
	}
}
