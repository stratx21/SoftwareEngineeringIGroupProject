package DysfunctionalDesigners.CompSciMerchStore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Administrator extends Vendor{
	private static Logger logger = Logger.getLogger(Administrator.class.getName());

    /**
     * Constructor allowed to take a string array of all the values needed
     * @param d The string array containing the needed values for construction
     */
    public Administrator(String[] d) {
        super(d);
        
    }

    /**
     * Full Constructor in the case of needing to individually feed the values in.
     * Used by Jackson for JSON serialization
     * @param email email
     * @param motherMaidenName mother's maiden name
     * @param userName The username associated with this admin account
     * @param password the password for this account
     * @param name The real name of the user
     * @param userID the User's id number
     * @param uploadedItems items previously uploaded to the store by the user
     * @param pastSales previous sales made
     */
    public Administrator(String email, String motherMaidenName, String userName, String password, String name, int userID, List<Integer> uploadedItems, List<Sale> pastSales) {
        super(email, motherMaidenName, userName, password, name, userID, uploadedItems, pastSales);
    }

    /**
     * Default constructor also used by Jackson for JSON
     */
    public Administrator() {
    	super();
    }

    /**
     * Removes the item with the associated ID from the catalogue
     * @param id The item id to be removed.
     */
    public void removeItemFromCatalogue(int id) {
        Catalogue.getInstance().removeItem(id);
    }

    //public void disableItem(int id) {}

    //TODO: When we decide how complaints are written. Write this

    /**
     * Returns all the complaints made that have been written to the file containing all complaints
     * @return A string array of the complaints
     */
    //public String[] getAllComplaints() {

    //}

    /**
     * A method that generates a report of all sales made on the store and writes it to a file
     * @return Returns true if the report was successfully generated
     */
    public boolean generateAllSalesReport() {
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
            return false;
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
            return false;
        }
        return true;
    }

    /**
     * Generates a report on some info about all users on the store. Also gives some info on certain users
     * who had significant usage.
     * @return Returns true if the report was generated successfully.
     */
    public boolean generateAllUsersReport() {
        UserDataController dataController = UserDataController.getInstance();
        BufferedWriter writer = null;
        File userReportFile = new File("./src/main/resources/reports/usersReport.txt");
        int numberOfUsers, numberOfAdmins;


        try {
            userReportFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            writer = new BufferedWriter(new FileWriter(userReportFile));

            Path userFile = Paths.get("./src/main/resources/UserData/customers.txt");
            Path adminPath = Paths.get("./src/main/resources/UserData/admins.txt");
            numberOfUsers = (int)Files.lines(userFile).count();
            numberOfAdmins = (int)Files.lines(adminPath).count();

            writer.write("Number of Users/Customers: " + numberOfUsers + "\n");
            writer.write("Number of admins: " + numberOfAdmins + "\n");

            List<String> allUsernames = new ArrayList<>();
            allUsernames.addAll(dataController.getCustomerUsernames());
            allUsernames.addAll(dataController.getAdminUsernames());
            Collections.sort(allUsernames);

            writer.write("All Usernames: \n");
            for (String username : allUsernames) {
                writer.write("\t" + username + "\n");
            }

            //Calculate user with the most purchases
            Customer custWithMostPurchases = null;
            List<Customer> allCustomerObjects = dataController.getAllCustomers();
            List<Integer> numberOfPurchasesPerCust = allCustomerObjects.stream()
                    .map(e -> e.getPreviousPurchases().size())
                    .collect(Collectors.toList());
            int biggestAmount = Collections.max(numberOfPurchasesPerCust);
            custWithMostPurchases = allCustomerObjects.get(numberOfPurchasesPerCust.indexOf(biggestAmount));

            writer.write("User with the most Purchases: " + custWithMostPurchases.getUserName() + "\n");

            //TODO:Find vendor with most uploaded items

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
