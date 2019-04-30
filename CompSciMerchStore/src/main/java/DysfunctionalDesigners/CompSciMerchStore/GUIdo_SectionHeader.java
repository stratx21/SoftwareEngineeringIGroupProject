package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComboBox;

public class GUIdo_SectionHeader extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_SectionHeader.class.getName());

	/**
	 * The number of sections of professors that are shown
	 */
	final int SECTIONS=8;

	/**
	 * The buttons that are used for each professor
	 */
	private ArrayList<GUIdo_CButton> buttons = new ArrayList<>();

	/**
	 * This sets up the section header using the x, y, width, and height of the toolbar that is allotted,
	 *  and the ActionListener for when a section option is clicked to control the flow to an item collection
	 *  for whatever section is chosen.
	 * @param x the x location to start the toolbar at
	 * @param y the y location to start the toolbar at
	 * @param width the allotted width for the toolbar
	 * @param height the alloted height for the toolbar
	 * @param done the ActionListener that is used to return from the section header to go to the item collection
	 * 	for the section.
	 */
	public GUIdo_SectionHeader(int x, int y, int width, int height, final ActionListener done) {
		this.setSize(width, height);
		
		//the button for Dr. Cerny's section
		GUIdo_CButton section1 = new GUIdo_CButton(x+0*width/SECTIONS,y,width/SECTIONS,height,"Dr. Cerny");
		section1.setActionCommand("cerny");
		section1.setActionListener_clicked(done);
		section1.setBackground(new Color(255,228,225));
		section1.setHoverColor(new Color(255,192,203));

		//the button for Dr. Booth's section
		GUIdo_CButton section2 = new GUIdo_CButton(x+1*width/SECTIONS,y,width/SECTIONS,height,"Dr. Booth");
		section2.setActionCommand("booth");
		section2.setActionListener_clicked(done);
		section2.setBackground(new Color(255,228,225));
		section2.setHoverColor(new Color(255,192,203));

		//the button for Professor Fry's section
		GUIdo_CButton section3 = new GUIdo_CButton(x+2*width/SECTIONS,y,width/SECTIONS,height,"Professor Fry");
		section3.setActionCommand("fry");
		section3.setActionListener_clicked(done);
		section3.setBackground(new Color(255,228,225));
		section3.setHoverColor(new Color(255,192,203));

		//the button for Dr. Hamerly's section
		GUIdo_CButton section4 = new GUIdo_CButton(x+3*width/SECTIONS,y,width/SECTIONS,height,"Dr. Hamerly");
		section4.setActionCommand("hamerly");
		section4.setActionListener_clicked(done);
		section4.setBackground(new Color(255,228,225));
		section4.setHoverColor(new Color(255,192,203));

		//the button for Bald Aars' section
		GUIdo_CButton section5 = new GUIdo_CButton(x+4*width/SECTIONS,y,width/SECTIONS,height,"Professor Bald Aars");
		section5.setActionCommand("baldaars");
		section5.setActionListener_clicked(done);
		section5.setBackground(new Color(255,228,225));
		section5.setHoverColor(new Color(255,192,203));

		//the button for Hairy Aars' section
		GUIdo_CButton section6 = new GUIdo_CButton(x+5*width/SECTIONS,y,width/SECTIONS,height,"Professor Hairy Aars");
		section6.setActionCommand("hairyaars");
		section6.setActionListener_clicked(done);
		section6.setBackground(new Color(255,228,225));
		section6.setHoverColor(new Color(255,192,203));

		//the button for Dr. Maurer's section
		GUIdo_CButton section7 = new GUIdo_CButton(x+6*width/SECTIONS,y,width/SECTIONS,height,"Dr. Maurer");
		section7.setActionCommand("maurer");
		section7.setActionListener_clicked(done);
		section7.setBackground(new Color(255,228,225));
		section7.setHoverColor(new Color(255,192,203));

		//the button for the Dysfunctional Designers section
		GUIdo_CButton section8 = new GUIdo_CButton(x+7*width/SECTIONS,y,width/SECTIONS,height,"Dysfunctional Designers");
		section8.setActionCommand("dys_des");
		section8.setActionListener_clicked(done);

		section8.setBackground(new Color(255,228,225));
		section8.setHoverColor(new Color(255,192,203));


		//add to the toolbar panel:
		this.add(section1);
		this.add(section2);
		this.add(section3);
		this.add(section4);
		this.add(section5);
		this.add(section6);
		this.add(section7);
		this.add(section8);

		//add the buttons to the list to allow enabling and disabling
		buttons.add(section1);
		buttons.add(section2);
		buttons.add(section3);
		buttons.add(section4);
		buttons.add(section5);
		buttons.add(section6);
		buttons.add(section7);
		buttons.add(section8);


		//this.repaint();
	}

	/*
	 * disable all the section buttons, and do not allow them to be clicked
	 */
	public void disable_all_buttons() {
		logger.info("All Buttons Disabled: Logging Out");
		for(GUIdo_CButton button : buttons) {
			button.disable();
		}
	}

	/**
	 * enable all the section buttons, and allow them to be clicked
	 */
	public void enable_all_buttons() {
		logger.info("All Buttons Enabled: Logging In");
		for(GUIdo_CButton button : buttons) {
			button.enable();
		}
	}

}
