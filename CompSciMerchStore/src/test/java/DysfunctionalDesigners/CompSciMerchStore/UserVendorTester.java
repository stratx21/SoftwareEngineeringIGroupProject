package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserVendorTester {

	protected Customer c = null;
	protected Administrator a = null;
	
	@BeforeEach
	void init() {
		//String email, String motherMaidenName, String userName, String password, String name, int userID
		String cdat[] = {"mail@mail.com", "julie", "jn", "pw","julie a","10000"};
		String adat[] = {"mail2@mail.com", "julie", "jn", "pw","julie a","40000"};
		
		c = new Customer(cdat);
		a = new Administrator(adat);
		
	}
	
	@Test 
	void isAdminTest(){
		assertTrue(a.isAdmin());
		assertFalse(c.isAdmin());
	}
	
	@Test
	void vendorUploadingItemsTestNotTheirs() {
		Catalogue catty = Catalogue.getInstance();
		ItemInfo toChange = catty.getItem(12);
		ItemInfo other = catty.getItem(12);
		toChange.setDisplayName("Nonexistent");
		c.updateUploadedItems(12, toChange);
		ItemInfo changed = catty.getItem(12);
		assertTrue(other.equals(changed));
		
	}
	
	@Test
	void vendorUploadingItemsTheirs() {
		Catalogue catty = Catalogue.getInstance();
		ItemInfo toChange = catty.getItem(12);
		ItemInfo other = catty.getItem(12);
		
		toChange.setDisplayName("Nonexistent");
		a.updateUploadedItems(12, toChange);
		ItemInfo changed = catty.getItem(12);
		
		assertTrue(other.equals(changed));
	}
	
	@Test 
	void addItemToCatty() {
		Catalogue catty = Catalogue.getInstance();
		ItemInfo ni = new ItemInfo(2000);
		c.addItemToCatalogue(ni);
		ItemInfo test = catty.getItem(2000);
		assertTrue(ni.equals(test));
	}
	
	@Test
	void addNewSale() {
		Sale k = new Sale(c.getUserID());
		int saleid = k.getSaleID();
		c.addNewSale(k);
		List<Sale> l = c.getPastSales();		
		assertTrue(l.contains(k));
		
	}
	
	@Test
	void remItemFromCatty() {
		Catalogue catty = Catalogue.getInstance();
		ItemInfo ni = new ItemInfo(2000);
		c.addItemToCatalogue(ni);
		
		c.removeItemFromCatalogue(2000);
		ItemInfo test = catty.getItem(2000);
		assertTrue(!test.isEnabled());
	}
	
	
}
