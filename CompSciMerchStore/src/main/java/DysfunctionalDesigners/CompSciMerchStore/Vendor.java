package DysfunctionalDesigners.CompSciMerchStore;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public abstract class Vendor extends User{
	private static Logger logger = Logger.getLogger(Vendor.class.getName());

	private List<Integer> uploadedItems;
	private List<Sale> pastSales;
	
	
	/**
	 * This function constructs a vendor object based on a string array for the super class, and creates
	 * 	empty lists for uploadedItems and pastSales.
	 * @param d the string array containing all pertinent information to be passed to the superclass.
	 */
	public Vendor(String d[]) {
		super(d);
		this.uploadedItems = new ArrayList<Integer>();
		this.pastSales = new ArrayList<Sale>();
		logger.info("New instance of a vendor with id " + this.getUserID() + " username " + this.getUserName() + " id " + this.getUserID());
	}

	/**
	 * This constructor takes all of the fields, passes necessary ones in to the super constructor, and sets uploadedItems
	 * and pastSales.
	 * 
	 * @param email
	 * @param motherMaidenName
	 * @param userName
	 * @param password
	 * @param name
	 * @param userID
	 * @param uploadedItems
	 * @param pastSales
	 */
	public Vendor(String email, String motherMaidenName, String userName, String password, String name, int userID, List<Integer> uploadedItems, List<Sale> pastSales) {
		super(email, motherMaidenName, userName, password, name, userID);
		this.uploadedItems = uploadedItems;
		this.pastSales = pastSales;
		logger.info("New instance of a vendor with id " + this.getUserID() + " username " + this.getUserName() + " id " + this.getUserID());
	}

	/**
	 * This function creates a vendor object based on given parameters.
	 * @param d all pertinent information to be passed to the super class
	 * @param items a list of item id's to become the vendor's uploadedItems
	 * @param sales a list of sales to become the vendor's pastSales
	 */
	public Vendor(String d[], List<Integer> items, List<Sale> sales) {
		super(d);
		this.uploadedItems = items;
		this.pastSales = sales;
		logger.info("New instance of a vendor with id " + this.getUserID() + " username " + this.getUserName() + " id " + this.getUserID());
	}
	
	/**
	 * Required by Jackson JSON
	 */
	public Vendor() {
		super();
	}
	
	/**
	 * This function updates uploaded items when a vendor uploads a new item.
	 * @param id the id is used to update the vendor specific uploaded items
	 * @param info the item info is used to add the new item to the catalogue
	 */
	public void updateUploadedItems(Integer id, ItemInfo info) {
		if(this.uploadedItems.contains(id)) {
			try {
				Catalogue.getInstance().updateItem(id, info);
				logger.info("Updated item id " + id + " with new item info from user.");
			} catch (Exception e) {
				logger.severe("ERROR UPDATING UPLOADED ITEM: " + e.getMessage());
				e.printStackTrace();
			}	
		}
		
	}
	
	/**
	 * Adds an item to the catalogue and to the vendors uploaded items.
	 * @param info item to be added
	 */
	public void addItemToCatalogue(ItemInfo info) {
		logger.info("Adding item id " + info.getExtendedItemID() + " to catalogue and uploaded items list for user " + this.getUserID());
		this.uploadedItems.add(info.getItemID());
		Catalogue.getInstance().addItem(info);
	}
	
	
	
	/**
	 * This function adds a sale to the vendor's sale history upon completion of the sale
	 * @param sale is the new sale that occurred
	 */
	public void addNewSale(Sale sale) {
		logger.info("Adding a previous sale (id " + sale.getSaleID() + ")");
		this.pastSales.add(sale);
	}

	/**
	 * This function returns a list of item id's that reference items the vendor has uploaded for sale.
	 * @return List<Integer> a list of item id's that reference items the vendor has uploaded for sale.
	 */
	public List<Integer> getUploadedItems() {
		return uploadedItems;
	}
	/**
	 * This function gets a list of sales that were made on the Vendor's behalf.
	 * @return List<Sale> a list of sales that were made on the Vendor's behalf.
	 */
	public List<Sale> getPastSales() {
		return pastSales;
	}
	
	/**
	 * Removes an item from the catalogue IF this vendor is original uploader.
	 * @param id The item id to be removed.
	 */
	public void removeItemFromCatalogue(int id) {
		if(this.uploadedItems.contains(id)) {
			Catalogue.getInstance().removeItem(id);
			logger.fine("Removing " + id + " from the catalogue (right now = disabling it)");
		}
			
	}
	
//	public void disableItem(Integer id) {}
	
}
