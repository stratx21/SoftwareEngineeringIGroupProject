package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

//Creates screen, doesn't have things in correct place. Adjust values to get spacing and placement right.

public class GUIdo_CreateAccount extends GUIdo_CPanel{
	
	//user, pass, confirm pass, email, confirm email, create an account
	JLabel l1, l2, l3, l4, l5, l6, l7;
	//user, pass, confirm pass, email, confirm email
	JTextField tf1, tf2, tf3, tf4, tf5;
	//sign up
	GUIdo_CButton btn1;
	
	GUIdo_CreateAccount(final ActionListener al){
		super(600);
		GUIdo_CPanel panel = this;
		l1 = new JLabel("Username");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Select a Username:");
		l3 = new JLabel("Enter a Password:");
		l4 = new JLabel("Confirm Password:");
		l5 = new JLabel("Enter email:");
		l6 = new JLabel("Confirm email:");
		l7 = new JLabel("Create an Account");
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		btn1 = new GUIdo_CButton(550, 550, 170, 30, "Sign Up!");
		
		l2.setBounds(50, 50, 200, 30);
		l3.setBounds(50, 100, 200, 30);
		l4.setBounds(50, 150, 200, 30);
		l5.setBounds(50, 200, 200, 30);
		l6.setBounds(50, 250, 200, 30);
		l7.setBounds(200, 30, 200, 30);
		
		tf1.setBounds(50, 60, 200, 30);
		tf2.setBounds(50, 110, 200, 30);
		tf3.setBounds(50, 160, 200, 30);
		tf4.setBounds(50, 210, 200, 30);
		tf5.setBounds(50, 260, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
		this.add(l4);
		this.add(l5);
		this.add(l6);
		this.add(l7);
		this.add(tf1);
		this.add(tf2);
		this.add(tf3);
		this.add(tf4);
		this.add(tf5);
		this.add(btn1);
		
	}

}
