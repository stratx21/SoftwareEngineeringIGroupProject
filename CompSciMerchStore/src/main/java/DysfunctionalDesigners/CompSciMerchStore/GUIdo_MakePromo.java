package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIdo_MakePromo extends GUIdo_CPanel {
	
	/**
	 * The Font instance that is used for the title of the admin page. 
	 * 
	 */
	private static final Font TITLE_FONT = new Font("Cambria", Font.PLAIN,50);
	
	/**
	 * The height of the parts for the input for the promo. 
	 * 
	 */
	private static final int PART_HEIGHT = 100;
	
	private static final int PART_Y_GAP = 15;
	
	private static final int ITEMS_PER_LINE = 5;
	
	private static final int ITEM_X_GAP = 30;

	private static final int ITEM_Y_GAP = 75;
	
	private static final int LABEL_HEIGHT = 75;
	
	private static final int DONE_WIDTH = 150;
	
	private static final int DONE_HEIGHT = 100;
	
	/**
	 * The height of the item display for each display. 
	 */
//	private static final int ITEM_DISPLAY_HEIGHT = 600;
	
//	private int item_count = 0;
	
	
	private int item_listings_start = 0;
	
	
	/**
	 * the ids of the selected items. 
	 * 
	 */
	List<Integer> selectedItems = new ArrayList<Integer>();
	
	public GUIdo_MakePromo(ActionListener done, int width) {
		super(width,1200);
		
		final int PART_WIDTH = width*2/3;
		
		int y = 75;
		
		
		////////////////////////////////////////////////////////////////////
		//PROMO TEXT::::
		
		JLabel sale_info = new JLabel("Note: leaving the promo code text blank will make the "
				+ "discount a sale with no code required.");
		sale_info.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
		this.add(sale_info);
		
		JLabel promo_text_label = new JLabel("code:");
		promo_text_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
		this.add(promo_text_label);
		
		JTextField promo_text = new JTextField("CODE");
		promo_text.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.PART_HEIGHT);
		this.add(promo_text);
		y+=GUIdo_MakePromo.PART_HEIGHT + GUIdo_MakePromo.PART_Y_GAP; 
		
		////////////////////////////////////////////////////////////////////
		//PROMO percent off:
		JLabel percent_off_label = new JLabel("Percent off");
		percent_off_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
		this.add(percent_off_label);
		
		JTextField percent_off = new JTextField("% off");
		percent_off.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.PART_HEIGHT);
		this.add(percent_off);
		y+=GUIdo_MakePromo.PART_HEIGHT + GUIdo_MakePromo.PART_Y_GAP; 
		
		
		//////////////////////////////////////////////////////////////////////
		
		List<String> options = new ArrayList<String>();
		
		Arrays.asList(Professor.values()).forEach(p -> options.add(p.name()));
		options.add("all items");
		
		JComboBox section_box = new JComboBox((String[])options.toArray());
		section_box.setBounds(width/2-PART_WIDTH/2,y,PART_WIDTH,PART_HEIGHT);
		section_box.setBackground(new Color(255,228,225));
		section_box.setActionCommand("section");
		this.add(section_box);
		y+=PART_HEIGHT+PART_Y_GAP;
		
		////////////////////////////////////////////////////////////////////
		//DONE::
		GUIdo_CButton done_button = new GUIdo_CButton(width/2-DONE_WIDTH/2,y,DONE_WIDTH,DONE_HEIGHT,"done");
		this.add(done_button);
		done_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean valid = true;
				
				String error_message = "ERROR: \n";
				
				Double percentoff = null;
				Professor section = null;
				
				try {
					int index = section_box.getSelectedIndex();
					if(index < Professor.values().length)
						section = Professor.values()[index];
				} catch(Exception err) {
					//TODO logger 
					error_message += "converting the section error; choose a professor or all";
				}
				
				try {
					percentoff = Double.parseDouble(percent_off.getText());
					if(percentoff < 0 || percentoff > 100) {
						throw new Exception();
					}
				} catch(Exception err) {
					//TODO logger 
					valid = false;
					error_message += " percent off invalid. Must be between 0 and 100 % and be in number format only.\n";
					System.out.println("ERROR: percent off invalid. Must be between 0 and 100 % and be in number format only.");
				}
				
				if(valid) {
					
//					for(Integer id : selectedItems) {
//						ItemInfo item = Catalogue.getInstance().getItem(id);
//						item.addPromoDiscount(promo_text.getText(), percentoff);
//					}
					
					String code = promo_text.getText();
					
					if(section == null) {
						//then apply to all 
						if(code.length() > 0 )
							Catalogue.getInstance().addDiscountToAll(percentoff, code);
						else
							Catalogue.getInstance().addDiscountToAll(percentoff);
					} else {
						if(code.length() > 0 )
							Catalogue.getInstance().addDiscountToAll(percentoff, code, section);
						else
							Catalogue.getInstance().addDiscountToAll(percentoff, section);
					}
					
					//exit this page: 
					done.actionPerformed(new ActionEvent(null,ActionEvent.ACTION_PERFORMED,"added_promos"));
					
				} else {
					JOptionPane.showMessageDialog(new JPanel(), error_message, 
						      "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		
		/////////////////////////////////////////////////
//		JLabel apply_to = new JLabel("Apply to: ");
//		apply_to.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
//		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
//		this.add(apply_to);
//		
		item_listings_start = y + GUIdo_MakePromo.ITEM_Y_GAP;
		
//		item_count = Catalogue.getInstance().getNumItems();
		
	}
	
	public void display_item_toggle(int x, int y, int width, int height, ItemInfo item) {
		
	}
	
//	public void paintComponent(Graphics g) {
//		this.setBackground(Color.WHITE);
//		
//		int x=ITEM_X_GAP,
//				y = this.item_listings_start;
//		int width = this.getWidth()/ITEMS_PER_LINE-2*ITEM_X_GAP;
//		
//		List<ItemInfo> items = Catalogue.getInstance().new;
//		
//		// for each item to be displayed 
//		for(int itemIndex = 0; itemIndex < this.item_count; itemIndex++) {
//			//draw the display in the item function for the display of the image and other data 
//			items.get(itemIndex).drawDisplay(g, x, y, width, ITEM_DISPLAY_HEIGHT);
//			//go to the next row if the row is finished, otherwise add to the x to continue
//			//in the same row.
//			if(itemIndex%ITEMS_PER_LINE==ITEMS_PER_LINE-1) {
//				x=ITEM_X_GAP;
//				y+=ITEM_DISPLAY_HEIGHT+GUIdo_MakePromo.ITEM_Y_GAP;
//			} else {
//				x+=width+GUIdo_MakePromo.ITEM_X_GAP;
//			}
//		}
//		
//	}
	
}













