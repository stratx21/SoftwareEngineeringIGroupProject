package DysfunctionalDesigners.CompSciMerchStore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdministratorTester {
	protected Administrator admin = null;

	@BeforeEach
	void init() {
		admin = new Administrator();
	}
	
	@Test
	void removeItemTest() {
		//theoretically shouldn't affect the catalogue because it doesn't export
		Catalogue c = Catalogue.getInstance();
		assertTrue(c.getItem(0) != null);
		
		admin.removeItemFromCatalogue(0);
		assertFalse(c.getItem(0).isEnabled());
	}

	@Test
	void getAllComplaintTest() {
		String[] complaints = admin.getAllComplaints();
		for (String complaint : complaints) {
			System.out.println(complaint);
		}
	}
	
	@Test
	void testReport() {
		assertTrue(admin.generateAllUsersReport());
		assertTrue(admin.generateAllSalesReport());
	}
}
