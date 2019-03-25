package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ItemInfo {
	public static final int EXTENDED_ID_LENGTH = 5;
	private static Font DESC_FONT=new Font("Ariel",Font.PLAIN,10);
	private static int NEXT_ID = 0;
	private String description, displayName, extendedItemID;
	private int itemID, stock, vendorID;
	private double saleDiscount, price;
	private Map<String, Double> promoDiscounts;
	private boolean enabled = true;
	private Professor prof;
	
	/**
	 * this is for quick searching and comparison objects -- not meant to be a permanent object
	 * @param ID a temp id to compare with
	 */
	public ItemInfo(int ID) {
		this.itemID = ID;
		this.description = "ERROR: This is a temporary ItemInfo used for searching/comparing and shouldn't be used for more than that";
		this.displayName = "ERROR: This is a temporary ItemInfo used for searching/comparing and shouldn't be used for more than that";
		this.extendedItemID = "ERROR: This is a temporary ItemInfo used for searching/comparing and shouldn't be used for more than that";
		this.stock = -1;
		this.vendorID = -1;
		this.saleDiscount = -1;
		this.price = -1;
		this.promoDiscounts = null;
	}
	
	/**
	 * Creates an ItemInfo with the specified fields
	 * @throws Exception 
	 */
	public ItemInfo(String description, String displayName, int stock, int vendorID, double price) throws Exception {
		this(description, displayName, stock, vendorID, price, NEXT_ID);
		if(NEXT_ID == 0) {
			throw new Exception("ERROR: NEXT_ID HASN'T BEEN SET YET, CALL CATALOGUE SETID FUNCTION BASED ON CATALOGUE DATA FILE");
		} else {
			NEXT_ID++;
		}
	}
	
	public ItemInfo(String description, String displayName, int stock, int vendorID, double price, int id) {
		super();
		this.description = description;
		this.displayName = displayName;
		this.itemID = id;///////////////////////////////////////////this will need to be changed to be incorporated into one of the files so new items don't get set to old item's numbers
		this.extendedItemID = String.format("%0" + EXTENDED_ID_LENGTH, this.itemID);
		this.stock = stock;
		this.vendorID = vendorID;
		this.saleDiscount = 0.0;
		this.promoDiscounts = new HashMap<String, Double>();
		this.price = price;
	}
	
	public static void setNextID(int id) { NEXT_ID = id; }
	
	public void drawDisplay(Graphics g, int x, int y, int width, int height) {
		int currenty = y+height*3/4;
		int line_height = g.getFontMetrics(DESC_FONT).getHeight();
		try {
			g.drawImage(ImageIO.read(new File("src/main/resources/ItemImages/"+String.format("%05d",this.itemID)+".jpg")), 
					x,y,width,currenty-y, null);
		} catch(IOException ioex) {
			ioex.printStackTrace();
		}
		
		g.setFont(DESC_FONT);
		
		ArrayList<String> lines = GUIdo_OutputTools.formatStringForPrompt(this.displayName,DESC_FONT,width);
		for(String line : lines) {
			g.drawString(line, x, currenty);
			currenty+=line_height;
		}
		g.drawString(this.price+"", x, currenty);
		currenty+=line_height;
	}

	/**
	 * This returns the total discount counting the sale discount and promo discounts.
	 * 
	 * @param promoKeys the list of promotion strings associated with this product
	 * @return discount the decimal percent (.1 = 10%) off the product is
	 */
	public double getTotalDiscount(List<String> promoKeys) {
		if(!this.promoDiscounts.isEmpty()) {
			double total = saleDiscount;
			
			for(String key : promoKeys) {
				if(this.promoDiscounts.containsKey(key)) {
					total += this.promoDiscounts.get(key);
				}
			}
			
			return (total >= 1 ? 1 : total);
		} else {
			return saleDiscount;
		}	
	}
	
	/**
	 * This returns the total discount counting only sale discount.
	 * 
	 * @return discount the decimal percent (.1 = 10%) off the product is
	 */
	public double getTotalDiscount() {
		return saleDiscount;
	}
	
	/**
	 * This returns the total price counting the sale discount and promo discounts.
	 * 
	 * @return discount the decimal percent (.1 = 10%) off the product is
	 */
	public double getTotalPrice(List<String> promoKeys) {
		return this.price - this.price*this.getTotalDiscount(promoKeys);
	}
	
	/**
	 * This returns the total price counting only sale discount.
	 * 
	 * @return discount the decimal percent (.1 = 10%) off the product is
	 */
	public double getTotalPrice() {
		return this.price - this.price*this.getTotalDiscount();
	}
	
	/**
	 * This function allows you to generate and add a promo code.
	 * 
	 * @param keyword the promo keyword used to access this discount (prepended before the item id)
	 * @param discount the amount off the discount adds
	 * @return code the promo code generated that can be used to access this discount
	 */
	public String makePromoDiscount(String keyword, double discount) {
		//key will be keyword+id
		this.promoDiscounts.put(keyword + this.extendedItemID, (discount > 1 ? 1 : discount));
		return keyword + this.extendedItemID;
	}
	
	/**
	 * This function allows you to update a promo code and will replace any previous discount.
	 * 
	 * @param key the promo key used to access this discount with the itemID appended
	 * @param discount the amount off the discount adds
	 */
	public void updatePromoDiscount(String key, double discount) {
		this.promoDiscounts.put(key, (discount > 1 ? 1 : discount));
	}
	
	/**
	 * This function allows the removal of one promo code
	 * 
	 * @param key the promo code to remove
	 */
	public void removePromoDiscount(String key) {
		this.promoDiscounts.remove(key);
	}
	
	/**
	 * This function returns whether or not this item has the given promo code
	 * @param code the promo code to check
	 * @return whether or not the item has the given promo code
	 */
	public boolean hasPromoCode(String code) {
		return this.promoDiscounts.containsKey(code);
	}
	
	/**
	 * This decreases the stock by amount (or to zero if greater).
	 * @param amount the amount to reduce the stock by
	 * @throws Exception If amount is > stock
	 */
	public void decreaseAmount(int amount) throws Exception { 
		if(amount > this.stock) {
			throw new Exception("ERROR: Not enough stock");
		} else if(amount > 0){
			this.stock -= amount;
		}
	}
	
	/**
	 * This increases the stock by amount.
	 * @param amount the amount to increase the stock by
	 */
	public void increaseAmount(int amount) { 
		if(amount > 0) {
			this.stock += amount;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemInfo other = (ItemInfo) obj;
		if (itemID != other.itemID)
			return false;
		return true;
	}

	///////getters and setters
	public String getDescription() { return description; }
	public String getDisplayName() { return displayName; }
	public int getItemID() { return itemID; }
	public String getExtendedItemID() { return extendedItemID; }
	public int getStock() { return stock; }
	public int getVendorID() { return vendorID; }
	public double getSaleDiscount() { return saleDiscount; }
	public Map<String, Double> getPromoDiscounts() { return promoDiscounts; }
	public double getPrice() { return price; }
	public Professor getProf() { return prof; }
	
	public void enable() { this.enabled = true; }
	public void disable() { this.enabled = false; }
	public boolean isEnabled() { return this.enabled; }
	public void setPrice(double price) { this.price = price; }
	public void setDescription(String description) { this.description = description; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public void setStock(int stock) { this.stock = stock; }
	public void setSaleDiscount(double saleDiscount) { this.saleDiscount = (saleDiscount > 1 ? 1 : saleDiscount); }
	public void setPromoDiscounts(Map<String, Double> promoDiscounts) { this.promoDiscounts = promoDiscounts; }
	public void setProf(Professor prof) { this.prof = prof; }
	
}
