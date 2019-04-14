package DysfunctionalDesigners.CompSciMerchStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void removeItemFromCatalogue(int id) {
        Catalogue.getInstance().removeItem(id);
    }

    //public void disableItem(int id) {}

    //TODO: When we decide how complaints are written. Write this
    //public String[] getAllComplaints() {

    //}

    public void generateAllSalesReport() {
        UserDataController dataController = UserDataController.getInstance();
        List<Sale> salesFromCust = new ArrayList<>();
        List<Sale> storeSales = dataController.getStoreSales();

        //Get all customer sales. Gets each customer and then
        //for each customer it gets their past sales list. If the list
        //is not empty, add it to the salesFromCust list.
        List<Customer> custs = dataController.getAllCustomers();
        custs.forEach(e -> {
            List<Sale> nextSales = new ArrayList<>();
            try {
                nextSales = dataController.getUserSales(e.getUserName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(!nextSales.isEmpty()) {
                salesFromCust.addAll(nextSales);
            }
        });

        //filters all cust Sales to only unique ones
        List<Sale> uniqueCustSales = salesFromCust.stream().distinct().collect(Collectors.toList());
        storeSales = storeSales.stream().distinct().collect(Collectors.toList());

        File salesReportFile = new File("./src/main/resources/reports/salesReport.txt");
        try {
            salesReportFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(salesReportFile));
            writer.write("Customer Sales: \n");
            Catalogue catalogue = Catalogue.getInstance();

            //For each sale in the customer's sales;
            List<Administrator> admins = dataController.getAllAdmins();
            for (Sale uniqueCustSale : uniqueCustSales) {
                //Print the sale to the file with the Sale's to string function.
                writer.write(uniqueCustSale.toString());

                //Gets the entry set to the map in the sale obj.
                //then, for each entry in the set, get the item from the catalogue
                //with the itemID stored in the LineItem in the Entry.
                //Then get the vendor id and use that to get the customer object that belongs to that id.
                //Then print the item name with the username of the vendor that sold it
                Set<Entry<Integer, LineItem>> entrySet = uniqueCustSale.getItemList().entrySet();
                for (Entry<Integer, LineItem> integerLineItemEntry : entrySet) {
                    ItemInfo nextItem = catalogue.getItem(integerLineItemEntry.getValue().getItemID());
                    Customer theVendor = custs.stream()
                            .filter(e -> e.getUserID() == nextItem.getVendorID())
                            .collect(Collectors.toList())
                            .get(0);
                    writer.write("Item " + nextItem.getDisplayName() + " was sold by " + theVendor.getUserName()+"\n");
                }
            }

            writer.write("Store Sales:\n");
            //for each sale in the store's sales
            for (Sale storeSale : storeSales) {
                //Print the sale to the file with the Sale's to string function.
                writer.write(storeSale.toString());

                //Gets the entry set to the map in the sale obj.
                //then, for each entry in the set, get the item from the catalogue
                //with the itemID stored in the LineItem in the Entry.
                //Then print out the item name and that it was sold by the store
                Set<Entry<Integer, LineItem>> entrySet = storeSale.getItemList().entrySet();
                for (Entry<Integer, LineItem> integerLineItemEntry : entrySet) {
                    ItemInfo nextItem = catalogue.getItem(integerLineItemEntry.getValue().getItemID());
                    writer.write("Item " + nextItem.getDisplayName() + " was sold by the store\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
