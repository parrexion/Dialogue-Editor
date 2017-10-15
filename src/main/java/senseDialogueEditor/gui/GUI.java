package senseDialogueEditor.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import senseDialogueEditor.data.Backgrounds;
import senseDialogueEditor.data.Persons;
import senseDialogueEditor.dialogueEditor.App;
import senseDialogueEditor.dialogueEditor.DialogueDialogues;
import senseDialogueEditor.dialogueEditor.DialogueLines;
import senseDialogueEditor.dialogueEditor.DialogueScene;

public class GUI implements ActionListener, KeyListener, ListSelectionListener {

	public final static int SCREENWIDTH = 900;
	public final static int SCREENHEIGHT = 500;

	public static boolean loading = false;

	private JFrame frame;
	private JPanel mainPanel;
	private JPanel sidePanel;
	private BottomPanel bottomPanel;
	private RightPanel rightPanel;
	private MiddlePanel middlePanel;
	private DialogueListPanel dialoguePanel;
	
	private DialogueDialogues dialogues;
	private int dialogueIndex;

	public GUI(DialogueDialogues dialogues) {

		dialogueIndex = 0;

		frame = new JFrame("Dialogue Editor");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.dialogues = dialogues;

		// System.out.println(lines.dataList.length);
		dialoguePanel = new DialogueListPanel(this, dialogues);
		middlePanel = new MiddlePanel(this, this, new Backgrounds(), new Persons());
		rightPanel = new RightPanel(this, dialoguePanel, dialogues.lines[dialogueIndex].size - 1);
		bottomPanel = new BottomPanel(this, this, dialogues.lines[dialogueIndex]);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());

		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(middlePanel, BorderLayout.CENTER);

		sidePanel.add(dialoguePanel, BorderLayout.CENTER);

