package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIdo_ContactUs extends GUIdo_CPanel{

	//whatever, user, email, message, title
	JLabel l1, l2, l3, l4, l5;
	//user, email, message
	JTextField tf1, tf2, tf3;
	GUIdo_CButton btn1;
	
	GUIdo_ContactUs(final ActionListener al){
		super(800);
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		l1 = new JLabel("Contact Us");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Enter username:");
		l3 = new JLabel("Enter email:");
		l4 = new JLabel("Enter message/complaint:");
		l5 = new JLabel("Contact Us!");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		
		btn1 = new GUIdo_CButton(750, 750, 170, 30, "Submit");
		
		l2.setBounds(100, 100, 200, 30);
		l3.setBounds(300, 100, 200, 30);
		l4.setBounds(100, 200, 200, 30);
		l5.setBounds(400, 30, 200, 30);
		
		tf1.setBounds(100, 140, 200, 30);
		tf2.setBounds(300, 140, 200, 30);
		tf3.setBounds(100, 240, 400, 100);
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!tf1.getText().isEmpty()) {
					for(int i = 0; i < control.getAllCustomers().size(); i++) {
						if(tf1.getText().equals(control.getAllCustomers().get(i).getUserName())) {
							
						}
					}
				}
			}
			
		});
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
		this.add(l4);
		this.add(l5);
		
		this.add(tf1);
		this.add(tf2);
		this.add(tf3);
		
		this.add(btn1);
	}
}
