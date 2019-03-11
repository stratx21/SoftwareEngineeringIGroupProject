package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Dimension;
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
	private final int TOOLBAR_HEIGHT = 75;//pixels
	
	/**
	 * the width in pixels of the window
	 */
	private final int INITIAL_WIDTH=1350;
	
	/**
	 * The height in pixels of the window
	 */
	private final int INITIAL_HEIGHT=850;

	/**
	 * the GUIdo_CToolbar instance to be used for the toolbar in this frame
	 */
	GUIdo_CToolbar toolbar = null;
	
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
	 * This initializes this instance of GUIdo_Frame by setting up the frame,
	 * 	setting up the toolbar and the scrolling pane with the current panel
	 *  within a panel to add to the frame. 
	 */
	public void initialize() {
		
		
		//frame setup:
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Computer Science Merchandise Store");
		this.setSize(this.INITIAL_WIDTH, this.INITIAL_HEIGHT);
		this.setResizable(true);
		
		
		toolbar = new GUIdo_CToolbar(0,0,this.getWidth(),this.TOOLBAR_HEIGHT,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("search")) {
					//the flow for the search, when the search is submitted
					
				} else if(e.getActionCommand().equals("home")) {
					current_panel=new GUIdo_Homescreen();
					current_panel.repaint();
				} else if(e.getActionCommand().equals("wishlist")) {
					System.out.println("wishlist");
				} else if(e.getActionCommand().equals("login")) {
					System.out.println("login");
				}
			}
		});
//		toolbar.setMinimumSize(new Dimension(250,TOOLBAR_HEIGHT));
//		toolbar.setMaximumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),TOOLBAR_HEIGHT));
		
		this.current_panel=new GUIdo_Homescreen();
		
		
		
		
		
		final JScrollPane pane = new JScrollPane(current_panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setAutoscrolls(true);
		
		pane.setLocation(0, TOOLBAR_HEIGHT);
		pane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-150));
		
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
