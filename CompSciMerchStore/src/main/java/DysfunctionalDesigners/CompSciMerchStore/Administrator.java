package DysfunctionalDesigners.CompSciMerchStore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Administrator extends Vendor{
	private static Logger logger = Logger.getLogger(Administrator.class.getName());

    /**
     * Constructor allowed to take a string array of all the values needed
     * @param d The string array containing the needed values for construction
     */
    public Administrator(String[] d) {
        super(d);
        logger.info("Creating Admin Object");
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
        logger.info("Creating Admin Object");
    }

    /**
     * Default constructor also used by Jackson for JSON
     */
    public Administrator() {
    	super();
        logger.info("Creating Admin Object");
    }

    /**
     * Removes the item with the associated ID from the catalogue
     * @param id The item id to be removed.
     */
    public void removeItemFromCatalogue(int id) {
        logger.info("Removing item " + id + " from catalogue. Initiated by admin: " + this.getUserName());
        Catalogue.getInstance().removeItem(id);
    }

    //public void disableItem(int id) {}

    /**
     * Returns all the complaints made that have been written to the file containing all complaints
     * @return A string array of the complaints
     */
    public String[] getAllComplaints() {
        logger.info("Getting all user complaints");
        UserDataController dataController = UserDataController.getInstance();
        List<String> complaints = new ArrayList<>();
        List<Customer> customers = dataController.getAllCustomers();

        BufferedReader reader;
        try {
            StringBuilder fileText = new StringBuilder();
            logger.info("Attempting to open reader for complaints file");
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("./src/main/resources/complaints.txt"))));
            logger.info("Reader successfully opened");
            String line;
            while((line = reader.readLine()) != null) {
                fileText.append(line).append(" ");
            }
            reader.close();
            logger.info("Reader successfully closed");

            String fullFile = fileText.toString();
            String[] allComplaints = fullFile.split("[0-9]{5}:\\Q|\\E:");
            //(?=.*[0-9]{5}:\\Q|\\E:)"

            //Temp list to get rid of any possible spaces left as their own elements in the array.
            //Filters then collects back to list. Then list put back to array.
            List<String> tempList = Arrays.asList(allComplaints);
            tempList = tempList.stream().filter(e -> !e.equals("")).collect(Collectors.toList());
            allComplaints = tempList.toArray(new String[0]);

            //Gets all ids for the complaints based on regex pattern that matches the ID pattern
            List<String> allIds = new ArrayList<>();
            Pattern pat = Pattern.compile("[0-9]{5}:\\Q|\\E:");
            Matcher m = pat.matcher(fullFile);
            while(m.find()) {
                allIds.add(m.group());
            }
            //Gets only the number part of the string out of a format like this -> "00000:|:"
            allIds = allIds.stream().map(e -> e.substring(0, e.indexOf(':'))).collect(Collectors.toList());

            //Get the usernames associated with the ids
            List<String> usernames = new ArrayList<>();
            allIds.forEach( e -> {
                for (Customer customer : customers) {
                    if(customer.getUserID() == Integer.valueOf(e)) {
                        usernames.add(customer.getUserName());
                        break; //ensures this is only run once per id in the allIds list
                    }
                }
            });

            //Put the id and associated complaint together and add to the final list
            for(int i = 0; i < allComplaints.length; i++) {
                complaints.add(usernames.get(i) + ": " + allComplaints[i]);
            }
        } catch (IOException e) {
            logger.severe("IOException thrown while fetching all complaints. get complaints failed");
            e.printStackTrace();
        }

        logger.info("Fetching all User Complaints: Success");
        return complaints.toArray(new String[0]);
    }

    /**
     * A method that generates a report of all sales made on the store and writes it to a file
     * @return Returns true if the report was successfully generated
     */
    public boolean generateAllSalesReport() {
        logger.info("Generating a Report of all Sales");
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
                logger.severe("Failure to fetch " + e.getUserName() + "'s sales");
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
            logger.severe("Failure to create new salesReport file");
            e.printStackTrace();
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(salesReportFile));
            logger.info("Writing Customer Sales to Sales Report");
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
            logger.info("Writing Customer Sales to Report: SUCCESS");

            logger.info("Writing All Store Sales to Sales Report");
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
            logger.info("Writing Store Sales to Report: SUCCESS");
            writer.close();
            logger.info("Writer Closed");
        } catch (IOException e) {
            logger.severe("IOException thrown. Possible failure to open Writer");
            e.printStackTrace();
            return false;
        }
        logger.info("All Sales Report Generation: SUCCESS");
        return true;
    }

    /**
     * Generates a report on some info about all users on the store. Also gives some info on certain users
     * who had significant usage.
     * @return Returns true if the report was generated successfully.
     */
    public boolean generateAllUsersReport() {
        logger.info("Generating a report of all Users");
        UserDataController dataController = UserDataController.getInstance();
        BufferedWriter writer = null;
        File userReportFile = new File("./src/main/resources/reports/usersReport.txt");
        int numberOfUsers, numberOfAdmins;

        try {
            userReportFile.createNewFile();
        } catch (IOException e) {
            logger.severe("Failure to create new usersReport file");
            e.printStackTrace();
            return false;
        }
        try {
            writer = new BufferedWriter(new FileWriter(userReportFile));

            logger.info("Writing info to Report");
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
            logger.severe("IOException thrown. Possible failure to open writer");
            e.printStackTrace();
            return false;
        }
        logger.info("All Users Report Generation: SUCCESS");
        return true;
    }
}
