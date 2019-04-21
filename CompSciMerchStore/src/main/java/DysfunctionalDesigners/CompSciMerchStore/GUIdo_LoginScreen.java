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
	GUIdo_CButton btn1, btn2, btn3, btn4, btn5;
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
		btn1 = new GUIdo_CButton(150, 210, 170, 30, "Login as ADMIN");
		btn5 = new GUIdo_CButton(150, 160, 170, 30, "Login as CUSTOMER");
		btn2 = new GUIdo_CButton(330, 160, 170, 30, "Be our guest");
		btn3 = new GUIdo_CButton(150, 260, 170, 30, "Create Account");
		btn4 = new GUIdo_CButton(330, 260, 170, 30, "Forgot Password");
		
		btn1.setActionCommand("Enter Admin");
		btn1.setBackground(new Color(255,181,9));
		
		btn5.setActionCommand("Enter Customer");
		btn5.setBackground(new Color(255,181,9));
		
		btn2.setActionCommand("Continue as a guest!");
		btn2.setActionListener_clicked(al);
		btn2.setBackground(new Color(255,181,9));
		
		btn3.setActionCommand("Create Account");
		btn3.setActionListener_clicked(al);
		btn3.setBackground(new Color(255,181,9));
		
		btn4.setActionCommand("Forgot Password?");
		btn4.setActionListener_clicked(al);
		btn4.setBackground(new Color(255,181,9));		
		
		l2.setBounds(  80,  70, 200, 30);
		l3.setBounds(  80, 110, 200, 30);
		tf1.setBounds(300,  70, 200, 30);
		p1.setBounds( 300, 110, 200, 30);
		
		ActionListener alistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uname = tf1.getText();
				String pass = p1.getText();
				UserDataController dataControl = UserDataController.getInstance();
				BufferedReader reader;
				List<String> usernames = new ArrayList<>(), passwords = new ArrayList<>();
				List<User> users = new ArrayList<>();
				boolean userexists=false, wrongpass=true;
				
				try {
					if(e.getActionCommand().equals("Login as ADMIN")) {
						reader = new BufferedReader(new FileReader(new File("src/main/resources/UserData/admins.txt")));
						dataControl.getUsernamesAndPasswords(reader, usernames, passwords);
						
						for(int i=0;i<usernames.size();i++) {
							if(usernames.get(i).equals(uname)) {
								userexists = true;
								if(passwords.get(i).equals(pass)) {
									List<String> temp = new ArrayList<>();
									temp.add(usernames.get(i));
									users = dataControl.getAdmins(temp);
									wrongpass = false;
								} else {
									//TODO:: logger event fine incorrect password admin
								}
							} else {
								//TODO:: logger event fine incorrect username admin
							}
						}
					} else if(e.getActionCommand().equals("Login as CUSTOMER")){
						reader = new BufferedReader(new FileReader(new File("src/main/resources/UserData/customers.txt")));
						dataControl.getUsernamesAndPasswords(reader, usernames, passwords);

						for(int i=0;i<usernames.size();i++) {
							if(usernames.get(i).equals(uname)) {
								userexists = true;
								if(passwords.get(i).equals(pass)) {
									List<String> temp = new ArrayList<>();
									temp.add(usernames.get(i));
									users = dataControl.getCustomers(temp);
									wrongpass = false;
								} else {
									//TODO:: logger event fine incorrect password customer
								}
							} else {
								//TODO:: logger event fine incorrect username customer
							}
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				if(!userexists) {
					JOptionPane.showMessageDialog(panel, "User does not exist", 
						      "Error", JOptionPane.ERROR_MESSAGE); 
				} else if(wrongpass) {
					JOptionPane.showMessageDialog(panel, "Incorrect password", 
						      "Error", JOptionPane.ERROR_MESSAGE); 
				} else {
					//all was correct, user and password
					ActionEvent forPerformed = new ActionEvent(users.get(0), ActionEvent.ACTION_PERFORMED, "Enter");
					//forPerformed.setSource(tf1.getText());
					
					al.actionPerformed(forPerformed);
				}
				
				
//				BufferedReader br = null, userData = null;
//				String fileLine, userLine;
//				String[] fileInfo, userInfo;
				
				
//				List<User> users = dataControl.getAllCustomers();
//				users.addAll(dataControl.getAllAdmins());
//				List<User> users = new ArrayList<User>();
				
				
//				try {
//					br = new BufferedReader(new FileReader(new File("src/main/resources/UserData/usernames.txt")));
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
//				try {  //                 not empty          and     not found yet 
//					while((fileLine = br.readLine() )!= null && !(userexists && !wrongpass)) {
//						fileInfo = fileLine.split(" ");
//						if(uname.equals(fileInfo[0])){
//							userexists=true;
//							if(pass.equals(fileInfo[1])) {
//								//user and password found
//								
//								wrongpass=false;
//								userData = new BufferedReader(new FileReader(new File("src/main/resources/UserData/" + uname + ".txt")));
//								userLine = userData.readLine();
//								fileInfo = userLine.split(",");
//								fileInfo[5] = fileInfo[5].replaceAll(" ", "");
//								int id = Integer.parseInt(fileInfo[5]);
//								if(id >= 40000) {
//									users.add(new Administrator(fileInfo));
//								} else if(id >= 10000) {
//									users.add(new Customer(fileInfo));
//								}
//								else {
//									try {
//										throw new Exception("Unknown user id in file " + uname + ".txt");
//									} catch (Exception e1) {
//										
//										e1.printStackTrace();
//									}
//								}
//							}
//						}
//					}
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				
				
			}
			
		};
//		tf1.addActionListener(alistener);
//		p1.addActionListener(alistener);
		btn1.addActionListener(alistener);
		btn5.addActionListener(alistener);
		
		this.add(l1);
		this.add(l2); 
		this.add(tf1); 
		this.add(l3); 
		this.add(p1); 
		this.add(btn1);
		this.add(btn2); 
		this.add(btn3); 
		this.add(btn4);
		this.add(btn5);
	}
}
