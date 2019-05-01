package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUIdo_EditProfile extends GUIdo_CPanel{
	
	private static Logger logger = Logger.getLogger(GUIdo_CreateAccount.class.getName());
	
	//whatever, email, confirm email, mother's name, user, pass, confirm pass, name, create account
	JLabel l1, email, confirmEmail, momName, enterPass, confirmPass, enterName, title, memberLevel;
	//email, confirm email, mother's name, user, pass, confirm pass, name
	JTextField getEmail, getEmailCon, getMom, getPass, getconfirmPass, getName, getMemberLevel;
	//sign up
	GUIdo_CButton btn1;
	
	Customer current = null;

	/**
	 * The edit profile frame
	 * @param al	the action listener to go to other frames
	 * @param u		The user to edit
	 */
	GUIdo_EditProfile(final ActionListener al, User u){
		super(1200);
		if(!u.isAdmin()) {
			current = (Customer)u;
		}
		this.setBackground(Color.WHITE);
		logger.info("Switched to panel CreateAccount");
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();

		
		email = new JLabel("Edit email:");
		confirmEmail = new JLabel("Confirm email:");
		momName = new JLabel("Edit mother's maiden name:");
		enterPass = new JLabel("Edit password:");
		confirmPass = new JLabel("Confirm password:");
		enterName = new JLabel("Edit name:");
		memberLevel = new JLabel("Edit member level (General, Middle, Elite)");
		title = new JLabel("Edit Profile");
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		
		
		email.setFont(new Font("Cambria",Font.PLAIN,20));
		confirmEmail.setFont(new Font("Cambria",Font.PLAIN,20));
		momName.setFont(new Font("Cambria",Font.PLAIN,20));
		enterPass.setFont(new Font("Cambria",Font.PLAIN,20));
		confirmPass.setFont(new Font("Cambria",Font.PLAIN,20));
		enterName.setFont(new Font("Cambria",Font.PLAIN,20));
		memberLevel.setFont(new Font("Cambria", Font.PLAIN, 20));
		
		
		
		getEmail = new JTextField(u.getEmail());
		getEmailCon = new JTextField();
		getMom = new JTextField(u.getMotherMaidenName());
		getPass = new JTextField(u.getPassword());
		getconfirmPass = new JTextField();
		getName = new JTextField(u.getName());
		getMemberLevel = new JTextField();
		btn1 = new GUIdo_CButton(900, 900, 170, 30, "Submit Changes");
		
		getEmail.setPreferredSize(new Dimension(10,50));
		getEmailCon.setPreferredSize(new Dimension(10,50));
		getMom.setPreferredSize(new Dimension(10,50));
		getPass.setPreferredSize(new Dimension(10,50));
		getconfirmPass.setPreferredSize(new Dimension(10,50));
		getName.setPreferredSize(new Dimension(10,50));
		getMemberLevel.setPreferredSize(new Dimension(10,50));
		btn1.setPreferredSize(new Dimension(10,50));
		btn1.setActionCommand("submit_changes");	
		
		String[] toInput = new String[6];
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean valid = true;
				if(getEmail.getText().isEmpty()) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Email field is blank!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with blank emails");
				} else if(getMom.getText().isEmpty()) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Mother's maiden name field is blank!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with blank mom field");
				} else if(getPass.getText().isEmpty()) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Password field is blank!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with blank emails");
				} else if(getName.getText().isEmpty()) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Name field is blank!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with blank name");
				} else if(!getEmail.getText().equals(getEmailCon.getText())) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Emails are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with non matching emails");
				} else if(!getPass.getText().equals(getconfirmPass.getText())) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Passwords are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with non matching passwords");
				} 
				
//				if(!getEmail.getText().isEmpty()) {
//					for(int i = 0; i < control.getAllCustomers().size(); i++) {
//						if(getEmail.getText().equals(control.getAllCustomers().get(i).getEmail())){
//							valid = false;
//							JOptionPane.showMessageDialog(panel, "Email already has an account!", "Error", JOptionPane.ERROR_MESSAGE);
//							logger.info("Attempted submission with already taken email");
//						}
//					}
//				}
				
				if(valid) {
					u.setEmail(getEmail.getText()); 
					u.setMotherMaidenName(getMom.getText());
					u.setPassword(getPass.getText());
					u.setName(getName.getText());
					if(!u.isAdmin()) {
						if(getMemberLevel.getText().equals("General")) {
							current.setStatus(MemberLevel.GENERAL);
						}else if(getMemberLevel.getText().equals("Middle")) {
							current.setStatus(MemberLevel.MIDDLE);
						}else if(getMemberLevel.getText().equals("Elite")) {
							current.setStatus(MemberLevel.ELITE);
						}
					}
					
					if(u.isAdmin()) {
						control.writeAdmin((Administrator)u);
					}else {
						control.writeCustomer(current);
					}
					control.updateUserPassword(u);
					
					btn1.setActionListener_clicked(al);
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
		
		this.add(title,c);
		
		c.gridy = 1;
		this.add(enterName,c);
		
		c.gridy = 2;
		this.add(getName,c);
		
		c.gridy = 6;
		this.add(email,c);
		
		c.gridy = 8;
		this.add(getEmail,c);
		
		c.gridy = 10;
		this.add(confirmEmail,c);
		
		c.gridy = 12;
		this.add(getEmailCon,c);
		
		c.gridy = 14;
		this.add(enterPass,c);
		
		c.gridy = 16;
		this.add(getPass,c);
		
		c.gridy = 18;
		this.add(confirmPass,c);
		
		c.gridy = 20;
		this.add(getconfirmPass,c);
		
		
		c.gridy = 22;
		this.add(momName,c);
		
		c.gridy = 24;
		this.add(getMom,c);
		
		if(!u.isAdmin()) {
			c.gridy = 26;
			this.add(memberLevel,c);
		
			c.gridy = 28;
			this.add(getMemberLevel,c);
		}
		
		c.gridy = 34;
		this.add(btn1,c);
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
		
	}
}
