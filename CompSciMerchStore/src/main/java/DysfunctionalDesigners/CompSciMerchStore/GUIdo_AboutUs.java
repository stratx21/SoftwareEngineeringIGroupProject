package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GUIdo_AboutUs extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_AboutUs.class.getName());

	JLabel aboutUs;
	JTextArea description;
	
	/**
	 * Function draws the about us page.
	 */
	GUIdo_AboutUs(){
		super(600);	
		this.setBackground(Color.WHITE);
		logger.info("Switched to panel AboutUs");
		
		aboutUs = new JLabel("About us\n");
		
		
		
//		description = new JTextArea("We are the Dysfunctional Designers!\n\n"
//				+ "We are Computer Science Students in CSI 3471.\n\n" 
//				+ "Our team consists of 6 people: Ethan Dickey, Josh Holland,\n\n"
//				+ "Mackenna Semeyn, Em Lakin, Harrison Rogers, and Josh Hanscheck.\n\n"
//				+ "You can find us in the second row on the left side during class and\n\n"
//				+ "our favorite theme when presenting is imitating the great and powerful Dr. Booth.\n\n");
//		
		//1 per line
		String about = "Welcome to our store, we hope you are enjoying your shopping experience! \n"
				     + "Our store was founded with the intent of creating a warm and welcoming atmosphere for our shoppers who are interested in Computer Science at Baylor. \n"
				     + "Whether or not you are looking to buy something for yourself or others, we hope we can be here for you!\n"
				     + "Our merchandise is targeted at a select market: those who understand and/or support the daily life of a Baylor Computer Science Major.\n"
				     + "Our merch is dedicated to the amazing professors we have had throughout the first two years of our degree. \n"
				     + "We hope you can support us! Have a wonderful day!";
		
		String about2 = "Welcome to our store, we hope you are enjoying your shopping experience! Our store was \n"
				+ "founded with the intent of creating a warm and welcoming atmosphere for our shoppers \n"
				+ "who are interested in Computer Science at Baylor. Whether or not you are looking to \n"
				+ "buy something for yourself or others, we hope we can be here for you! Our merchandise \n"
				+ "is targeted at a select market: those who understand and/or support the daily life of \n"
				+ "a Baylor Computer Science Major. Our merch is dedicated to the amazing professors we \n"
				+ "have had throughout the first two years of our degree. We hope you can support us! \n"
				+ "Have a wonderful day!";
		
		description = new JTextArea(about2);
				
		
		JLabel who = new JLabel("Brought to you by: Ethan Dickey, Josh Holland, Em Lakin, \n"
				+ "Mackenna Semeyn, Harrison Rogers, and Josh Hanscheck");
		
		aboutUs.setHorizontalAlignment(JLabel.CENTER);
		aboutUs.setVerticalAlignment(JLabel.CENTER);
		//aboutUs.setBounds(100, 0, 100, 100);
		aboutUs.setFont(new Font("Cambria", Font.BOLD, 34));
		
		description.setBackground(this.getBackground());
		description.setFont(new Font("Cambria", Font.BOLD, 22));
		//description.setBounds(50, 100, 600, 700);
		
		who.setFont(new Font("Cambria",Font.ITALIC, 18));
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		//c.insets = new Insets(1, 10, 1, 10);
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(aboutUs,c);
		
		c.gridy = 1;
		
		this.add(description,c);
		c.gridy = 2;
		this.add(who,c);
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
	
	}
}
