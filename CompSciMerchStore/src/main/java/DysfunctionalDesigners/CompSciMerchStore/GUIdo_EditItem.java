package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;
import java.util.Arrays;

public class GUIdo_EditItem extends GUIdo_CPanel{
	
	/**
	 * The Logger instance that is used to log for the program.
	 * 
	 */
	private static Logger logger = Logger.getLogger(GUIdo_EditItem.class.getName());
	
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
	
	/**
	 * The height of the box for the smaller text sections. 
	 */
	private static final int SMALLER_TEXT_HEIGHT = 75;
	
	/**
	 * The width of the done button. 
	 */
	private static final int DONE_WIDTH = 170;
	
	/**
	 * The height of the done button. 
	 */
	private static final int DONE_HEIGHT = 80;
	
	/**
	 * The number of pixels in the gap in the height between each area to edit. 
	 */
	private static final int Y_GAP = 10;
	
	/**
	 * the font used for the labels. 
	 * 
	 */
	private static final Font LABEL_FONT = new Font("Cambria", Font.BOLD, 24);
	
	/**
	 * The String that is used to display the discounts information for
	 * the PROMO codes. 
	 * 
	 */
	private String discounts_display_string = "";
	
	/**
	 * The new image instance uploaded for the item.
	 * 
	 */
	private BufferedImage newimage = null;
	
	/**
	 * The height of the labels. 
	 * 
	 */
	private static final int LABEL_HEIGHT = 125;
	
	/**
	 * The JLabel used for the file name that was used to show
	 *  the name of the file that was uploaded for the edit. 
	 * 
	 */
	private JLabel current_filename = null;
	
	/**
	 * The visible width used for the button to show
	 *  
	 * 
	 */
	private static final int IS_VISIBLE_WIDTH = 325;
	
	/**
	 * 
	 * This sets up the edit item page so that the item information can be edited. 
	 * 
	 * @param item_to_edit the item to edit in this page. 
	 * @param done the ActionListener instance that is used to return from this page. 
	 */
	public GUIdo_EditItem(int width,ItemInfo item_to_edit, ActionListener done, User user) {
		//page length and width 
		super(width,2500);
		this.setBackground(Color.WHITE);
		logger.info("Switched to EditItem frame");
		//get the width to use for the boxes 
		GUIdo_EditItem.TEXTBOX_WIDTH = width*2/3;
		this.item=item_to_edit;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//the y to use to increase to move down the page in Components to add
		int y = 200;
		
		//
		GUIdo_CPanel panel = this;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//the name to edit 
		JLabel title = new JLabel("Upload An Item");
		title.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT - 70);
		title.setFont(new Font("Cambria", Font.BOLD, 34));
		this.add(title);
		
		
		JLabel name_label = new JLabel("Item Display Name:");
		name_label.setFont(LABEL_FONT);
		name_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(name_label);
		JTextField name = new JTextField(item.getDisplayName());
		name.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(name);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//description text field  
		JLabel desc_label = new JLabel("Description Of Item:"); 
		desc_label.setFont(LABEL_FONT); 
		desc_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT); 
		y+=LABEL_HEIGHT+7; 
		this.add(desc_label); 
		JTextField desc = new JTextField(item.getDescription()); 
		desc.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, GUIdo_EditItem.TEXTBOX_WIDTH, GUIdo_EditItem.TEXTBOX_HEIGHT); 
		this.add(desc);  
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.TEXTBOX_HEIGHT; 
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//the price editing 
		JLabel price_label = new JLabel("Item Price:");
		price_label.setFont(LABEL_FONT);
		price_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(price_label);
		JTextField price = new JTextField(""+item.getPrice());
		price.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(price);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//the stock editing to change the stock 
		JLabel stock_label = new JLabel("Current Stock:");
		stock_label.setFont(LABEL_FONT);
		stock_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(stock_label);
		JTextField stock = new JTextField(""+item.getStock());
		stock.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(stock);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
