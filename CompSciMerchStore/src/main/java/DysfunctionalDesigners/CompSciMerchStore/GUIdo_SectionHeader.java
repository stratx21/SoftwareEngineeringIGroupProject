package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionListener;

public class GUIdo_SectionHeader extends GUIdo_CPanel{
	
	final int SECTIONS=6;
	
	public GUIdo_SectionHeader(int x, int y, int width, int height, final ActionListener done) {
		this.setSize(width, height);
		
		GUIdo_CButton section1 = new GUIdo_CButton(x+0*width/SECTIONS,y,width/SECTIONS,height,"cerny");
		section1.setActionCommand("Dr. Cerny");
		section1.setActionListener_clicked(done);
		section1.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section2 = new GUIdo_CButton(x+1*width/SECTIONS,y,width/SECTIONS,height,"booth");
		section2.setActionCommand("Dr. Booth");
		section2.setActionListener_clicked(done);
		section2.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section3 = new GUIdo_CButton(x+2*width/SECTIONS,y,width/SECTIONS,height,"fry");
		section3.setActionCommand("Professor Fry");
		section3.setActionListener_clicked(done);
		section3.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section4 = new GUIdo_CButton(x+3*width/SECTIONS,y,width/SECTIONS,height,"hamerly");
		section4.setActionCommand("Dr. Hamerly");
		section4.setActionListener_clicked(done);
		section4.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section5 = new GUIdo_CButton(x+4*width/SECTIONS,y,width/SECTIONS,height,"aars");
		section5.setActionCommand("Bald and Balding Aars");
		section5.setActionListener_clicked(done);
		section5.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section6 = new GUIdo_CButton(x+5*width/SECTIONS,y,width/SECTIONS,height,"maurer");
		section6.setActionCommand("Maurer");
		section6.setActionListener_clicked(done);
		section6.setBackground(new Color(255,181,9));
		
		this.add(section1);
		this.add(section2);
		this.add(section3);
		this.add(section4);
		this.add(section5);
		this.add(section6);
		
		this.repaint();
	}
	
}
