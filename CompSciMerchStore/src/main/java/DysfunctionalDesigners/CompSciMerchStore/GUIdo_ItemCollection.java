package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUIdo_ItemCollection extends GUIdo_CPanel{

	private List<ItemInfo> items_to_display = null;
	
	private static final int ITEM_DISPLAY_HEIGHT = 300;
	
	private static final int VIEW_BUTTON_HEIGHT = 50;
	
	private static final int TITLE_Y = 10;
	
	private static final int ORIGINAL_X = 50;
	
	private static final int GAP_Y = 20;
	
	private static final int GAP_X = 15;
	
	private int item_display_width;
	
	private static final int ITEMS_PER_ROW = 3;
	
//	private static final int ITEMS_PER_PAGE = 30;
	
	private static final Font TITLE_FONT = new Font("Ariel",Font.BOLD,50);
	
	private String title = null;
	
	private int item_count=-1;
	
//	private int current_page=1;
	
	//for pages ? page numbers, loop, for i, while i*items_per_page < display_items.size()
	
	public GUIdo_ItemCollection(List<ItemInfo> display_items, String title, ActionListener done) {
		super(ITEM_DISPLAY_HEIGHT*display_items.size()/ITEMS_PER_ROW+800);//item display height * item rows + const
		this.item_count=display_items.size();
//		this.current_page=1;
		this.items_to_display=display_items;
		this.title = title;
		
		this.item_display_width= this.getWidth()-2*ORIGINAL_X-this.ITEMS_PER_ROW*this.GAP_X;
		
		GUIdo_CButton button_to_add = null;
		int x = ORIGINAL_X;
		int y = this.ITEM_DISPLAY_HEIGHT-this.VIEW_BUTTON_HEIGHT;
		
		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
			button_to_add = new GUIdo_CButton(x,y,this.item_display_width,this.VIEW_BUTTON_HEIGHT,"view");
			button_to_add.setActionCommand(itemIndex+"");
			button_to_add.setActionListener_clicked(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					done.actionPerformed(new ActionEvent(items_to_display.get(Integer.parseInt(e.getActionCommand())),ActionEvent.ACTION_PERFORMED,items_to_display.get(Integer.parseInt(e.getActionCommand())).getExtendedItemID()));
				}
			});
			this.add(button_to_add);
			if(itemIndex%this.ITEMS_PER_ROW==this.ITEMS_PER_ROW-1) {
				x = ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+this.GAP_Y;
			} else {
				x+=this.item_display_width+this.GAP_X;
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
			this.items_to_display.get(itemIndex).drawDisplay(g, x, y, this.item_display_width, ITEM_DISPLAY_HEIGHT);
			if(itemIndex%ITEMS_PER_ROW==ITEMS_PER_ROW-1) {
				x=ORIGINAL_X;
				y+=ITEM_DISPLAY_HEIGHT+this.GAP_Y;
			} else {
				x+=this.item_display_width+this.GAP_X;
			}
		}
	}
}