		loadDialogue();
		loadFrame(rightPanel.frameNr);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.add(sidePanel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Handles all the actions performed in the GUI.
	 * Action command is split at "," and value[0] is the name of the action.
	 * <br>
	 * radio - <br>
	 * bkg - <br>
	 * person - <br>
	 * pose - <br>
	 * loadframe - <br>
	 * addbattle - <br>
	 * addframe - <br>
	 * insertframe - <br>
	 * deleteframe - <br>
	 * savedialogue - <br>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] values = e.getActionCommand().split(",");
		int currFrame = rightPanel.frameNr;
		switch (values[0]) {
		case "radio":
			SetRadio(Integer.valueOf(values[1]), true, currFrame);
			break;

		case "bkg":
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			middlePanel.setBackgroundImage(combo.getSelectedIndex());
			if (!loading) {
				dialogues.lines[dialogueIndex].dataList.get(currFrame).background = combo.getSelectedIndex();
				bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
			}
			break;

		case "person":
			int person = Integer.valueOf(values[1]);
			JComboBox<String> combo2 = (JComboBox<String>) e.getSource();
			int character = combo2.getSelectedIndex() - 1;
			setPerson(person, character, currFrame);
			break;

		case "pose":
			if (middlePanel.manipulating)
				return;
			int index = Integer.valueOf(values[1]);
			JComboBox<String> combo3 = (JComboBox<String>) e.getSource();
			int pose = combo3.getSelectedIndex();
			if (combo3.getSelectedItem().toString() == "")
				pose = -1;
			setPose(index, pose, currFrame);
			break;

		case "loadframe":
			int selectedFrame = bottomPanel.lines.getSelectedRow();
			if (selectedFrame != -1)
				loadFrame(selectedFrame);
			break;

		case "addbattle":
			addBattle();
			break;

		case "addframe":
			addFrame();
			break;

		case "insertframe":
			insertFrame();
			break;

		case "deleteframe":
			removeFrame();
			break;

		case "savedialogue":
			App.printFile();
			break;
		}
	}

	/**
	 * Sets the radio button at index or removes the selection if remove is true 
	 * for the frame currFrame.
	 * @param index Radio button manipulated
	 * @param remove If it's possible to deselect the radio button
	 * @param currFrame Frame manipulated
	 */
	private void SetRadio(int index, boolean remove, int currFrame) {

		if (index == -1 || (index == middlePanel.talkingIndex && remove)) {
			//If deselecting or the same is clicked again, remove selection.
			middlePanel.talkingIndex = -1;
			middlePanel.closeup.setText("");
			middlePanel.closePose.setText("");
			middlePanel.talkingImage.setIcon(null);
			middlePanel.group.clearSelection();

			if (loading)
				return;
			
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPosition = -1;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingCharacter = -1;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPose = -1;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).characterName = "";
			bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
		} 
		else if (index == 4) {
			//If it's the extra character in the middle who's speaking.
			middlePanel.talkingIndex = index;
			middlePanel.closeup.setText(middlePanel.unknownSpeaker.getText());
			middlePanel.closePose.setText("");
			middlePanel.talkingImage.setIcon(null);
			middlePanel.group.clearSelection();
			middlePanel.group.setSelected(middlePanel.buttons[4].getModel(), true);
			 System.out.println("Set radio: 4");
			if (loading)
				return;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPosition = 4;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingCharacter = -1;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPose = -1;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).characterName = middlePanel.closeup.getText();
			bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
		} 
		else {
			//If one of the normal characters are speaking
			int indexChar = middlePanel.characters.get(index).getSelectedIndex() - 1;
			int indexPose = middlePanel.charPoses.get(index).getSelectedIndex();
//System.out.println("charpos: "+index + " , "+indexChar + " , " + indexPose);

			if (indexChar < 0) {
				//If the character is undefined.
				index = -1;
				indexPose = -1;
				middlePanel.talkingIndex = -1;
				middlePanel.closeup.setText("");
				middlePanel.closePose.setText("");
				middlePanel.talkingImage.setIcon(null);
				middlePanel.group.clearSelection();
			}
			else {
				//If the character exists
				middlePanel.talkingIndex = index;
				middlePanel.closeup.setText(middlePanel.characters.get(index).getSelectedItem().toString());
				middlePanel.closePose.setText(middlePanel.charPoses.get(index).getSelectedItem().toString());
				middlePanel.talkingImage.setIcon(middlePanel.persons.personList.get(indexChar).poses[indexPose]);
				middlePanel.group.clearSelection();
				middlePanel.group.setSelected(middlePanel.buttons[index].getModel(), true);
			}
			
			if (loading)
				return;
			
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPosition = index;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingCharacter = indexChar;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).talkingPose = indexPose;
			dialogues.lines[dialogueIndex].dataList.get(currFrame).characterName = middlePanel.closeup.getText();
			bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
		}
	}
	
	/**
	 * Set the character for the person at index position.
	 * @param index
	 * @param character
	 * @param currFrame
	 */
	private void setPerson(int index, int character, int currFrame) {
		int lastPose = dialogues.lines[dialogueIndex].dataList.get(currFrame).currentPoses[index];
		middlePanel.setPersonImage(index, character, lastPose);
		if (index == middlePanel.talkingIndex || middlePanel.talkingIndex == -1 || middlePanel.talkingIndex == 4)
			SetRadio(middlePanel.talkingIndex, false, currFrame);
		
		if (loading)
			return;
		
		dialogues.lines[dialogueIndex].dataList.get(currFrame).positions[index] = character;
		if (character < 0)
			dialogues.lines[dialogueIndex].dataList.get(currFrame).currentPoses[index] = -1;
		bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
	}
	
	private void setPose(int index, int pose, int currFrame){
		middlePanel.setPersonPose(index, pose);
		if (index == middlePanel.talkingIndex)
			SetRadio(middlePanel.talkingIndex, false, currFrame);
		if (loading)
			return;
		
		dialogues.lines[dialogueIndex].dataList.get(currFrame).currentPoses[index] = pose;
		bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
	}

	private void addBattle() {

	}

	/**
	 * Appends a new frame to the dialogue at the end of it.
	 */
	private void addFrame() {
		int selectedFrame = dialogues.lines[dialogueIndex].size - 1;
		// System.out.println("Size: " + selectedFrame);
		DialogueScene copyScene = new DialogueScene(dialogues.lines[dialogueIndex].dataList.get(selectedFrame));
		copyScene.dialogue = "";

		bottomPanel.AddFrame(copyScene);
		bottomPanel.table.addFrame(copyScene);
		dialoguePanel.data.updateFrame(dialogues.lines[dialogueIndex], dialogueIndex);
		loadFrame(selectedFrame + 1);
		bottomPanel.lines.setRowSelectionInterval(selectedFrame + 1, selectedFrame + 1);
	}

	/**
	 * Insert a frame after the current selected frame.
	 */
	private void insertFrame() {
		int selectedFrame = bottomPanel.lines.getSelectedRow();
//		System.out.println("Size: " + selectedFrame);
		DialogueScene copyScene = new DialogueScene(dialogues.lines[dialogueIndex].dataList.get(selectedFrame));
		copyScene.dialogue = "";

		bottomPanel.InsertFrame(selectedFrame+1, copyScene);
		bottomPanel.table.insertFrame(selectedFrame+1, copyScene);
		dialoguePanel.data.updateFrame(dialogues.lines[dialogueIndex], dialogueIndex);
		loadFrame(selectedFrame + 1);
		bottomPanel.lines.setRowSelectionInterval(selectedFrame + 1, selectedFrame + 1);
	}

	private void removeFrame() {
		if (dialogues.lines[dialogueIndex].dataList.size() > 1) {

			int selectedFrame = rightPanel.frameNr;
			int newIndex = selectedFrame + 1;
			if (newIndex == dialogues.lines[dialogueIndex].dataList.size())
				newIndex -= 2;

			bottomPanel.lines.setRowSelectionInterval(newIndex, newIndex);
			loadFrame(newIndex);
			bottomPanel.RemoveFrame(selectedFrame);
			bottomPanel.table.removeFrame(selectedFrame);
			dialoguePanel.data.updateFrame(dialogues.lines[dialogueIndex], dialogueIndex);
		}
	}

	/**
	 * Loads the selected frame and sets up all the information.
	 * @param selectedFrame Frame to load
	 */
	private void loadFrame(int selectedFrame) {
		System.out.println("Loading frame " + selectedFrame);
		loading = true;
		DialogueScene loadScene = dialogues.lines[dialogueIndex].dataList.get(selectedFrame);
		
		//Load background
		middlePanel.backgrounds.setSelectedIndex(loadScene.background);
		
		//Load Characters and poses
		for (int i = 0; i < 4; i++) {
			middlePanel.characters.get(i).setSelectedIndex(loadScene.positions[i] + 1);
			if (loadScene.positions[i] < 0)
				middlePanel.charPoses.get(i).setSelectedIndex(0);
			else
				middlePanel.charPoses.get(i).setSelectedIndex(loadScene.currentPoses[i]);
		}
		
		//Load unknown speaker name
		if (loadScene.talkingCharacter == 4)
			middlePanel.unknownSpeaker.setText(loadScene.characterName);
		
		//Load talking closeup
		if (loadScene.talkingPosition != 4 && loadScene.talkingPosition != -1){
			middlePanel.closeup.setText(middlePanel.characters.get(loadScene.talkingPosition).getSelectedItem().toString());
			middlePanel.closePose.setText(middlePanel.charPoses.get(loadScene.talkingPosition).getSelectedItem().toString());
			middlePanel.talkingImage.setIcon(middlePanel.persons.personList.get(loadScene.talkingCharacter).poses[loadScene.talkingPose]);
		}
		else {
			if (loadScene.talkingPosition == 4)
				middlePanel.closeup.setText(loadScene.characterName);
			else
				middlePanel.closeup.setText("");
			
			middlePanel.closePose.setText("");
			middlePanel.talkingImage.setIcon(null);
		}

		//Load talking radio buttons
		middlePanel.group.clearSelection();
		middlePanel.talkingIndex = loadScene.talkingPosition;
		if (loadScene.talkingPosition != -1) {
			middlePanel.group.setSelected(middlePanel.buttons[loadScene.talkingPosition].getModel(),true);
			//middlePanel.buttons[loadScene.talkingPosition].setSelected(true);
		}
		
		//Load character 4 talking
		if (loadScene.talkingPosition == 4){
			middlePanel.unknownSpeaker.setText(loadScene.characterName);
		}
		
		//Load dialogue text
		middlePanel.dialogueText.setText(loadScene.dialogue);

		//Load frame information
		rightPanel.setFrame(selectedFrame);
		
		//Set line selection
		bottomPanel.lines.setRowSelectionInterval(selectedFrame, selectedFrame);
		
		loading = false;
		System.out.println("charpos: "+loadScene.talkingPosition + " , "+loadScene.talkingCharacter + " , " +
	 			loadScene.talkingPose);
	}
	
	private void loadDialogue() {
		DialogueLines currentDialogue = dialogues.lines[dialogueIndex];
		rightPanel.dialogueId.setText(currentDialogue.name);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int currFrame = rightPanel.frameNr;
		if (e.getSource() instanceof JTextArea) {
			JTextArea tArea = (JTextArea) e.getSource();
			if (!loading) {
				String text = tArea.getText();
				if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
					int pos = tArea.getCaretPosition();
					String text1 = text.substring(0, pos);
					String text2 = text.substring(pos);
					text = text1 + e.getKeyChar() + text2;
				}
				dialogues.lines[dialogueIndex].dataList.get(currFrame).dialogue = text;
				bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
			}
		} else if (e.getSource() instanceof JTextField) {
			JTextField tField = (JTextField) e.getSource();
			if (!loading && middlePanel.talkingIndex == 4) {
				String text = tField.getText();
				if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
					int pos = tField.getCaretPosition();
					String text1 = text.substring(0, pos);
					String text2 = text.substring(pos);
					text = text1 + e.getKeyChar() + text2;
				}
				dialogues.lines[dialogueIndex].dataList.get(currFrame).characterName = text;
				middlePanel.closeup.setText(text);
				bottomPanel.table.updateFrame(dialogues.lines[dialogueIndex].dataList.get(currFrame), currFrame);
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selectedFrame = bottomPanel.lines.getSelectedRow();
		loadFrame(selectedFrame);
	}

}
