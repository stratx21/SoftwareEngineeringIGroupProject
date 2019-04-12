package DysfunctionalDesigners.CompSciMerchStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
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

    public void addItemToCatalogue(ItemInfo itemInfo) {
        Catalogue.getInstance().addItem(itemInfo);
    }

    public void removeItemFromCatalogue(int id) {
        Catalogue.getInstance().removeItem(id);
    }

    //public void disableItem(int id) {}

    //TODO: When we decide how complaints are written. Write this
    //public String[] getAllComplaints() {

    //}

    public void generateAllSalesReport() {
        BufferedReader readCust = null;
        try {
            readCust = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("./src/main/resources/UserData/customers.txt"))));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        try {
            String line;
            while((line = readCust.readLine()) != null) {
                String[] lineSplit = null;
                try {
                    lineSplit = line.split(" ");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ObjectMapper mapper = new ObjectMapper();
                Customer tempUsr = null;
                File usrFile = new File("./src/main/resources/UserData/" + lineSplit[0] + ".json");

                tempUsr = mapper.readValue(usrFile, Customer.class);
                if(tempUsr.isAdmin()) {
                    continue;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
