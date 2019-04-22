package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemInfoTest {

	ItemInfo infy = null;
	Catalogue catty = Catalogue.getInstance();
	
	@BeforeEach
	void init() {
		//String description, String displayName, int stock, int vendorID, double price, Professor p
		
		try {
			infy = new ItemInfo("test", "testitem", 20, 4000, 1.20, Professor.DYS_DES);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddingDiscounts() {
		//System.out.println(infy.getSaleDiscount());
		//init
		assertTrue(infy.getSaleDiscount() == 0.0);
		
		infy.addPromoDiscount("disc1", .01);
		infy.addPromoDiscount("disc2", .5);
		
		assertTrue(infy.getPromoDiscounts().containsKey("disc1"));
		assertTrue(infy.getPromoDiscounts().containsKey("disc2"));
		
		assertTrue(infy.getPromoDiscounts().containsValue(.01));
		assertTrue(infy.getPromoDiscounts().containsValue(.5));
	}
	
	@Test
	void testCalculateDiscounts() {
		infy.addPromoDiscount("disc1", .01);
		infy.addPromoDiscount("disc2", .5);
		
		List<String> n = Arrays.asList("disc1", "disc2");
		assertTrue(infy.getTotalPrice(n) == (infy.getPrice() - (infy.getPrice()*.51)));
	}
	
	@Test
	void testUpdatePromo() {
		infy.addPromoDiscount("disc1", .01);
		infy.addPromoDiscount("disc2", .5);
		
		infy.updatePromoDiscount("disc1", .1);
		
		assertTrue(infy.getPromoDiscounts().get("disc1").equals(.1));
		
	}
	
	@Test
	void testRemovePromo() {
		infy.addPromoDiscount("disc1", .01);
		infy.addPromoDiscount("disc2", .5);
		
		infy.removePromoDiscount("disc1");
		assertFalse(infy.getPromoDiscounts().keySet().contains("disc1"));
	}
	
	@Test
	void testReduction() {
		
		try {
			infy.decreaseAmount(2);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		assertTrue(infy.getStock() == 18);
		
	}
	
	@Test
	void testStockIncrease() {
		
		infy.increaseAmount(4);
		assertTrue(infy.getStock() == 24);
	}
	
	
}
