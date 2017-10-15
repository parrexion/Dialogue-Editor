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
	public List<JComboBox<String>> characters;
	public List<JComboBox<String>> charPoses;
	public List<JLabel> personImages;
	/** Textfield containing the name of the extra character in the middle.*/
	public JTextField unknownSpeaker;
	
	public JRadioButton[] buttons;
	public int talkingIndex;
	public JLabel talkingImage;
	public JTextField closeup;
	public JTextField closePose;
	public JTextArea dialogueText;

	public ButtonGroup group;

	

	public MiddlePanel(ActionListener listner,KeyListener listner2, Backgrounds bkgs, Persons persons) {
		super();

		this.persons = persons;
		characters = new ArrayList<JComboBox<String>>();
		charPoses = new ArrayList<JComboBox<String>>();
		personImages = new ArrayList<>();
		closeup = new JTextField();
		closePose = new JTextField();
		
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
			combo.setActionCommand("pose,"+i);
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
	
	public void setBackgroundImage(int index){
		
		bkgPanel.background = bkgs.backgrounds.get(index).image;
		bkgPanel.repaint();
	}
	
	/**
	 * Updates the character at index to the selected character.
	 * @param position Character index
	 * @param charIndex Selected character
	 */
	public void setPersonImage(int position, int charIndex, int lastPose){
		
		manipulating = true;
		
		if (charIndex < 0){
			personImages.get(position).setIcon(null);
			charPoses.get(position).removeAllItems();
			charPoses.get(position).addItem("");
		}
		else {
			if (lastPose < 0)
				lastPose = 0;
			personImages.get(position).setIcon((Icon)persons.personList.get(charIndex).poses[lastPose]);
			charPoses.get(position).removeAllItems();
			for(String s : persons.personList.get(charIndex).poseNames)
				charPoses.get(position).addItem(s);
			charPoses.get(position).setSelectedIndex(lastPose);
			bkgPanel.repaint();
		}
		manipulating = false;
	}
	
	public void setPersonPose(int index, int poseIndex){
		
		if (poseIndex < 0){
			return;
		}
		else {
			int image = characters.get(index).getSelectedIndex()-1;
			if (image >= 0){
				personImages.get(index).setIcon((Icon) persons.personList.get(image).poses[poseIndex]);
	//			System.out.println("index: " + image + ", selected: " + selected);
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

		closeup.setEditable(false);
		closePose.setEditable(false);
		
		panel.add(closeup);
		panel.add(Box.createRigidArea(new Dimension(0, 8)));
		panel.add(closePose);
		
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
//		dialogueText.getDocument().addDocumentListener(generateDocumentListener());
		dialogueText.setPreferredSize(new Dimension(200, 30));
		panel.add(dialogueText, BorderLayout.CENTER);
		
		return panel;
	}

	
//	public DocumentListener generateDocumentListener() {
//	return new DocumentListener() {
//		  public void changedUpdate(DocumentEvent e) {
//		    check();
//		  }
//		  public void removeUpdate(DocumentEvent e) {
//		    check();
//		  }
//		  public void insertUpdate(DocumentEvent e) {
//		    check();
//		  }
//
//		  public void check() {
//		     if (dialogueText.getLineCount()>3){//make sure no more than 3 lines
//		       dialogueText.set
//		       System.out.println("Rows are now: " + dialogueText.getLineCount());
//		     }
//		  }
//		};
//	}
}
