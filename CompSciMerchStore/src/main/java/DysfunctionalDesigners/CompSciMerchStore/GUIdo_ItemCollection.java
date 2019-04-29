package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GUIdo_ItemCollection extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ItemCollection.class.getName());

	/**
	 * the list of items to display in the collection. 
	 */
	private List<ItemInfo> items_to_display = null;
	
	
	/**
	 * The height of the item display for each display. 
	 */
	private static final int ITEM_DISPLAY_HEIGHT = 600;
	
	/**
	 * The view button height to click to view the specific
	 *  item in the item collection. 
	 */
	private static final int VIEW_BUTTON_HEIGHT = 50;
	
	/**
	 * The height of the wishlist button to toggle the 
	 *  wishlist option for each item. 
	 */
	public static final int WISHLIST_BUTTON_HEIGHT = 45;
	
	/**
	 * the y to start the title at. 
	 */
	private static final int TITLE_Y = 55;
	
	/**
	 * the original x to start building the items at. 
	 */
	private static final int ORIGINAL_X = 50;
	
	/**
	 * The gap height between each row of items. 
	 */
	private static final int GAP_Y = 20;
	
	/**
	 * the gap width between each column of items. 
	 */
	private static final int GAP_X = 15;
	
	/**
	 * the adjuster variable used to compensate to make the height look right. 
	 */
	private static final int HEIGHT_ADJUSTER = 70;
	
	/**
	 * The ImageIcon instances that are used for the wishlist button. 
	 */
	public static ImageIcon onlist1 ,onlist2,onlist3,offlist1,offlist2,offlist3;
	
	/**
	 * the width of each display for each item. 
	 */
	private int item_display_width;
	
	/**
	 * the number of items per row of the item collection. 
	 */
	private static final int ITEMS_PER_ROW = 5;
	
