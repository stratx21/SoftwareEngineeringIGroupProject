package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class GUIdo_ForgotPassword extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ForgotPassword.class.getName());
	
	//whatever, user, mother's, new pass, confirm new pass
	
	
	GUIdo_ForgotPassword(final ActionListener al){
		super(500);
		logger.info("Switched to ForgotPassword");
		this.setBackground(Color.WHITE);
		
		JLabel l1, enterUN, l3, l4, l5, title;
		
		JTextField tf1, tf2, tf3, tf4;
		
		GUIdo_CButton btn1;
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		//l1 = new JLabel("Username");
		//l1.setForeground(Color.blue);
		
		
		enterUN = new JLabel("Enter username:");
		enterUN.setFont(new Font("Cambria",Font.PLAIN,20));
		l3 = new JLabel("Enter mother's maiden name:");
		l3.setFont(new Font("Cambria",Font.PLAIN,20));
		l4 = new JLabel("Enter new password:");
		l4.setFont(new Font("Cambria",Font.PLAIN,20));
		l5 = new JLabel("Confirm new password:");
		l5.setFont(new Font("Cambria",Font.PLAIN,20));
		title = new JLabel("Reset Password");
		title.setFont(new Font("Camrbia", Font.BOLD, 34));
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		
		btn1 = new GUIdo_CButton(450, 450, 170, 30, "Reset Password");
		btn1.setPreferredSize(new Dimension(10,50));
		
//		enterUN.setBounds(50, 50, 200, 30);
//		l3.setBounds(50, 120, 200, 30);
//		l4.setBounds(50, 190, 200, 30);
//		l5.setBounds(50, 260, 200, 30);
//		title.setBounds(250, 30, 200, 30);
//		
//		tf1.setBounds(50, 90, 200, 30);
//		tf2.setBounds(50, 160, 200, 30);
//		tf3.setBounds(50, 230, 200, 30);
//		tf4.setBounds(50, 300, 200, 30);
//		
		
		tf1.setPreferredSize(new Dimension(new Dimension(10,50)));
		tf2.setPreferredSize(new Dimension(10,50));
		tf3.setPreferredSize(new Dimension(10,50));
		tf4.setPreferredSize(new Dimension(10,50));
		
		JTextField tf5 = new JTextField();
		tf5.setPreferredSize(new Dimension(10,50));
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tf3.getText().equals(tf4.getText())) {
					JOptionPane.showMessageDialog(panel, "Passwords are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(!tf1.getText().isEmpty()) {
					List<Customer> customers = control.getAllCustomers();
					for(int i = 0; i < customers.size(); i++) {
						Customer current = customers.get(i);
						if(tf1.getText().equals(current.getUserName())) {
							if(tf2.getText().equals(current.getMotherMaidenName())) {
								current.setPassword(tf3.getText());
								control.writeCustomer(current);
								logger.info("Set new password for " + current.getUserName());
							}else {
								JOptionPane.showMessageDialog(panel, "Mother's maiden name doesn't match!", "Error", JOptionPane.ERROR_MESSAGE);
								logger.info("Tried to change " + current.getUserName() + " password with incorrect maiden name");
							}
						}else {
							JOptionPane.showMessageDialog(panel, "Username doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
							logger.info("Tried to change " + current.getUserName() + " password with incorrect username");
						}
					}
				}
			}
			
		});
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		JLabel spacer5 = new JLabel ("\n");
		
		this.add(title,c);
		c.gridy = 1;
		this.add(spacer5,c);
		
		c.gridy = 2;
		this.add(enterUN, c);
		
		
		
		c.gridy = 4;
		this.add(enterUN,c);
		
		c.gridy = 5;
		this.add(tf2,c);
		
		c.gridy = 6;
		this.add(l3,c);
		
		c.gridy = 7;
		this.add(tf3,c);
		
		c.gridy = 8;
		this.add(l4,c);
		
		c.gridy = 9;
		this.add(tf4,c);
		
		c.gridy = 10;
		this.add(l5,c);
		
		c.gridy = 11;
		this.add(tf5,c);
//		
//		this.add(l1);
//		this.add(enterUN);
//		this.add(l3);
//		this.add(l4);
//		this.add(l5);
//		this.add(title);
//		
//		this.add(tf1);
//		this.add(tf2);
//		this.add(tf3);
//		this.add(tf4);
		
		
		c.gridy = 12;
		
		this.add(btn1,c);
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
		
	}

}
