package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GUIdo_Homescreen extends GUIdo_CPanel{
	
	public GUIdo_Homescreen(){
		super();
		this.setPreferredSize(new Dimension(this.getWidth(), 1500));
		this.repaint();
//		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(50, 50, 100, 1000);
	}

}
