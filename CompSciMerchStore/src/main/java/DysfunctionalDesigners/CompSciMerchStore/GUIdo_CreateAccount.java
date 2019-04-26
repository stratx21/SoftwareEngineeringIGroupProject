package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
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
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
	//email, confirm email, mother's name, user, pass, confirm pass, name
	JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
	//sign up
	GUIdo_CButton btn1;
	
	GUIdo_CreateAccount(final ActionListener al){
		super(700);
		logger.info("Switched to panel CreateAccount");
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		l1 = new JLabel("Username");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Enter email:");
		l3 = new JLabel("Confirm email:");
		l4 = new JLabel("Enter mother's maiden name:");
		l5 = new JLabel("Enter username:");
		l6 = new JLabel("Enter password:");
		l7 = new JLabel("Confirm password:");
		l8 = new JLabel("Enter name:");
		l9 = new JLabel("Create an Account");
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		tf6 = new JTextField();
		tf7 = new JTextField();
		btn1 = new GUIdo_CButton(650, 650, 170, 30, "Sign Up!");
		
		l2.setBounds(50, 50, 200, 30);
		l3.setBounds(50, 120, 200, 30);
		l4.setBounds(50, 190, 200, 30);
		l5.setBounds(50, 260, 200, 30);
		l6.setBounds(50, 330, 200, 30);
		l7.setBounds(50, 400, 200, 30);
		l8.setBounds(50, 470, 200, 30);
		l9.setBounds(200, 30, 200, 30);
		
		tf1.setBounds(50, 90, 200, 30);
		tf2.setBounds(50, 160, 200, 30);
		tf3.setBounds(50, 230, 200, 30);
		tf4.setBounds(50, 300, 200, 30);
		tf5.setBounds(50, 370, 200, 30);
		tf6.setBounds(50, 440, 200, 30);
		tf7.setBounds(50, 510, 200, 30);
		
		String[] toInput = new String[6];
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!tf1.getText().equals(tf2.getText())) {
					JOptionPane.showMessageDialog(panel, "Emails are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with non matching emails");
				} else if(!tf5.getText().equals(tf6.getText())) {
					JOptionPane.showMessageDialog(panel, "Passwords are not the same!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with non matching passwords");
				} else if(control.getCustomerUsernames().contains(tf4.getText())){
					JOptionPane.showMessageDialog(panel, "Username is taken already!", "Error", JOptionPane.ERROR_MESSAGE);
					logger.info("Attempted submission with already taken usernames");
				}
				
				if(!tf1.getText().isEmpty()) {
					for(int i = 0; i < control.getAllCustomers().size(); i++) {
						if(tf1.getText().equals(control.getAllCustomers().get(i).getEmail())){
							JOptionPane.showMessageDialog(panel, "Email already has an account!", "Error", JOptionPane.ERROR_MESSAGE);
							logger.info("Attempted submission with already taken email");
						}
					}
				}
				
				toInput[0] = tf1.getText(); 
				toInput[1] = tf3.getText();
				toInput[2] = tf4.getText();
				toInput[3] = tf5.getText();
				toInput[4] = tf7.getText();
				toInput[5] = User.hashUserNameToCustomerID(tf4.getText());
				
				control.writeCustomer(new Customer(toInput));
			}
			
		});
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
		this.add(l4);
		this.add(l5);
		this.add(l6);
		this.add(l7);
		this.add(l8);
		this.add(l9);
		this.add(tf1);
		this.add(tf2);
		this.add(tf3);
		this.add(tf4);
		this.add(tf5);
		this.add(tf6);
		this.add(tf7);
		this.add(btn1);
		
	}

}
