package DysfunctionalDesigners.CompSciMerchStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

public class GUIdo_ViewAllUsers extends GUIdo_CPanel {
	
	/**
	 * Instance of the loggger
	 */
	private static Logger logger = Logger.getLogger(GUIdo_ViewAllUsers.class.getName());
	
	/**
	 * Font for all JLabels
	 */
	private static final Font COMPLAINTS_FONT = new Font("Cambria", Font.PLAIN, 20);
	
	/**
	 * Constant button height for buttons
	 */
	private static final int BUTTON_WIDTH = 250;
	
	/**
	 * Constant button width
	 */
	private static final int BUTTON_HEIGHT = 80;
	
	/**
	 * Constant gap between things
	 */
	private static final int Y_GAP = 15;
	
	/**
	 * Constant height for text
	 */
	private static final int COMPLAINT_HEIGHT
			= GUIdo_OutputTools.getPixelHeight("User:", GUIdo_ViewAllUsers.COMPLAINTS_FONT) + 40;
	private static final int COMPLAINT_GAP = 7;
	private int y = 75;
	private boolean users_shown = false;
	
	public GUIdo_ViewAllUsers(ActionListener done, int width) {
		//set 1200 to something greater later if needed: 
		super(width,1200);//width, length of page in pixels.
		
		//all components can be added to -this- in this area
		GUIdo_CButton show_users = new GUIdo_CButton(width/2-BUTTON_WIDTH/2, y,
				BUTTON_WIDTH, BUTTON_HEIGHT/2,
				"Show Users List");
		show_users.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!users_shown) {
					users_shown = true;
					List<Customer> customers = UserDataController.getInstance().getAllCustomers();

					JLabel toadd = null;
					for (Customer customer : customers) {
						toadd = new JLabel(customer.getUserName() + ": ");
						toadd.setFont(GUIdo_ViewAllUsers.COMPLAINTS_FONT);
						toadd.setBounds(getWidth()/6,
								y,
								getWidth()*6/7,
								GUIdo_ViewAllUsers.COMPLAINT_HEIGHT);
						add(toadd);
						y += GUIdo_ViewAllUsers.COMPLAINT_HEIGHT+GUIdo_ViewAllUsers.COMPLAINT_GAP;

						//Build string
						StringBuilder stringToMake = new StringBuilder();
						stringToMake.append("User ID: " + customer.getUserID() + "\n");
						stringToMake.append("Name: " + customer.getName() + "\n");
						stringToMake.append("Email: " + customer.getEmail() + "\n");
						stringToMake.append("Member Level: " + customer.getStatus() + "\n");
						stringToMake.append("Shipping Address: " + customer.getShippingAddr() + "\n");
						String finalString = stringToMake.toString();
						String[] splitByLine = finalString.split("\\n");

						//for each line in the complaint, print the lines so that they are
						//not word wrapped: 
						for(String line : splitByLine) {

							toadd = new JLabel(line);
							toadd.setFont(GUIdo_ViewAllUsers.COMPLAINTS_FONT);
							toadd.setBounds(getWidth()/6,
									y,
									getWidth()*2/3,
									GUIdo_ViewAllUsers.COMPLAINT_HEIGHT);
							add(toadd);
							y += GUIdo_ViewAllUsers.COMPLAINT_HEIGHT+GUIdo_ViewAllUsers.COMPLAINT_GAP;
						}
						y+= GUIdo_ViewAllUsers.COMPLAINT_GAP*2;
					}
				}

				set_new_length(y+200);
				repaint();
			}
		});
		this.add(show_users);
		y+= BUTTON_HEIGHT/2+Y_GAP;

		GUIdo_CButton returnToHomePage = new GUIdo_CButton(width/2-BUTTON_WIDTH/2, y,
				BUTTON_WIDTH, BUTTON_HEIGHT/3,
				"Return to Home Page");
		returnToHomePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				done.actionPerformed(e);
			}
		});
		this.add(returnToHomePage);
		y+= BUTTON_HEIGHT/2+Y_GAP;
		
		//change the length of the page dynamically by using: 
		this.set_new_length(1200);
		//(added a function to make it easier)
	}

}
