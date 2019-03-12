package DysfunctionalDesigners.CompSciMerchStore;

import java.util.Set;

public class Catalogue {
	private static Set<ItemInfo> catalogue;
	private static int numItems;
	
	public static void addItem(ItemInfo e) {
		catalogue.add(e);
	}
	
	public static void removeItem(int itemID) {
		//TODOOOOOOOO
	}
	//all should be static as the top-level class unfortunately cannot be
}
