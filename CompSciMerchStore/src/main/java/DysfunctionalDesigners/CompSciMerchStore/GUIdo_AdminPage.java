package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUIdo_AdminPage extends GUIdo_CPanel {
	private static Logger logger = Logger.getLogger(GUIdo_AdminPage.class.getName());
	
	
	/**
	 * 
	 * The Font instance used for the complaints to display them. 
	 * 
	 */
	private static final Font COMPLAINTS_FONT = new Font("Cambria", Font.PLAIN, 20);
	
	/**
	 * The width of the buttons that are in the admin page used in pixels
	 * 	for the buttons width. 
	 * 
	 */
	private static final int BUTTON_WIDTH = 250;
	
	/**
	 * The height of the buttons that are in the admin page in pixels
	 *  for the height of the buttons. 
	 *  
	 */
	private static final int BUTTON_HEIGHT = 80;
	
	/**
	 * The gap in pixels in terms of the y axis between each part of
	 *  the buttons. 
	 * 
	 */
	private static final int Y_GAP = 15;
	
	
	/**
	 * The height in pixels of each line of the complaints. 
	 * 
	 */
	private static final int COMPLAINT_HEIGHT 
		= GUIdo_OutputTools.getPixelHeight("Complaint", GUIdo_AdminPage.COMPLAINTS_FONT) + 5;
	
	/**
	 * The y value used to move the buttons and more below each other; the default value
	 *  is set here for the start of the title. 
	 * 
	 */
	private int y = 75;
	
	/**
	 * The boolean value to tell if the complaints are being shown already so that
	 *  the complaints are not added twice. 
	 * 
	 */
	private boolean complaints_shown = false;
	
	/**
	 * The title for the admin page. 
	 * 
	 */
	private static final String TITLE = "Admin Page";
	
	/**
	 * The Font instance that is used for the title of the admin page. 
	 * 
	 */
	private static final Font TITLE_FONT = new Font("Cambria", Font.PLAIN,50);
	
	/**
	 * The gap in pixels between each line of the complaints.
	 * 
	 */
	private static final int COMPLAINT_GAP = 7;
	
	
	/**
	 * This sets up the Admin Page with the given width in pixels of the page 
	 * 	and the Administrator instance that is logged in that is trying to
	 *  access the admin page. 
	 * 
	 * @param width the width in pixels of the page. 
	 * @param admin the Administrator instance that is used for the login
	 *  to see the admin page. 
	 *  @param done the ActionListener instance that is used to go to the 
	 *   promo code adding. 
	 */
	public GUIdo_AdminPage(int width, Administrator admin, ActionListener done) {
		//set up the page using the width given of the frame and a default 
		//page length. 
		super(width, 1200);
		this.setBackground(Color.WHITE);
		logger.info("Switched to AdminPage");
		//the title for the page 
		
		JLabel title = new JLabel(GUIdo_AdminPage.TITLE); 
		title.setBounds(width/2-GUIdo_OutputTools.getPixelWidth(GUIdo_AdminPage.TITLE, GUIdo_AdminPage.TITLE_FONT)/2,
					y,BUTTON_WIDTH,BUTTON_HEIGHT);
		title.setFont(GUIdo_AdminPage.TITLE_FONT);
		this.add(title);
		
		//add some buffer below the title: 
		y += BUTTON_HEIGHT+30;
		
		
		GUIdo_CButton generateAllSalesReports = new GUIdo_CButton(
				width/2-BUTTON_WIDTH/2,y,BUTTON_WIDTH,BUTTON_HEIGHT,
				"generate all sales report");
		generateAllSalesReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(admin.generateAllSalesReport()) {
					JOptionPane.showMessageDialog(new JFrame(), "All Sales Report generated successfully!");
					logger.info("All Sales Report generated successfully");
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "Sales Report failed to generate");
					logger.info("Unable to generate all sales report");

				}
					
			}
		});
		this.add(generateAllSalesReports); 
		
		y+=BUTTON_HEIGHT+this.Y_GAP; 
		
		
		//////////////////////////////////////////////////////
		
		GUIdo_CButton generateAllUsersReports = new GUIdo_CButton(
				width/2-BUTTON_WIDTH/2,y,BUTTON_WIDTH,BUTTON_HEIGHT,
				"generate all users report");
		generateAllUsersReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(admin.generateAllUsersReport()) {
					JOptionPane.showMessageDialog(new JFrame(), "All Users Report generated successfully!");
					logger.fine("All Users report generated successfully");
				}
					
				else {
					JOptionPane.showMessageDialog(new JFrame(), "All Users Report failed to generate");
					logger.fine("All Users report failed to generate");
				}
					
			}
		});
		
		this.add(generateAllUsersReports);
		
		y+=BUTTON_HEIGHT+GUIdo_AdminPage.Y_GAP;
		
		//////////////////////////////////////////////////////
		
		GUIdo_CButton make_promo = new GUIdo_CButton(width/2-BUTTON_WIDTH/2,
													  y,
													  BUTTON_WIDTH,
													  BUTTON_HEIGHT,
													  "Generate Promo Code");
		make_promo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				done.actionPerformed(new ActionEvent(null, ActionEvent.ACTION_PERFORMED, "make_promo"));
			}
		});
		this.add(make_promo);
		
		y+=BUTTON_HEIGHT+GUIdo_AdminPage.Y_GAP;
		
		//////////////////////////////////////////////////////
		
		//the button to get the complaints and show it: 
		GUIdo_CButton get_all_complaints = new GUIdo_CButton(
				width/2-BUTTON_WIDTH/2,y,BUTTON_WIDTH,BUTTON_HEIGHT,
				"Get All Complaints");
		get_all_complaints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//only if the complaints have not already been added: 
				if(!complaints_shown) {
					
					//get the complaints list from the admin priviledges: 
					List<String> complaints = null;
					try {
						complaints = Arrays.asList(admin.getAllComplaints());
					} catch (Exception e1) {
						logger.severe("ERROR: CAUGHT EXCEPTION IN ADMIN PAGE COMPLAINTS"); 
						e1.printStackTrace();
					}
					
					//make JLabel objects for the complaints: 
					JLabel toadd = new JLabel("Complaint: ");
					try {
						for(String complaint : complaints) {
							toadd = new JLabel("Complaint: ");
							toadd.setFont(GUIdo_AdminPage.COMPLAINTS_FONT); 
							toadd.setBounds(getWidth()/6, 
												y, 
												250, 
												GUIdo_AdminPage.COMPLAINT_HEIGHT); 
							add(toadd); 
							y += GUIdo_AdminPage.COMPLAINT_HEIGHT+GUIdo_AdminPage.COMPLAINT_GAP; 
							
							//for each line in the complaint, print the lines so that they are
							//not word wrapped: 
							for(String line : 
								GUIdo_OutputTools.formatStringForPrompt(complaint,  
										GUIdo_AdminPage.COMPLAINTS_FONT, getWidth()*2/3)) { 
								
								toadd = new JLabel(line);
								toadd.setFont(GUIdo_AdminPage.COMPLAINTS_FONT);
								toadd.setBounds(getWidth()/6, 
												y, 
												getWidth()*2/3, 
												GUIdo_AdminPage.COMPLAINT_HEIGHT); 
								add(toadd); 
								y += GUIdo_AdminPage.COMPLAINT_HEIGHT+GUIdo_AdminPage.COMPLAINT_GAP; 
							} 
							y+= GUIdo_AdminPage.COMPLAINT_GAP*2;  
						}
						
				}catch(NullPointerException e1) {
					logger.severe("ERROR: NULLPOINTER CAUGHT IN ADMINPAGE");
					e1.printStackTrace();
				}
				}
				
				page_length= y+200;
				setPreferredSize(new Dimension(getWidth(),page_length));
				repaint();
			}
		});
		//add to this page: 
		this.add(get_all_complaints);
        
		y+= GUIdo_AdminPage.BUTTON_HEIGHT+GUIdo_AdminPage.Y_GAP;
		
		/////////////////////////////////////
		
		
	}

}