//		JTextField prof = new JTextField(""+item.getProf().name());
//		prof.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
//		this.add(prof);
//		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		//the professor option to change the professor 
		JLabel professor_label = new JLabel("Professor:");
		professor_label.setFont(LABEL_FONT);
		professor_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(professor_label);
		ArrayList<String> profnames = new ArrayList<>();
		Arrays.asList(Professor.values()).forEach(p -> profnames.add(p.name()));
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		//remove the dysfunctional designers option to not allow any more items
		//to be added to that seciton: 
		if(!user.isAdmin())
			profnames.remove(profnames.size()-1);
		JComboBox professors = new JComboBox(profnames.toArray());
		professors.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		professors.setBackground(new Color(255,228,225));
		professors.setActionCommand("professor");
		professors.setSelectedItem(item.getProf().name());
		this.add(professors);
		y+=professors.getHeight()+GUIdo_EditItem.Y_GAP;
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//the label for seeing the discount: 
		JLabel discount_label = new JLabel("Discount:");
		discount_label.setFont(LABEL_FONT);
		discount_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(discount_label);
		JTextField discount = new JTextField(""+item.getSaleDiscount());
		discount.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(discount);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//changing the display image:: 
		JLabel image_label = new JLabel("Display Image:");
		image_label.setFont(LABEL_FONT);
		image_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(image_label);
		
		GUIdo_CButton image_upload_button = new GUIdo_CButton(width/2-DONE_WIDTH/2,y,DONE_WIDTH,DONE_HEIGHT,
				"upload image");
		image_upload_button.addActionListener(new ActionListener() {//the process to upload an image: 
			public void actionPerformed(ActionEvent e) {
				JFileChooser prompt = new JFileChooser();//let the user choose the file 
		        prompt.setFileFilter(new FileNameExtensionFilter(//should only be jpg,jpeg, or png. 
		                "JPG, JPEG, and PNG", "jpg", "jpeg", "png"));
		        //^ must be translated to a bufferedimage 
		        
		        if(prompt.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		            try {
		            	//attempt to get an image from the selected file: 
		            	//does this to ensure it can be read and used as an
		            	//image as it would be for displaying the item 
		            	newimage = ImageIO.read(prompt.getSelectedFile());
		            	//update the label for the current file chosen: 
		            	current_filename.setText(prompt.getSelectedFile().getName());
		            	current_filename.repaint();
		            } catch(Exception err) {
		            	logger.severe("ERROR getting image from the the selected image, \"" + prompt.getSelectedFile().getName() + "\"");
		            	err.printStackTrace();
		            }
		        }
			}
		});
		//set the original default to none for the file chosen: (updates only)
		this.current_filename = new JLabel("no new image chosen");
		//put to the right of the image upload button: 
		this.current_filename.setBounds(image_upload_button.getX()+image_upload_button.getWidth()+7,y,this.getHeight()/3,70);
		//add the label to this page 
		this.add(this.current_filename);
		y += GUIdo_EditItem.DONE_HEIGHT;
		this.add(image_upload_button);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//get the promo set and put it in a string to show to the user:
		item.getPromoDiscounts().entrySet().forEach(e -> discounts_display_string += e.getKey() + "," + e.getValue() + "\n");
		JLabel promo_label = new JLabel("Promo Codes:");
		promo_label.setFont(LABEL_FONT);
		promo_label.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2, y, this.getWidth()/2, LABEL_HEIGHT);
		y+=LABEL_HEIGHT+7;
		this.add(promo_label);
		JTextArea promocodes = new JTextArea(""+discounts_display_string);
		promocodes.setBackground(Color.WHITE);
		promocodes.setBounds(width/2-GUIdo_EditItem.TEXTBOX_WIDTH/2,y,GUIdo_EditItem.TEXTBOX_WIDTH,GUIdo_EditItem.SMALLER_TEXT_HEIGHT);
		this.add(promocodes);
		y += GUIdo_EditItem.Y_GAP+GUIdo_EditItem.SMALLER_TEXT_HEIGHT;
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//the viewable stuff for enabling and disabling the item on the store: 
		
		//put label to the left of the yes/no button 
		JLabel isViewable = new JLabel("Item is visible on the store? "); 
		isViewable.setFont(LABEL_FONT); 
		isViewable.setBounds(width/2-GUIdo_EditItem.IS_VISIBLE_WIDTH-Y_GAP,y, 
				GUIdo_EditItem.IS_VISIBLE_WIDTH,LABEL_HEIGHT); 
		
		this.add(isViewable); 
		
		GUIdo_CButton viewable 
			= new GUIdo_CButton(width/2 + Y_GAP, 
					y,GUIdo_EditItem.DONE_WIDTH,GUIdo_EditItem.DONE_HEIGHT,"YES"); 
		viewable.setBackground(Color.GREEN); 
		if(!item.isEnabled()) { 
			viewable.setText("NO"); 
			viewable.setBackground(Color.RED); 
//			viewable.repaint();
		}
		
		//the toggle for on the store or not: 
		viewable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item.isEnabled()) {
					item.disable();
					viewable.setText("NO");
					viewable.setBackground(Color.RED);
					viewable.repaint();
				} else {
					item.enable();
					viewable.setText("YES");
					viewable.setBackground(Color.GREEN);
					viewable.repaint();
				}
			}
		});
		
		y+=GUIdo_EditItem.DONE_HEIGHT+GUIdo_EditItem.Y_GAP*3;
		this.add(viewable);
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		GUIdo_CButton done_button 
		    = new GUIdo_CButton(
		    		width/2-GUIdo_EditItem.DONE_WIDTH/2,y,GUIdo_EditItem.DONE_WIDTH,GUIdo_EditItem.DONE_HEIGHT, 
		    		"done");
		done_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validated = true;
				String error_message = "ERROR on data: \n";
				//validate data 
				String[] promolines = promocodes.getText().split("\n");
				
				//data to get: 
				String new_name = null;
				String new_desc = null;
				Double new_price = null;
				Integer new_stock = null;
				Professor new_professor = null;
				Double new_discount = null;
				Map<String,Double> new_promos = new HashMap<String,Double>(); // promo codes
				
				new_name = name.getText();
				
				if(new_name == null) {//ensure that the new name is valid: 
					error_message += " error: new name is null \n";
				}
				
				new_desc = desc.getText();
				
				if(new_desc == null) {//ensure that the new description is valid 
					error_message += " error: new description is null \n";
				}
				
				//get the price data: 
				try {
					new_price = Double.parseDouble(price.getText());
					if(new_price < 0.00)
						throw new Exception();
				} catch(Exception err) {
					error_message += " converting the price; use format of 0.00. Price must be "
							+ "greater than or equal to zero. \n";
					logger.severe("ERROR casting to double : GUIdo_EditItem constructor, "
							+ "done_button listener, parsing price");
					validated = false;
				}
				
				//get the stock - not less than zero 
				if(validated) {
					try {
						new_stock = Integer.parseInt(stock.getText());
						if(new_stock < 0)
							throw new Exception();
					} catch(Exception err) {
						error_message += " converting the stock; use format of integer values only \n";
						logger.severe("ERROR casting to int : GUIdo_EditItem constructor, "
								+ "done_button listener, parsing stock");
						validated = false;
					}
				}
				
				//professor data: 
				if(validated) {
					try {
						new_professor = Professor.values()[professors.getSelectedIndex()];
					} catch(Exception err) {
						//low chance of exception - most likely a programmer error. 
						error_message += " converting the professor; choose one option. The index chosen is: "
								+professors.getSelectedIndex() +" \n";
						logger.severe("ERROR getting professor from data, "
								+ "done_button listener, parsing stock");
						validated = false;
					}
				}
				
				
				//get discount data: 
				if(validated) {
					try {
						new_discount = Double.parseDouble(discount.getText());
					} catch(NumberFormatException err) {
						error_message += " converting the discount; use format of 0.00 \n";
						logger.severe("ERROR casting to double : GUIdo_EditItem constructor, "
								+ "done_button listener, parsing discount " + "\""+ discount.getText() +"\"");
						validated = false;
					}
				}
				
				
				if(validated) {
					for(String line : promolines) {
						String [] parts = line.split(",");
						Double amount = null;
						if(parts.length > 0 && parts [0].length()>0) {
							if(parts.length<2) {
								error_message += "not enough arguments on line for promo code. line: \"" + line + "\". \n";
								validated = false;
							}
							if(validated) {
								try{
								  amount = Double.parseDouble(parts[1]);
								  new_promos.put(parts[0], amount);
								}catch(NumberFormatException err) {
								  //not a double
									error_message += " converting the amount for the discount; use format of 0.00 \n";
									logger.severe("ERROR casting to double : GUIdo_EditItem constructor, "
											+ "done_button listener, parsing promo code");
									validated = false;
								}
							}
						}
					}
				}
				
				if(validated) {
					try {
						//update the item information:
						item.setDisplayName(new_name);
						item.setDescription(new_desc);
						item.setPrice(new_price);
						item.setStock(new_stock);
						item.setProf(new_professor);
						item.setSaleDiscount(new_discount);
						item.setPromoDiscounts(new_promos);
					} catch(Exception err) {
						logger.severe("ERROR putting information into the item instance : "
								+ "GUIdo_EditItem constructor, done_button listenner, parsing promo code");
					}
				}
				
				
				//if validated, then update the item's info. 
				if(validated) {
					
					try {
						Catalogue.getInstance().updateItem(item.getItemID(), item);
					} catch(Exception err) {
						err.printStackTrace();
						//something related to what is going on in the update item function in the Catalogue
						logger.severe("ERROR: updating the item being edited, for exception thrown by Catalogue updateItem; could "
								+ "be a null error or something else. Doubtful that it's null though.");
						// error updating the item being edited, for exception thrown by Catalogue updateItem; could
						//be a null error or something else. Doubtful that it's null though 
					}
					
					
					if(newimage != null) {
						try {
							//write the image to the proper spot with
							//the proper id: 
							ImageIO.write(newimage, "jpg", new File(App.resourceTarget + "itemimages/"
									+ item.getExtendedItemID() + ".jpg"));
						} catch(IOException ioex) {
							logger.severe("ERROR saving the image in edit item");
							ioex.printStackTrace();
						}
					}
					
					done.actionPerformed(new ActionEvent(item,ActionEvent.ACTION_PERFORMED,
								"item_updated"));
					//unreachable point
				}
				// (else): not validated confirmed in theory - action listener 
				//done call should make this not validated only from the
				//unreachable point
				if(!validated) {
					JOptionPane.showMessageDialog(panel, error_message, 
						      "Error", JOptionPane.ERROR_MESSAGE); 
					//TODO make prompt to user about what was wrong 
					System.err.println("ERROR inputting data to edit item ");
				}
			}
		});
		this.add(done_button); //add done button from above 
		//add the height
		y += GUIdo_EditItem.DONE_HEIGHT;
		
		
	}
	
}
