package senseDialogueEditor.dialogueEditor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import battleEditor.BattleEditorFrame;
import characterEditor.CharacterEditorFrame;
import enemyEditor.EnemyEditorFrame;
import senseDialogueEditor.data.DialogueCollection;
import senseDialogueEditor.gui.GUI;


public class EditorTabber {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	
	
	public EditorTabber(DialogueCollection dialogues) {
		frame = new JFrame("Dialogue Editor");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane();

		GUI gui = new GUI(dialogues);
		tabbedPane.addTab("Dialogue", gui.tabPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		CharacterEditorFrame cef = new CharacterEditorFrame();
		tabbedPane.addTab("Chars", cef);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		BattleEditorFrame bef = new BattleEditorFrame();
		tabbedPane.addTab("Battles", bef);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		EnemyEditorFrame eef = new EnemyEditorFrame();
		tabbedPane.addTab("Enemies", eef);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		
		frame.add(tabbedPane);
		frame.pack();
		frame.setVisible(true);
	}

	
}
