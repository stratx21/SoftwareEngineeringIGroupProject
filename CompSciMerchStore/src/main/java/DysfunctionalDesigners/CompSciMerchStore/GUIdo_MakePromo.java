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
	private static final Font TITLE_FONT = new Font("Cambria", Font.BOLD,50);
	 
	/** 
	 * The height of the parts for the input for the promo.  
	 *  
	 */ 
	private static final int PART_HEIGHT = 100; 
	
	/**
	 * The gap between each part in pixels in the y axis. 
	 * 
	 */
	private static final int PART_Y_GAP = 15; 
	
	/**
	 * The height of the labels. 
	 * 
	 */
	private static final int LABEL_HEIGHT = 75; 
	
	/**
	 * The width of the done button. 
	 * 
	 */
	private static final int DONE_WIDTH = 150; 
	
	/**
	 * The height of the done button. 
	 * 
	 */
	private static final int DONE_HEIGHT = 100; 
	
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
		
		//telling what the box is: 
		JLabel promo_text_label = new JLabel("Enter New Promo Code:"); 
		promo_text_label.setFont(new Font("Cambria", Font.BOLD, 24));
		promo_text_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
		this.add(promo_text_label);
		
		//the box used for the promo code text itself: 
		JTextField promo_text = new JTextField("CODE"); 
		promo_text.setFont(new Font("Cambria", Font.PLAIN, 14));
		promo_text.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.PART_HEIGHT);
		this.add(promo_text); 
		y+=GUIdo_MakePromo.PART_HEIGHT + GUIdo_MakePromo.PART_Y_GAP;
		
		////////////////////////////////////////////////////////////////////
		//PROMO percent off:
		JLabel percent_off_label = new JLabel("Discount Amount:");
		percent_off_label.setFont(new Font("Cambria", Font.BOLD, 24));
		percent_off_label.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.LABEL_HEIGHT);
		y+=GUIdo_MakePromo.LABEL_HEIGHT+7;
		this.add(percent_off_label);
		
		//the percent off that should be changed to numbers: 
		JTextField percent_off = new JTextField("% off");
		percent_off.setFont(new Font("Cambria", Font.PLAIN, 14));
		percent_off.setBounds(width/2-PART_WIDTH/2, y, PART_WIDTH, GUIdo_MakePromo.PART_HEIGHT);
		this.add(percent_off);
		y+=GUIdo_MakePromo.PART_HEIGHT + GUIdo_MakePromo.PART_Y_GAP; 
		
		
		//////////////////////////////////////////////////////////////////////
		
		//the options for the combo box: 
		List<String> options = new ArrayList<String>();
		
		Arrays.asList(Professor.values()).forEach(p -> options.add(p.name()));
		options.add("all items");
		
		JComboBox section_box = new JComboBox(options.toArray());
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
					percentoff/=100.0; 
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
					done.actionPerformed(new ActionEvent(percentoff,ActionEvent.ACTION_PERFORMED,"added_promos"));
					
				} else {
					JOptionPane.showMessageDialog(new JPanel(), error_message, 
						      "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		
		/////////////////////////////////////////////////
//		
		
	}
	
	
}













