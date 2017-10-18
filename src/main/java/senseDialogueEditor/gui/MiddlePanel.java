package senseDialogueEditor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import senseDialogueEditor.data.Background;
import senseDialogueEditor.data.Backgrounds;
import senseDialogueEditor.data.Person;
import senseDialogueEditor.data.Persons;

/**
 * GUI panel which shows the main area of the GUI.
 * Contains the dialogue view which means:
 * Background, characters, dialogue text and all the combo boxes.
 *
 * @author Parrexion
 */
public class MiddlePanel extends JPanel {

	private static final String RESOURCES_IMAGES_GREEN_PNG = "resources/images/green.png";
	private static final long serialVersionUID = 1L;

	public boolean manipulating = false;
	
	public Backgrounds bkgs;
	public JComboBox<String> backgrounds;
	public PicturePanel bkgPanel;
	
	public Persons persons;
	private List<JComboBox<String>> characters;
	private List<JComboBox<String>> charPoses;
	public List<JLabel> personImages;
	/** Textfield containing the name of the extra character in the middle.*/
	public JTextField unknownSpeaker;
	
	public JRadioButton[] buttons;
	public int talkingIndex;
	private JLabel talkingImage;
	private JTextField closeupChar;
	private JTextField closeupPose;
	public JTextArea dialogueText;

	private ButtonGroup group;


