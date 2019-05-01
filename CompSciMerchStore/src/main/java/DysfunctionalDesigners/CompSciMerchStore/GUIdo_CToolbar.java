package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GUIdo_CToolbar extends GUIdo_CPanel{
	
	/**
	 * The logger instance that is used to log for the program. 
	 */
	private static Logger logger = Logger.getLogger(GUIdo_CToolbar.class.getName());
	
	/**
	 * This function tells if the buttons are disabled. 
	 * 
	 * @return a boolean concerning if the buttons are
	 * 	disabled. 
	 * 
	 */
	public boolean getButtons_disabled() {
		return disabled;
	}

	/**
	 * The text field that is used for the search bar to search the store. 
	 * 
	 */
	private JTextField searchBar;
	
	/**
	 * The boolean to tell if the buttons and search bar are disabled; This is 
	 *  used to disable the search bar. 
	 *  
	 */
	private boolean disabled;
	
	/**
	 * The list of buttons in the toolbar to be used so that they can be disabled
	 *  and enabled. 
	 *  
	 */
	private ArrayList<GUIdo_CButton> buttons = new ArrayList<>();
	
	/**
	 * 
	 * The other options given in the dropdown menu. 
	 * 
	 */
	private JComboBox otherOptions;
	
	/**
	 * This sets up the toolbar using an x and y position, a width and height used to restrict the 
	 * 	toolbar to where it needs to be, and an ActionListener instance used to go to whatever page
	 *  it needs to go to once an option is chosen. 
	 * @param x the x value to put the toolbar at 
	 * @param y the y value to put the toolbar at 
	 * @param width the width of the toolbar in the frame 
	 * @param height the height of the toolbar in the frame 
	 * @param done the ActionListener instance that is used to go to whatever page it needs to go
	 *  to based on what option is chosen. 
	 */
	public GUIdo_CToolbar(int x, int y, int width, int height, final ActionListener done) {
		disabled = false;
//		super();
		//general setup:
		this.setBackground(Color.WHITE);
		this.setSize(width, height);
//		this.width=width;
//		this.height=height;
		this.setBackground(new Color(255,192,203));
		
		//home button:
		
		
		//set up the logout button 
		GUIdo_CButton logout = new GUIdo_CButton(x+width-height*2,y,height*2,height,"LOGIN/OUT");
		logout.setActionCommand("login");
		logout.setActionListener_clicked(done);
		logout.setBackground(new Color(255,228,225));
		logout.setHoverColor(new Color(255,192,203));
		
		
		//search bar:
		
		searchBar = new JTextField("search");
		
		searchBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("SEARCH invoked with string \"" + searchBar.getText() + "\"");
				
				if(!disabled) {
					ActionEvent forPerformed = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,"search");
					forPerformed.setSource(searchBar.getText());
					
					done.actionPerformed(forPerformed);
				}
			}
		});
		searchBar.setSize(width-height*7, height/2);
		searchBar.setLocation(height, height/4);
		
		searchBar.setBackground(new Color(255,228,225));
		
		GUIdo_CButton cart = new GUIdo_CButton (searchBar.getWidth() + 70,y , height, height, "CART" ) ;
		cart. setActionCommand("cart");
		cart. setActionListener_clicked( done);
		cart.setBackground(new Color(255,228,225));
		cart.setHoverColor(new Color(255,192,203));

		
		//add the home button in the top left 
		GUIdo_CButton home_button= null;
		try  {
			home_button  =  new GUIdo_CButton(x,y,height,height,
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/home (1).png"))),
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/home (2).png"))),
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/home (3).png"))));
			home_button.setActionCommand("home");
			home_button.setActionListener_clicked(done);
			home_button.setBackground(new Color(255,228,225));
			home_button.setHoverColor(new Color(255,228,225));
		} catch(Exception e) {
			logger.severe("ERROR setting up home button initialization");
			e.printStackTrace();
		}
		
		
		//set up the wishlist button for the toolbar to see the User's wishlist 
		GUIdo_CButton wishlist=null;
		try {
			wishlist = new GUIdo_CButton(searchBar.getWidth() + 70 + cart.getWidth(),y,height,height,
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/wish1.png"))),
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/wish2.png"))),
					new ImageIcon(ImageIO.read(new File(App.resourceTarget + "Toolbar/wish3.png"))));
			wishlist.setActionCommand("wishlist");//command to check by to ensure the wishlist action choice 
			wishlist.setActionListener_clicked(done);
			wishlist.setBackground(new Color(255,228,225));
			wishlist.setHoverColor(new Color(255,192,203));
		} catch (IOException e1) {
			logger.severe("ERROR: IOException caught in CToolbar");
			e1.printStackTrace();
		}
		
		
		
		//the options used for the combo box: 
		List<String> options = new ArrayList<String>();
		options.add("Other Options"); 
		options.add("Member Deals");
		options.add("View Profile");
		options.add("Contact Us"); 
		options.add("FAQ"); 
		options.add("About Us");
		options.add("Add Item");
		options.add("All Uploaded Items");
		options.add("Admin Page");
		options.add("View Past Sales");
		
		
		//set up the dropdown menu with a JComboBox for the other options: 
		otherOptions = new JComboBox(options.toArray());
		try {
			otherOptions.setBounds(searchBar.getWidth() + home_button.getWidth() + cart.getWidth() + wishlist.getWidth(), 
					y, cart.getWidth()+home_button.getWidth(), height);
			otherOptions.setBackground(new Color(255,228,225));
			//otherOptions.setHoverColor(new Color(255,192,203));
			otherOptions.addActionListener(done);
			otherOptions.setActionCommand("other_opt");	
		}
		
		catch(NullPointerException e) {
			logger.severe("ERROR: NULLPOINTER ON HOME/WISHLIST BUTTON -- EXCEPTION CAUGHT");
		}
		
		
		
		//add all buttons 
		try {
			this.add(home_button);
			
			this.add(searchBar);
			this.add(wishlist);
			this.add(logout);
			this.add(otherOptions);
			this.add(cart);	
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		
		//add the buttons so they can be disabled 
		try {
			buttons.add(home_button);
			buttons.add(wishlist);
			buttons.add(logout);
			buttons.add(cart);
			
			home_button.repaint();		
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	
		
		this.repaint();
	}
	
	/**
	 * disable the functionality of all the toolbar buttons and the 
	 * search bar. 
	 */
	public void disable_all_buttons() {
		disabled = true;
		for(GUIdo_CButton button : buttons) {
			button.disable();
		}
		otherOptions.setEnabled(!disabled);
		
	}
	
	/**
	 * enable the functionality of the toolbar buttons and the search bar. 
	 */
	public void enable_all_buttons() {
		disabled = false;
		for(GUIdo_CButton button : buttons) {
			button.enable();
		}
		otherOptions.setEnabled(!disabled);
	}
	
}
