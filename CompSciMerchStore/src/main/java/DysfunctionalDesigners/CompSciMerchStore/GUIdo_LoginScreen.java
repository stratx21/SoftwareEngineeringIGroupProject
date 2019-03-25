package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUIdo_LoginScreen extends GUIdo_CPanel {

	JLabel l1, l2, l3;
	JTextField tf1;
	GUIdo_CButton btn1, btn2, btn3, btn4;
	JPasswordField p1;
	
	public GUIdo_LoginScreen(final ActionListener al) {
		super(400);
		GUIdo_CPanel panel = this;
		l1 = new JLabel("Username");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		
		l2 = new JLabel("Username");
		l3 = new JLabel("Password");
		tf1 = new JTextField();
		p1 = new JPasswordField();
		btn1 = new GUIdo_CButton(150, 160, 100, 30, "Enter");
		btn2 = new GUIdo_CButton(250, 160, 100, 30, "Continue as a guest!");
		btn3 = new GUIdo_CButton(150, 260, 100, 30, "Create Account");
		btn4 = new GUIdo_CButton(250, 260, 100, 30, "Forgot Password?");
		
		btn1.setActionCommand("Enter");
		btn1.setActionListener_clicked(al);
		btn1.setBackground(Color.blue);
		
		btn2.setActionCommand("Continue as a guest!");
		btn2.setActionListener_clicked(al);
		btn2.setBackground(Color.blue);
		
		btn3.setActionCommand("Create Account");
		btn3.setActionListener_clicked(al);
		btn3.setBackground(Color.blue);
		
		btn4.setActionCommand("Forgot Password?");
		btn4.setActionListener_clicked(al);
		btn4.setBackground(Color.blue);		
		
		l2.setBounds(80, 70, 200, 30);
		l3.setBounds(80, 110, 200, 30);
		tf1.setBounds(300, 70, 200, 30);
		p1.setBounds(300, 110, 200, 30);
		
		tf1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String uname = tf1.getText();
				String pass = p1.getText();
				BufferedReader br = null, userData = null;
				String fileLine, userLine;
				String[] fileInfo, userInfo;
				List<User> users = new ArrayList<User>();
				
				try {
					br = new BufferedReader(new FileReader(new File("src/main/java/resources/UserData/usernames.txt")));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					fileLine = br.readLine();
					fileInfo = fileLine.split(" ");
					if(uname == fileInfo[0]) {
						if(pass == fileInfo[1]) {
							userData = new BufferedReader(new FileReader(new File("src/main/java/resources/UserData/" + uname + ".txt")));
							userLine = userData.readLine();
							fileInfo = fileLine.split(",");
							users.add(new User(fileInfo));
						}else {
							 JOptionPane.showMessageDialog(panel, "Incorrect password", 
								      "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}else {
						 JOptionPane.showMessageDialog(panel, "User does not exist", 
							      "Error", JOptionPane.ERROR_MESSAGE); 
					}
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				ActionEvent forPerformed = new ActionEvent(users.get(0), ActionEvent.ACTION_PERFORMED, "Enter");
				forPerformed.setSource(tf1.getText());
				
				al.actionPerformed(forPerformed);
			}
			
		});
		
		this.add(l1);
		this.add(l2); 
		this.add(tf1); 
		this.add(l3); 
		this.add(p1); 
		this.add(btn1);
		this.add(btn2); 
		this.add(btn3); 
		this.add(btn4);
		 		
		//panel.setSize(new Dimension(400, 400));
		
	}
	
	
	
}
