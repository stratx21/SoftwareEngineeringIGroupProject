package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUIdo_Frame extends JFrame{
	
	/**
	 * the height in pixels of the toolbar.
	 */
	private final int TOOLBAR_HEIGHT = 70;//pixels
	
	/**
	 * The JScrollPane instance used for the pages in the main part of the frame.
	 */
	private JScrollPane scrollpane;
	
	/**
	 * The cart used to hold the items that are being bought in the current session.
	 */
	private Sale cart; 

	/**
	 * the GUIdo_CToolbar instance to be used for the toolbar in this frame.
	 */
	GUIdo_CToolbar toolbar = null;
	
	/**
	 * the GUIdo_SectionHeader instance to be used for the toolbar for the 
	 * 	list of professors.
	 */
	GUIdo_SectionHeader professorHeader = null;
	
	/**
	 * The current logged in user 
	 */
	private User current_user = null;
	
	/**
	 * The item to display the details of to the user
	 */
	private ItemInfo toDisplay = null;
	
	/**
	 * the current panel that is being used for the page to scroll through
	 */
	GUIdo_CPanel current_panel;
	
	/**
	 * list of items for general member deals
	 */
	private List<Integer> generalMemberDeals = Arrays.asList(16,22,35);
	
	/**
	 * list of items for middle member deals
	 */
	private List<Integer> middleMemberDeals = Arrays.asList(10,16,22,17,35);
	/**
	 * list of items for elite member deals
	 */
	private List<Integer> eliteMemberDeals = Arrays.asList(4,9,10,16,22,17,39,35);
	
	/**
	 * This initializes the GUIdo_Frame instance; it calls initialize to
	 * 	organize the setup and have the ability to call it again. 
	 */
	public GUIdo_Frame() {
		this.initialize();
	}
	
	/**
	 * The toolbar will be called here to initialize it and to set up the listener
	 *  for actions made through the toolbar.
	 * @param e the ActionEvent used by the ActionListener to call on buttons in
	 * 	the toolbar.
	 */
	private void toolbar_call(ActionEvent e) {
		if(e.getActionCommand().equals("search")) {
			//the flow for the search, when the search is submitted
			if(!toolbar.getButtons_disabled()) {
				ActionListener toItemDisplay = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						display_item((ItemInfo)(e.getSource()), current_user);
					}
				};
				String searched = (String)e.getSource();
				current_panel = new GUIdo_ItemCollection(getWidth(),Catalogue.getInstance().search(searched),"Search: \""+searched+"\"",toItemDisplay,current_user);
				scrollpane.getViewport().add(current_panel);
			}
		} else if(e.getActionCommand().equals("home")) {
			to_homescreen();
		} else if(e.getActionCommand().equals("wishlist")) {
			to_wishlist(current_user);
		} else if(e.getActionCommand().equals("login")) {
			to_login();
		} else if(e.getActionCommand().equals("cart")) {
			to_cart(cart, (Customer) current_user);
		} else if(e.getActionCommand().equals("other_opt")) {
			JComboBox cb = (JComboBox)e.getSource();
			String option = (String)cb.getSelectedItem();
			if(option.equals("Contact Us")) {
				to_contactus();
			}else if(option.equals("FAQ")) {
				to_faq();
			}else if(option.equals("About Us")) {
				to_aboutus();
			}
		}
	}
	
	private void to_contactus() {
		current_panel = new GUIdo_ContactUs();
		current_panel.repaint();
	}
	
	private void to_faq() {
		current_panel = new GUIdo_FAQScreen();
		current_panel.repaint();
	}
	
	private void to_aboutus() {
		current_panel = new GUIdo_AboutUs();
		current_panel.repaint();
	}
	
	/**
	 * This function goes to the wishlist page for the specified User. 
	 * @param user_to_see the user to see the wishlist page for.
	 */
	public void to_wishlist(User user_to_see) {
		//get the items in the user's wishlist: 
		List<ItemInfo> items_for_wishlist = Catalogue.getInstance().getItems(((Customer)user_to_see).getWishList());
		//this page is a new ItemCollection that is for the wishlist, having the items in the user's wishlist, a title of Wishlist,
		//and other necessary information that is from the frame data
		this.current_panel = new GUIdo_ItemCollection(this.getWidth(),items_for_wishlist,"Wishlist",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display_item((ItemInfo)(e.getSource()), user_to_see);
			}
		},user_to_see);
		//add again 
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * This function sets up the login screen and adds it to this frame.
	 */
	private void to_login() {
		//disable all buttons before allowing a login to not exit the login screen 
		toolbar.disable_all_buttons();
		//save the data
		if(current_user != null) {
			if(!current_user.getUserName().equals("guest")) {
				UserDataController dataControl = UserDataController.getInstance();
				switch((current_user.getUserID() + "").charAt(0)){
					case '4': dataControl.writeAdmin((Administrator) current_user); break;
					case '1': dataControl.writeCustomer((Customer) current_user); break;
					default:
						//TODO:: logger log invalid user detected
				}
			}
			current_user = null;
		}
		
		//go to the login screen to allow the user to login, using simply an ActionListener instance 
		//to allow the login to finish 
		current_panel = new GUIdo_LoginScreen(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				login_call(e);
			}
		});
		scrollpane.getViewport().add(current_panel);
		
	}
	
	/**
	 * This function is used for when the user logs in and the different
	 * 	actions that are called through the ActionListener. 
	 * @param e the ActionEvent instance that is sent by the ActionListener
	 * 	instance for the buttons in the login menu.
	 */
	private void login_call(ActionEvent e) {
		if(e.getActionCommand().equals("Enter")) {
			current_user = (User)e.getSource();
			
			
			//if the user is not an admin and is not the guest 
			if(!this.current_user.isAdmin() && !this.current_user.getName().equals("guest")) {
				if(((Customer) this.current_user).getCart() == null) {
					this.cart = new Sale(this.current_user.getUserID());
					((Customer) this.current_user).setCart(this.cart);
				} else {
					this.cart = ((Customer) this.current_user).getCart();
				}
			} else {
				this.cart = new Sale(this.current_user.getUserID());
			}
			
			//re-enable all the buttons 
			toolbar.enable_all_buttons();
			//go to the homescreen 
			to_homescreen();
		} else if(e.getActionCommand().equals("Continue as a guest!")) {
			//login as the guest 
			this.current_user = new Customer(new String[]{"guest@email.com", "guesty", "guest", "guestPass", "Guest", "19999"});
			//reset the cart 
			this.cart = new Sale(this.current_user.getUserID());
			toolbar.enable_all_buttons();
			to_homescreen();
			
		} else if(e.getActionCommand().equals("Create Account")) {
			ActionListener l = null;
			current_panel = new GUIdo_CreateAccount(l);
			scrollpane.getViewport().add(current_panel);
			
		} else if(e.getActionCommand().equals("Forgot Password?")) {
			ActionListener l = null;
			current_panel = new GUIdo_ForgotPassword(l);
			scrollpane.getViewport().add(current_panel);
		}
	}
	
	/**
	 * This function transfers the page to the cart page, where the user can see their
	 * 	subtotal of items including tax and change some values about the items such
	 * 	as quantity. 
	 * @param sale the Sale object that is being used for the Cart to be able to display
	 * 	the cart. 
	 */
	private void to_cart(Sale sale, Customer cust) {
		//set the current page to a review and edit order to see the cart 
		this.current_panel = new GUIdo_ReviewAndEditOrder(sale, cust);
		scrollpane.getViewport().add(this.current_panel);
		scrollpane.repaint();
		this.current_panel.repaint();
	}
	
	/**
	 * This function goes to and sets up the homescreen. 
	 */
	private void to_homescreen() {
		//set up the homescreen using an ActionListener for the 2 featured items and the width
		current_panel = new GUIdo_Homescreen(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("display1") || e.getActionCommand().equals("display2")) {
					display_item((ItemInfo)e.getSource(), current_user);
				} else if(e.getActionCommand().equals("memberdeals")) {
					if(!((Customer)current_user).getUserName().equals("guest")) {
						to_MemberDeals(true);
					}
					
				}
			}
		}, scrollpane.getWidth());
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * This function displays the item specified by the parameter with all the 
	 * 	details for the user to see.
	 * @param item the item to display information for. 
	 */
	private void display_item(ItemInfo item, User user) {
		//display a singular item's information and allow the user to add it to the cart and choose a quantity
		//uses the item to display, given by display_item function (this function) parameter, the width of the
		//page, and an ActionListener instance for when an item is added to the cart.
		current_panel = new GUIdo_ItemDisplay(item,scrollpane.getWidth(),cart,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("item_added")) {
					cart = (Sale)e.getSource();
					to_cart(cart, (Customer) user);
				} else if(e.getActionCommand().equals("add_review")) {
					/* Mackenna : TODO add review viewing here */
					//e.getSource() is the item to find reviews for 
				}
			}
		},current_user);
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * Function loads the member deals page
	 */
	private void to_MemberDeals(boolean popup) {
		Boolean ready = false;
		String title = "";
		final String code[] = {""};
		final double discount[] = {0.0};
		
		
		List<Integer> toiterate = new ArrayList<Integer>();
		List<ItemInfo> topass = new ArrayList<ItemInfo>();
		Catalogue cat = Catalogue.getInstance();
		
		if(((Customer) current_user).getStatus() == MemberLevel.GENERAL) {		
			toiterate = generalMemberDeals;	
			title = "Enter code \"SEROCKS\" for 15% off these items!!!";
			code[0] = "SEROCKS";
			discount[0] = 0.15;
		}
		else if(((Customer) current_user).getStatus() == MemberLevel.MIDDLE) {			
			toiterate = middleMemberDeals;
			title = "Enter code \"COOLPROJECT\" for 20% off these items!!!";
			code[0] = "COOLPROJECT";
			discount[0] = 0.2;
		}
		else if(((Customer) current_user).getStatus() == MemberLevel.ELITE || current_user.isAdmin()) {		
			toiterate = eliteMemberDeals;
			if(current_user.isAdmin()) {
				title = "ADMIN VIEW";
			}
			else {
				title = "Enter code \"CERNYISTHEBEST\" for 25% off these items!!!";	
			}
			
			code[0] = "CERNYISTHEBEST";
			discount[0] = 0.25;
		}
		else {
			//nah
		}
		
		for(int i = 0; i < toiterate.size(); i++) {
			cat.getItem(toiterate.get(i)).addPromoDiscount(code[0], discount[0]);
			topass.add(cat.getItem(toiterate.get(i)));
			System.out.println(topass.get(i).getDisplayName());
		}
		
		current_panel = new GUIdo_ItemCollection(getWidth(),topass,title,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				display_item((ItemInfo)(e.getSource()), current_user);
				
			}}
			,current_user);

				
		scrollpane.getViewport().add(current_panel);
		
		if(popup) {
			boolean same = false;
			MemberLevel mlem = ((Customer)current_user).getStatus();
			if(((Customer)current_user).getStatus().equals(MemberLevel.GENERAL) || 
					((Customer)current_user).getStatus().equals(MemberLevel.MIDDLE)) {
				final String options[] = {"General", "Middle", "Elite", "No change"};
				
				String input = (String) JOptionPane.showInputDialog(null, "Before You Shop:",
				        "Would you like to change your member level?", JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
				boolean reload = true;
				if(input != null) {//cancel button
					switch(input) {
					
					case "General":
						((Customer) current_user).setStatus(MemberLevel.GENERAL);
						break;
					case "Middle":
						((Customer) current_user).setStatus(MemberLevel.MIDDLE);
						break;
					case "Elite":
						((Customer) current_user).setStatus(MemberLevel.ELITE);
						break;
					case "No Change":
						reload = false;
						break;
					default:
						reload = false;
					}
				} else {
					reload = false;
				}
				
				if(((Customer)current_user).getStatus().equals(mlem)) {
					same = true;
				}
				
				if(reload && !same) {
					to_MemberDeals(false);
				}
				
			}
		}
		
		
	}
	
	/**
	 * This initializes this instance of GUIdo_Frame by setting up the frame,
	 * 	setting up the toolbar and the scrolling pane with the current panel
	 *  within a panel to add to the frame. 
	 */
	public void initialize() {
		
		//frame setup:
		//dispose of the frame on close, but do not exit the program
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//add a window listener in order to have actions when the frame is closed 
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent windowEvent) {
		        //code here for exiting the program... 
		    	Catalogue.getInstance().catalogueToJSON();
		    	//write user to file
		    	if(current_user != null) {
					if(!current_user.getUserName().equals("guest")) {
						UserDataController dataControl = UserDataController.getInstance();
						switch((current_user.getUserID() + "").charAt(0)){
							case '4': dataControl.writeAdmin((Administrator) current_user); break;
							case '1': dataControl.writeCustomer((Customer) current_user); break;
							default:
								//TODO:: logger log invalid user detected
						}
					}
				}
		    }
		});
		
		//make it look pretty 
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Computer Science Merchandise Store");
		//get the screen size in a Dimension object to set the size of the frame to the size of
		//the user's screen. 
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		//set the size to the size of the user's screen 
		this.setSize(screen_size);
		//make the frame full-screen justified 
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//don't allow the user to resize the frame
		this.setResizable(false);
		
		//initialize the top toolbar with the home button, the search bar, a wishlist button to access
		//the wishlist, and a logout button. It is initialized to be at the top, with the width of the frame,
		//the static final height given for the toolbar, and an ActionListener to control the flow based on
		//what button was clicked
		toolbar = new GUIdo_CToolbar(0,0,this.getWidth(),this.TOOLBAR_HEIGHT,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolbar_call(e);
			}
		});
		
		//add the section header that shows the section buttons for each professor 
		professorHeader = new GUIdo_SectionHeader(0,0,this.getWidth(),this.TOOLBAR_HEIGHT,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//an ActionListener to display a singular item if/when one of the items in a section is chosen 
				ActionListener toItemDisplay = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						display_item((ItemInfo)(e.getSource()), current_user);
					}
				};
				
				//set the List of items and the title to display the list of items for the given professor: 
				
				List<ItemInfo> display_items=null;
				String title=null;
				//set display_items to the items given by the professor list
				//set title to the professor's name
				if(e.getActionCommand().equals("cerny"))          {
					title = "Dr. Cerny";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.CERNY);
				} else if(e.getActionCommand().equals("booth"))   {
					title = "Dr. Booth";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.BOOTH);
				} else if(e.getActionCommand().equals("fry"))     {
					title = "Professor Fry";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.FRY);
				} else if(e.getActionCommand().equals("hamerly")) {
					title = "Dr. Hamerly";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.HAMERLY);
				} else if(e.getActionCommand().equals("baldaars"))    {
					title = "Bald Aars";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.AARSBALD);
				} else if(e.getActionCommand().equals("hairyaars"))    {
					title = "Hairy Aars";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.AARSHAIRY);
				} else if(e.getActionCommand().equals("maurer"))  {
					title = "Dr. Maurer";
					// set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.MAURER);
				} else if(e.getActionCommand().equals("dys_des"))  {
					title = "Dysfunctional Designers";
					//set display_items to the items given by the professor list
					display_items = Catalogue.getInstance().searchByProfessor(Professor.DYS_DES); 
				} else {
					System.err.println("ERROR: GUIdo_Frame.initialize() professor not found!");
					System.err.println("name given: \"" + e.getActionCommand()+"\"");
					return;//throw exception???
				}
				//set the current panel to an item collection that shows the items based on the professor 
				current_panel = new GUIdo_ItemCollection(getWidth(),display_items,title,toItemDisplay,current_user);
				scrollpane.getViewport().add(current_panel);
				scrollpane.repaint();
			}
		});
		
		//set the scrollpane to scroll on the current page, by the current_panel 
		//always show the vertical scrollbar but only show the horizontal scrollbar
		//when it is needed, when there is more on the sides than can be seen in the
		//span given 
		scrollpane = new JScrollPane(current_panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setAutoscrolls(true);
		
		scrollpane.setLocation(0, TOOLBAR_HEIGHT);
		
		//set the size based on the size of this frame, compensating for the toolbars 
		scrollpane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-4*TOOLBAR_HEIGHT));
		//scroll faster: 
		scrollpane.getVerticalScrollBar().setUnitIncrement(5);
		
		//add an action listener to repaint when the scrollpane is scrolled in
		scrollpane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				//******THIS is what seems to be causing re-clicking the button to repaint the page over itself
				
				scrollpane.repaint();
//				toolbar.repaint();
				
			}
			
		});
		
		//get a new panel to put everything in to put within the frame 
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		//add toolbar and pane to the panel
		panel.add(toolbar);
		panel.add(professorHeader);
		panel.add(scrollpane);
		this.add(panel);
		
		//
//		this.addComponentListener(new ComponentAdapter() {
//			public void componentResized(ComponentEvent evt) {
//				JFrame thisframe = (JFrame)evt.getSource();
//			}
//		});
		
		
		//set visible after adding Components: 
		this.setVisible(true);
		
		this.to_login();
		toolbar.disable_all_buttons();
		
		//repaint for toolbar, then for others:
		this.toolbar.repaint();
		this.current_panel.repaint();
		
	}
}
