package DysfunctionalDesigners.CompSciMerchStore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Catalogue {//all should be static as the top-level class unfortunately cannot be
	private Map<Integer, ItemInfo> catalogue = new HashMap<Integer, ItemInfo>();
	private int numItems = 0;
	private static Catalogue instance = null;
	
	public static Catalogue getInstance() {
		if(instance == null) {
			synchronized (Catalogue.class) {
				if(instance == null) {
					instance = new Catalogue();
				}
            }
		}
		return instance;
	}
	
	protected Catalogue() {
		try{
//    		instance.readFile(new BufferedReader(new FileReader(new File("src/main/resources/catalogue.txt"))));
			this.readJSONFile();
    	} catch(Exception e) {
    		System.out.println("Error importing the Catalogue: ");
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Reads the catalogue from a file and sets the next itemid to add.
	 * @param bf the file to read from
	 * @throws Exception if it can't read from the catalogue
	 */
	private void readFile(BufferedReader bf) throws Exception {
		String line;
		String[] splitLine;
		try {
			line = bf.readLine();
			numItems = Integer.parseInt((line.split(" "))[1]);
			ItemInfo.setNextID(numItems);
			
			while((line = bf.readLine()) != "" && line != null) {
				splitLine = line.split(", ");
				int id = Integer.parseInt(splitLine[0]);
				catalogue.put(id, new ItemInfo(splitLine[2], splitLine[1], Integer.parseInt(splitLine[3]), 
							  Integer.parseInt(splitLine[4]), Double.parseDouble(splitLine[5]), id, Professor.valueOf(splitLine[6])));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Couldn't read catalogue from file stack trace");
		}		
	}
	
	/**
	 * Reads the catalogue from a JSON file and sets the next itemid to add.
	 * @throws Exception if the json isn't formatted correctly
	 */
	private void readJSONFile() throws Exception {
		//get the next number
		Scanner scan = new Scanner(new File("src/main/resources/NEXT_ID.txt"));
		int nextNum = scan.nextInt();
		ItemInfo.setNextID(nextNum);
		scan.close();
		
		ObjectMapper mapper = new ObjectMapper();

		this.catalogue = null;
        //User readAdmin2 = null;
        File in = new File("src/main/resources/catalogue.json");
        try {
        	catalogue = mapper.readValue(in, new TypeReference<HashMap<Integer, ItemInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        
      //pretty print
//        String readAdminString = "";
//        try {
//            readAdminString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(catalogue);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Printing catalogue read from json file");
//        System.out.println(readAdminString);
	}
	
	public void catalogueToJSON() {
		if(this.catalogue != null) {
			ObjectMapper mapper = new ObjectMapper();
	
	        try {
	            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/catalogue.json"), this.catalogue);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        try {
				BufferedWriter bf = new BufferedWriter(new FileWriter(new File("src/main/resources/NEXT_ID.txt")));
				bf.write(ItemInfo.getNEXTID() + "");
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	//		String jsonString = "";
	//        try {
	////            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testAdmin);
	//            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.catalogue);
	//        } catch (JsonProcessingException e) {
	//            e.printStackTrace();
	//        }
	//        System.out.println(jsonString);
	//		return jsonString;
		} else {
			//TODO: LOG IT UNDER SEVERE
		}
	}
	
//////////////////modifiers
	/**
	 * Adds an item (or replaces an old one) to the catalogue
	 * 
	 * @param e the item to add
	 */
	public void addItem(ItemInfo e) {
		if(catalogue.put(e.getItemID(), e) == null) {
			numItems++;
		}
	}
	
	/**
	 * Removes an item from the catalogue if present.  Actually just disables it because previous sales depend on it.
	 * 
	 * @param itemID the item to remove
	 */
	public void removeItem(int itemID) {
		disableItem(itemID);
//		if(catalogue.remove(itemID) != null) {
//			numItems--;
//		}
	}
	
	/**
	 * Decreases the quantity of the given item if the amount is valid.
	 * 
	 * @param itemID the item to decrease
	 * @param amount the amount to decrease by
	 */
	public void decreaseQuantity(int itemID, int amount) {
		if(checkAmount(itemID, amount)) {
			try {
				catalogue.get(itemID).decreaseAmount(amount);
			} catch (Exception e) {
				System.err.println("ERROR: checkAmount failed");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Increases the quantity of the given item if the amount is valid.
	 * 
	 * @param itemID the item to increase
	 * @param amount the amount to increase by
	 */
	public void increaseQuantity(int itemID, int amount) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).increaseAmount(amount);
		}
	}
	
	/**
	 * Disables the item
	 * @param itemID the item to disable
	 */
	public void disableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).disable();
		}
	}
	
	/**
	 * Enables the item
	 * @param itemID the item to enable
	 */
	public void enableItem(int itemID) {
		if(catalogue.containsKey(itemID)) {
			catalogue.get(itemID).enable();
		}
	}
	
	/**
	 * 
	 * @param itemID
	 * @param info
	 * @throws Exception
	 */
	public void updateItem(int itemID, ItemInfo info) throws Exception {
		if(info.getItemID() != itemID) {
			throw new Exception("New ItemInfo ID does NOT match given itemID");
		}
		catalogue.put(itemID, info);
	}
	
	/**
	 * Sets the next id to start from.
	 * @param id the next id to start from (aka next item has that id and the one after has that id+1 etc)
	 */
	public void setNextID(int id) { ItemInfo.setNextID(id); }
	
	/**
	 * ONLY applies discount to item if it is greater than the current discount
	 * @param discount the discount to be applied
	 */
	public void addDiscountToAll(Double discount){//will be a sale discount
		for(Entry<Integer, ItemInfo> item : catalogue.entrySet()) {
			if(discount > item.getValue().getSaleDiscount()) {
				item.getValue().setSaleDiscount(discount);
			}
		}
	}
	
	/**
	 * Adds a promo discount to all items
	 * @param discount the discount
	 * @param code the promo code to apply
	 */
	public void addDiscountToAll(Double discount, String code){
		catalogue.entrySet().stream().forEach(e -> e.getValue().addPromoDiscount(code, discount));
	}
	
	/**
	 * add a sale discount to all items associated with a professor
	 * @param discount the discount
	 * @param prof the professor to search for
	 */
	public void addDiscountToAll(Double discount, Professor prof){
		searchByProfessor(prof).stream().forEach(e -> {
			if(discount > e.getSaleDiscount()) {
				e.setSaleDiscount(discount);
			}
		});
	}
	
	/**
	 * add a promo discount to all items associated with a professor
	 * @param discount the discount
	 * @param code the promo code to apply
	 * @param prof the professor to apply it to
	 */
	public void addDiscountToAll(Double discount, String code, Professor prof){
		searchByProfessor(prof).stream().forEach(e -> e.addPromoDiscount(code, discount));
	}
	
//////////////////accessors
	/**
	 * @param itemID the item to get
	 * @return the ItemInfo for that item
	 */
	@JsonIgnore
	public ItemInfo getItem(int itemID) {
		return catalogue.get(itemID);
	}
	
	public List<ItemInfo> getItems(List<Integer> itemIDs){
		List<ItemInfo> toReturn = new ArrayList<ItemInfo>();
		for(Integer i : itemIDs) {
			toReturn.add(getItem(i));
		}
		return toReturn;
	}
	
	/**
	 * @return the number of unique items in the catalogue
	 */
	public int getNumItems() { return numItems; }
	
	/**
	 * @return the catalogue (only for use in json output)
	 */
	public Map<Integer, ItemInfo> getCatalogue() { return catalogue; }

	/**
	 * @param itemID item to check
	 * @return if it's enabled
	 */
	public boolean isEnabled(int itemID) {
		if(catalogue.containsKey(itemID)) {
			return catalogue.get(itemID).isEnabled();
		}
		return false;
	}
	
//////////////////others
	/**
	 * Checks if the given amount can be taken from the item given.
	 * 
	 * @param itemID the item to check
	 * @param amount the amount to withdraw
	 * @return if that amount can be taken from that item
	 */
	public boolean checkAmount(int itemID, int amount) {
		ItemInfo temp = catalogue.get(itemID);
		if(temp != null) {
			return (amount >= 0 && amount <= temp.getStock());
		}
		return false;
	}
	
	/**
	 * Searches the catalogue for a keyword and pulls up items like it.
	 * 
	 * @param keywords the string to search for
	 * @return the list of ItemInfos which match the keywords
	 */
	public List<ItemInfo> search(String keywords){
		List<ItemInfo> toReturn = new ArrayList<ItemInfo>();
		String lower = keywords.toLowerCase();
		for(Entry<Integer, ItemInfo> i : catalogue.entrySet()) {
			ItemInfo temp = i.getValue();
			
			if(temp.getDisplayName().toLowerCase().indexOf(lower) != -1) {//check name
				toReturn.add(temp);
			} else if(temp.getDescription().toLowerCase().indexOf(lower) != -1) {//check description
				toReturn.add(temp);
			} else if(temp.getProf().toString().toLowerCase().indexOf(lower) != -1) {//check description
				toReturn.add(temp);
			} else if((temp.getExtendedItemID() + "").indexOf(lower) != -1) {//check itemid
				toReturn.add(temp);
			} else if((temp.getPrice() + "").indexOf(lower) != -1) {//check price
				toReturn.add(temp);
			} else if((temp.getVendorID() + "").indexOf(lower) != -1) {//check vendor id
				toReturn.add(temp);
			}
		}
		return toReturn;
	}
	
	/**
	 * Searches the catalogue for all items associated with the given professor.
	 * @param prof the professor to look for
	 * @return the list of ItemInfos which match the professor
	 */
	public List<ItemInfo> searchByProfessor(Professor prof){
		return catalogue.entrySet().stream()
				 .filter(e -> e.getValue().getProf() == prof)
				 .map(e -> e.getValue())
				 .collect(Collectors.toList());
	}
	
}
