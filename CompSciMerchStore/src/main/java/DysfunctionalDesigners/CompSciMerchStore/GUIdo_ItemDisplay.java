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
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class GUIdo_ItemDisplay extends GUIdo_CPanel{
	private static Logger logger = Logger.getLogger(GUIdo_ItemDisplay.class.getName());
	
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
	
	private int lowestPointY = 50;
	
	private final int GAP_BETWEEN_REVIEWS = 75;
	
	private final int REVIEW_DESC_BUFFER = 45;
	
	private final int[] STAR_X = {42,52,72,52,60,40,15,28,9,32,42};
	private final int[] STAR_Y = {38,62,68,80,105,85,102,75,58,60,38};
	
	private Customer customer = null;
	
	private final int STAR_WIDTH = 70;
	
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
		//set up the page with a page length/height of 1500 pixels 
		super(1500);
		//set this instance's instance of user to the User given 
		this.current_user=user;
		this.setSize(new Dimension(width,1500));
		
		GUIdo_CButton add_review_button = null;
		
		this.item = itemToDisplay;
		try {
			this.item_image = ImageIO.read(new File("src/main/resources/itemimages/"+ this.item.getExtendedItemID() + ".jpg"));
		} catch(Exception e) {
			logger.severe("ERROR getting image from file");
			e.printStackTrace();
		}
		//set the image ratio to the image's width divided by the images height 
		try {
			this.image_ratio = (this.item_image.getWidth()*1.0/this.item_image.getHeight());	
		}
		catch(NullPointerException e) {
			logger.severe("ERROR: NullPointException Caught for Images in ItemDisplay");
			e.printStackTrace();
		}
		
		
		//add the name of the item 
		JLabel name =  new JLabel(this.item.getDisplayName());
		//make the Font for the name decently big: 
		name.setFont(new Font("Calibri",Font.PLAIN,55));
		name.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*1/10, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(name);
		
		//show the price formatting the item, always showing at least one dollar place and always showing two decimal places: 
		JLabel price =  new JLabel("$"+new DecimalFormat("0.00").format(this.item.getPrice()));
		price.setFont(new Font("Calibri",Font.PLAIN,35));
		price.setBounds(this.getWidth()/2+this.getWidth()/10, this.getHeight()*5/35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(price);
		
		//set the amount to subtract to an initial zero 
		int amount = 0;
		//if the cart has the item that this instance is displaying: 
		if(cart.getItemList().containsKey(item.getItemID())) {
			//then set the amount to subtract to the number of this instance's display
			//item's quantity 
			amount = cart.getItemList().get(item.getItemID()).getQuantity();
		}
		
		//the String used for telling the user how many of the item remain 
		String quantity_ad;
		int quantitycount = this.item.getStock()-amount;
		if(quantitycount == 0) {
			//there remain no more items of this type 
			quantity_ad = "This item is out of stock!";
		} else if(quantitycount <= 8) {
			//tell the user to get it fast 
			quantity_ad = "Only " + quantitycount + " left! Get it before it runs out!";
		} else {
			//tell the user without extra words how many are in stock 
			quantity_ad = quantitycount + " in stock!";
		}
		
		//add the String for the quantity to the display 
		JLabel stock = new JLabel(quantity_ad);
		stock.setBounds(price.getX(), price.getY()+35, this.getWidth()/2-this.getWidth()/10, this.getHeight()/10);
		this.add(stock);
		
		//set the current y value to use to put items at 
		int currenty = stock.getY()+stock.getHeight()+5;
		final int DESC_LINE_HEIGHT = 40;//g.getFontMetrics(DESC_FONT).getHeight();
		
		//get the description in the form of multiple lines so that it can wrap 
		ArrayList<String> desc_lines = GUIdo_OutputTools.formatStringForPrompt(this.item.getDescription(), DESC_FONT, this.getWidth()/2-this.getWidth()/10-50);
		
		JLabel desc = null;
		
		//show each line of the description
		for(String line : desc_lines) {
			desc =  new JLabel(line);
			
			desc.setFont(DESC_FONT);
			desc.setBounds(this.getWidth()/2+this.getWidth()/10, currenty, this.getWidth()/2-this.getWidth()/10, DESC_LINE_HEIGHT);
			//increase the current y value so that the next added item will be put in the right place below 
			currenty += DESC_LINE_HEIGHT;
			this.add(desc);
		}
		
		//The label for the quantity to indicate what the dropdown menu for the quantity is 
		JLabel quantityis = new JLabel("Quantity: ");
		quantityis.setFont(new Font("Calibri",Font.PLAIN,20));
		quantityis.setBounds(desc.getX(), currenty+DESC_LINE_HEIGHT+5, 88, 25);
		this.add(quantityis);
		
		//the options that are given in the quantity 
		ArrayList<String> options = new ArrayList<>();
		
		//add the possible options, with a max of 10 or the stock remaining,
		//whichever is smaller. 
		for(int i = 1; i <= item.getStock() - amount && i < 10; i++) {
			options.add(i+"");
		}
		
		//the dropdown menu for the quantity 
		JComboBox quantity = new JComboBox(options.toArray());
		if(!options.isEmpty()) {
			quantity.setSelectedIndex(0);
		}
	    quantity.setBounds(quantityis.getX()+quantityis.getWidth()+5,quantityis.getY(), 50, 25);
	    
	    //set the quantity chosen if another option is clicked 
	    quantity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quantity_chosen = Integer.parseInt((String)(((JComboBox)e.getSource()).getSelectedItem()));
				
			}
	    	
	    });
	    
	    this.add(quantity);
	    
	  //get the Customer user to get the wishlist 
	    lowestPointY = quantity.getY()+quantity.getHeight();
  		
  		if(!current_user.isAdmin()) {
		    //the button for the option to add the quantity chosen to the cart 
			GUIdo_CButton addtocart = new GUIdo_CButton(quantity.getX()+quantity.getWidth()+5 ,quantity.getY(), 250, 50, "add to cart");
			addtocart.setActionCommand("add_to_cart");
			addtocart.setActionListener_clicked(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cart.addItem(quantity_chosen, item.getItemID());
					logger.info("Item Added to cart; quantity: " + quantity_chosen + " of " + item.getItemID());
					done.actionPerformed(new ActionEvent(cart,ActionEvent.ACTION_PERFORMED,"item_added"));
				}
			});
			addtocart.setBackground(new Color(255,181,9));
			addtocart.setHoverColor(new Color(242,170,0));//the color for when the mouse hovers over the button 
			this.add(addtocart);
			
			//add the wishlist button for the user to have the option to add or remove 
			//this item from their wishlist: 
			GUIdo_CButton wishlist_button = new GUIdo_CButton(quantityis.getX(),quantityis.getY()+quantityis.getHeight()+5,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT,GUIdo_ItemCollection.WISHLIST_BUTTON_HEIGHT);
		
		
			customer = (Customer)current_user;
			
			//if it exists in the wishlist 
			if(customer.getWishList().contains(itemToDisplay.getItemID())) {
				//filled heart 
				wishlist_button.enableIcons(GUIdo_ItemCollection.onlist1,GUIdo_ItemCollection.onlist2,GUIdo_ItemCollection.onlist3);
			} else {
				//is not in the wishlist,
				//then unfilled heart 
				wishlist_button.enableIcons(GUIdo_ItemCollection.offlist1,GUIdo_ItemCollection.offlist2,GUIdo_ItemCollection.offlist3);
			}
			
			//set the item that the wishlist button affects 
			wishlist_button.setData_from_holding(itemToDisplay);
			
			//toggle the wishlist option:
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
					} catch(Exception ex) {
						logger.severe("ERROR casting to ItemInfo : GUIdo_ItemCollection");
						ex.printStackTrace();
					}
					
					if(customer.getWishList() != null) {
						if(customer.getWishList().contains(item.getItemID())) {
	//						System.out.println("Exists, removing...");
							//has item, so remove it 
							logger.info("Removing item from wishlist for user " + customer.getUserID());
							customer.removeItemFromWishlist(item.getItemID());
							thisbutton.enableIcons(GUIdo_ItemCollection.offlist1,GUIdo_ItemCollection.offlist2,GUIdo_ItemCollection.offlist3);
						} else {
							//does not have item, so add it 
	//						System.out.println("Doesn't exist, adding...");
							customer.addItemToWishlist(item.getItemID());
							logger.info("Adding item to wishlist for user " + customer.getUserID());
							thisbutton.enableIcons(GUIdo_ItemCollection.onlist1,GUIdo_ItemCollection.onlist2,GUIdo_ItemCollection.onlist3);
	//						System.out.println("wishlist items: ");
	
						}
						thisbutton.repaint();	
					}
					
				}
			});
			wishlist_button.repaint();
			this.add(wishlist_button);
			
			add_review_button = new GUIdo_CButton(wishlist_button.getX(),wishlist_button.getY()+wishlist_button.getHeight() + 7, 200,50, "Add Reviews");
			add_review_button.setBackground(new Color(255,181,9));
			add_review_button.setHoverColor(new Color(242,170,0));//the color for when the mouse hovers over the button 
			add_review_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					done.actionPerformed(new ActionEvent(item,ActionEvent.ACTION_PERFORMED,"add_review"));
				}
			});
			this.add(add_review_button);
			
			lowestPointY = add_review_button.getY()+add_review_button.getHeight();
		}
		
		if(item.getVendorID() == user.getUserID() || user.isAdmin()) {
			//then the user is the vendor of this item
			//add the button to edit the item's information 
			GUIdo_CButton edit_item_button = new GUIdo_CButton(quantityis.getX(),lowestPointY+10,200,75,"Edit item");
			edit_item_button.setBackground(new Color(255,181,9));
			edit_item_button.setHoverColor(new Color(242,170,0));//the color for when the mouse hovers over the button 
			edit_item_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					done.actionPerformed(new ActionEvent(item,ActionEvent.ACTION_PERFORMED,"edit_item"));
				}
			});
			this.add(edit_item_button);
		}
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
		
		//get the width and height in terms of the scale : 
		int width = (int)(this.getWidth()/2*this.image_ratio);
		int height = (int)(this.getWidth()/2/this.image_ratio);
		
		//change the width and height in the case that the width is overlapping with the middle 
		if(width > this.getWidth()/2) {
			height *= (width*1.0/(this.getWidth()/2));
			width=this.getWidth()/2;
		}
		
		//draw the image using the scaled width and height
		g.drawImage(this.item_image, 
				this.getWidth()*1/10, this.getHeight()*1/10, width, height, null);
		
		int y = this.getHeight()*1/10 + height;
		
		if(this.lowestPointY > y) {
			y = this.lowestPointY;
		}
		
		y += 25;
		
		g.drawString("REVIEWS", this.getWidth()/2-GUIdo_OutputTools.getPixelWidth("REVIEWS", DESC_FONT)/2,y);
		
		int [] starx_t = STAR_X;
		int [] stary_t = STAR_Y;
		
		for(int i = 0; i < 11; i++) {
			stary_t[i] += y;
		}
		
		for(Review review : item.getReviews()) {
			int oldy = y;
			y += GAP_BETWEEN_REVIEWS;
			int i = 0;
			g.setColor(Color.YELLOW);
			for(;i < review.getRating(); i++) {
				g.fillPolygon(starx_t, stary_t, 11);
				for(int k = 0; k < 11; k++) {
					starx_t [k] += STAR_WIDTH;
				}
			}
			
			
			g.setColor(Color.GRAY);
			for(;i < 5; i++) {
				g.fillPolygon(starx_t, stary_t, 11);
				for(int k = 0; k < 11; k++) {
					starx_t [k] += STAR_WIDTH;
				}
			}
			
			y += REVIEW_DESC_BUFFER;
			
			String dateStr = review.getDate().toString();
			g.drawString(dateStr, this.getWidth(), y);
			y += REVIEW_DESC_BUFFER;
			
			for(String line : GUIdo_OutputTools.formatStringForPrompt(review.getDescription(), DESC_FONT, this.getWidth()*2/3)) {
				g.drawString(line, this.getWidth()/2-GUIdo_OutputTools.getPixelWidth(line, DESC_FONT), y);
				y += this.REVIEW_DESC_BUFFER;
			}
			
			starx_t = STAR_X;
			for(int k = 0; k < 11; k++) {
				stary_t[k] += (y-oldy);
			}
		}
		
		
	}
	
}
