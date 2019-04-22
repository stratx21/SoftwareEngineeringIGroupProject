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
		
		l2 = new JLabel("");
		l3 = new JLabel("Frequently Asked Questions!");
		
		l2.setBounds(50, 50, 400, 500);
		l3.setBounds(300, 50, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
	}
}
