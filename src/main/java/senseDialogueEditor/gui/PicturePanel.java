package senseDialogueEditor.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private static final String RESOURCES_IMAGES_BACKGROUND = "resources/images/TestBackground.jpg";

	public BufferedImage background;
	
	public PicturePanel(String filename) {
		super();
		
		background = null;
		try {
		    background = ImageIO.read(new File(filename));
		    Image img = background.getScaledInstance(GUI.SCREENWIDTH, GUI.SCREENHEIGHT, Image.SCALE_SMOOTH);
		    background = new BufferedImage(GUI.SCREENWIDTH, GUI.SCREENHEIGHT, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = background.createGraphics();
		    g2d.drawImage(img,0,0,null);
		    g2d.dispose();
		    //System.out.println("pic");
		} catch (IOException e) {
			System.out.println("no pic :(");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}
