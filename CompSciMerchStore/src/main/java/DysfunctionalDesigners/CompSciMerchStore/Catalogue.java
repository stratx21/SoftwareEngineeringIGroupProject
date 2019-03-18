package DysfunctionalDesigners.CompSciMerchStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalogue {//all should be static as the top-level class unfortunately cannot be
	private static Map<Integer, ItemInfo> catalogue = new HashMap<Integer, ItemInfo>();
	private static int numItems = 0;
	
//////////////////modifiers
	/**
	 * Adds an item (or replaces an old one) to the catalogue
	 * 
	 * @param e the item to add
	 */
	public static void addItem(ItemInfo e) {
		if(catalogue.put(e.getItemID(), e) == null) {
			numItems++;
		}
	}
	
	/**
	 * Removes an item from the catalogue if present.
	 * 
	 * @param itemID the item to remove
	 */
	public static void removeItem(int itemID) {
		if(catalogue.remove(itemID) != null) {
			numItems--;
		}
	}
	
	/**
	 * Decreases the quantity of the given item if the amount is valid.
	 * 
	 * @param itemID the item to decrease
	 * @param amount the amount to decrease by
	 */
	public static void decreaseQuantity(int itemID, int amount) {
		if(checkAmount(itemID, amount)) {
			try {
				catalogue.get(itemID).decreaseAmount(amount);
			} catch (Exception e) {
				System.err.println("ERROR: checkAmount failed");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Increases the quantity of the given item if the amount is valid.
	 * 
	 * @param itemID the item to increase
	 * @param amount the amount to increase by
	 */
	public static void increaseQuantity(int itemID, int amount) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).increaseAmount(amount);
		}
	}
	
	/**
	 * Disables the item
	 * @param itemID the item to disable
	 */
	public void disableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).disable();
		}
	}
	
	/**
	 * Enables the item
	 * @param itemID the item to enable
	 */
	public void enableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).enable();
		}
	}
	
	public void updateItem(int itemID, ItemInfo info) {
		catalogue.put(itemID, info);
	}
	
//////////////////accessors
	/**
	 * @param itemID the item to get
	 * @return the ItemInfo for that item
	 */
	public static ItemInfo getItem(int itemID) {
		return catalogue.get(itemID);
	}
	
	/**
	 * @return the number of unique items in the catalogue
	 */
	public static int getNumItems() { return numItems; }
	
	/**
	 * @param itemID item to check
	 * @return if it's enabled
	 */
	public static boolean isEnabled(int itemID) {
		if(catalogue.containsKey(itemID)) {
			return catalogue.get(itemID).isEnabled();
		}
		return false;
	}
	
//////////////////others
	/**
	 * Checks if the given amount can be taken from the item given.
	 * 
	 * @param itemID the item to check
	 * @param amount the amount to withdraw
	 * @return if that amount can be taken from that item
	 */
	public static boolean checkAmount(int itemID, int amount) {
		ItemInfo temp = catalogue.get(itemID);
		if(temp != null) {
			return (amount >= 0 && amount <= temp.getStock());
		}
		return false;
	}
	
	/**
	 * Checks to see if a promo code is valid.
	 * 
	 * @param code the code to check
	 * @return if the code is valid
	 */
	public static boolean checkPromoCode(String code) {
		if(code.length() >= ItemInfo.EXTENDED_ID_LENGTH) {
			int itemID = Integer.parseInt(code.substring(code.length()-ItemInfo.EXTENDED_ID_LENGTH));
			return catalogue.get(itemID).hasPromoCode(code);
		}
		return false;
	}
	
	/**
	 * Searches the catalogue for a keyword and pulls up items like it.
	 * 
	 * @param keywords the string to search for
	 * @return the list of ItemInfos which match the keywords
	 */
	public static List<ItemInfo> search(String keywords){
		//TODO: Kind of auto-generated method stub
		return null;
	}
	
}
