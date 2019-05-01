package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
	
	private static final int ITEM_X_GAP = 30;

	private static final int ITEM_Y_GAP = 75;
	
	private static final int LABEL_HEIGHT = 75;
	
	private static final int DONE_WIDTH = 150;
	
	private static final int DONE_HEIGHT = 100;
	
	
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
		JLabel promo_text_label = new JLabel("code:");
		promo_text_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(promo_text_label);
		
		JTextField promo_text = new JTextField("CODE");
		promo_text.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, PART_HEIGHT);
		this.add(promo_text);
		y+=PART_HEIGHT + PART_Y_GAP; 
		
		////////////////////////////////////////////////////////////////////
		//PROMO percent off:
		JLabel percent_off_label = new JLabel("Percent off");
		percent_off_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(promo_text_label);
		
		JTextField percent_off = new JTextField("% off");
		percent_off.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, PART_HEIGHT);
		this.add(percent_off);
		y+=PART_HEIGHT + PART_Y_GAP; 
		
		
		////////////////////////////////////////////////////////////////////
		//DONE::
		GUIdo_CButton done_button = new GUIdo_CButton(width/2-DONE_WIDTH/2,y,DONE_WIDTH,DONE_HEIGHT,"done");
		this.add(done_button);
		done_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean valid = true;
				
				String error_message = "ERROR: \n";
				
				Double percentoff = null;
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
					
					for(Integer id : selectedItems) {
						ItemInfo item = Catalogue.getInstance().getItem(id);
						item.addPromoDiscount(promo_text.getText(), percentoff);
					}
					done.actionPerformed(new ActionEvent(null,ActionEvent.ACTION_PERFORMED,"added_promos"));
					
				} else {
					JOptionPane.showMessageDialog(new JPanel(), error_message, 
						      "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		
	}
	
	public void display_item_toggle(int x, int y, int width, int height, ItemInfo item) {
		
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
}













