package senseDialogueEditor.data;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import senseDialogueEditor.gui.GUI;

public class Background {

	public String name;
	public String filename;
	public BufferedImage image;
	
	
	public Background(String name, String filename) {
		this.name = name;
		this.filename = filename;
		try {
		    image = ImageIO.read(new File(filename));
		    Image img = image.getScaledInstance(GUI.SCREENWIDTH, GUI.SCREENHEIGHT, Image.SCALE_SMOOTH);
		    image = new BufferedImage(GUI.SCREENWIDTH, GUI.SCREENHEIGHT, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = image.createGraphics();
		    g2d.drawImage(img,0,0,null);
		    g2d.dispose();
		    //System.out.println("Pic: " + image.getWidth() + ", " + image.getHeight());
		} catch (IOException e) {
			System.out.println("no pic :(");
		}
	}
	

}
