package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIdo_ForgotPassword extends GUIdo_CPanel{
	
	//whatever, user, mother's, new pass, confirm new pass
	JLabel l1, l2, l3, l4, l5, l6;
	
	JTextField tf1, tf2, tf3, tf4;
	
	GUIdo_CButton btn1;
	
	GUIdo_ForgotPassword(final ActionListener al){
		super(500);
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		l1 = new JLabel("Username");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Enter username:");
		l3 = new JLabel("Enter mother's maiden name:");
		l4 = new JLabel("Enter new password:");
		l5 = new JLabel("Confirm new password:");
		l6 = new JLabel("Forgot Password?");
		
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		
		btn1 = new GUIdo_CButton(450, 450, 170, 30, "Reset Password");
		
		l2.setBounds(50, 50, 200, 30);
		l3.setBounds(50, 120, 200, 30);
		l4.setBounds(50, 190, 200, 30);
		l5.setBounds(50, 260, 200, 30);
		l6.setBounds(250, 30, 200, 30);
		
		tf1.setBounds(50, 90, 200, 30);
		tf2.setBounds(50, 160, 200, 30);
		tf3.setBounds(50, 230, 200, 30);
		tf4.setBounds(50, 300, 200, 30);
		
		this.add(l1);
		this.add(l2);
		this.add(l3);
		this.add(l4);
		this.add(l5);
		this.add(l6);
		
		this.add(tf1);
		this.add(tf2);
		this.add(tf3);
		this.add(tf4);
		
	}

}
