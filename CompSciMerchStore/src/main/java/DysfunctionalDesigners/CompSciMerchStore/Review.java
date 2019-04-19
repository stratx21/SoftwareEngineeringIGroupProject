package DysfunctionalDesigners.CompSciMerchStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Review {
	private static Logger logger = Logger.getLogger(Review.class.getName());
	
	private String description;
	private int userID;
	private Date date;
	private int rating;
	
	/**
	 * This constructor is for NEW ratings ONLY.  The other constructor is for the JSON parser.
	 * 
	 * @param description the review content
	 * @param userID the user associated with the review
	 * @param rating the rating 1-5 associated with the review
	 */
	public Review(String description, int userID, int rating) {
		super();
		this.description = description;
		this.userID = userID;
		this.rating = (rating > 5 ? 5 : (rating < 0 ? 0 : rating));//0 <= rating <= 5
		this.date = new Date();
		logger.info("Initialized a review with userID" + userID + " for " + this.rating + " stars on " + (new SimpleDateFormat("MM dd, YYYY")).format(this.date));
	}

	/**
	 * Required for json parser
	 */
	public Review() {}
	
	public String getDescription() { return description; }
	public int getRating() { return rating; }
	public int getUserID() { return userID; }
	public Date getDate() { return date; }

	public void setRating(int rating) { this.rating = (rating > 5 ? 5 : (rating < 0 ? 0 : rating)); }//0 <= rating <= 5
	public void setDescription(String description) { this.description = description; }
	public void setDate(Date date) { this.date = date; }
	
	
}
