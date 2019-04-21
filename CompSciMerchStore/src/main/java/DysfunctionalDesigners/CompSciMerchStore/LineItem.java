package DysfunctionalDesigners.CompSciMerchStore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LineItem {
	private static Logger logger = Logger.getLogger(LineItem.class.getName());

	private int quantity;
	private int itemID;
	private List<String> promoCodes;
	
	public LineItem(int quantity, int itemID) {
		this.quantity = quantity;
		this.itemID = itemID;
		this.promoCodes = new ArrayList<String>();
		logger.info("New LineItem initialized for item " + itemID);
	}
	
	/**
	 * Adds a promo code to the item
	 * @param promo the promo code to add
	 */
	public void addPromoCode(String promo) {
		if(Catalogue.getInstance().getItem(itemID).hasPromoCode(promo)) {
			this.promoCodes.add(promo);
			logger.info("Adding promo code \"" + promo + "\" to LineItem " + itemID);
		}
	}
	
	/**
	 * Returns the total price for this item counting by quantity.
	 * @return the total price
	 */
	
	@JsonIgnore 
	public double getTotalPrice() {
		return this.quantity * Catalogue.getInstance().getItem(this.itemID).getTotalPrice(this.promoCodes);
	}
	
	/**
	 * required by json parser
	 */
	public LineItem() {}
	
	public int getQuantity() { return quantity; }
	public int getItemID() { return itemID; }
	
	public void setQuantity(int quantity) { this.quantity = quantity; }
	public void setItem(int itemID) { this.itemID = itemID; }
	public void addSome(int quantity) { this.quantity += quantity; }
	
	
}
