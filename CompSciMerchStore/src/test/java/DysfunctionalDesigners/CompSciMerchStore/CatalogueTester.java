package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatalogueTester {
	protected Catalogue c = Catalogue.getInstance();
	protected ItemInfo i = null;

	@BeforeEach
	void init() {
		i = new ItemInfo(100);
		i.increaseAmount(20);
		c.addItem(i);
	}
	
	@AfterEach
	void breakdown() {
		c.removeItem(i.getItemID());
	}
	
	@Test
	void addTest() {
		ItemInfo temp = c.getItem(i.getItemID());
		assertTrue(temp != null);
		assertTrue(temp.equals(i));
		
		assertThrows(Exception.class, () -> {
			c.addItem(null);
		});
	}
	
	@Test
	void removeDisableTest() {
		c.removeItem(i.getItemID());
		ItemInfo temp = c.getItem(i.getItemID());
		assertFalse(temp.isEnabled());
		c.enableItem(i.getItemID());
		assertTrue(temp.isEnabled());
	}
	
	@Test
	void decreaseQuantityTest() {
		int origQuan = i.getStock();
		c.decreaseQuantity(i.getItemID(), 3);
		assertTrue(origQuan == c.getItem(i.getItemID()).getStock()+3);
		
		origQuan = i.getStock();
		c.decreaseQuantity(i.getItemID(), 1000000);
		assertTrue(origQuan == c.getItem(i.getItemID()).getStock());
	}
	
	@Test
	void increaseQuantityTest() {
		int origQuan = i.getStock();
		c.increaseQuantity(i.getItemID(), 3);
		assertTrue(origQuan == c.getItem(i.getItemID()).getStock()-3);
		
		origQuan = i.getStock();
		c.increaseQuantity(i.getItemID(), -3);
		assertTrue(origQuan == c.getItem(i.getItemID()).getStock());
	}
	
	@Test
	void checkUpdate() {
		assertThrows(Exception.class, () -> {
			c.updateItem(1, i);
		});
		ItemInfo temp = new ItemInfo(20000);
		assertThrows(Exception.class, () -> {
			c.updateItem(i.getItemID(), null);
		});
		//the rest are tested in other testing classes
	}
	
	@Test
	void checkAmountTest() {
		assertTrue(c.checkAmount(i.getItemID(), 1));
		assertFalse(c.checkAmount(i.getItemID(), -1));
		assertFalse(c.checkAmount(i.getItemID(), 200));
	}
	
	@Test
	void checkSearch() {
		assertThrows(Exception.class, () -> {
			c.search("100");
		});
		c.removeItem(i.getItemID());
		List<ItemInfo> ls = c.search("1");
		assertTrue(ls != null);
		assertTrue(ls.contains(c.getItem(1)));
	}
	
	@Test
	void checkSearchByProfessor() {
		List<ItemInfo> ls = c.searchByProfessor(Professor.BOOTH);
		assertTrue(ls != null);
		assertTrue(ls.contains(c.getItem(1)));
	}
}
