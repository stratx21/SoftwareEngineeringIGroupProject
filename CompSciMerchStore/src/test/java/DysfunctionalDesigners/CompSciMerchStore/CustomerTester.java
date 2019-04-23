package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTester {
	protected Customer c = null;
	
	@BeforeEach
	void init() {
		
		String cdat[] = {"mail@mail.com", "julie", "jn", "pw","julie a","10000"};
		
		c = new Customer(cdat);
	}
	
	@Test
	void testAddWishlist() {
		int itemID = 27;
		c.addItemToWishlist(27);
		assertTrue(c.getWishList().contains(itemID));
	}
	
	@Test
	void testRemoveWishlist(){
		int itemID = 27;
		c.addItemToWishlist(27);
		assertTrue(c.getWishList().contains(itemID));
		c.removeItemFromWishlist(27);
		assertFalse(c.getWishList().contains(itemID));
		
	}
	
	@Test
	void testAddToCart() {
		int id = 2;
		c.addToCart(id, 0);
		assertFalse(c.getCart().getItemList().containsKey(id));
		c.addToCart(id, 1);
		assertTrue(c.getCart().getItemList().containsKey(id));
	}
	
	@Test
	void testPrevPurchases() {
		Sale sally = new Sale(c.getUserID());
		c.addNewSale(sally);
		assertTrue(c.getPastSales().contains(sally));
	}
	
}
