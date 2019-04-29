package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.event.ActionListener;

public class GUIdo_UploadItem extends GUIdo_EditItem{
	
	/**
	 * This sets up the UploadItem page using the width of the page, the ActionListener
	 *  instance used to go back, and the User which must be confirmed to be a vendor by 
	 *  this point.
	 * 
	 * preconditions: the user is a Vendor. All users are vendors. This  is guaranteed. 
	 * 
	 * @param width the width of the page in pixels.
	 * @param done the ActionListener instance that is used to go back. 
	 * @param user the User instance that is logged in. 
	 * @throws Exception the Exception thrown from the ItemInfo construction.
	 */
	public GUIdo_UploadItem(int width, ActionListener done,User user) throws Exception{
		super(width,new ItemInfo("","",0,((Vendor)(user)).getUserID(),0.00,Professor.AARSBALD),done,user);
		this.setBackground(Color.WHITE);
	}
	
}