//	private static final int ITEMS_PER_PAGE = 30;
	
	/**
	 * The Font object used for the title at the top of the collection. 
	 */
	private static final Font TITLE_FONT = new Font("Ariel",Font.BOLD,50);
	
	/**
	 * The title used at the top of the page. 
	 */
	private String title = null;
	
	/**
	 * The number of items in the item collection. 
	 */
	private int item_count=-1;
	
	/**
	 * The user that is logged in. 
	 */
	private Customer customer=null;
	
	/**
	 * set up the ImageIcon objects for the wishlist buttons. 
	 */
	static {
		try {
			onlist1 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/1_onlist.png")));
		    onlist2 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/2_onlist.png")));
		    onlist3 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/3_onlist.png")));
		
		
			offlist1 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/1.png")));
			offlist2 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/2.png")));
			offlist3 = new ImageIcon(ImageIO.read(new File(App.resourceTarget + "wishlist/3.png")));
		} catch(IOException ioex) {
			logger.severe("ERROR importing images for wishlist in GUIdo_ItemCollection");
			ioex.printStackTrace();
		}
	}
	
	/**
	 * Set up the item collection with the pag width, the items to display, the title of the page, the ActionListener
	 *  to be used when an item is selected to view, and the current user that is logged in. 
	 * 
	 * @param width the width of the page in pixels. 
	 * @param display_items the list of items to display. 
	 * @param title the title to put at the top of the page. 
	 * @param done the ActionListener instance to use when an item is selected. 
	 * @param user the current user that is logged in. 
	 */
	public GUIdo_ItemCollection(int width, List<ItemInfo> display_items, String title, ActionListener done, User user) {
		super(/*width,*/ITEM_DISPLAY_HEIGHT*display_items.size()/ITEMS_PER_ROW+800);//item display height * item rows + const
		
		this.item_count=display_items.size();
		this.items_to_display=display_items;
		this.title = title;
		
		//calculate the width for the display of each item 
		this.item_display_width= (width-2*ORIGINAL_X-GUIdo_ItemCollection.ITEMS_PER_ROW*GUIdo_ItemCollection.GAP_X)/ITEMS_PER_ROW;
		
		
		GUIdo_CButton button_to_add = null;
		GUIdo_CButton wishlist_button = null;
		
		//if the user is not an admin then add the wishlist buttons
		if(!user.isAdmin()) {
			wishlist_button = new GUIdo_CButton(0,0,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
			customer = (Customer)user;
		}
		
		//set the x and y values to the original locations to start the process. 
		int x = ORIGINAL_X;
		int y = GUIdo_ItemCollection.ITEM_DISPLAY_HEIGHT-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT+GUIdo_ItemCollection.HEIGHT_ADJUSTER;
		
		//for each item to display: 
		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
			//add a view button for it:
			button_to_add = new GUIdo_CButton(x,y,this.item_display_width,GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT,"view");
			button_to_add.setActionCommand(itemIndex+"");
			button_to_add.setActionListener_clicked(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					done.actionPerformed(new ActionEvent(items_to_display.get(Integer.parseInt(e.getActionCommand())),ActionEvent.ACTION_PERFORMED,items_to_display.get(Integer.parseInt(e.getActionCommand())).getExtendedItemID()));
				}
			});
			
			//if the wishlist button is to be put on then put it on with the item listing.
			if(wishlist_button != null) {
				
				//is customer, add wishlist button 
				wishlist_button = new GUIdo_CButton(x,y-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT/*-GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT*/,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
				if(customer.getWishList().contains(display_items.get(itemIndex).getItemID())) {
					//the item is on the wishlist, make it a filled heart
					wishlist_button.enableIcons(onlist1,onlist2,onlist3);
				} else {
					//the item is not on the wishlist, make it an open heart
					wishlist_button.enableIcons(offlist1,offlist2,offlist3);
				}
				
				//set the item that it will be acting on 
				wishlist_button.setData_from_holding(items_to_display.get(itemIndex));
				
				//toggle if it is on the wishlist:
				wishlist_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUIdo_CButton thisbutton=null;
						try {
							thisbutton = (GUIdo_CButton)(e.getSource());
						} catch(Exception ex) {
							logger.severe("ERROR casting to GUIdo_CButton : GUIdo_ItemCollection");
							ex.printStackTrace();
						}
						
						ItemInfo item=null;
						
						try {
							item = (ItemInfo)((thisbutton).getData_from_holding());
						} catch(NullPointerException ex) {
							logger.severe("ERROR casting to ItemInfo : GUIdo_ItemCollection");
							ex.printStackTrace();
						}
						
						if(customer.getWishList() != null) {
							if(customer.getWishList().contains(item.getItemID())) {
								//has item, so remove it 
								customer.removeItemFromWishlist(item.getItemID());
								thisbutton.enableIcons(offlist1,offlist2,offlist3);
							} else {
								//does not have the item, so add it 
								customer.addItemToWishlist(item.getItemID());
								thisbutton.enableIcons(onlist1,onlist2,onlist3);
							}	
						}
						
					}
				});
				
				this.add(wishlist_button);
				
			}
			
			this.add(button_to_add);
			
			//if the max number of items per row has been achieved by filling with items, then 
			//go to the next row, otherwise just increase the x value to go to the next item
			//listing in the same row 
			if(itemIndex%GUIdo_ItemCollection.ITEMS_PER_ROW==GUIdo_ItemCollection.ITEMS_PER_ROW-1) {
				x = ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+GUIdo_ItemCollection.GAP_Y;
			} else {
				x+=this.item_display_width+GUIdo_ItemCollection.GAP_X;
			}
		}
		
		
		
	}
	
	/**
	 * This repaints the item collection images and data using the Java.awt.Graphics instance used by 
	 *  the page's panel. 
	 *  
	 *  @param g the Java.awt.Graphics instance used to draw the data for the items. 
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		int x=ORIGINAL_X,
		    y=ORIGINAL_X+GUIdo_ItemCollection.HEIGHT_ADJUSTER;
		
		//set the font to the title font then put the title at the top 
		g.setFont(GUIdo_ItemCollection.TITLE_FONT);
		g.drawString(this.title, this.getWidth()/2-GUIdo_OutputTools.getPixelWidth(this.title, GUIdo_ItemCollection.TITLE_FONT)/2, GUIdo_ItemCollection.TITLE_Y);
		
		// for each item to be displayed 
		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
			//draw the display in the item function for the display of the image and other data 
			this.items_to_display.get(itemIndex).drawDisplay(g, x, y, this.item_display_width, GUIdo_ItemCollection.ITEM_DISPLAY_HEIGHT-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT-GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
			//go to the next row if the row is finished, otherwise add to the x to continue
			//in the same row.
			if(itemIndex%ITEMS_PER_ROW==ITEMS_PER_ROW-1) {
				x=ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+GUIdo_ItemCollection.GAP_Y;
			} else {
				x+=this.item_display_width+GUIdo_ItemCollection.GAP_X;
			}
		}
	}
}
