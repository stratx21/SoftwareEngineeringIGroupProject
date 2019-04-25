package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class GUIdo_AboutUs extends GUIdo_CPanel{

	JLabel l1, l3;
	JTextArea l2;
	
	GUIdo_AboutUs(){
		
		super(600);
		
		l1 = new JLabel("About us");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JTextArea("We are Dysfunctional Designers!\n\n"
				+ "We are Computer Science Students in CSI 3471.\n\n" 
				+ "Our team consists of 6 people: Ethan Dickey, Josh Holland,\n\n"
				+ "Mackenna Semeyn, Em Lakin, Harrison Rogers, and Josh Hanscheck.\n\n"
				+ "You can find us in the second row on the left side during class and\n\n"
				+ "our favorite theme when presenting is imitating the great and powerful Dr. Booth.\n\n");
		l3 = new JLabel("About Us!");
		
		l2.setBackground(this.getBackground());
		l2.setFont(new Font("Arial", Font.BOLD, 12));
		l2.setBounds(50, 100, 600, 700);
		
		l3.setBounds(300, 50, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
	}
}
