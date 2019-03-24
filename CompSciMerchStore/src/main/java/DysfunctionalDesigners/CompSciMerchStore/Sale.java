package DysfunctionalDesigners.CompSciMerchStore;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Sale {
	static final double SHIPPING = 68, TAX = 0.08;//$, %
	private static int nextSaleID = 0;
	
	private Payment payment;
	private Date dateTime;//finished when finalized
	private int customerID, saleID;
	private Map<Integer, LineItem> itemList;
	private Address shippingAddr;
	private boolean finalized = false;
	
	public Sale(int custID) {
		this.customerID = custID;
		this.saleID = nextSaleID++;
		this.itemList = new HashMap<Integer, LineItem>();
		this.payment = null;
		this.dateTime = null;
		this.shippingAddr = null;
	}

	/**
	 * This function adds an item to the sale, or increases the amount currently in the cart if it's already present.
	 * 
	 * @param quantity the number of that item to add
	 * @param itemID the item to add
	 * @return if the add was successful (if the quantity was within the bounds of the stock)
	 */
	public boolean addItem(int quantity, int itemID) {
		boolean success = false;
		if(!this.finalized) {
			if(itemList.containsKey(itemID)) {
				if(Catalogue.checkAmount(itemID, quantity + itemList.get(itemID).getQuantity())) {
					itemList.get(itemID).addSome(quantity);
					success = true;
				}
			} else {
				if(Catalogue.checkAmount(itemID, quantity)){
					itemList.put(itemID, new LineItem(quantity, itemID));
					success = true;
				}
			}
		}
		return success;
	}
	
	/**
	 * This function allows the removal of an item from the sale
	 * 
	 * @param itemID the item to remove
	 */
	public void removeItem(int itemID) {
		if(!this.finalized && itemList.containsKey(itemID)) {
			itemList.remove(itemID);
		}
	}
	
	/**
	 * Allows the editing of the quantity of one of the line items.
	 * 
	 * @param itemID the item to edit
	 * @param newQuantity the new amount to add
	 * @return if the edit was successful (if the quantity is in bounds of the stock)
	 */
	public boolean editQuantity(int itemID, int newQuantity) {
		if(!this.finalized && Catalogue.checkAmount(itemID, newQuantity)) {
			itemList.get(itemID).setQuantity(newQuantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows a promo code to be added to the sale, if it exists.
	 * 
	 * @param code the promo code to add
	 */
	public void applyPromoCode(String code) {
		if(!this.finalized && Catalogue.checkPromoCode(code)) {
			//guaranteed to be the right length
			this.itemList.get(Integer.parseInt(code.substring(code.length()-ItemInfo.EXTENDED_ID_LENGTH))).addPromoCode(code);
		}
	}
	
	/**
	 * Checks if the payment amount is above or at the total amount for the purchase
	 * 
	 * @return if the payment is sufficient
	 */
	public boolean checkPaymentAmount() {
		return (this.payment != null ? payment.getAmount() >= this.getTotalWithTax() : false);//if they want to give us more, sure!
	}
	
	/**
	 * @return the total without tax or shipping
	 */
	public double getTotalWithoutTax() { 
		double total = 0;
		
		for(LineItem li : itemList.values()) {
			total += li.getTotalPrice();
		}
		return total;
	}

	
	/**
	 * @return the estimated tax to be collected
	 */
	public double getEstimatedTax() {
		return this.getTotalWithoutTax()*TAX;
	}
	
	/**
	 * @return the total for the sale with tax and shipping
	 */
	public double getTotalWithTax() {
		double total = this.getTotalWithoutTax();
		return total + total*TAX + SHIPPING;
	}
	
	/**
	 * @return the total number of items in the sale
	 */
	public int getNumItems() {
		return itemList.values().stream().mapToInt(e -> e.getQuantity()).reduce(0, Integer::sum);
	}
	
	/**
	 * @return the number of unique items in the sale
	 */
	public int getNumUniqueItems() { return this.itemList.size(); }
	
	/**
	 * Finalizes the sale and prevents anything else from being changed in the class.
	 * 
	 * @return if the payment is enough or if the sale was already finalized
	 */
	public boolean finalizePayment(){
		if(!this.finalized && this.checkPaymentAmount() && this.shippingAddr != null) {
			this.finalized = true;
			//decrease quantity from catalogue
			for(LineItem li : this.itemList.values()) {
				Catalogue.decreaseQuantity(li.getItem(), li.getQuantity());
			}
			this.dateTime = new Date();
			/////////here is where we would add some of the database and remote
			/////////stuff if this was a real online store
		}
		return this.finalized;
	}
	
	////////getters and setters
	public void setPayment(Payment payment) { if(!this.finalized) this.payment = payment; }
	public void setShippingAddr(Address shippingAddr) { if(!this.finalized) this.shippingAddr = shippingAddr; }

	public Payment getPayment() { return payment; }
	public Date getDateTime() { return dateTime; }
	public int getCustomerID() { return customerID; }
	public int getSaleID() { return saleID; }
	public Map<Integer, LineItem> getItemList() { return itemList; }
	public static double getShipping() { return SHIPPING; }
	public static double getTax() { return TAX; }
	public Address getShippingAddr() { return shippingAddr; }
	
	
}
