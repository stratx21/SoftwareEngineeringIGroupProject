package DysfunctionalDesigners.CompSciMerchStore;

import java.util.List;

public class Administrator extends Vendor{
    public Administrator(String[] d) {
        super(d);
        
    }

    public Administrator(String email, String motherMaidenName, String userName, String password, String name, int userID, List<Integer> uploadedItems, List<Sale> pastSales) {
        super(email, motherMaidenName, userName, password, name, userID, uploadedItems, pastSales);
    }
    
    public Administrator() {
    	super();
    }
}
