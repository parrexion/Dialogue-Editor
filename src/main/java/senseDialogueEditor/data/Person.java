package senseDialogueEditor.data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Class which contains the different images for the characters as well
 * as a list of all the poses available in the game.
 *
 * @author Parrexion
 */
public class Person {

	private final String charFolder = "resources/Characters/";
	private final String poseFolder = "resources/Poses/";
	
	private int uuid;
	public String name;
	public String charFilePath;
	public String[] poseNames;
	public ImageIcon[] poses;
	public ImageIcon[] closeups;
	
	public Person(String name, String charColor) {
		this.name = name;
		charFilePath = charFolder + charColor + ".png";

		poseNames = new String[Persons.defaultPoseNames.length];
		poses = new ImageIcon[Persons.defaultPoseNames.length];
		closeups = new ImageIcon[Persons.defaultPoseNames.length];
		
		try {
			final BufferedImage imgColor = ImageIO.read(new File(charFilePath));
			BufferedImage poseImage;
			BufferedImage paintedImage;
			Graphics2D g2d;
			
			for (int i = 0; i < Persons.defaultPoseNames.length; i++) {
				poseNames[i] = Persons.defaultPoseNames[i];
				poseImage = ImageIO.read(new File(poseFolder + Persons.defaultPoseNames[i] + ".png"));
				paintedImage = new BufferedImage(imgColor.getWidth(), imgColor.getHeight(), BufferedImage.TYPE_INT_ARGB);
				
				g2d = paintedImage.createGraphics();
				g2d.drawImage(imgColor, 0, 0, null);
				g2d.drawImage(poseImage, 0, 0, null);
				g2d.dispose();
			    
				poses[i] = new ImageIcon(paintedImage);
			    closeups[i] = poses[i];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getPoseNames(){
		return Persons.defaultPoseNames;
	}
}
