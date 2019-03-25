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

	/**
	 * the GUIdo_CToolbar instance to be used for the toolbar in this frame
	 */
	GUIdo_CToolbar toolbar = null;
	
	/**
	 * the GUIdo_SectionHeader instance to be used for the toolbar for the 
	 * 	list of professors 
	 */
	GUIdo_SectionHeader professorHeader = null;
	
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
			current_panel=new GUIdo_Homescreen();
			current_panel.repaint();
		} else if(e.getActionCommand().equals("wishlist")) {
			System.out.println("wishlist");
		} else if(e.getActionCommand().equals("login")) {
			System.out.println("login");
			current_panel = new GUIdo_LoginScreen(new ActionListener () {
				public void actionPerformed(ActionEvent e) {
					login_call(e);
				}
			});
		}
	}
	
	private void login_call(ActionEvent e) {
		
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
		
		this.current_panel=new GUIdo_Homescreen();
		
		
		
		
		
		final JScrollPane pane = new JScrollPane(current_panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setAutoscrolls(true);
		
		pane.setLocation(0, TOOLBAR_HEIGHT);
		pane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-4*TOOLBAR_HEIGHT));
		
		pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				
				pane.repaint();
				toolbar.repaint();
				
			}
			
		});
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		//add toolbar and pane to the panel
		panel.add(toolbar);
		panel.add(professorHeader);
		panel.add(pane);
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
		
		//repaint for toolbar, then for others:
		this.toolbar.repaint();
		this.current_panel.repaint();
		
	}
}
