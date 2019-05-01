package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.event.ActionListener;

public class GUIdo_ViewAllUsers extends GUIdo_CPanel {
	
	public GUIdo_ViewAllUsers(ActionListener done, int width) {
		//set 1200 to something greater later if needed: 
		super(width,1200);//width, length of page in pixels. 
		
		
		//all components can be added to -this- in this area  
		
		//change the length of the page dynamically by using: 
		this.set_new_length(1200);
		//(added a function to make it easier)
	}

}
