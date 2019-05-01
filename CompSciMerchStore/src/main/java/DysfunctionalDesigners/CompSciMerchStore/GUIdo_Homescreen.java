package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class GUIdo_Homescreen extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_Homescreen.class.getName());
	private static final String SCHPIEL = "Our store vision is simple. "
			+ "We strive to give our users a shopping experience filled "
			+ "with reasonably-priced, entertaining, and relatable merchandise. "
			+ "Our merchandise is related directly to being a Computer Science "
			+ "Major at Baylor University. With the use of our product, we believe "
			+ "Computer Science Majors will better be able to understand and relate "
			+ "to each other.";
	private final static ItemInfo display_item1 = Catalogue.getInstance().getItem(00000),
						          display_item2 = Catalogue.getInstance().getItem(00001);
	
	private static BufferedImage cashn = null;
			
	/**
	 * This sets up the GUIdo_Homescreen instance 
	 */
	public GUIdo_Homescreen(ActionListener done, int width){
		super(1500);
		logger.info("Switched to Homescreen");
		this.setBackground(Color.WHITE);
		this.setSize(new Dimension(width, 800));
		
		System.out.println("width = " + width);
		
		GUIdo_CButton display_item_1 = new GUIdo_CButton(this.getWidth()*1/10, this.getWidth()*3/10*3/4+this.getWidth()*3/10, (int)(this.getWidth()*1.5/10), 30, 
				//50,50,100,100,
				"view");
		display_item_1.setActionListener_clicked(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done.actionPerformed(new ActionEvent(display_item1, ActionEvent.ACTION_PERFORMED, "display1"));
			}
		});
		this.add(display_item_1);
		
		GUIdo_CButton display_item_2 = new GUIdo_CButton(this.getWidth()*3/10, this.getWidth()*3/10*3/4+this.getWidth()*3/10, (int)(this.getWidth()*1.5/10), 30, "view");
		display_item_2.setActionListener_clicked(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done.actionPerformed(new ActionEvent(display_item2, ActionEvent.ACTION_PERFORMED, "display2"));
			}
		});
		this.add(display_item_2);
		

		
	//	this.setOpaque(false);
		
		try {
			cashn = (BufferedImage)ImageIO.read(new File(App.resourceTarget + "cashn.jpg"));
		} catch(Exception e) {
			logger.severe("Error drawing CASHN");
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw the graphical representation of this panel.
	 * 
	 * @param g the java.awt.Graphics instance to use to draw things on this
	 * 	instance of GUIdo_Homescreen
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(new Color(255,255,225));
		this.setBackground(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int currenty = this.getHeight()*2/28;
		int welcome_width = this.getWidth()*3/10;
		int welcome_x_start = this.getWidth()*6/10+15;
		Font desc_font = new Font("Cambria",Font.PLAIN, this.getWidth()/60);
		
		g.setColor(new Color(176,48,96));
		g.setFont(new Font("Cambria",Font.PLAIN,this.getWidth()/17));
		
		
		g.drawImage(this.cashn, 
				this.getWidth()*1/10, this.getHeight()*1/18+100, this.getWidth()*2/5,this.getWidth()*3/14, null);
		
		
		//g.drawRect(this.getWidth()*6/10, this.getHeight()*1/10, welcome_width, this.getHeight()*7/10);
		g.drawString("Welcome!", welcome_x_start, currenty);
		currenty+=this.getFontMetrics((new Font("Cambria",Font.BOLD,this.getWidth()/16))).getHeight()/2;
		
		ArrayList<String> schpiel_lines = GUIdo_OutputTools.formatStringForPrompt(SCHPIEL, desc_font, welcome_width);
		g.setFont(desc_font);
		
		for(String line : schpiel_lines) {
			g.drawString(line, welcome_x_start, currenty);
			currenty+=this.getFontMetrics(desc_font).getHeight();
		}
		
		
		display_item1.drawDisplay(g, this.getWidth()*1/10, this.getHeight()*1/18+this.getWidth()*3/14+30, (int)(this.getWidth()*1.5/10), welcome_width*3/4);
	
		display_item2.drawDisplay(g, this.getWidth()*3/10, this.getHeight()*1/18+this.getWidth()*3/14+30, (int)(this.getWidth()*1.5/10), welcome_width*3/4);
		
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			logger.severe("ERROR: Caught InterruptException in Homescreen");
			e.printStackTrace();
		}
	}

}
