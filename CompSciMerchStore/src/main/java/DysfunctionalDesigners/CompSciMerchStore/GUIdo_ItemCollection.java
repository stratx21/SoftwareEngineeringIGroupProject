package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GUIdo_ItemCollection extends GUIdo_CPanel{

	private List<ItemInfo> items_to_display = null;
	
	private static final int ITEM_DISPLAY_HEIGHT = 300;
	
	private static final int VIEW_BUTTON_HEIGHT = 50;
	
	public static final int WISHLIST_BUTTON_HEIGHT = 45;
	
	private static final int TITLE_Y = 55;
	
	private static final int ORIGINAL_X = 50;
	
	private static final int GAP_Y = 20;
	
	private static final int GAP_X = 15;
	
	public static ImageIcon onlist1 ,onlist2,onlist3,offlist1,offlist2,offlist3;
	
	private int item_display_width;
	
	private static final int ITEMS_PER_ROW = 3;
	
//	private static final int ITEMS_PER_PAGE = 30;
	
	private static final Font TITLE_FONT = new Font("Ariel",Font.BOLD,50);
	
	private String title = null;
	
	private int item_count=-1;
	
	private Customer customer=null;
	
	static {
		try {
			onlist1 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/1_onlist.png")));
		    onlist2 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/2_onlist.png")));
		    onlist3 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/3_onlist.png")));
		
		
			offlist1 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/1.png")));
			offlist2 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/2.png")));
			offlist3 = new ImageIcon(ImageIO.read(new File("src/main/resources/wishlist/3.png")));
		} catch(IOException ioex) {
			System.err.println("ERROR importing images for wishlist in GUIdo_ItemCollection");
			ioex.printStackTrace();
		}
	}
	
//	private int current_page=1;
	
	//for pages ? page numbers, loop, for i, while i*items_per_page < display_items.size()
	
	public GUIdo_ItemCollection(List<ItemInfo> display_items, String title, ActionListener done, User user) {
		super(ITEM_DISPLAY_HEIGHT*display_items.size()/ITEMS_PER_ROW+800);//item display height * item rows + const
		this.item_count=display_items.size();
//		this.current_page=1;
		this.items_to_display=display_items;
		this.title = title;
		
		this.item_display_width= this.getWidth()-2*ORIGINAL_X-this.ITEMS_PER_ROW*this.GAP_X;
		
		GUIdo_CButton button_to_add = null;
		GUIdo_CButton wishlist_button = null;
		
		if(!user.isAdmin()) {
			wishlist_button = new GUIdo_CButton(0,0,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
			customer = (Customer)user;
		}
		
		int x = ORIGINAL_X;
		int y = GUIdo_ItemCollection.ITEM_DISPLAY_HEIGHT-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT;
		
		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
			button_to_add = new GUIdo_CButton(x,y,this.item_display_width,GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT,"view");
			button_to_add.setActionCommand(itemIndex+"");
			button_to_add.setActionListener_clicked(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					done.actionPerformed(new ActionEvent(items_to_display.get(Integer.parseInt(e.getActionCommand())),ActionEvent.ACTION_PERFORMED,items_to_display.get(Integer.parseInt(e.getActionCommand())).getExtendedItemID()));
				}
			});
			
			if(wishlist_button != null) {
				
				//is customer, add wishlist button 
				wishlist_button = new GUIdo_CButton(x,y-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT-GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
				if(customer.getWishList().contains(itemIndex)) {
					wishlist_button.enableIcons(onlist1,onlist2,onlist3);
				} else {
					wishlist_button.enableIcons(offlist1,offlist2,offlist3);
				}
				
				wishlist_button.setData_from_holding(items_to_display.get(itemIndex));
				
				//toggle:
				wishlist_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUIdo_CButton thisbutton=null;
						try {
							thisbutton = (GUIdo_CButton)(e.getSource());
						} catch(Exception ex) {
							System.err.println("ERROR casting to GUIdo_CButton : GUIdo_ItemCollection");
							ex.printStackTrace();
						}
						
						ItemInfo item=null;
						
						try {
							item = (ItemInfo)((thisbutton).getData_from_holding());
						} catch(Exception ex) {
							System.err.println("ERROR casting to ItemInfo : GUIdo_ItemCollection");
							ex.printStackTrace();
						}
						
						if(customer.getWishList().contains(item.getItemID())) {
							//has item, so remove it 
							customer.removeItemFromWishlist(item.getItemID());
							thisbutton.enableIcons(offlist1,offlist2,offlist3);
						} else {
							customer.addItemToWishlist(item.getItemID());
							thisbutton.enableIcons(onlist1,onlist2,onlist3);
						}
					}
				});
				
				this.add(wishlist_button);
				
			}
			
			this.add(button_to_add);
			if(itemIndex%GUIdo_ItemCollection.ITEMS_PER_ROW==GUIdo_ItemCollection.ITEMS_PER_ROW-1) {
				x = ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+GUIdo_ItemCollection.GAP_Y;
			} else {
				x+=this.item_display_width+GUIdo_ItemCollection.GAP_X;
			}
		}
		
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		int x=ORIGINAL_X,
		    y=ORIGINAL_X;
		
		
		g.setFont(this.TITLE_FONT);
		g.drawString(this.title, this.getWidth()/2-GUIdo_OutputTools.getPixelWidth(this.title, this.TITLE_FONT)/2, this.TITLE_Y);
		
		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
			this.items_to_display.get(itemIndex).drawDisplay(g, x, y, this.item_display_width, GUIdo_ItemCollection.ITEM_DISPLAY_HEIGHT-GUIdo_ItemCollection.VIEW_BUTTON_HEIGHT-GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
			if(itemIndex%ITEMS_PER_ROW==ITEMS_PER_ROW-1) {
				x=ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+this.GAP_Y;
			} else {
				x+=this.item_display_width+this.GAP_X;
			}
		}
	}
}
