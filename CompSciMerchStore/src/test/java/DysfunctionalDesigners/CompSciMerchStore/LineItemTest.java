package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LineItemTest {

	ItemInfo info = null;
	LineItem line = null;
	final String testCode = "testy";
	Catalogue catty = Catalogue.getInstance();
	
	@BeforeEach
	void init(){
		
		//String description, String displayName, int stock, int vendorID, double price, int id, Professor p
		info = new ItemInfo("fake", "testitem", 20, 4000, 10.29, 120, Professor.DYS_DES);
		
		catty.addItem(info);
		
		
		
	}
	
	
	@Test
	void testLineItemItemID(){
		line = new LineItem(1,120);
		assertTrue(line.getItemID() == info.getItemID());
	}
	
	@Test
	void testPromoCode() {
		line = new LineItem(1,120);
		catty.getItem(120).addPromoDiscount(testCode, 0.99);
		line.addPromoCode(testCode);
		assertTrue(line.getPromoCodes().contains(testCode));
	}
	
	@Test
	void testPrices1() {
		line = new LineItem(1,120);
		assertTrue(line.getTotalPrice() == info.getPrice());
		
	}
	
	@Test
	void testPrices2() {
		line = new LineItem(2,120);
		assertTrue(line.getTotalPrice() == (2*info.getPrice()));
	}
	
	@Test
	void testPrices3() {
		line = new LineItem(1,120);
		catty.getItem(120).addPromoDiscount(testCode, 0.99);
		line.addPromoCode(testCode);
		assertTrue(line.getTotalPrice() == info.getPrice()-(info.getPrice()*0.99));
		
	}
	
	@Test
	void testPrices4() {
		line = new LineItem(2,120);
		catty.getItem(120).addPromoDiscount(testCode, 0.99);
		line.addPromoCode(testCode);
		assertTrue(line.getTotalPrice() == (2*info.getPrice() - (2*info.getPrice())*0.99));
	}
	
}
