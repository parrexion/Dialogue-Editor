package senseDialogueEditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel which contains the current dialogue information.
 * Also contains buttons for managing the frames of the current dialogue.
 * There are also several buttons for adding dialogue effects.
 *
 * @author Parrexion
 */
public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JTextField dialogueId;
	public JTextField currentFrame;
	public int frameNr;
	

	public RightPanel(ActionListener listner, KeyListener keylis,int startFrame) {
		super();
		
		frameNr = startFrame;
		
		this.setBorder(BorderFactory.createTitledBorder("Information"));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 400));

		this.add(createRightPanelInfo(keylis),BorderLayout.NORTH);
		this.add(createRightPanelOtherOptions(listner),BorderLayout.CENTER);
		this.add(createRightPanelFrameButton(listner),BorderLayout.SOUTH);

	}
	
	public void setFrame(int nr){
		frameNr = nr;
		currentFrame.setText("               "+(frameNr+1));
	}
	
	private JPanel createRightPanelInfo(KeyListener keylis){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Dialogue info"));
		panel.setLayout(new BorderLayout());
		
		dialogueId = new JTextField();
		dialogueId.setText("Test 1");
		dialogueId.setBorder(BorderFactory.createTitledBorder("Dialogue ID"));
		dialogueId.addKeyListener(keylis);

		currentFrame = new JTextField();
		currentFrame.setText("               "+frameNr);
		currentFrame.setEditable(false);
		currentFrame.setBorder(BorderFactory.createTitledBorder("Current frame"));
		
		panel.add(dialogueId,BorderLayout.NORTH);
		panel.add(currentFrame,BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createRightPanelOtherOptions(ActionListener listner){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Transitions"));
		panel.setLayout(new FlowLayout());
		
		JButton battleButton = new JButton("Add battle");
		battleButton.setPreferredSize(new Dimension(160, 30));
		battleButton.addActionListener(listner);
		battleButton.setActionCommand("addbattle");
		battleButton.setEnabled(false);
		panel.add(battleButton);
		
		battleButton = new JButton("Move to dialogue");
		battleButton.setPreferredSize(new Dimension(160, 30));
		battleButton.addActionListener(listner);
		battleButton.setActionCommand("moveToDialogue");
		battleButton.setEnabled(false);
		panel.add(battleButton);
		
		
		return panel;
	}
	
	private JPanel createRightPanelFrameButton(ActionListener listner){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Frames"));
		BorderLayout border = new BorderLayout();
		border.setVgap(8);
		panel.setLayout(border);
		panel.setPreferredSize(new Dimension(200, 150));
		
		JButton frameButton = new JButton("Add frame");
		frameButton.addActionListener(listner);
		frameButton.setActionCommand("addframe");
		
		JButton insertButton = new JButton("Insert frame");
		insertButton.addActionListener(listner);
		insertButton.setActionCommand("insertframe");
		
		JButton deleteButton = new JButton("Delete frame");
		deleteButton.addActionListener(listner);
		deleteButton.setActionCommand("deleteframe");

		panel.add(deleteButton,BorderLayout.NORTH);
		panel.add(frameButton,BorderLayout.CENTER);
		panel.add(insertButton,BorderLayout.SOUTH);
		return panel;
	}
	

}
