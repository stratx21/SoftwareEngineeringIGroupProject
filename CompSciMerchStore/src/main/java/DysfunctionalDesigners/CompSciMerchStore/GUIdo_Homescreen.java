package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Graphics;

public class GUIdo_Homescreen extends GUIdo_CPanel{
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(50, 50, 100, 100);
	}

}
