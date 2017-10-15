package senseDialogueEditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import senseDialogueEditor.dialogueEditor.DialogueLines;
import senseDialogueEditor.dialogueEditor.DialogueScene;
import senseDialogueEditor.extra.SmartScroller;

/**
 * Panel containing the table of all the frames of the current dialogue.
 * Also contains the buttons for creating and saving dialogues.
 *
 * @author Parrexion
 */
public class BottomPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JTable lines;
	public FrameLineTable table;
	public DialogueLines lineData;
	private JScrollPane scrollPane;
	
	
	public BottomPanel(ActionListener listner, ListSelectionListener listlis, DialogueLines data) {
		super();
		
		table = new FrameLineTable();
		lineData = data;
		
		this.setBorder(BorderFactory.createTitledBorder("List"));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 200));
		this.add(createBottomPanelSaveLoad(listner), BorderLayout.EAST);
		this.add(createBottomPanelLines(listlis), BorderLayout.CENTER);
	}

	public void AddFrame(DialogueScene scene){
		lineData.AddAction(scene);
		validate();
	}
	public void InsertFrame(int index, DialogueScene scene){
		lineData.InsertAction(index,scene);
		validate();
	}
	public void RemoveFrame(int index){
		lineData.RemoveAction(index);
		validate();
	}
	
	private JPanel createBottomPanelSaveLoad(ActionListener listner){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Save/Load"));
		panel.setPreferredSize(new Dimension(200, 200));
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));

		JButton newButton = new JButton("New Dialogue");
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		newButton.setEnabled(false);
		JButton loadButton = new JButton("Load Dialogue");
		loadButton.setAlignmentX(CENTER_ALIGNMENT);
		loadButton.setEnabled(false);
		JButton saveButton = new JButton("Save Dialogue");
		saveButton.setAlignmentX(CENTER_ALIGNMENT);
		saveButton.addActionListener(listner);
		saveButton.setActionCommand("savedialogue");

		panel.add(Box.createRigidArea(new Dimension(0, 16)));
		panel.add(newButton);
		panel.add(Box.createRigidArea(new Dimension(0, 16)));
		panel.add(loadButton);
		panel.add(Box.createRigidArea(new Dimension(0, 16)));
		panel.add(saveButton);
		
		return panel;
	}
	
	private JPanel createBottomPanelLines(ListSelectionListener listlis){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Lines"));
		panel.setLayout(new BorderLayout());
		
		
		panel.add(createTable(listlis), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JScrollPane createTable(ListSelectionListener listlis){
		table = new FrameLineTable(lineData);
		lines = new JTable(table);
		lines.setFont(new Font("Arial",Font.PLAIN,16));
		DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
		rend.setHorizontalAlignment(SwingConstants.CENTER);
		lines.setDefaultRenderer(Object.class, rend);
		rend = (DefaultTableCellRenderer) lines.getTableHeader().getDefaultRenderer();
		rend.setHorizontalAlignment(SwingConstants.CENTER);
		JTableHeader head = lines.getTableHeader();
		head.setDefaultRenderer(rend);
		lines.setTableHeader(head);
		lines.setRowSelectionInterval(lineData.size-1, lineData.size-1);
		lines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lines.getSelectionModel().addListSelectionListener(listlis);
		
		TableColumnModel model = lines.getColumnModel();
		model.getColumn(0).setMinWidth(80);
		model.getColumn(1).setMinWidth(120);
		model.getColumn(2).setMinWidth(120);
		model.getColumn(3).setMinWidth(150);
		model.getColumn(4).setPreferredWidth(5000);
		
		scrollPane = new JScrollPane(lines);
		new SmartScroller(scrollPane);

		lines.setFillsViewportHeight(true);
		return scrollPane;
	}
	
}
