package DysfunctionalDesigners.CompSciMerchStore;

import java.util.ArrayList;
import java.util.List;

public class LineItem {
	private int quantity;
	private int itemID;
	private List<String> promoCodes;
	
	public LineItem(int quantity, int itemID) {
		this.quantity = quantity;
		this.itemID = itemID;
		this.promoCodes = new ArrayList<String>();
	}
	
	/**
	 * Adds a promo code to the item
	 * @param promo the promo code to add
	 */
	public void addPromoCode(String promo) {
		this.promoCodes.add(promo);
	}
	
	/**
	 * Returns the total price for this item counting by quantity.
	 * @return the total price
	 */
	public double getTotalPrice() {
		return this.quantity * Catalogue.getItem(this.itemID).getTotalPrice(this.promoCodes);
	}
	
	public int getQuantity() { return quantity; }
	public int getItemID() { return itemID; }
	
	public void setQuantity(int quantity) { this.quantity = quantity; }
	public void setItem(int itemID) { this.itemID = itemID; }
	public void addSome(int quantity) { this.quantity += quantity; }
	
	
}
