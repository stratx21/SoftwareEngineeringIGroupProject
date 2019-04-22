package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class GUIdo_FAQScreen extends GUIdo_CPanel{

	//whatever, text, title
	JLabel l1, l2, l3;
	
	GUIdo_FAQScreen(final ActionListener al){
		super(600);
		
		l1 = new JLabel("FAQ");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("How do I search?\n"
				+ "Answer: In the top left corner of the screen is a home button. Beside that is "
				+ "is the word search. Click and backspace the word and type in what you're searching for!\n"
				+ "How do I see items by professor?\n"
				+ "Answer: Across the top of the home screen are the names of each of the professors featured\n"
				+ "in our store. Simply click on whichever you would like to view!\n"
				+ "How do I upload an item?\n"
				+ "Answer: \n"
				+ "Why is my item not on the market?\n"
				+ "Answer: Either your item's stock has reached zero or your item went against the store\n"
				+ "policies and was removed.\n"
				+ "How do I add something to my wishlist?\n"
				+ "Answer: On the item page you're viewing, there is a small heart next to the image. Clicking\n"
				+ "this will turn the heart black and add the item to your wishlist. You can view your wishlist\n"
				+ "by clicking the larger heart in the top right corner of the screen.\n"
				+ "How do I review an item?\n"
				+ "Answer: \n");
		l3 = new JLabel("Frequently Asked Questions!");
		
		l2.setBounds(50, 50, 400, 500);
		l3.setBounds(300, 50, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
	}
}
