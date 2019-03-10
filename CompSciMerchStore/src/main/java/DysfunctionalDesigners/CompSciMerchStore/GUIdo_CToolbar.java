package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;

public class GUIdo_CToolbar extends GUIdo_CPanel{
	
	private int x,y;
	private int width, height;
	
	public GUIdo_CToolbar(int x, int y, int width, int height) {
		this.setSize(width, height);
		this.width=width;
		this.height=height;
		this.setBackground(Color.DARK_GRAY);
		
		
		GUIdo_CButton home_button = new GUIdo_CButton(x,y,height,height,"HOME");
		
		this.add(home_button);
		
	}
	
}
