package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JLabel;

public class GUIdo_ViewProfile extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ViewProfile.class.getName());
	JLabel l1, email, momName, yourUN, yourPass, yourName, title;
	JLabel showEmail, showMomName, showUN, showPass, showName;
	
	GUIdo_CButton editprofile;
	
	GUIdo_ViewProfile(final ActionListener al){
		super(800);
		this.setBackground(Color.white);
		logger.info("Switched to panel ViewProfile");
		
		GUIdo_CPanel panel = this;
		UserDataController control = UserDataController.getInstance();
		
		
	}

}
