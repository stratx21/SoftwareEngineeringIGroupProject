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
		
		JLabel  enterUN, enterMom, enterPass, confirmPass, title;
		
		JTextField tf1, getUN, getMomName, getPass1;
		
		GUIdo_CButton btn1;
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		//l1 = new JLabel("Username");
		//l1.setForeground(Color.blue);
		
		
		enterUN = new JLabel("Enter username:");
		enterUN.setFont(new Font("Cambria",Font.PLAIN,20));
		enterMom = new JLabel("Enter mother's maiden name:");
		enterMom.setFont(new Font("Cambria",Font.PLAIN,20));
		enterPass = new JLabel("Enter new password:");
		enterPass.setFont(new Font("Cambria",Font.PLAIN,20));
		confirmPass = new JLabel("Confirm new password:");
		confirmPass.setFont(new Font("Cambria",Font.PLAIN,20));
		title = new JLabel("Reset Password");
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		
	
		getUN = new JTextField();
		getMomName = new JTextField();
		getPass1 = new JTextField();
		
		btn1 = new GUIdo_CButton(450, 450, 170, 30, "Reset Password");
		btn1.setPreferredSize(new Dimension(10,50));

		btn1.setActionCommand("login");
		
		
		JTextField getConfirmPass = new JTextField();
		getUN.setPreferredSize(new Dimension(10,50));
		getMomName.setPreferredSize(new Dimension(10,50));
		getPass1.setPreferredSize(new Dimension(10,50));
		getConfirmPass.setPreferredSize(new Dimension(10,50));
		
		
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean userFound = false, deservesPage = false;
				if(!getPass1.getText().equals(getConfirmPass.getText())) {
					JOptionPane.showMessageDialog(panel, "Passwords are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(!getUN.getText().isEmpty()) {
					
					List<Customer> customers = control.getAllCustomers();
					for(int i = 0; i < customers.size(); i++) {
						
						Customer current = customers.get(i);
						if(getUN.getText().equals(current.getUserName())) {
							userFound = true;
							if(getMomName.getText().equals(current.getMotherMaidenName())) {
								current.setPassword(getConfirmPass.getText());
								control.writeCustomer(current);
								logger.info("Set new password for " + current.getUserName());
								deservesPage = true;
								
								break;
							}else {
								JOptionPane.showMessageDialog(panel, "Mother's maiden name doesn't match!", "Error", JOptionPane.ERROR_MESSAGE);
								logger.info("Tried to change " + current.getUserName() + " password with incorrect maiden name");
							}
						}
						//else {
//							JOptionPane.showMessageDialog(panel, "Username doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
////							logger.info("Tried to change " + current.getUserName() + " password with incorrect username");
//						}
					}
					if(!userFound) {
						JOptionPane.showMessageDialog(panel, "Username doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
						logger.fine("Tried to change password of non-existent account: \"" + getUN.getText() + "\"");
					}
					else {
						if(deservesPage) {
							btn1.setActionListener_clicked(al);		
							//btn1.doClick();	
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
		this.add(getUN,c);
		
		c.gridy = 6;
		this.add(enterMom,c);
		
		c.gridy = 7;
		this.add(getMomName,c);
		
		c.gridy = 8;
		this.add(enterPass,c);
		
		c.gridy = 9;
		this.add(getPass1,c);
		
		c.gridy = 10;
		this.add(confirmPass,c);
		
		c.gridy = 11;
		this.add(getConfirmPass,c);
//		
//		this.add(l1);
//		this.add(enterUN);
//		this.add(enterMom);
//		this.add(enterPass);
//		this.add(confirmPass);
//		this.add(title);
//		
//		this.add(tf1);
//		this.add(getUN);
//		this.add(getMomName);
//		this.add(getPass1);
		
		
		c.gridy = 12;
		
		this.add(btn1,c);
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
		
	}

}
