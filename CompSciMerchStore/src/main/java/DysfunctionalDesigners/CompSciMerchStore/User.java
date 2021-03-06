package DysfunctionalDesigners.CompSciMerchStore;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class User {
	private static Logger logger = Logger.getLogger(User.class.getName());
	
	private String email, motherMaidenName, userName, password, name;
	private Integer userID;
	
	
	
	public void setEmail(String email) {this.email = email;	}
	public void setMotherMaidenName(String motherMaidenName) {this.motherMaidenName = motherMaidenName;}
	public void setUserName(String userName) {this.userName = userName;}
	public void setPassword(String password) {this.password = password;}
	public void setName(String name) {this.name = name;}
	public void setUserID(int userID) {this.userID = userID;}
	
	public String getEmail() {return email;	}
	public String getMotherMaidenName() {return motherMaidenName;}
	public String getUserName() {return userName;}
	public String getPassword() {return password;}
	public String getName() {return name;}	
	public int getUserID() {return userID;}

	@JsonIgnore
	/**
	 * 
	 * @return a string with the proper format of a filed complaint; user ID + correct prefix
	 */
	public String getComplaintPrefix() {
		return this.userID + ":|: ";
	}

	/**
	 * Generates the user ID based on the user ID.
	 * @param userName Username
	 * @return Created ID.
	 */
	public static String hashUserNameToCustomerID(String userName) {
		final int prime = 31;
		String result = (prime + ((userName == null) ? 0 : userName.hashCode())) + "";
		result = (Integer.parseInt(result) % 10000) + "";
		if(result.length() > 4) {
			result = result.substring(1);
		}
		result = "1" + result;
		logger.info("Hashed username \"" + userName + "\" to " + result);
		return result;
	}
	
	/**
	 * Custom constructor for user. All parameters are for object fields.
	 * @param email
	 * @param motherMaidenName
	 * @param userName
	 * @param password
	 * @param name
	 * @param userID
	 */
	protected User(String email, String motherMaidenName, String userName, String password, String name, int userID) {
		super();
		this.email = email;
		this.motherMaidenName = motherMaidenName;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.userID = userID;
		logger.info("Initializing userName: " + this.userName + " id: " + this.userID);
	}
	/**
	 * Basic constructor
	 * @param d Contains all fields
	 */
	protected User(String[] d) {
		this.email = d[0];
		this.motherMaidenName = d[1];
		this.userName = d[2];
		this.password = d[3];
		this.name = d[4];
		this.userID = Integer.parseInt(d[5]);
		logger.info("Initializing userName: " + this.userName + " id: " + this.userID);
	}
	
	/**
	 * Jackson Json deserializer requires this
	 */
	@SuppressWarnings("unused")
	public User() {}
	
	public String toStringDisplay() {
		return "User [email=" + email + ", motherMaidenName=" + motherMaidenName + ", userName=" + userName
				+ ", password=" + password + ", name=" + name + ", userID=" + userID + "]";
	}
	
	/** 
	 * 
	 * @return User data in JSON format
	 */
//	public String toStringFile() {
//		String jsonFormat = "";
//		jsonFormat += "{\n";
//		
//		jsonFormat += "\t\"email\"    : \"" + this.email    + "\",\n";
//		jsonFormat += "\t\"motherMaidenName\" : \"" + this.motherMaidenName + "\",\n";
//		jsonFormat += "\t\"username\" : \"" + this.userName + "\",\n";
//		jsonFormat += "\t\"password\" : \"" + this.password + "\",\n";
//		jsonFormat += "\t\"userID\"   : " + this.userID   + ",\n";
//		
//		jsonFormat+="}";
//		return jsonFormat;
//	}
	
	/**
	 * 
	 * @return whether or not a user object is an admin based on the ID
	 */
	@JsonIgnore
	public boolean isAdmin() {
		String id = Integer.toString(this.userID);
		if(id.charAt(0) == '4') {
			return true;
		}
		else {
			return false;
		}
	}
}
