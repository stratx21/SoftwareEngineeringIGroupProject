package DysfunctionalDesigners.CompSciMerchStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
            for (Sale uniqueCustSale : uniqueCustSales) {
                writer.write(uniqueCustSale.toString());
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
