package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionListener;

public class GUIdo_SectionHeader extends GUIdo_CPanel{
	
	final int SECTIONS=7;
	
	public GUIdo_SectionHeader(int x, int y, int width, int height, final ActionListener done) {
		this.setSize(width, height);
		
		GUIdo_CButton section1 = new GUIdo_CButton(x+0*width/SECTIONS,y,width/SECTIONS,height,"Dr. Cerny");
		section1.setActionCommand("cerny");
		section1.setActionListener_clicked(done);
		section1.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section2 = new GUIdo_CButton(x+1*width/SECTIONS,y,width/SECTIONS,height,"Dr. Booth");
		section2.setActionCommand("booth");
		section2.setActionListener_clicked(done);
		section2.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section3 = new GUIdo_CButton(x+2*width/SECTIONS,y,width/SECTIONS,height,"Professor Fry");
		section3.setActionCommand("fry");
		section3.setActionListener_clicked(done);
		section3.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section4 = new GUIdo_CButton(x+3*width/SECTIONS,y,width/SECTIONS,height,"Dr. Hamerly");
		section4.setActionCommand("hamerly");
		section4.setActionListener_clicked(done);
		section4.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section5 = new GUIdo_CButton(x+4*width/SECTIONS,y,width/SECTIONS,height,"Professor Bald Aars");
		section5.setActionCommand("baldaars");
		section5.setActionListener_clicked(done);
		section5.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section6 = new GUIdo_CButton(x+5*width/SECTIONS,y,width/SECTIONS,height,"Professor Hairy Aars");
		section6.setActionCommand("hairyaars");
		section6.setActionListener_clicked(done);
		section6.setBackground(new Color(255,181,9));
		
		GUIdo_CButton section7 = new GUIdo_CButton(x+6*width/SECTIONS,y,width/SECTIONS,height,"Dr. Maurer");
		section7.setActionCommand("maurer");
		section7.setActionListener_clicked(done);
		section7.setBackground(new Color(255,181,9));
		
		this.add(section1);
		this.add(section2);
		this.add(section3);
		this.add(section4);
		this.add(section5);
		this.add(section6);
		this.add(section7);
		
		this.repaint();
	}
	
}
