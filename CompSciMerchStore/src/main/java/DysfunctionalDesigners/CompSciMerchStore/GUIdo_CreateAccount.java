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

//Creates screen, doesn't have things in correct place. Adjust values to get spacing and placement right.

public class GUIdo_CreateAccount extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_CreateAccount.class.getName());
	
	//whatever, email, confirm email, mother's name, user, pass, confirm pass, name, create account
	JLabel l1, email, confirmEmail, momName, enterUN, enterPass, confirmPass, enterName, title;
	//email, confirm email, mother's name, user, pass, confirm pass, name
	JTextField getEmail, getEmailCon, getMom, getUN, getPass, getconfirmPass, getName;
	//sign up
	GUIdo_CButton btn1, backBtn;
	
	/**
	 * This creates the Create Account page. It updates the frame to show the page.
	 * @param al is the action listener needed for the button
	 * 
	 */
	GUIdo_CreateAccount(final ActionListener al){
		super(700);
		this.setBackground(Color.WHITE);
		logger.info("Switched to panel CreateAccount");
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();

		
		email = new JLabel("Enter email:");
		confirmEmail = new JLabel("Confirm email:");
		momName = new JLabel("Enter mother's maiden name:");
		enterUN = new JLabel("Enter username:");
		enterPass = new JLabel("Enter password:");
		confirmPass = new JLabel("Confirm password:");
		enterName = new JLabel("Enter name:");
		title = new JLabel("Create an Account");
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		
		
		email.setFont(new Font("Cambria",Font.PLAIN,20));
		confirmEmail.setFont(new Font("Cambria",Font.PLAIN,20));
		momName.setFont(new Font("Cambria",Font.PLAIN,20));
		enterUN.setFont(new Font("Cambria",Font.PLAIN,20));
		enterPass.setFont(new Font("Cambria",Font.PLAIN,20));
		confirmPass.setFont(new Font("Cambria",Font.PLAIN,20));
		enterName.setFont(new Font("Cambria",Font.PLAIN,20));
		
		
		
		getEmail = new JTextField();
		getEmailCon = new JTextField();
		getMom = new JTextField();
		getUN = new JTextField();
		getPass = new JTextField();
		getconfirmPass = new JTextField();
		getName = new JTextField();
		btn1 = new GUIdo_CButton(650, 650, 170, 30, "Create!");
		backBtn = new GUIdo_CButton(650, 650, 170, 30, "Back!");
		
		getEmail.setPreferredSize(new Dimension(10,50));
		getEmailCon.setPreferredSize(new Dimension(10,50));
		getMom.setPreferredSize(new Dimension(10,50));
		getUN.setPreferredSize(new Dimension(10,50));
		getPass.setPreferredSize(new Dimension(10,50));
		getconfirmPass.setPreferredSize(new Dimension(10,50));
		getName.setPreferredSize(new Dimension(10,50));
		getName.setPreferredSize(new Dimension(10,50));
		btn1.setPreferredSize(new Dimension(10,50));
		btn1.setActionCommand("login");
		
		backBtn.setPreferredSize(new Dimension(10,50));
		backBtn.setActionCommand("login");
		backBtn.setActionListener_clicked(al);		
		
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
				} else if(getUN.getText().isEmpty()) {
					valid = false;
					JOptionPane.showMessageDialog(panel, "Username field is blank!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with blank username");
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
				} else if(control.getCustomerUsernames().contains(getUN.getText())){
					valid = false;
					JOptionPane.showMessageDialog(panel, "Username is taken already!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with already taken usernames");
				}
				
				if(!getEmail.getText().isEmpty()) {
					for(int i = 0; i < control.getAllCustomers().size(); i++) {
						if(getEmail.getText().equals(control.getAllCustomers().get(i).getEmail())){
							valid = false;
							JOptionPane.showMessageDialog(panel, "Email already has an account!", "Error", JOptionPane.ERROR_MESSAGE);
							logger.info("Attempted submission with already taken email");
						}
					}
				}
				
				if(valid) {
					toInput[0] = getEmail.getText(); 
					toInput[1] = getMom.getText();
					toInput[2] = getUN.getText();
					toInput[3] = getPass.getText();
					toInput[4] = getName.getText();
					toInput[5] = User.hashUserNameToCustomerID(getUN.getText());
					
					Customer temp = new Customer(toInput);
					control.writeCustomer(temp);
					control.addUsernameAndPasswordToLists(temp);
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
		
		c.gridy = 3;
		this.add(enterUN,c);
		
		c.gridy = 4;
		this.add(getUN,c);
		
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
		
		c.gridy = 26;
		this.add(btn1,c);
		
		c.gridy = 28;
		this.add(backBtn, c);
		
		c.gridy = 30;
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
		
		
	
		
	}

}
