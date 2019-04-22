package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class GUIdo_AboutUs extends GUIdo_CPanel{

	JLabel l1, l2, l3;
	
	GUIdo_AboutUs(final ActionListener al){
		
		super(600);
		
		l1 = new JLabel("About us");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("We are Dysfunctional Designers! "
				+ "We are Computer Science Students in CSI 3471." 
				+ "Our team consists of 6 people: Ethan Dickey, Josh Holland,"
				+ "Mackenna Semeyn, Em Lakin, Harrison Rogers, and Josh Hanscheck."
				+ "You can find us in the second row on the left side during class and"
				+ " our favorite theme when presenting is imitating the great and powerful Dr. Booth.");
		l3 = new JLabel("About Us!");
		
		l2.setBounds(50, 50, 400, 500);
		l3.setBounds(300, 50, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
	}
}
