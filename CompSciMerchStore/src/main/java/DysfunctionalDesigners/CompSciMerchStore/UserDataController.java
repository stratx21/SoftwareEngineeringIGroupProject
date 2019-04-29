package DysfunctionalDesigners.CompSciMerchStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserDataController {
	private static Logger logger = Logger.getLogger(UserDataController.class.getName());
    private static UserDataController instance = null;

    /**
     * Gets an instance of the UserDataController. Double locked  to protect in multithreading
     * @return An instance of the user data controller
     */
    public static UserDataController getInstance() {
        logger.info("Returning instance of UserDataController");
        if(instance == null) {
            logger.info("UserDataController not yet initialized.");
            synchronized (UserDataController.class) {
                if(instance == null) {
                    logger.info("Initializing UserDataController");
                    instance = new UserDataController();
                }
            }
        }
        return instance;
    }

    /**
     * Empty default constructor for jackson to use for JSON
     */
    protected UserDataController() {}

    /**
     * Gets a list of User objects generated from the specified users registered to the store who's
     * account info is stored in our files.
     * @param usernames The usernames of the users that are to be returned
     * @return a list of user objects created from the info files for each user given
     */
    public List<User> getCustomers(List<String> usernames) {
        logger.info("Fetching customers with names: " + usernames);
        List<User> users = new ArrayList<>();
        List<String> validCustomerUsernames = getCustomerUsernames();

        //Filter to only usernames existing in the usernames.txt file
        usernames = usernames.stream()
                .filter(e -> validCustomerUsernames.contains(e))
                .collect(Collectors.toList());

        //For each username, go to the user file and deserialize rom JSON to a Customer Object
        //and add it to the list of User objects
        for (String username : usernames) {
            ObjectMapper mapper = new ObjectMapper();
            Customer nextCust = null;
            File usrFile = new File(App.resourceTarget + "UserData/" + username + ".json");

            try {
                nextCust = mapper.readValue(usrFile, Customer.class);
            } catch (IOException e) {
                logger.severe("Failure to deserialize from JSON user: " + username);
                e.printStackTrace();
            }

            users.add(nextCust);
        }

        logger.info("Requested users successfully retrieved");
        return users;
    }

    /**
     * Gets the admins specified by the list given and returns a list of admin objects generated
     * from the info in the user files found
     * @param usernames the list of usernames of the admins to fetch the info of
     * @return A list of admin objects of the users specified
     */
    public List<User> getAdmins(List<String> usernames) {
        logger.info("Fetching admins with names: " + usernames);
        List<User> users = new ArrayList<>();
        List<String> validAdminUsernames = getAdminUsernames();

        //Filter to only usernames existing in the usernames.txt file
        usernames = usernames.stream()
                .filter(e -> validAdminUsernames.contains(e))
                .collect(Collectors.toList());

        //For each username, go to the user file and deserialize rom JSON to a Customer Object
        //and add it to the list of User objects
        for (String username : usernames) {
            ObjectMapper mapper = new ObjectMapper();
            Administrator nextCust = null;
            File usrFile = new File(App.resourceTarget + "UserData/" + username + ".json");

            try {
                nextCust = mapper.readValue(usrFile, Administrator.class);
            } catch (IOException e) {
                logger.severe("Failure to deserialize from JSON admin: " + username);
                e.printStackTrace();
            }

            users.add(nextCust);
        }

        logger.info("Requested admins successfully retrieved");
        return users;
    }

    /**
     * Gets all the sales of the user given
     * @param username The username of the user who's sales info is being retrieved
     * @return A list of sale objects of the given user's sale info
     * @throws Exception if the user given is not found an exception is thrown
     */
    public List<Sale> getUserSales(String username) throws Exception {
        logger.info("Getting past sales of user: " + username);
        List<String> validUsernames = getCustomerUsernames();
        List<Sale> pastSalesOfUser;

        if(!validUsernames.contains(username)) {
            logger.severe("Username supplied is not a valid username and does not not exist in our records");
            throw new Exception("Username is either not a valid customer or an admin");
        }

        ObjectMapper mapper = new ObjectMapper();
        Customer cust = null;
        cust = mapper.readValue(new File(App.resourceTarget + "UserData/" + username + ".json"),
                Customer.class);
        pastSalesOfUser = cust.getPastSales();

        logger.info("User " + username + "'s past sales successfully retrieved");
        return pastSalesOfUser;
    }

    /**
     * Gets all sales info of items that the store was selling and not ones that a vendor user had uploaded.
     * (Pretty much all items that start on the store or are uploaded by admins)
     * @return a list of sale objects
     */
    public List<Sale> getStoreSales() {
        logger.info("Fetching all past sales made by the store");
        List<Sale> storeSales = new ArrayList<>();
        List<String> validAdmins = getAdminUsernames();

        validAdmins.forEach(e -> {
            File nxtAdminFile = new File(App.resourceTarget + "UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Administrator nextAdmin = null;
            try {
                nextAdmin = mapper.readValue(nxtAdminFile, Administrator.class);
            } catch (IOException ex) {
                logger.severe("Failure to deserialize " + e + " from JSON");
                ex.printStackTrace();
            }
            storeSales.addAll(nextAdmin.getPastSales());
        });

        logger.info("All store sales successfully retrieved");
        return storeSales;
    }

    /**
     * Gets all the admins registered to the store
     * @return A list of all admins
     */
    public List<Administrator> getAllAdmins() {
        logger.info("Getting all admins registered in our records");
        List<Administrator> admins = new ArrayList<>();
        List<String> validAdmins = getAdminUsernames();

        validAdmins.forEach(e -> {
            File nxtAdminFile = new File(App.resourceTarget + "UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Administrator nextAdmin = null;
            try {
                nextAdmin = mapper.readValue(nxtAdminFile, Administrator.class);
            } catch (IOException ex) {
                logger.severe("Failure to deserialize admin: " + e + ", during retrieval of all admins");
                ex.printStackTrace();
            }
            admins.add(nextAdmin);
        });

        logger.info("All admins successfully retrieved");
        return admins;
    }

    /**
     * Gets all customers registered to the store
     * @return a list of all the users registered
     */
    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers in our records");
        List<Customer> customers = new ArrayList<>();
        List<String> validCustomers = getCustomerUsernames();

        validCustomers.forEach(e -> {
            File nxtCustFile = new File(App.resourceTarget + "UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Customer nextCust = null;
            try {
                nextCust = mapper.readValue(nxtCustFile, Customer.class);
            } catch (IOException ex) {
                logger.severe("Failure to deserialize Customer: " + e + ", from JSON");
                ex.printStackTrace();
            }
            customers.add(nextCust);
        });

        logger.info("All customers successfully retrieved");
        return customers;
    }

    /**
     * Gets all usernames of the customers
     * @return a list of all registered customer usernames
     */
    public List<String> getCustomerUsernames() {
        logger.info("Fetching all customer usernames");
        List<String> usernames = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(App.resourceTarget + "UserData/customers.txt"))));
            addNamesToList(reader, usernames);
        } catch (FileNotFoundException ex) {
            logger.severe("Failure to open reader for customers file. In Method getCustomerUsernames. Severe Failure, exiting...");
            ex.printStackTrace();
            System.exit(1);
        }

        logger.info("All customer usernames successfully retrieved");
        return usernames;
    }

    /**
     * gets the individual usernames and passwords from the customers file
     * @param reader The reader to read through the file
     * @param usernames A list to store the usernames into
     * @param passwords a list to store the passwords into
     */
    public void getUsernamesAndPasswords(BufferedReader reader, List<String> usernames, List<String> passwords) {
        logger.info("Fetching all usernames and passwords from file opened in reader");
    	String line;
        try {
//        	BufferedReader reader = new BufferedReader(new FileReader(new File("/src/main/resources/UserData/customers.txt")));
            while((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                if(split.length != 2) {
                    continue;
                }
                usernames.add(split[0]);
                passwords.add(split[1]);
            }
            reader.close();
        } catch (IOException e) {
            logger.severe("IOException thrown in getUsernamesAndPasswords method in UserDataController");
            e.printStackTrace();
        }
    }

    /**
     * Gets all admin usernames
     * @return a list of all usernames associated with admins
     */
    public List<String> getAdminUsernames() {
        logger.info("Fetching all admin usernames");
        List<String> usernames = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(App.resourceTarget + "UserData/admins.txt"))));
        } catch (FileNotFoundException ex) {
            logger.severe("Failure to open reader for admins file in getAdminUsernames. Severe Failure, exiting...");
            ex.printStackTrace();
            System.exit(1);
        }

        addNamesToList(reader, usernames);

        logger.info("All admin usernames successfully retrieved");
        return usernames;
    }

    /**
     * Writes the admin object given to its file. Updates the file if info is changed, creates a new file if
     * the admin file does not exist yet
     * @param adminToWrite the admin object to write/update
     */
    public void writeAdmin(Administrator adminToWrite) {
        logger.info("Writing/updating admin: " + adminToWrite.getUserName());
        File adminFile = new File(App.resourceTarget + "UserData/" + adminToWrite.getUserName() + ".json");
        try {
            adminFile.createNewFile(); //creates  new file if it doesn't exist
        } catch (IOException e) {
            logger.severe("Failure to create new admin File for admin: " + adminToWrite.getUserName());
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(adminFile, adminToWrite);
        } catch (IOException e) {
            logger.severe("Failure to serialize to JSON while attempting to write admin: " + adminToWrite.getUserName());
            e.printStackTrace();
        }
    }

    /**
     * Writes the customer object given to its file. Updates the file if info is changed, creates a new file if
     * the customer file does not exist yet.
     * @param custToWrite the customer object to write/update
     */
    public void writeCustomer(Customer custToWrite) {
        logger.info("Writing/Updating Customer: " + custToWrite.getUserName());
        File custFile = new File(App.resourceTarget + "UserData/" + custToWrite.getUserName() + ".json");
        try {
            custFile.createNewFile(); //creates  new file if it doesn't exist
        } catch (IOException e) {
            logger.severe("Failure to create new customer file for User: " + custToWrite.getUserName());
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(custFile, custToWrite);
        } catch (IOException e) {
            logger.severe("Failure to serialize to JSON while attempting to write User: " + custToWrite.getUserName());
            e.printStackTrace();
        }
    }

    private void addNamesToList(BufferedReader reader, List<String> listToAddTo) {
        String line;
        try {
            while((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                if(split.length > 2) {
                    continue;
                }
                listToAddTo.add(split[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
