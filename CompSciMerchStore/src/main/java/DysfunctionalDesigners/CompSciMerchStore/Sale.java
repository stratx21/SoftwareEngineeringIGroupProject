package DysfunctionalDesigners.CompSciMerchStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Sale {
	static final double SHIPPING = 68, TAX = 0.08;//$, %
	private static int nextSaleID = -1;
	private static Logger logger = Logger.getLogger(Sale.class.getName());
	
	private Payment payment;
	private Date dateTime;//finished when finalized
	private int customerID = -1, saleID;
	private Map<Integer, LineItem> itemList;
	private Address shippingAddr;
	private boolean finalized = false;
	
	static {
		try {
			Scanner scan = new Scanner(new File("src/main/resources/NEXT_SALE_ID.txt"));
			nextSaleID = scan.nextInt();
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Sale(int custID) {
		this.customerID = custID;
		this.saleID = nextSaleID++;
		
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File("src/main/resources/NEXT_SALE_ID.txt")));
			bf.write(nextSaleID + "");
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.itemList = new HashMap<Integer, LineItem>();
		this.payment = null;
		this.dateTime = null;
		this.shippingAddr = null;
		logger.info("New sale (id " + this.saleID + ") created for user " + custID);
	}
	
	/**
	 * Jackson Json parser requires this
	 */
	public Sale() {}
	
	/**
	 * This function adds an item to the sale, or increases the amount currently in the cart if it's already present.
	 * 
	 * @param quantity the number of that item to add
	 * @param itemID the item to add
	 * @return if the add was successful (if the quantity was within the bounds of the stock)
	 */
	public boolean addItem(int quantity, int itemID) {
		boolean success = false;
		if(!this.finalized) {
			if(itemList.containsKey(itemID)) {
				if(Catalogue.getInstance().checkAmount(itemID, quantity + itemList.get(itemID).getQuantity())) {
					itemList.get(itemID).addSome(quantity);
					success = true;
					logger.info("SALE " + this.saleID + " of user " + this.customerID + ": Increased quantity of " + itemID + " to " + quantity);
				}
			} else {
				if(Catalogue.getInstance().checkAmount(itemID, quantity)){
					itemList.put(itemID, new LineItem(quantity, itemID));
					success = true;
					logger.info("SALE " + this.saleID + " of user " + this.customerID + ": Added " + quantity  + " " + itemID + "s (new item)");
				}
			}
		}
		return success;
	}
	
	/**
	 * This function allows the removal of an item from the sale
	 * 
	 * @param itemID the item to remove
	 */
	public void removeItem(int itemID) {
		if(!this.finalized && itemList.containsKey(itemID)) {
			itemList.remove(itemID);
			logger.info("SALE " + this.saleID + " of user " + this.customerID + ": Removed " + itemID + " from sale");
		}
	}
	
	/**
	 * Allows the editing of the quantity of one of the line items.
	 * 
	 * @param itemID the item to edit
	 * @param newQuantity the new amount to add
	 * @return if the edit was successful (if the quantity is in bounds of the stock)
	 */
	public boolean editQuantity(int itemID, int newQuantity) {
		if(!this.finalized && Catalogue.getInstance().checkAmount(itemID, newQuantity)) {
			itemList.get(itemID).setQuantity(newQuantity);
			logger.info("SALE " + this.saleID + " of user " + this.customerID + ": Changed quantity of " + itemID + " to " + newQuantity);
			if(newQuantity == 0) {
				itemList.remove(itemID);
				logger.info("SALE " + this.saleID + " of user " + this.customerID + ": REMOVED " + itemID);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Allows a promo code to be added to the sale, if it exists.
	 * 
	 * @param code the promo code to add
	 */
	public void applyPromoCode(String code) {
		if(!this.finalized) {// && this.checkPromoCode(code)
			//guaranteed to be the right length
			logger.info("SALE " + this.saleID + " of user " + this.customerID + ": Attempting to add promo code " + code + " to any items in sale that can");
			for(Integer i : this.itemList.keySet()) {
				this.itemList.get(i).addPromoCode(code);
			}
		}
	}
	
	/**
	 * Checks to see if a promo code is valid.
	 * 
	 * @param code the code to check
	 * @return if the code is valid
	 */
	public boolean checkPromoCode(String code) {
		for(Entry<Integer, LineItem> i : this.itemList.entrySet()){
			if(Catalogue.getInstance().getItem(i.getValue().getItemID()).hasPromoCode(code)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the payment amount is above or at the total amount for the purchase
	 * 
	 * @return if the payment is sufficient
	 */
	public boolean checkPaymentAmount() {
		return (this.payment != null && payment.getAmount() >= this.getTotalWithTax());//if they want to give us more, sure!
	}
	
	/**
	 * @return the total without tax or shipping
	 */
	@JsonIgnore
	public double getTotalWithoutTax() { 
		double total = 0;
		
		for(LineItem li : itemList.values()) {
			total += li.getTotalPrice();
		}
		
		return total;
	}

	
	/**
	 * @return the estimated tax to be collected
	 */
	@JsonIgnore
	public double getEstimatedTax() {
		return this.getTotalWithoutTax()*TAX;
	}
	
	/**
	 * @return the total for the sale with tax and shipping
	 */
	@JsonIgnore
	public double getTotalWithTax() {
		double total = this.getTotalWithoutTax();
		return total + total*TAX + SHIPPING;
	}
	
	/**
	 * @return the total number of items in the sale
	 */
	@JsonIgnore
	public int getNumItems() {
		return itemList.values().stream().mapToInt(e -> e.getQuantity()).reduce(0, Integer::sum);
	}
	
	/**
	 * @return the number of unique items in the sale
	 */
	@JsonIgnore
	public int getNumUniqueItems() { return this.itemList.size(); }
	
	/**
	 * Finalizes the sale and prevents anything else from being changed in the class.
	 * 
	 * @return if the payment is enough or if the sale was already finalized
	 */
	public boolean finalizePayment(){
		if(!this.finalized && this.checkPaymentAmount() && this.shippingAddr != null && this.customerID != -1) {
			logger.fine("SALE " + this.saleID + " of user " + this.customerID + ": FINIALIZING PAYMENT");
			this.finalized = true;
			//decrease quantity from catalogue
			for(LineItem li : this.itemList.values()) {
				logger.fine("SALE " + this.saleID + " of user " + this.customerID + ": Decreasing item " + li.getItemID() + " by " + li.getQuantity());
				Catalogue.getInstance().decreaseQuantity(li.getItemID(), li.getQuantity());
			}
			this.dateTime = new Date();
			
			//add sale to vendor who has it
			UserDataController data = UserDataController.getInstance();
			List<Administrator> admins = data.getAllAdmins();
			List<Customer> cust = data.getAllCustomers();
			Catalogue c = Catalogue.getInstance();
			Set<Administrator> adminsChanged = new HashSet<>();
			Set<Customer> custChanged = new HashSet<>();
			
			logger.fine("SALE " + this.saleID + " of user " + this.customerID + ": Adding this sale to the vendors that sold it.");
			this.itemList.entrySet().stream().forEach(e -> {
				String id = (c.getItem(e.getValue().getItemID()).getVendorID() + "");
				switch(id.charAt(0)) {
					case '4':
						Administrator a = admins.stream()
											    .filter(ads -> (ads.getUserID()+"").compareTo(id) == 0)
												.collect(Collectors.toList()).get(0);
						if(a != null) {
							//a is a reference, at the end, write all changed users back to file
							a.addNewSale(this);
							adminsChanged.add(a);
						} else {
							logger.severe("SALE " + this.saleID + " of user " + this.customerID + ": COULDN'T FIND ADMINISTRATOR \'" + id + "\'");
						}
						break;
					case '1':
						Customer a1 = cust.stream()
										 .filter(ads -> (ads.getUserID()+"").compareTo(id) == 0)
										 .collect(Collectors.toList()).get(0);
						if(a1 != null) {
							//a is a reference, at the end, write all changed users back to file
							a1.addNewSale(this);
							custChanged.add(a1);
						} else {
							logger.severe("SALE " + this.saleID + " of user " + this.customerID + ": COULDN'T FIND CUSTOMER \'" + id + "\'");
						}
						break;
					default:
						//why are we even here
						logger.severe("SALE " + this.saleID + " of user " + this.customerID + ": COULDN'T FIND USER WITH IMPROPER ID \'" + id + "\'");
				}
			});
			
			adminsChanged.stream().forEach(e -> data.writeAdmin(e));
			custChanged.stream().forEach(e -> data.writeCustomer(e));
			/////////here is where we would add some of the database and remote
			/////////stuff if this was a real online store
		}
		return this.finalized;
	}
	
	////////getters and setters
	public void setPayment(Payment payment) { if(!this.finalized) this.payment = payment; }
	public void setShippingAddr(Address shippingAddr) { if(!this.finalized) this.shippingAddr = shippingAddr; }

	public Payment getPayment() { return payment; }
	public Date getDateTime() { return dateTime; }
	public int getCustomerID() { return customerID; }
	public int getSaleID() { return saleID; }
	public Map<Integer, LineItem> getItemList() { return itemList; }
	public static double getShipping() { return SHIPPING; }
	public static double getTax() { return TAX; }
	public Address getShippingAddr() { return shippingAddr; }
	public boolean getFinalized() { return finalized; }

    @Override
    public String toString() {
        String toReturnStr = "Sale Number " + getSaleID() + "::\n";
        toReturnStr += "Payment Info: " + getPayment() + "\n";
        toReturnStr += "Date of Purchase: " + getDateTime() + "\n";
        toReturnStr += "Customer ID: " + getCustomerID() + "\n";
        toReturnStr += "Shipping Cost: " + getShipping() + "\n";
        toReturnStr += "Tax Amount: " + getTax() + "\n";
        toReturnStr += "Shipping Address: " + getShippingAddr() + "\n";
        toReturnStr += "Item List: \n";
        Map<Integer, LineItem> items = getItemList();

        Set<Entry<Integer,LineItem>> entrySet = items.entrySet();
        int itemNum = 1;
        StringBuilder builder = new StringBuilder(toReturnStr);
        Catalogue thisInstance = Catalogue.getInstance();
        for (Entry<Integer, LineItem> integerLineItemEntry : entrySet) {
            LineItem next = integerLineItemEntry.getValue();
            builder.append("Line Item ").append(itemNum).append(":\n");
            builder.append("\t").append("Item ID and Name: ").append(next.getItemID()).append(", ")
                    .append(thisInstance.getItem(next.getItemID()).getDisplayName()).append("\n");
            builder.append("\tItem Quantity: ").append(next.getQuantity()).append("\n");
            itemNum++;
        }

        toReturnStr = builder.toString();
        return toReturnStr;
    }
}
