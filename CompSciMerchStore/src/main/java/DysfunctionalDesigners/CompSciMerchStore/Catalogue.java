package DysfunctionalDesigners.CompSciMerchStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalogue {//all should be static as the top-level class unfortunately cannot be
	private static Map<Integer, ItemInfo> catalogue = new HashMap<Integer, ItemInfo>();
	private static int numItems = 0;
	
	public static void readFile(BufferedReader bf) throws Exception {
		String line;
		String[] splitLine;
		try {
			line = bf.readLine();
			numItems = Integer.parseInt((line.split(" "))[1]);
			ItemInfo.setNextID(numItems);
			
			while((line = bf.readLine()) != null) {
				splitLine = line.split(", ");
				int id = Integer.parseInt(splitLine[0]);
				catalogue.put(id, new ItemInfo(splitLine[2], splitLine[1], Integer.parseInt(splitLine[1]), 
							  Integer.parseInt(splitLine[4]), Double.parseDouble(splitLine[4]), id));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Couldn't read catalogue from file stack trace");
		}		
	}
	
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
	 * Removes an item from the catalogue if present.  Actually just disables it because previous sales depend on it.
	 * 
	 * @param itemID the item to remove
	 */
	public static void removeItem(int itemID) {
		Catalogue.disableItem(itemID);
//		if(catalogue.remove(itemID) != null) {
//			numItems--;
//		}
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
	public static void disableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).disable();
		}
	}
	
	/**
	 * Enables the item
	 * @param itemID the item to enable
	 */
	public static void enableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).enable();
		}
	}
	
	/**
	 * 
	 * @param itemID
	 * @param info
	 * @throws Exception
	 */
	public static void updateItem(int itemID, ItemInfo info) throws Exception {
		if(info.getItemID() != itemID) {
			throw new Exception("New ItemInfo ID does NOT match given itemID");
		}
		catalogue.put(itemID, info);
	}
	
	/**
	 * Sets the next id to start from.
	 * @param id the next id to start from (aka next item has that id and the one after has that id+1 etc)
	 */
	public static void setNextID(int id) { ItemInfo.setNextID(id); }
	
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
