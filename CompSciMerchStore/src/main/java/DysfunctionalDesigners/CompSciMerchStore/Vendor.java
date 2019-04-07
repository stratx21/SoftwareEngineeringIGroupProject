package DysfunctionalDesigners.CompSciMerchStore;
import java.util.ArrayList;
import java.util.List;
public abstract class Vendor extends User{

	List<Integer> uploadedItems;
	List<Sale> pastSales;
	
	
	/**
	 * This function constructs a vendor object based on a string array for the super class, and creates
	 * empty lists for uploadedItems and pastSales.
	 * @param d the string array containing all pertinent information to be passed to the superclass.
	 */

	public Vendor(String d[]) {
		super(d);
		this.uploadedItems = new ArrayList<Integer>();
		this.pastSales = new ArrayList<Sale>();
	}

	public Vendor(String email, String motherMaidenName, String userName, String password, String name, int userID, List<Integer> uploadedItems, List<Sale> pastSales) {
		super(email, motherMaidenName, userName, password, name, userID);
		this.uploadedItems = uploadedItems;
		this.pastSales = pastSales;
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
	}
	
	@SuppressWarnings("unused")
	public Vendor() {
		super();
	}
	
	/**
	 * This function updates uploaded items when a vendor uploads a new item.
	 * @param id the id is used to update the vendor specific uploaded items
	 * @param info the item info is used to add the new item to the catalogue
	 */
	public void updateUploadedItems(Integer id, ItemInfo info) {
		this.uploadedItems.add(id);
		Catalogue.addItem(info);
	}
	
	/**
	 * This function adds a sale to the vendor's sale history upon completion of the sale
	 * @param sale is the new sale that occurred
	 */
	public void adddNewSale(Sale sale) {
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
	
	public void removeItemFromCatalogue(int id) {
		if(this.uploadedItems.contains(id))
			Catalogue.removeItem(id);
	}
	
//	public void disableItem(Integer id) {}
	
	
	
}
