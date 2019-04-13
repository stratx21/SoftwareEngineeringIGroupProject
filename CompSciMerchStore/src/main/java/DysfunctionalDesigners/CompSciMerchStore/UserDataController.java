package DysfunctionalDesigners.CompSciMerchStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataController {
    private static UserDataController instance = null;

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

    public List<Administrator> getAdmins() {
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

    public List<String> getCustomerUsernames() {
        List<String> usernames = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File("./src/main/resources/UserData/customers.txt"))));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        addNamesToList(reader, usernames);

        return usernames;
    }

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
