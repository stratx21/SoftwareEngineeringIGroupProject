package DysfunctionalDesigners.CompSciMerchStore;

import java.util.Date;

public class Review {
	private String description;
	private int userID;
	private Date date;
	private int rating;
	
	public Review(String description, int userID, int rating) {
		super();
		this.description = description;
		this.userID = userID;
		this.rating = rating;
		this.date = new Date();
	}

	/**
	 * Required for json parser
	 */
	public Review() {}
	
	public String getDescription() { return description; }
	public int getRating() { return rating; }
	public int getUserID() { return userID; }
	public Date getDate() { return date; }

	public void setRating(int rating) { this.rating = rating; }
	public void setDescription(String description) { this.description = description; }
	public void setDate(Date date) { this.date = date; }
	
	
}
