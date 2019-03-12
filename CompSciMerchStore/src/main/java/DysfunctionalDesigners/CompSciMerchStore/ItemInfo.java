package DysfunctionalDesigners.CompSciMerchStore;

import java.util.Map;

public class ItemInfo {
	static int NEXT_ID = 0;
	String description, displayName;
	int itemID, stock, vendorID;
	double saleDiscount;
	Map<String, Double> promoDiscounts;
	
	public ItemInfo(String description, String displayName, int stock, int vendorID) {
		super();
		this.description = description;
		this.displayName = displayName;
		this.itemID = NEXT_ID++;///////////////////////////////////////////this will need to be changed to be incorporated into one of the files so new items don't get set to old item's numbers
		this.stock = stock;
		this.vendorID = vendorID;
	}
	
	
	
}
