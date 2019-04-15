package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class GUIdo_ItemDisplay extends GUIdo_CPanel{
	
	/**
	 * The BufferedImage instance that is used as the image to represent the item
	 * 	graphically. 
	 */
	private BufferedImage item_image = null;
	
	/**
	 * The item instance that is being displayed. 
	 */
	private ItemInfo item = null;
	
	/**
	 * The Font instance that is used for the description for the item. 
	 */
	private static final Font DESC_FONT = new Font("Calibri",Font.PLAIN,35);
	
	/**
	 * The quantity chosen of the item that is being displayed. 
	 */
	private int quantity_chosen = 1;
	
	/**
	 * The image ratio to use to scale the image; it is the width of the image divided by
	 * 	the height of the image. 
	 */
	private double image_ratio = 0;
	
	/**
	 * the current user instance that is logged in. 
	 */
	private User current_user = null;
	
	/**
	 * This sets up the item display to display the information for the ItemInfo itemToDisplay given by
	 * 	parameter. 
	 * 
	 * @param itemToDisplay the item to display information for.  
	 * @param width the width in pixels of the page. 
	 * @param cart the Sale Object to add items to to put in the cart. 
	 * @param done the ActionListener instance that is used to return based on whatever action was performed
	 * 	that merits leaving the item page. 
	 * @param user the current logged in user.
	 */
	public GUIdo_ItemDisplay(ItemInfo itemToDisplay,int width, Sale cart,ActionListener done, User user) {
		super(1500);
		this.current_user=user;
		this.setSize(new Dimension(width,1500));
		
		this.item = itemToDisplay;
		try {
			this.item_image = ImageIO.read(new File("src/main/resources/itemimages/"+ this.item.getExtendedItemID() + ".jpg"));
		} catch(Exception e) {
			System.out.println("Error getting image for item in GUIdo_ItemDisplay");
			e.printStackTrace();
		}
//		System.out.println("width = " + this.getWidth());
		this.image_ratio = (this.item_image.getWidth()*1.0/this.item_image.getHeight());
		
		JLabel name =  new JLabel(this.item.getDisplayName());
		name.setFont(new Font("Calibri",Font.PLAIN,55));
		name.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*1/10, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(name);
		
		JLabel price =  new JLabel("$"+new DecimalFormat("0.00").format(this.item.getPrice()));
		price.setFont(new Font("Calibri",Font.PLAIN,35));
		price.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*5/35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(price);
		
		int amount = 0;
		if(cart.getItemList().containsKey(item.getItemID())) {
			amount = cart.getItemList().get(item.getItemID()).getQuantity();
		}
		
		String quantity_ad;
		int quantitycount = this.item.getStock()-amount;
		if(quantitycount == 0) {
			quantity_ad = "This item is out of stock!";
		} else if(quantitycount <= 8) {
			quantity_ad = "Only " + quantitycount + " left! Get it before it runs out!";
		} else {
			quantity_ad = quantitycount + " in stock!";
		}
		
		JLabel stock = new JLabel(quantity_ad);
		stock.setBounds(price.getX(), price.getY()+35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(stock);
		
		
		int currenty = stock.getY()+stock.getHeight()+5;
		final int DESC_LINE_HEIGHT = 40;//g.getFontMetrics(DESC_FONT).getHeight();
		
		ArrayList<String> desc_lines = GUIdo_OutputTools.formatStringForPrompt(this.item.getDescription(), DESC_FONT, this.getWidth()/2-this.getWidth()/10-50);
		
		JLabel desc = null;
		
		for(String line : desc_lines) {
			desc =  new JLabel(line);
			
			desc.setFont(DESC_FONT);
			desc.setBounds(this.getWidth()/2+this.getWidth()/10, currenty, this.getWidth()/2-this.getWidth()/10, DESC_LINE_HEIGHT);
			currenty += DESC_LINE_HEIGHT;
			this.add(desc);
		}
		
		JLabel quantityis = new JLabel("Quantity: ");
		quantityis.setFont(new Font("Calibri",Font.PLAIN,20));
		quantityis.setBounds(desc.getX(), currenty+DESC_LINE_HEIGHT+5, 88, 25);
		this.add(quantityis);
		
		ArrayList<String> options = new ArrayList<>();
		
		
		for(int i = 1; i <= item.getStock() - amount && i < 10; i++) {
			options.add(i+"");
		}
		
		
		JComboBox quantity = new JComboBox(options.toArray());
		if(!options.isEmpty()) {
			quantity.setSelectedIndex(0);
		}
	    quantity.setBounds(quantityis.getX()+quantityis.getWidth()+5,quantityis.getY(), 50, 25);
	    
	    
	    quantity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quantity_chosen = Integer.parseInt((String)(((JComboBox)e.getSource()).getSelectedItem()));
				
			}
	    	
	    });
	    
	    this.add(quantity);
		
		GUIdo_CButton addtocart = new GUIdo_CButton(quantity.getX()+quantity.getWidth()+5 ,quantity.getY(), 250, 50, "add to cart");
		addtocart.setActionCommand("add_to_cart");
		addtocart.setActionListener_clicked(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cart.addItem(quantity_chosen, item.getItemID());
				System.out.println("CART SADFGDNF quant : " + cart.getNumItems());
				done.actionPerformed(new ActionEvent(cart,ActionEvent.ACTION_PERFORMED,"item_added"));
			}
		});
		addtocart.setBackground(new Color(255,181,9));
		addtocart.setHoverColor(new Color(242,170,0));
		this.add(addtocart);
		
		GUIdo_CButton wishlist_button = new GUIdo_CButton(quantityis.getX(),quantityis.getY()+quantityis.getHeight()+5,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
		
		Customer customer = (Customer)current_user;
		
		if(customer.getWishList().contains(itemToDisplay.getItemID())) {
			wishlist_button.enableIcons(GUIdo_ItemCollection.onlist1,GUIdo_ItemCollection.onlist2,GUIdo_ItemCollection.onlist3);
		} else {
			wishlist_button.enableIcons(GUIdo_ItemCollection.offlist1,GUIdo_ItemCollection.offlist2,GUIdo_ItemCollection.offlist3);
		}
		
		wishlist_button.setData_from_holding(itemToDisplay);
		
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
					System.out.println("Exists, removing...");
					//has item, so remove it 
					customer.removeItemFromWishlist(item.getItemID());
					thisbutton.enableIcons(GUIdo_ItemCollection.offlist1,GUIdo_ItemCollection.offlist2,GUIdo_ItemCollection.offlist3);
				} else {
					System.out.println("Doesn't exist, adding...");
					customer.addItemToWishlist(item.getItemID());
					thisbutton.enableIcons(GUIdo_ItemCollection.onlist1,GUIdo_ItemCollection.onlist2,GUIdo_ItemCollection.onlist3);
					System.out.println("wishlist items: ");
					for(Integer item_id : customer.getWishList()) {
						System.out.println(Catalogue.getInstance().getItem(item_id).getDisplayName());
					}
				}
				thisbutton.repaint();
			}
		});
		wishlist_button.repaint();
		this.add(wishlist_button);
	}
	
	/**
	 * This overrides the function paintComponent for the panel that draws on
	 * 	the panel using the Java.awt.Graphics instance to draw the information
	 * 	such as the image for the item to be displayed. 
	 * 
	 * @param g the Java.awt.Graphics instance that is used for drawing on the panel. 
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		int width = (int)(this.getWidth()/2*this.image_ratio);
		int height = (int)(this.getWidth()/2/this.image_ratio);
		
		if(width > this.getWidth()/2) {
			height *= (width*1.0/(this.getWidth()/2));
			width=this.getWidth()/2;
		}
		
		g.drawImage(this.item_image, 
				this.getWidth()*1/10, this.getHeight()*1/10, width, height, null);
	}
	
}
