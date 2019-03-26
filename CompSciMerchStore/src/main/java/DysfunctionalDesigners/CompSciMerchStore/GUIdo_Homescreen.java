package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GUIdo_Homescreen extends GUIdo_CPanel{
	private static final String SCHPIEL = "Our store vision is simple. "
			+ "We strive to give our users a shopping experience filled "
			+ "with reasonably-priced, entertaining, and relatable merchandise. "
			+ "Our merchandise is related directly to being a Computer Science "
			+ "Major at Baylor University. With the use of our product, we believe "
			+ "Computer Science Majors will better be able to understand and relate "
			+ "to each other.";
			
	/**
	 * This sets up the GUIdo_Homescreen instance 
	 */
	public GUIdo_Homescreen(){
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1000));
		
		
		
		
	}
	
	/**
	 * Draw the graphical representation of this panel.
	 * 
	 * @param g the java.awt.Graphics instance to use to draw things on this
	 * 	instance of GUIdo_Homescreen
	 */
	@Override
	public void paintComponent(Graphics g) {
		int currenty = this.getHeight()*2/10+15;
		int welcome_width = this.getWidth()*3/10;
		int welcome_x_start = this.getWidth()*6/10+15;
		Font desc_font = new Font("Ariel",Font.PLAIN, this.getWidth()/60);
		
		g.setColor(Color.PINK);
		g.setFont(new Font("Ariel",Font.PLAIN,this.getWidth()/16));
		
		
		g.drawRect(this.getWidth()*6/10, this.getHeight()*1/10, welcome_width, this.getHeight()*7/10);
		g.drawString("Welcome!", welcome_x_start, currenty);
		currenty+=this.getFontMetrics((new Font("Ariel",Font.PLAIN,this.getWidth()/16))).getHeight()/2;
		
		ArrayList<String> schpiel_lines = GUIdo_OutputTools.formatStringForPrompt(SCHPIEL, desc_font, welcome_width);
		g.setFont(desc_font);
		
		for(String line : schpiel_lines) {
			g.drawString(line, welcome_x_start, currenty);
			currenty+=this.getFontMetrics(desc_font).getHeight();
		}
		
		try {//in case the item doesn't actually exist or import properly 
			Catalogue
				.getItem(00000)
				.drawDisplay(g, this.getWidth()*1/10, this.getWidth()*4/10, this.getWidth()*2/10, welcome_width);
		} catch(Exception e) {
			System.out.println("Error importing item: ");
			e.printStackTrace();
		}
		
		try {
			g.drawImage(ImageIO.read(new File("src/main/resources/hat.jpg")), 
					this.getWidth()*1/10, this.getWidth()*1/10, this.getWidth()*2/10,this.getWidth()*2/10, null);
		} catch(Exception e) {
			System.out.println("ERROR drawing picture: ");
			e.printStackTrace();
		}
		
	}

}
