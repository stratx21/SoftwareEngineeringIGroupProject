package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaleTester {
	protected Sale sale = null;
	protected ItemInfo i = null;
	protected Catalogue c = Catalogue.getInstance();
	
	@BeforeEach
	void init() {
		sale = new Sale(40100);
		i = new ItemInfo(100);
		c.addItem(i);
	}
	
	@AfterEach
	void tearDown() {
		c.removeItem(i.getItemID());
	}
	
	@Test
	void addItemTest1() {
		i.setStock(29);
		sale.addItem(27, i.getItemID());
		assertTrue(sale.getItemList().containsKey(i.getItemID()));
		assertFalse(sale.getItemList().containsKey(0));
		
		sale.addItem(2, i.getItemID());
		assertTrue(sale.getItemList().containsKey(i.getItemID()));
		assertTrue(sale.getItemList().get(i.getItemID()).getQuantity() == 29);
		
		sale.addItem(2, i.getItemID());
		assertTrue(sale.getItemList().containsKey(i.getItemID()));
		assertTrue(sale.getItemList().get(i.getItemID()).getQuantity() == 29);
	}
	
	@Test
	void addItemTest2() {
		i.setStock(26);
		sale.addItem(27, i.getItemID());
		assertFalse(sale.getItemList().containsKey(i.getItemID()));
	}
	
	@Test
	void removeItemTest() {
		i.setStock(20);
		sale.addItem(2, i.getItemID());
		sale.removeItem(i.getItemID());
		assertFalse(sale.getItemList().containsKey(i.getItemID()));
	}
	
	@Test
	void editQuantityTest() {
		i.setStock(20);
		int origQuan = 2;
		sale.addItem(2, i.getItemID());
		
		sale.editQuantity(i.getItemID(), 3);
		assertTrue(3 == sale.getItemList().get(i.getItemID()).getQuantity());
		
		origQuan = i.getStock();
		sale.editQuantity(i.getItemID(), -3);
		assertTrue(3 == sale.getItemList().get(i.getItemID()).getQuantity());
		
		origQuan = i.getStock();
		sale.editQuantity(i.getItemID(), 0);
		assertThrows(NullPointerException.class, () -> {
			sale.getItemList().get(i.getItemID()).getQuantity();
		});
	}
	
	@Test
	void promoCodeTest() {
		String promo = "key";
		i.setStock(20);
		i.addPromoDiscount(promo, 1);
		sale.addItem(20, i.getItemID());
		
		sale.applyPromoCode(promo);
		assertTrue(sale.getItemList().get(i.getItemID()).getPromoCodes().contains(promo));
		
		assertFalse(sale.checkPromoCode("nah"));
		assertTrue(sale.checkPromoCode(promo));
	}
	
	@Test
	void paymentCheck() {
		i.setStock(20);
		i.setPrice(20.0);
		sale.addItem(20, i.getItemID());
		sale.setPayment(new Payment(19.0, null));
		
		assertFalse(sale.checkPaymentAmount());
		sale.setPayment(new Payment(500.0, null));
		assertTrue(sale.checkPaymentAmount());
	}
	
	@Test
	void itemsCheck() {
		i.setStock(20);
		sale.addItem(20, i.getItemID());
		assertTrue(20 == sale.getNumItems());
		assertTrue(1 == sale.getNumUniqueItems());
	}
	
	@Test
	void checkFinalize() {
		i.setStock(20);
		i.setPrice(20.0);
		sale.addItem(20, i.getItemID());
		sale.setPayment(new Payment(500.0, null));
		sale.setShippingAddr(new Address());
		
		assertTrue(sale.finalizePayment());
	}
}
