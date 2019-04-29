package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIdo_FAQScreen extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(Catalogue.class.getName());
	
	
	
	
	GUIdo_FAQScreen(){
		
		
		super(600);
		this.setBackground(Color.WHITE);
		JLabel title;
		
		logger.info("Switched to FAQ Screen.");
		
		title = new JLabel("FAQ");		
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		
		JLabel faq1 = new JLabel("How do I search for an item?");
		JLabel ans1 = new JLabel("Our searchbar is on the top of all pages! Delete the word search, and search by key word or a specific ID!");
		
		JLabel faq2 = new JLabel("How do I make a suggestion for an item?");
		JLabel ans2 = new JLabel("Click 'Other Options', navigate to 'Contact Us', and let us know what you think!");
		
		JLabel faq3 = new JLabel("I have an item that would be great to sell! How do I add it to the store?");
		JLabel ans3 = new JLabel("Click 'Other Options', and navigate to 'Add an Item'!");
		
		JLabel faq4 = new JLabel("Why can't I find the item I listed for sale?");
		JLabel ans4 = new JLabel("A couple things could have happened here. Check that your item is still in stock, and that it wasn't removed.\n"
				+ "If your issues persist, please reach out!");
		
		JLabel faq5 = new JLabel("I changed my mind, how do I remove my item from your store?");
		JLabel ans5 = new JLabel("On the same page you can edit your item information, you can disable it!");
		
		JLabel faq6 = new JLabel("I have a question that isn't answered on this page. What do I do?");
		JLabel ans6 = new JLabel("Please reach out to use using the 'Contact Us' page! We'd be happy to help!");
		
				
//		faq1 = new JTextArea("How do I search?\n\n"
//				+ "Answer: In the top left corner of the screen is a home button. Beside that is\n "
//				+ "is the word search. Click and backspace the word and type in what you're searching for!\n\n"
//				+ "How do I see items by professor?\n\n"
//				+ "Answer: Across the top of the home screen are the names of each of the professors featured\n"
//				+ "in our store. Simply click on whichever you would like to view!\n\n"
//				+ "How do I upload an item?\n\n"
//				+ "Answer: \n\n"
//				+ "Why is my item not on the market?\n\n"
//				+ "Answer: Either your item's stock has reached zero or your item went against the store\n"
//				+ "policies and was removed.\n\n"
//				+ "How do I add something to my wishlist?\n\n"
//				+ "Answer: On the item page you're viewing, there is a small heart next to the image. Clicking\n"
//				+ "this will turn the heart black and add the item to your wishlist. You can view your wishlist\n"
//				+ "by clicking the larger heart in the top right corner of the screen.\n\n"
//				+ "How do I review an item?\n\n"
//				+ "Answer: \n\n");
		
		
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		
//		faq1.setHorizontalAlignment(JLabel.CENTER);
//		faq1.setVerticalAlignment(JLabel.CENTER);
//		
//		ans1.setHorizontalAlignment(JLabel.CENTER);
//		ans1.setVerticalAlignment(JLabel.CENTER);
//		
//		faq2.setHorizontalAlignment(JLabel.CENTER);
//		faq2.setVerticalAlignment(JLabel.CENTER);
//		
//		ans2.setHorizontalAlignment(JLabel.CENTER);
//		ans2.setVerticalAlignment(JLabel.CENTER);
//		
//		faq3.setHorizontalAlignment(JLabel.CENTER);
//		faq3.setVerticalAlignment(JLabel.CENTER);
//		
//		ans3.setHorizontalAlignment(JLabel.CENTER);
//		ans3.setVerticalAlignment(JLabel.CENTER);
//		
//		faq4.setHorizontalAlignment(JLabel.CENTER);
//		faq4.setVerticalAlignment(JLabel.CENTER);
//		
//		ans4.setHorizontalAlignment(JLabel.CENTER);
//		ans4.setVerticalAlignment(JLabel.CENTER);
//		
//		faq5.setHorizontalAlignment(JLabel.CENTER);
//		faq5.setVerticalAlignment(JLabel.CENTER);
//		
//		ans5.setHorizontalAlignment(JLabel.CENTER);
//		ans5.setVerticalAlignment(JLabel.CENTER);
//		
//		faq6.setHorizontalAlignment(JLabel.CENTER);
//		//faq6.setVerticalAlignment(JLabel.CENTER);
//		
//		ans6.setHorizontalAlignment(JLabel.CENTER);
//	//	ans6.setVerticalAlignment(JLabel.CENTER);
		JLabel spacer1 = new JLabel ("\n");
		JLabel spacer2 = new JLabel ("\n");
		JLabel spacer3 = new JLabel ("\n");
		JLabel spacer4 = new JLabel ("\n");
		JLabel spacer5 = new JLabel ("\n");
		JLabel bigspace = new JLabel ("\n\n");
		
		
		
		faq1.setFont(new Font("Cambria", Font.BOLD, 22));
		faq2.setFont(new Font("Cambria", Font.BOLD, 22));
		faq3.setFont(new Font("Cambria", Font.BOLD, 22));
		faq4.setFont(new Font("Cambria", Font.BOLD, 22));
		faq5.setFont(new Font("Cambria", Font.BOLD, 22));
		faq6.setFont(new Font("Cambria", Font.BOLD, 22));
		
		ans1.setFont(new Font("Cambria", Font.PLAIN, 20));
		ans2.setFont(new Font("Cambria", Font.PLAIN, 20));
		ans3.setFont(new Font("Cambria", Font.PLAIN, 20));
		ans4.setFont(new Font("Cambria", Font.PLAIN, 20));
		ans5.setFont(new Font("Cambria", Font.PLAIN, 20));
		ans6.setFont(new Font("Cambria", Font.PLAIN, 20));
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(title,c);
		
		c.gridy = 1;
		this.add(bigspace,c);
		
		c.gridy = 3;
		
		this.add(faq1,c);
		
		c.gridy = 4;
		this.add(ans1,c);
		
		c.gridy = 5;
		this.add(spacer1,c);
		
		c.gridy = 6;
		this.add(faq2,c);
		
		c.gridy = 7;
		this.add(ans2,c);
		
		c.gridy = 8;
		this.add(spacer2,c);
		
		c.gridy = 9;
		this.add(faq3,c);
		
		c.gridy = 10;
		this.add(ans3,c);
		
		c.gridy = 11;
		this.add(spacer3,c);
		
		c.gridy = 12;
		this.add(faq4,c);
		
		c.gridy = 13;
		this.add(ans4,c);
		
		c.gridy = 14;
		this.add(spacer4,c);
		
		c.gridy = 15;
		this.add(faq5,c);
		
		c.gridy = 16;
		this.add(ans5,c);
		
		c.gridy = 17;
		this.add(spacer5,c);
		
		c.gridy = 18;
		this.add(faq6,c);
		
		
		c.gridy = 19;
		this.add(ans6,c);
		c.gridy = 20;
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
	
		
	}
}










