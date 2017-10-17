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
import senseDialogueEditor.dialogueEditor.DialogueCollection;
import senseDialogueEditor.dialogueEditor.Dialogue;
import senseDialogueEditor.dialogueEditor.Frame;

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
	
	private DialogueCollection dialogues;
	private int dialogueIndex;

	public GUI(DialogueCollection dialogues) {

		dialogueIndex = 0;

		frame = new JFrame("Dialogue Editor");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.dialogues = dialogues;

		// System.out.println(lines.dataList.length);
		dialoguePanel = new DialogueListPanel(this, dialogues);
		middlePanel = new MiddlePanel(this, this, new Backgrounds(), new Persons());
		rightPanel = new RightPanel(this, dialoguePanel, dialogues.dialogues[dialogueIndex].size - 1);
		bottomPanel = new BottomPanel(this, this, dialogues.dialogues[dialogueIndex]);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());

		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(middlePanel, BorderLayout.CENTER);

		sidePanel.add(dialoguePanel, BorderLayout.CENTER);

		loadDialogue();
		//loadFrame(rightPanel.frameNr);
		bottomPanel.lines.setRowSelectionInterval(rightPanel.frameNr, rightPanel.frameNr);

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
				dialogues.dialogues[dialogueIndex].frames.get(currFrame).background = combo.getSelectedIndex();
				bottomPanel.table.updateFrame(dialogues.dialogues[dialogueIndex].frames.get(currFrame), currFrame);
			}
			break;

		case "person":
			int position = Integer.valueOf(values[1]);
			updatePerson(position, currFrame);
			break;

		case "loadframe":
			int selectedFrame = bottomPanel.lines.getSelectedRow();
			if (selectedFrame != -1)
				loadFrame(selectedFrame);
			break;

		case "addbattle":
			addBattle();
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
	 * @param position Radio button manipulated
	 * @param remove If it should be possible to deselect the radio button
	 * @param currFrame Frame manipulated
	 */
	private void SetRadio(int position, boolean remove, int currFrame) {

		String characterName = "";
		int indexChar = middlePanel.getSelectedCharacterIndex(position);
		
		if (position == middlePanel.talkingIndex && remove) {
			//If the same is clicked again, remove selection.
			position = -1;
		} 
		if (position == 4) {
			//If it's the extra character in the middle who's speaking.
			characterName = middlePanel.unknownSpeaker.getText();
		}
		else if (indexChar < 0) {
			//If there is no character to show
			position = -1;
		}
		
		middlePanel.setTalkingPerson(position);
		middlePanel.setTalkingIcon(position);
		
		if (loading)
			return;
		
		dialogues.dialogues[dialogueIndex].frames.get(currFrame).talkingPosition = position;
		dialogues.dialogues[dialogueIndex].frames.get(currFrame).characterName = characterName;
		bottomPanel.table.updateFrame(dialogues.dialogues[dialogueIndex].frames.get(currFrame), currFrame);
	}
	
	/**
	 * Updates the person at index position after changing the combo boxes.
	 * @param position
	 * @param character
	 * @param currFrame
	 */
	private void updatePerson(int position, int currFrame) {
		middlePanel.updatePersonImage(position);
		
		if (position == middlePanel.talkingIndex)
			SetRadio(middlePanel.talkingIndex, false, currFrame);
		
		if (loading)
			return;
		
		int character = middlePanel.getSelectedCharacterIndex(position);
		int pose = middlePanel.getSelectedPoseIndex(position);
		if (character < 0)
			pose = -1;
		dialogues.dialogues[dialogueIndex].frames.get(currFrame).currentCharacters[position] = character;
			dialogues.dialogues[dialogueIndex].frames.get(currFrame).currentPoses[position] = pose;
		bottomPanel.table.updateFrame(dialogues.dialogues[dialogueIndex].frames.get(currFrame), currFrame);
	}

	private void addBattle() {

	}

	/**
	 * Insert a frame after the current selected frame.
	 */
	private void insertFrame() {
		int selectedFrame = bottomPanel.lines.getSelectedRow();
//		System.out.println("Size: " + selectedFrame);
		Frame copyScene = new Frame(dialogues.dialogues[dialogueIndex].frames.get(selectedFrame));
		copyScene.dialogueText = "";

		bottomPanel.InsertFrame(selectedFrame+1, copyScene);
		bottomPanel.table.insertFrame(selectedFrame+1, copyScene);
		dialoguePanel.data.updateFrame(dialogues.dialogues[dialogueIndex], dialogueIndex);
		//loadFrame(selectedFrame + 1);
		bottomPanel.lines.setRowSelectionInterval(selectedFrame + 1, selectedFrame + 1);
	}

	private void removeFrame() {
		if (dialogues.dialogues[dialogueIndex].frames.size() > 1) {

			int selectedFrame = rightPanel.frameNr;
			int newIndex = selectedFrame + 1;
			if (newIndex == dialogues.dialogues[dialogueIndex].frames.size())
				newIndex -= 2;

			bottomPanel.lines.setRowSelectionInterval(newIndex, newIndex);
			loadFrame(newIndex);
			bottomPanel.RemoveFrame(selectedFrame);
			bottomPanel.table.removeFrame(selectedFrame);
			dialoguePanel.data.updateFrame(dialogues.dialogues[dialogueIndex], dialogueIndex);
		}
	}

	/**
	 * Loads the selected frame and sets up all the information.
	 * @param selectedFrame Frame to load
	 */
	private void loadFrame(int selectedFrame) {
		System.out.println("Loading frame " + selectedFrame);
		loading = true;
		Frame loadScene = dialogues.dialogues[dialogueIndex].frames.get(selectedFrame);
		
		//Load background
		middlePanel.backgrounds.setSelectedIndex(loadScene.background);
		
		//Load Characters and poses
		for (int i = 0; i < 4; i++) {
			middlePanel.setPersonImage(i, loadScene.currentCharacters[i], loadScene.currentPoses[i]);
		}
		
		//Load unknown speaker name
		if (loadScene.talkingPosition == 4)
			middlePanel.unknownSpeaker.setText(loadScene.characterName);
		
		//Load talking closeup
		middlePanel.setTalkingIcon(loadScene.talkingPosition);

		//Load talking radio buttons
		middlePanel.setTalkingPerson(loadScene.talkingPosition);
		
		//Load character 4 talking
		if (loadScene.talkingPosition == 4){
			middlePanel.unknownSpeaker.setText(loadScene.characterName);
		}
		
		//Load dialogue text
		middlePanel.dialogueText.setText(loadScene.dialogueText);

		//Load frame information
		rightPanel.setFrame(selectedFrame);
		
		//Set line selection
		//bottomPanel.lines.setRowSelectionInterval(selectedFrame, selectedFrame);
		
		loading = false;
	}
	
	private void loadDialogue() {
		Dialogue currentDialogue = dialogues.dialogues[dialogueIndex];
		rightPanel.dialogueId.setText(currentDialogue.name);
	}

	/**
	 * Updates the dialogue text and unknown speaker fields whenever the user types.
	 */
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
				dialogues.dialogues[dialogueIndex].frames.get(currFrame).dialogueText = text;
				bottomPanel.table.updateFrame(dialogues.dialogues[dialogueIndex].frames.get(currFrame), currFrame);
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
				dialogues.dialogues[dialogueIndex].frames.get(currFrame).characterName = text;
				middlePanel.setTalkingIcon(4);
				middlePanel.setUnknownSpeakerName(text);
				bottomPanel.table.updateFrame(dialogues.dialogues[dialogueIndex].frames.get(currFrame), currFrame);
			}
		}
	}

	/**
	 * Calls load frame whenever the user clicks a row in the frame table.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
            return;
		int selectedFrame = bottomPanel.lines.getSelectedRow();
		loadFrame(selectedFrame);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}
}
