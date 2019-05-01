package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JLabel;

public class GUIdo_ViewProfile extends GUIdo_CPanel{
	/**
	 * Instance of the logger
	 */
	private static Logger logger = Logger.getLogger(GUIdo_ViewProfile.class.getName());
	JLabel l1, email, momName, yourUN, yourPass, yourName, title, memberStatus, address;
	JLabel showEmail, showMomName, showUN, showPass, showName, showMemberStatus, showAddress;
	
	GUIdo_CButton editProfile, viewPreviousOrders;
	GUIdo_CPanel panel = this;
	
	/**
	 * Function constructs the frame for viewing a profile
	 * @param al To be able to go to a new frame
	 * @param u user to be displayed
	 */
	GUIdo_ViewProfile(final ActionListener al, User u){
		super(800);
		//cast u to vendor
		Vendor v = (Vendor)u;
		Customer current = null;
		if(!u.isAdmin()) {
			current = (Customer)u;
		}
		
		String memberS = "General";
		//display info for vendor
		this.setBackground(Color.white);
		logger.info("Switched to panel ViewProfile");
		
		UserDataController control = UserDataController.getInstance();
		
		if(!u.isAdmin()) {
			if(current.getStatus().equals(MemberLevel.GENERAL)) {
				memberS = "General";
			}else if(current.getStatus().equals(MemberLevel.MIDDLE)) {
				memberS = "Middle";
			}else if(current.getStatus().equals(MemberLevel.ELITE)) {
				memberS = "Elite";
			}
		}
		
		email = new JLabel("Your email:");
		momName = new JLabel("Your mother's maiden name:");
		yourUN = new JLabel("Your username:");
		yourPass = new JLabel("Your password:");
		yourName = new JLabel("Your name:");
		memberStatus = new JLabel("Your Member Status:");
		address = new JLabel("Your address is:");
		title = new JLabel("View Profile");
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		
		editProfile = new GUIdo_CButton(650, 650, 170, 30, "Edit Profile");
		editProfile.addActionListener(al);
		editProfile.setActionCommand("edit_profile");
		editProfile.setActionListener_clicked(al);
		
		viewPreviousOrders = new GUIdo_CButton(650, 650, 170, 30, "View Previous Orders");
		viewPreviousOrders.addActionListener(al);
		viewPreviousOrders.setActionCommand("View previous orders");
		viewPreviousOrders.setActionListener_clicked(al);
		
		email.setFont(new Font("Cambria",Font.BOLD,24));
		momName.setFont(new Font("Cambria",Font.BOLD,24));
		yourUN.setFont(new Font("Cambria",Font.BOLD,24));
		yourPass.setFont(new Font("Cambria",Font.BOLD,24));
		yourName.setFont(new Font("Cambria",Font.BOLD,24));
		memberStatus.setFont(new Font("Cambria",Font.BOLD,24));
		address.setFont(new Font("Cambria",Font.BOLD,24));
		
		showEmail = new JLabel(v.getEmail());
		showMomName = new JLabel(v.getMotherMaidenName());
		showUN = new JLabel(v.getUserName());
		showPass = new JLabel(v.getPassword());
		showName = new JLabel(v.getName());
		showMemberStatus = new JLabel(memberS);
		if(!u.isAdmin()) {//if shipping is null, need to handle it////////////////////////////// mackenna
			if(current.getShippingAddr() != null) {
				showAddress = new JLabel(current.getShippingAddr().getStreet() + "," + current.getShippingAddr().getCity() + ","
						+ current.getShippingAddr().getState() + " " + current.getShippingAddr().getZipCode());
			}
		}
		
		showEmail.setFont(new Font("Cambria",Font.PLAIN,24));
		showMomName.setFont(new Font("Cambria",Font.PLAIN,24));
		showUN.setFont(new Font("Cambria",Font.PLAIN,24));
		showPass.setFont(new Font("Cambria",Font.PLAIN,24));
		showName.setFont(new Font("Cambria",Font.PLAIN,24));
		showMemberStatus.setFont(new Font("Cambria",Font.PLAIN,24));
		if(!u.isAdmin()) {
			showAddress.setFont(new Font("Cambria",Font.PLAIN,24));

			editProfile.disable();
		}
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		
		JLabel blah = new JLabel(" ");
		this.add(title,c);
		c.gridy = 1;
		this.add(blah, c);
		
		c.gridy = 2;
		this.add(yourName,c);
		
		c.gridy = 4;
		this.add(showName,c);
		
		c.gridy = 6;
		this.add(email,c);
		
		c.gridy = 8;
		this.add(showEmail,c);
		
		c.gridy = 10;
		this.add(momName,c);
		
		c.gridy = 12;
		this.add(showMomName,c);
		
		c.gridy = 14;
		this.add(yourUN,c);
		
		c.gridy = 16;
		this.add(showUN,c);
		
		c.gridy = 18;
		this.add(yourPass, c);
		
		c.gridy = 20;
		this.add(showPass, c);
		
		if(!u.isAdmin()) {
			c.gridy = 22;
			this.add(memberStatus, c);
		
			c.gridy = 24;
			this.add(showMemberStatus, c);

			c.gridy = 26;
			this.add(address, c);
		
			c.gridy = 28;
			this.add(showAddress, c);
		}
		
		c.gridy = 2;
		c.gridx = 4;
		this.add(editProfile,c);
		
		c.gridy = 4;
		this.add(viewPreviousOrders, c);
		
		c.gridy = 28;
		
		c.gridy = 30;
		
		JLabel fake = new JLabel ("");
		c.weighty = 1;
		this.add(fake,c);
		
	}

}
