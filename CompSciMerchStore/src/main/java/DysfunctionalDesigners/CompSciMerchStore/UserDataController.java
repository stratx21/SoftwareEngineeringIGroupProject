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
        if(instance == null) {
            synchronized (UserDataController.class) {
                if(instance == null) {
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
            File usrFile = new File("./src/main/resources/UserData/" + username + ".json");

            try {
                nextCust = mapper.readValue(usrFile, Customer.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            users.add(nextCust);
        }

        return users;
    }

    /**
     * Gets the admins specified by the list given and returns a list of admin objects generated
     * from the info in the user files found
     * @param usernames the list of usernames of the admins to fetch the info of
     * @return A list of admin objects of the users specified
     */
    public List<User> getAdmins(List<String> usernames) {
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
            File usrFile = new File("./src/main/resources/UserData/" + username + ".json");

            try {
                nextCust = mapper.readValue(usrFile, Administrator.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            users.add(nextCust);
        }

        return users;
    }

    /**
     * Gets all the sales of the user given
     * @param username The username of the user who's sales info is being retrieved
     * @return A list of sale objects of the given user's sale info
     * @throws Exception if the user given is not found an exception is thrown
     */
    public List<Sale> getUserSales(String username) throws Exception {
        List<String> validUsernames = getCustomerUsernames();
        List<Sale> pastSalesOfUser;

        if(!validUsernames.contains(username)) {
            throw new Exception("Username is either not a valid customer or an admin");
        }

        ObjectMapper mapper = new ObjectMapper();
        Customer cust = null;
        cust = mapper.readValue(new File("./src/main/resources/UserData/" + username + ".json"),
                Customer.class);
        pastSalesOfUser = cust.getPastSales();

        return pastSalesOfUser;
    }

    /**
     * Gets all sales info of items that the store was selling and not ones that a vendor user had uploaded.
     * (Pretty much all items that start on the store or are uploaded by admins)
     * @return a list of sale objects
     */
    public List<Sale> getStoreSales() {
        List<Sale> storeSales = new ArrayList<>();
        List<String> validAdmins = getAdminUsernames();

        validAdmins.forEach(e -> {
            File nxtAdminFile = new File("./src/main/resources/UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Administrator nextAdmin = null;
            try {
                nextAdmin = mapper.readValue(nxtAdminFile, Administrator.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            storeSales.addAll(nextAdmin.getPastSales());
        });

        return storeSales;
    }

    /**
     * Gets all the admins registered to the store
     * @return A list of all admins
     */
    public List<Administrator> getAllAdmins() {
        List<Administrator> admins = new ArrayList<>();
        List<String> validAdmins = getAdminUsernames();

        validAdmins.forEach(e -> {
            File nxtAdminFile = new File("./src/main/resources/UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Administrator nextAdmin = null;
            try {
                nextAdmin = mapper.readValue(nxtAdminFile, Administrator.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            admins.add(nextAdmin);
        });

        return admins;
    }

    /**
     * Gets all customers registered to the store
     * @return a list of all the users registered
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<String> validCustomers = getCustomerUsernames();

        validCustomers.forEach(e -> {
            File nxtCustFile = new File("./src/main/resources/UserData/" + e + ".json");
            ObjectMapper mapper = new ObjectMapper();
            Customer nextCust = null;
            try {
                nextCust = mapper.readValue(nxtCustFile, Customer.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            customers.add(nextCust);
        });

        return customers;
    }

    /**
     * Gets all usernames of the customers
     * @return a list of all registered customer usernames
     */
    public List<String> getCustomerUsernames() {
        List<String> usernames = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("./src/main/resources/UserData/customers.txt"))));
            addNamesToList(reader, usernames);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return usernames;
    }

    /**
     * gets the individual usernames and passwords from the customers file
     * @param reader The reader to read through the file
     * @param usernames A list to store the usernames into
     * @param passwords a list to store the passwords into
     */
    public void getUsernamesAndPasswords(BufferedReader reader, List<String> usernames, List<String> passwords) {
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
            e.printStackTrace();
        }
    }

    /**
     * Gets all admin usernames
     * @return a list of all usernames associated with admins
     */
    public List<String> getAdminUsernames() {
        List<String> usernames = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("./src/main/resources/UserData/admins.txt"))));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        addNamesToList(reader, usernames);

        return usernames;
    }

    /**
     * Writes the admin object given to its file. Updates the file if info is changed, creates a new file if
     * the admin file does not exist yet
     * @param adminToWrite the admin object to write/update
     */
    public void writeAdmin(Administrator adminToWrite) {
        File adminFile = new File("./src/main/resources/UserData/" + adminToWrite.getUserName() + ".json");
        try {
            adminFile.createNewFile(); //creates  new file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(adminFile, adminToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the customer object given to its file. Updates the file if info is changed, creates a new file if
     * the customer file does not exist yet.
     * @param custToWrite the customer object to write/update
     */
    public void writeCustomer(Customer custToWrite) {
        File custFile = new File("./src/main/resources/UserData/" + custToWrite.getUserName() + ".json");
        try {
            custFile.createNewFile(); //creates  new file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(custFile, custToWrite);
        } catch (IOException e) {
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
