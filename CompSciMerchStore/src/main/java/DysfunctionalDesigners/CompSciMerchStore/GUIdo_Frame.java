package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUIdo_Frame extends JFrame{
	
	/**
	 * the height in pixels of the toolbar
	 */
	private final int TOOLBAR_HEIGHT = 70;//pixels
	
	private JScrollPane scrollpane;
	
	private Sale cart; 

	/**
	 * the GUIdo_CToolbar instance to be used for the toolbar in this frame
	 */
	GUIdo_CToolbar toolbar = null;
	
	/**
	 * the GUIdo_SectionHeader instance to be used for the toolbar for the 
	 * 	list of professors 
	 */
	GUIdo_SectionHeader professorHeader = null;
	
	private User current_user = null;
	
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
	
	private void toolbar_call(ActionEvent e) {
		if(e.getActionCommand().equals("search")) {
			//the flow for the search, when the search is submitted
			String searched = (String)e.getSource();
			
			
		} else if(e.getActionCommand().equals("home")) {
			to_homescreen();
		} else if(e.getActionCommand().equals("wishlist")) {
			System.out.println("wishlist");
		} else if(e.getActionCommand().equals("login")) {
			to_login();
			
			
		} else if(e.getActionCommand().equals("cart")) {
			to_cart((Sale)e.getSource());
		}
	}
	
	private void to_login() {
		current_panel = new GUIdo_LoginScreen(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				login_call(e);
			}
		});
		scrollpane.getViewport().add(current_panel);
		
	}
	
	private void login_call(ActionEvent e) {
		if(e.getActionCommand().equals("Enter")) {
			if(e.getSource() instanceof User) {
				current_user = (User)e.getSource();
			} else {
				System.err.println("ERROR: User not passed back as the source, GUIdo_Frame");
			}
			
			this.cart = new Sale(this.current_user.getUserID());
			to_homescreen();
		} else if(e.getActionCommand().equals("Continue as a guest!")) {
			
			//current_user = new Customer()*************************************************************************************************
			
			this.cart = new Sale(this.current_user.getUserID());
			to_homescreen();
		} else if(e.getActionCommand().equals("Create Account")) {
			
		} else if(e.getActionCommand().equals("Forgot Password?")) {
			
		}
	}
	
	private void to_cart(Sale sale) {
		this.current_panel = new GUIdo_ReviewAndEditOrder(sale);
		scrollpane.getViewport().add(this.current_panel);
	}
	
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
	
	private void display_item(ItemInfo item) {
		current_panel = new GUIdo_ItemDisplay(item,scrollpane.getWidth());
		scrollpane.getViewport().add(current_panel);
	}
	
	/**
	 * This initializes this instance of GUIdo_Frame by setting up the frame,
	 * 	setting up the toolbar and the scrolling pane with the current panel
	 *  within a panel to add to the frame. 
	 */
	public void initialize() {
		
		
		//frame setup:
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				if(e.getActionCommand().equals("section1")) {
					
				}
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
		
		
		
		
		//
		//this (frame) contains:
		//		- panel, contains:
		//			- toolbar
		//			- pane (JScrollPane), contains:
		//				- current_panel
		//		
		

		
		//this.current_panel.setLocation(0, TOOLBAR_HEIGHT);
		
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				JFrame thisframe = (JFrame)evt.getSource();
//				System.out.println("GUIdo_Frame resized to (" + thisframe.getWidth() + "," + thisframe.getHeight() + ")");
				
//				toolbar.setSize(thisframe.getWidth(),TOOLBAR_HEIGHT);
//				current_panel.setSize(thisframe.getWidth(), current_panel.getHeight());
//				current_panel.setLocation(0, TOOLBAR_HEIGHT);
//				thisframe.remove(pane);
//				pane.setLocation(0, TOOLBAR_HEIGHT);
//				pane.setSize(thisframe.getWidth(), thisframe.getHeight()-TOOLBAR_HEIGHT*3);
//				thisframe.add(pane);
			}
		});
		
		
		//set visible after adding Components: 
		this.setVisible(true);
		
		this.to_homescreen();
		
		//repaint for toolbar, then for others:
		this.toolbar.repaint();
		this.current_panel.repaint();
		
	}
}
