package DysfunctionalDesigners.CompSciMerchStore;

public abstract class User {
	String email;
	String motherMaidenName;
	String userName;
	String password;
	String name;
	Integer userID;
	public String getEmail() {return email;	}
	
	
	public void setEmail(String email) {this.email = email;	}
	public void setMotherMaidenName(String motherMaidenName) {this.motherMaidenName = motherMaidenName;}
	public void setUserName(String userName) {this.userName = userName;}
	public void setPassword(String password) {this.password = password;}
	public void setName(String name) {this.name = name;}
	public void setUserID(int userID) {this.userID = userID;}
	
	public String getMotherMaidenName() {return motherMaidenName;}
	public String getUserName() {return userName;}
	public String getPassword() {return password;}
	public String getName() {return name;}	
	public int getUserID() {return userID;}


	
	protected User(String email, String motherMaidenName, String userName, String password, String name, int userID) {
		super();
		this.email = email;
		this.motherMaidenName = motherMaidenName;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.userID = userID;
	}
	protected User(String[] d) {
		this.email = d[0];
		this.motherMaidenName = d[1];
		this.userName = d[2];
		this.password = d[3];
		this.name = d[4];
		this.userID = Integer.parseInt(d[5]);
	}
	
	@SuppressWarnings("unused")
	private User() throws Exception {
		throw new Exception("User must only be initialized by a subclass");
	}
	protected User(int notUsed) {}
	
	public String toStringDisplay() {
		return "User [email=" + email + ", motherMaidenName=" + motherMaidenName + ", userName=" + userName
				+ ", password=" + password + ", name=" + name + ", userID=" + userID + "]";
	}
	
	public String toStringFile() {
		String jsonFormat = "";
		jsonFormat += "{\n";
		
		
		jsonFormat += "\t\"email\"    : \"" + this.email    + "\",\n";
		jsonFormat += "\t\"motherMaidenName\" : \"" + this.motherMaidenName + "\",\n";
		jsonFormat += "\t\"username\" : \"" + this.userName + "\",\n";
		jsonFormat += "\t\"password\" : \"" + this.password + "\",\n";
		jsonFormat += "\t\"userID\"   : " + this.userID   + ",\n";
		
		
		
		jsonFormat+="}";
		return jsonFormat;
	}
	
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
