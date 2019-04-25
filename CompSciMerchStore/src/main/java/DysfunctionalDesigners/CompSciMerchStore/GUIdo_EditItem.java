package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIdo_EditItem extends GUIdo_CPanel{

	/**
	 * The ItemInfo instance that is being edited. 
	 */
	private ItemInfo item = null;
	
	/**
	 * The width, in pixels, of the textboxes to edit the larger data; This is dependent on 
	 *  the width of the page. 
	 */
	private static int TEXTBOX_WIDTH;
	
	/**
	 * The height of the textboxes to edit the larger data. 
	 */
	private static final int TEXTBOX_HEIGHT = 125;
	
	private static final int SMALLER_TEXT_HEIGHT = 75;
	
	private static final int DONE_WIDTH = 100;
	
	private static final int DONE_HEIGHT = 75;
	
	private static final int Y_GAP = 10;
	
	/**
	 * 
	 * This sets up the edit item page so that the item information can be edited. 
	 * 
	 * @param item_to_edit the item to edit in this page. 
	 * @param done the ActionListener instance that is used to return from this page. 
	 */
	public GUIdo_EditItem(int width,ItemInfo item_to_edit, ActionListener done) {
		super(width,1000);
		GUIdo_EditItem.TEXTBOX_WIDTH = width*2/3;
		this.item=item_to_edit;
		
		//the y to use to increase to move down the page in Components to add
		int y = 200;
		
		
		JTextField name = new JTextField(item.getDisplayName());
		name.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(name);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		//description text field 
		JTextField desc = new JTextField(item.getDescription());
		desc.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, GUIdo_EditItem.TEXTBOX_WIDTH, GUIdo_EditItem.TEXTBOX_HEIGHT);
		this.add(desc); 
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.TEXTBOX_HEIGHT;
		
		JTextField price = new JTextField(""+item.getPrice());
		price.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(price);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		
		
		GUIdo_CButton done_button 
		    = new GUIdo_CButton(width/2-GUIdo_EditItem.DONE_WIDTH/2,y,GUIdo_EditItem.DONE_WIDTH,GUIdo_EditItem.DONE_HEIGHT, 
		    		"done");
		done_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validated = true;
				String error_message = "ERROR on data: ";
				//validate data 
				
				
				
				//TODO if ( validated) : 
				if(validated) {
					try {
						Catalogue.getInstance().updateItem(item.getItemID(), item);
					} catch(Exception err) {
						System.err.println("ERROR updating item in GUIdo_EditItem ");
						err.printStackTrace();
						//TODO:: Ethan logger 
						// error updating the item being edited, for exception thrown by Catalogue updateItem; could
						//be a null error or something else. Doubtful that it's null though 
					}
					
					done.actionPerformed(new ActionEvent(item,ActionEvent.ACTION_PERFORMED,"item_updated"));
					//unreachable point
				}
				// (else):
				
				//TODO make prompt to user about what was wrong 
			}
		});
		this.add(done_button);
		y += GUIdo_EditItem.DONE_HEIGHT;
		
		
	}
	
}
