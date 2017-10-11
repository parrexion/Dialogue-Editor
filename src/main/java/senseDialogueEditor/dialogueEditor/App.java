package senseDialogueEditor.dialogueEditor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.google.gson.Gson;

import senseDialogueEditor.gui.GUI;


public class App {
	
	public static DialogueDialogues dialogue = null;
	public static String filepath = "C:\\Users\\Parrexion\\Documents\\2D Prototype - Sense\\Assets\\Resources\\";
	public static String backgroundFilepath = "C:\\Users\\Parrexion\\Documents\\2D Prototype - Sense\\Assets\\Backgrounds\\";
	public static String name = "test.json";
	public static String fullName = "";
	
    public static void main( String[] args ) {
    	
    	
//    	TemporaryDialogue();
//    	printFile();
    	    	
    	
//    	chooseFile();
    	readFile();

    	
    	new GUI(dialogue);
    	
    }
    
    public static void printFile() {
    	String filename = filepath + name;
    	Gson gson = new Gson();  
    	String userJson = gson.toJson(dialogue);      	
    	//System.out.println(userJson);
    	
    	try (FileWriter file = new FileWriter(filename)) {

            file.write(userJson);
            file.flush();
            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void readFile() {
    	String filename = /*fullName;*/filepath + name;
    	Gson gson = new Gson();  
    	
    	try (Reader reader = new FileReader(filename)) {
    	
	    	dialogue = gson.fromJson(reader, DialogueDialogues.class);
//	    	System.out.println(dialogue.lines[0].dataList.length);
//	    	System.out.println(dialogue.toString());
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
	private static void chooseFile(){

    	JFileChooser chooser = new JFileChooser();
    	chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Dialogue files *.json";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".json"))
					return true;
				if (f.isDirectory())
					return true;
				return false;
			}
		});
    	int retVal = chooser.showOpenDialog(null);
    	
    	if (retVal != JFileChooser.APPROVE_OPTION){
    		System.exit(1);
    	}
    	
    	fullName = chooser.getSelectedFile().getAbsolutePath();
    	
    }
    
    public static DialogueScene addScene(int bkg,int[] pos,int[] cpose,String cName,String text,int tPosition,int tChar,int tPose) {
    	DialogueScene scene = new DialogueScene();
		scene.background = bkg;
		scene.positions = pos;
		scene.currentPoses = cpose;
		scene.characterName = cName;
		scene.dialogue = text;
		scene.talkingPosition = tPosition;
		scene.talkingCharacter = tChar;
		scene.talkingPose = tPose;
		
		return scene;
    }
    

	private static void TemporaryDialogue(){

    	dialogue = new DialogueDialogues(1);
    	DialogueLines lineEntry = new DialogueLines("Test 1");
    	dialogue.lines[0] = lineEntry;
    	
    	int bkg = 0;
    	int[] pos = new int[]{-1,-1,-1,-1,-1};
    	int[] cpose = new int[5];
    	String cName = "";
    	String text = "";
    	int tPosition = -1;
    	int tChar = -1;
    	int tPose = -1;
    	
    	pos = new int[]{0,1,-1,-1,-1};
    	cpose = new int[]{0,2,-1,-1,-1};
    	tPosition = 1;
    	tChar = 1;
    	tPose = 1;
    	cName = "Orien";
    	text = "Hello there!";
    	lineEntry.AddAction(addScene(bkg,pos,cpose,cName,text,tPosition,tChar,tPose));
    	
    	bkg = 1;
    	pos = new int[]{0,2,1,3,-1};
    	cpose = new int[]{0,2,0,1,-1};
    	tChar = 3;
    	tPose = 1;
    	cName = "Joe";
    	text = "Hello to you as well!";
    	lineEntry.AddAction(addScene(bkg,pos,cpose,cName,text,tPosition,tChar,tPose));
    	
    	bkg = 2;
    	pos = new int[]{-1,2,-1,3,-1};
    	cpose = new int[]{0,2,0,1,-1};
    	tChar = 4;
    	tPose = -1;
    	cName = "";
    	text = "This is a long text so I hope that the dialogue box can handle long lines like this.\nAnd new line characters as well.";
    	lineEntry.AddAction(addScene(bkg,pos,cpose,cName,text,tPosition,tChar,tPose));
    	
    }
}
