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
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
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
				String searched = (String)e.getSource();
				
			}
		} else if(e.getActionCommand().equals("home")) {
			to_homescreen();
		} else if(e.getActionCommand().equals("wishlist")) {
			System.out.println("wishlist");
		} else if(e.getActionCommand().equals("login")) {
			to_login();
			
			
		} else if(e.getActionCommand().equals("cart")) {
			to_cart(cart);
		}
	}
	
	/**
	 * This function goes to the wishlist page for the specified User. 
	 * @param user_to_see the user to see the wishlist page for.
	 */
	public void to_wishlist(User user_to_see) {
		ArrayList<ItemInfo> items_for_wishlist = new ArrayList<>();
		for(Integer id : ((Customer)user_to_see).getWishList()) {
			if(id != null) {
				items_for_wishlist.add(Catalogue.getItem(id));
			}
		}
		this.current_panel = new GUIdo_ItemCollection(items_for_wishlist,"Wishlist",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display_item((ItemInfo)(e.getSource()));
			}
		},user_to_see);
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * This function sets up the login screen and adds it to this frame.
	 */
	private void to_login() {
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
			
			this.cart = new Sale(this.current_user.getUserID());
			toolbar.enable_all_buttons();
			to_homescreen();
		} else if(e.getActionCommand().equals("Continue as a guest!")) {
			
			this.current_user = new Customer(new String[]{"guest@email.com", "guesty", "guest", "guestPass", "Guest", "19999"});
			
			this.cart = new Sale(this.current_user.getUserID());
			toolbar.enable_all_buttons();
			to_homescreen();
			
		} else if(e.getActionCommand().equals("Create Account")) {
			ActionListener l = null;
			current_panel = new GUIdo_CreateAccount(l);
			scrollpane.getViewport().add(current_panel);
			
		} else if(e.getActionCommand().equals("Forgot Password?")) {
			
		}
	}
	
	/**
	 * This function transfers the page to the cart page, where the user can see their
	 * 	subtotal of items including tax and change some values about the items such
	 * 	as quantity. 
	 * @param sale the Sale object that is being used for the Cart to be able to display
	 * 	the cart. 
	 */
	private void to_cart(Sale sale) {
		this.current_panel = new GUIdo_ReviewAndEditOrder(sale);
		scrollpane.getViewport().add(this.current_panel);
		scrollpane.repaint();
		this.current_panel.repaint();
	}
	
	/**
	 * This function goes to and sets up the homescreen. 
	 */
	private void to_homescreen() {
		current_panel = new GUIdo_Homescreen(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("display1") || e.getActionCommand().equals("display2")) {
					display_item((ItemInfo)e.getSource());
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
	private void display_item(ItemInfo item) {
		current_panel = new GUIdo_ItemDisplay(item,scrollpane.getWidth(),cart,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("item_added")) {
					cart = (Sale)e.getSource();
					to_cart(cart);
				}
			}
		});
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * This initializes this instance of GUIdo_Frame by setting up the frame,
	 * 	setting up the toolbar and the scrolling pane with the current panel
	 *  within a panel to add to the frame. 
	 */
	public void initialize() {
		
		//frame setup:
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent windowEvent) {
		        //code here for exiting the program... 

		    }
		});
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Computer Science Merchandise Store");
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen_size);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setResizable(true);
		
		
		toolbar = new GUIdo_CToolbar(0,0,this.getWidth(),this.TOOLBAR_HEIGHT,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolbar_call(e);
			}
		});
		
		professorHeader = new GUIdo_SectionHeader(0,0,this.getWidth(),this.TOOLBAR_HEIGHT,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListener toItemDisplay = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						display_item((ItemInfo)(e.getSource()));
					}
				};
				List<ItemInfo> display_items=null;
				String title=null;
				//set display_items to the items given by the professor list
				//set title to the professor's name
				if(e.getActionCommand().equals("cerny"))          {
					title = "Dr. Cerny";
					//TODO set display_items to the items given by the professor list
				} else if(e.getActionCommand().equals("booth"))   {
					title = "Dr. Booth";
					//TODO set display_items to the items given by the professor list
				} else if(e.getActionCommand().equals("fry"))     {
					title = "Professor Fry";
					//TODO set display_items to the items given by the professor list
				} else if(e.getActionCommand().equals("hamerly")) {
					title = "Dr. Hamerly";
					//TODO set display_items to the items given by the professor list
				} else if(e.getActionCommand().equals("aars"))    {
					title = "Bald and Balding Aars";
					//TODO set display_items to the items given by the professor list
				} else if(e.getActionCommand().equals("maurer"))  {
					title = "Dr. Maurer, the Baldest Aars";
					//TODO set display_items to the items given by the professor list
				} else {
					System.err.println("ERROR: GUIdo_Frame.initialize() professor not found!");
					System.err.println("name given: \"" + e.getActionCommand()+"\"");
					return;//throw exception???
				}
				current_panel = new GUIdo_ItemCollection(display_items,title,toItemDisplay,current_user);
			}
		});
		
		
		scrollpane = new JScrollPane(current_panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setAutoscrolls(true);
		
		scrollpane.setLocation(0, TOOLBAR_HEIGHT);
		scrollpane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-4*TOOLBAR_HEIGHT));
		scrollpane.getVerticalScrollBar().setUnitIncrement(5);
		
		scrollpane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				
				scrollpane.repaint();
				toolbar.repaint();
				
			}
			
		});
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		//add toolbar and pane to the panel
		panel.add(toolbar);
		panel.add(professorHeader);
		panel.add(scrollpane);
		this.add(panel);
		
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				JFrame thisframe = (JFrame)evt.getSource();
			}
		});
		
		
		//set visible after adding Components: 
		this.setVisible(true);
		
		this.to_login();
		toolbar.disable_all_buttons();
		
		//repaint for toolbar, then for others:
		this.toolbar.repaint();
		this.current_panel.repaint();
		
	}
}
