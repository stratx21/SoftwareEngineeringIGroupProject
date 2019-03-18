package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionListener;

public class GUIdo_SectionHeader extends GUIdo_CPanel{
	
	final int SECTIONS=6;
	
	public GUIdo_SectionHeader(int x, int y, int width, int height, final ActionListener done) {
		this.setSize(width, height);
		
		GUIdo_CButton section1 = new GUIdo_CButton(x+0*width/SECTIONS,y,width/SECTIONS,height,"section1");
		section1.setActionCommand("section1");
		section1.setActionListener_clicked(done);
		section1.setBackground(Color.YELLOW);
		
		GUIdo_CButton section2 = new GUIdo_CButton(x+1*width/SECTIONS,y,width/SECTIONS,height,"section2");
		section2.setActionCommand("section2");
		section2.setActionListener_clicked(done);
		section2.setBackground(Color.YELLOW);
		
		GUIdo_CButton section3 = new GUIdo_CButton(x+2*width/SECTIONS,y,width/SECTIONS,height,"section3");
		section3.setActionCommand("section3");
		section3.setActionListener_clicked(done);
		section3.setBackground(Color.YELLOW);
		
		GUIdo_CButton section4 = new GUIdo_CButton(x+3*width/SECTIONS,y,width/SECTIONS,height,"section4");
		section4.setActionCommand("section4");
		section4.setActionListener_clicked(done);
		section4.setBackground(Color.YELLOW);
		
		GUIdo_CButton section5 = new GUIdo_CButton(x+4*width/SECTIONS,y,width/SECTIONS,height,"section5");
		section5.setActionCommand("section5");
		section5.setActionListener_clicked(done);
		section5.setBackground(Color.YELLOW);
		
		GUIdo_CButton section6 = new GUIdo_CButton(x+5*width/SECTIONS,y,width/SECTIONS,height,"section6");
		section6.setActionCommand("section6");
		section6.setActionListener_clicked(done);
		section6.setBackground(Color.YELLOW);
		
		this.add(section1);
		this.add(section2);
		this.add(section3);
		this.add(section4);
		this.add(section5);
		this.add(section6);
		
		this.repaint();
	}
	
}