	public MiddlePanel(ActionListener listner,KeyListener listner2, Backgrounds bkgs, Persons persons) {
		super();

		this.persons = persons;
		characters = new ArrayList<JComboBox<String>>();
		charPoses = new ArrayList<JComboBox<String>>();
		personImages = new ArrayList<>();
		closeupChar = new JTextField();
		closeupPose = new JTextField();
		
		for (int i = 0; i < 4; i++) {
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("");
			for(Person p : persons.personList){
				combo.addItem(p.name);
			}
			combo.addActionListener(listner);
			combo.setActionCommand("person,"+i);
			characters.add(combo);
			
			combo = new JComboBox<String>();
			combo.addItem("");
			combo.addActionListener(listner);
			combo.setActionCommand("person,"+i);
			charPoses.add(combo);
		}

		this.bkgs = bkgs;
		backgrounds = new JComboBox<>();
		for(Background b : bkgs.backgrounds){
			backgrounds.addItem(b.name);
		}
		backgrounds.addActionListener(listner);
		backgrounds.setActionCommand("bkg");
		
		talkingIndex = -1;

		this.setBorder(BorderFactory.createTitledBorder("Scene"));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GUI.SCREENWIDTH, GUI.SCREENHEIGHT));

		this.add(createMiddlePanelVisual(listner,listner2), BorderLayout.CENTER);
		this.add(createMiddlePanelTextPart(listner2), BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the background of the dialogue to the background at the given index.
	 * @param index
	 */
	public void setBackgroundImage(int index){
		bkgPanel.background = bkgs.backgrounds.get(index).image;
		bkgPanel.repaint();
	}
	
	/**
	 * Updates the character image and pose to the given values for the character at position.
	 * @param position
	 * @param charIndex
	 * @param poseIndex
	 * @param lastPose
	 */
	public void setPersonImage(int position, int charIndex, int poseIndex){
		
		manipulating = true;
		
		if (charIndex < 0){
			personImages.get(position).setIcon(null);
			charPoses.get(position).removeAllItems();
			charPoses.get(position).addItem("");
			setCharacterSelection(position, -1, -1);
		}
		else {
			int lastPose = Math.max(0, poseIndex);
			charPoses.get(position).removeAllItems();
			if (charPoses.get(position).getItemCount() == 0) {
				for(String s : persons.personList.get(charIndex).poseNames)
					charPoses.get(position).addItem(s);
			}
			setCharacterSelection(position, charIndex, lastPose);
			personImages.get(position).setIcon((Icon)persons.personList.get(charIndex).poses[lastPose]);
			bkgPanel.repaint();
		}
		manipulating = false;
	}
	
	/**
	 * Updates the character using the current state of the combo boxes.
	 * @param position
	 */
	public void updatePersonImage(int position){
		int charIndex = getSelectedCharacterIndex(position);
		int poseIndex = getSelectedPoseIndex(position);
		setPersonImage(position, charIndex, poseIndex);
	}
	
	public void setTalkingPerson(int talkingIndex){
		this.talkingIndex = talkingIndex;
		group.clearSelection();
		if (talkingIndex != -1){
			group.setSelected(buttons[talkingIndex].getModel(), true);
		}
	}
	
	/**
	 * Updates the closeup picture for the talking character.
	 * @param talkingPosition
	 */
	public void setTalkingIcon(int talkingPosition){
		closeupChar.setText("");
		closeupPose.setText("");
		talkingImage.setIcon(null);
		if (talkingPosition == 4){
			validate();
			closeupChar.setText(unknownSpeaker.getText());
		}
		else if (talkingPosition > -1) {
			int imageCharacter = characters.get(talkingPosition).getSelectedIndex()-1;
			int imagePose = charPoses.get(talkingPosition).getSelectedIndex();
			if (imageCharacter > -1){
				talkingImage.setIcon((Icon) persons.personList.get(imageCharacter).poses[imagePose]);
				closeupChar.setText(characters.get(talkingPosition).getSelectedItem().toString());
				closeupPose.setText(charPoses.get(talkingPosition).getSelectedItem().toString());
			}
		}
		bkgPanel.repaint();
	}
	
	private JPanel createMiddlePanelVisual(ActionListener listner,KeyListener listner2){
		bkgPanel = new PicturePanel(bkgs.backgrounds.get(0).filename);
		bkgPanel.setBorder(BorderFactory.createTitledBorder("Visual"));
		bkgPanel.setLayout(new GridLayout(0,5,4,0));

		group = new ButtonGroup();
		buttons = new JRadioButton[5];
		for (int i = 0; i < 5; i++) {
			buttons[i] = new JRadioButton("Talking");
			buttons[i].setActionCommand("radio,"+i);
			buttons[i].addActionListener(listner);
			group.add(buttons[i]);
		}
		
		bkgPanel.add(createMiddlePanelVisualCharacter(0,buttons[0]));
		bkgPanel.add(createMiddlePanelVisualCharacter(2,buttons[2]));
		bkgPanel.add(createMiddlePanelVisualBackground(buttons[4],listner2));
		bkgPanel.add(createMiddlePanelVisualCharacter(3,buttons[3]));
		bkgPanel.add(createMiddlePanelVisualCharacter(1,buttons[1]));
		
		return bkgPanel;
	}

	private JPanel createMiddlePanelVisualCharacter(int id, JRadioButton button){
		JPanel panel = new JPanel();
		String str = "<html><font style=\"background-color: #DDDDDD\">Character " + id + "</font></html>";
		panel.setBorder(BorderFactory.createTitledBorder(str));
		panel.setLayout(new BorderLayout());
		
		panel.setOpaque(false);
		panel.add(createMiddlePanelVisualCharacterOptions(id), BorderLayout.NORTH);
		panel.add(createMiddlePanelVisualCharacterImage(id,button), BorderLayout.CENTER);
		
		return panel;
	}

	private JPanel createMiddlePanelVisualCharacterOptions(int id){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Options " + id));
		panel.setLayout(new BorderLayout());

		panel.add(characters.get(id), BorderLayout.NORTH);
		panel.add(charPoses.get(id), BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createMiddlePanelVisualCharacterImage(int id, JRadioButton button){
		JPanel panel = new JPanel();
		String str = "<html><font style=\"background-color: #DDDDDD\">Image " + id + "</font></html>";
		panel.setBorder(BorderFactory.createTitledBorder(str));
		panel.setLayout(new BorderLayout());
		
		ImageIcon character = persons.personList.get(0).poses[0];
		JLabel label = new JLabel(character);
		label.setIcon(null);
		personImages.add(Math.min(id,personImages.size()), label);
		
		panel.setOpaque(false);
		panel.add(label, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createMiddlePanelVisualBackground(JRadioButton button, KeyListener listner){
		JPanel panel = new JPanel();
		String str = "<html><font style=\"background-color: #DDDDDD\">Background" + "</font></html>";
		panel.setBorder(BorderFactory.createTitledBorder(str));
		panel.setLayout(new BorderLayout());

		panel.setOpaque(false);
		panel.add(createMiddlePanelVisualBackgroundSetBackground(), BorderLayout.NORTH);
		panel.add(createMiddlePanelVisualBackgroundSetTalking(button,listner), BorderLayout.SOUTH);
		
		return panel;
	}

	private JPanel createMiddlePanelVisualBackgroundSetBackground(){
		JPanel panel = new JPanel();
		String str = "<html><font style=\"background-color: #DDDDDD\">Background" + "</font></html>";
		panel.setBorder(BorderFactory.createTitledBorder(str));
		panel.setLayout(new BorderLayout());

		panel.setOpaque(false);
		panel.add(backgrounds, BorderLayout.NORTH);
		
		return panel;
	}

	private JPanel createMiddlePanelVisualBackgroundSetTalking(JRadioButton button,KeyListener listner){
		JPanel panel = new JPanel();
		String str = "<html><font style=\"background-color: #DDDDDD\">Character talking" + "</font></html>";
		panel.setBorder(BorderFactory.createTitledBorder(str));
		panel.setLayout(new BorderLayout());

		unknownSpeaker = new JTextField("???");
		unknownSpeaker.setFont(new Font("Arial", Font.PLAIN, 16));
		unknownSpeaker.addKeyListener(listner);
		
		panel.setOpaque(false);
		panel.add(unknownSpeaker, BorderLayout.NORTH);
		panel.add(button, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createMiddlePanelTextPart(KeyListener listner){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Text box"));
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(800, 140));
		
		panel.add(createMiddlePanelTextPartImage(), BorderLayout.WEST);
		panel.add(createMiddlePanelTextPartText(listner), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMiddlePanelTextPartImage(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Image"));
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(330, 140));
		
		panel.add(createMiddlePanelTextPartImageIcon(), BorderLayout.CENTER);
		panel.add(createMiddlePanelTextPartImageOptions(), BorderLayout.WEST);
		
		return panel;
	}
	
	private JPanel createMiddlePanelTextPartImageIcon(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Face"));
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(130, 140));
		
		ImageIcon img = new ImageIcon(RESOURCES_IMAGES_GREEN_PNG);
		talkingImage = new JLabel(img);
		talkingImage.setIcon(null);
		panel.add(talkingImage, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMiddlePanelTextPartImageOptions(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Talking options"));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(200, 140));

		closeupChar.setEditable(false);
		closeupPose.setEditable(false);
		
		panel.add(closeupChar);
		panel.add(Box.createRigidArea(new Dimension(0, 8)));
		panel.add(closeupPose);
		
		return panel;
	}
	
	private JPanel createMiddlePanelTextPartText(KeyListener listner){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Text"));
		panel.setLayout(new BorderLayout());
		
		dialogueText = new JTextArea();
		dialogueText.setFont(new Font("Arial", Font.PLAIN, 24));
		dialogueText.setText("Dialogue text");
		dialogueText.setLineWrap(true);
		dialogueText.setWrapStyleWord(true);
		dialogueText.addKeyListener(listner);
		dialogueText.setPreferredSize(new Dimension(200, 30));
		panel.add(dialogueText, BorderLayout.CENTER);
		
		return panel;
	}


	private void setCharacterSelection(int position, int indexChar, int indexPose) {
		characters.get(position).setSelectedIndex(indexChar+1);
		if (indexChar < 0)
			indexPose = 0;
		charPoses.get(position).setSelectedIndex(indexPose);
	}
	public int getSelectedCharacterIndex(int position) {
		if (position < 0 || position == 4)
			return -1;
		return characters.get(position).getSelectedIndex()-1;
	}
	public int getSelectedPoseIndex(int position) {
		if (position < 0 || position == 4)
			return -1;
		return charPoses.get(position).getSelectedIndex();
	}
	public String getTalkingCharacter() {
		return closeupChar.getText();
	}
	public String getTalkingPose() {
		return closeupPose.getText();
	}
	public void setUnknownSpeakerName(String newName){
		closeupChar.setText(newName);
	}
}
