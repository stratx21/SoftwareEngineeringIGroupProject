package DysfunctionalDesigners.CompSciMerchStore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

enum MemberLevel{
	GENERAL, MIDDLE, ELITE;
}

public class Customer extends Vendor{
	private static Logger logger = Logger.getLogger(Customer.class.getName());
	
	private MemberLevel status;
	private List<PaymentInfo> paymentInfo;
	private Address shippingAddr;
	private List<Integer> wishList;
	private Sale cart;
	private List<Sale> previousPurchases;
	
	/**
	 * Sets everything for a customer object, passing necessary things to superconstructor.
	 * @param d
	 * @param status
	 * @param paymentInfo
	 * @param shippingAddr
	 * @param wishList
	 * @param cart
	 * @param previousPurchases
	 */
	public Customer(String[] d, MemberLevel status, List<PaymentInfo> paymentInfo, Address shippingAddr,
			List<Integer> wishList, Sale cart, List<Sale> previousPurchases) {
		super(d);
		this.status = status;
		this.paymentInfo = paymentInfo;
		this.shippingAddr = shippingAddr;
		this.wishList = wishList;
		this.cart = cart;
		this.previousPurchases = previousPurchases;
		logger.info("Initing CUSTOMER id " + this.getUserID() + ": " + this.getUserName() + " who is " + this.getName());
	}
	/**
	 * Creates empty versions of customer data, passes necessary information to super.
	 * @param d
	 */
	public Customer(String [] d) {
		super(d);
		this.status = MemberLevel.GENERAL;
		this.paymentInfo = new ArrayList<PaymentInfo>();
		this.shippingAddr = null;
		this.wishList = new ArrayList<Integer>();
		this.cart = null;
		this.previousPurchases = new ArrayList<Sale>();
		logger.info("Initing CUSTOMER id " + this.getUserID() + ": " + this.getUserName() + " who is " + this.getName());
	}
	/**
	 * Uses implicit super constructor, all other members are default/empty.
	 */
	public Customer() {
		super();
		this.paymentInfo = new ArrayList<PaymentInfo>();
		this.cart = null;
		this.shippingAddr = null;
		this.wishList = new ArrayList<Integer>();
		this.previousPurchases = new ArrayList<Sale>();
	}
	
	public MemberLevel getStatus() {return status;}
	public Address getShippingAddr() {return shippingAddr;}
	public List<Integer> getWishList() {return wishList;}
	public List<PaymentInfo> getPaymentInfo() { return this.paymentInfo; }
	public List<Sale> getPreviousPurchases() { return this.previousPurchases; }
	public Sale getCart() { return this.cart; }
	
	public void setStatus(MemberLevel status) {this.status = status;}
	public void setShippingAddr(Address shippingAddr) {this.shippingAddr = shippingAddr;}		
	public void setWishList(List<Integer> wishList) {this.wishList = wishList;}
	public void setCart(Sale cart) { this.cart = cart; }
	/**
	 * A customer adds an item to their own wishlist. Wishlist is created if needed.
	 * @param id Item to be added
	 */
	public void addItemToWishlist(Integer id) {
		logger.info("Adding item " + id + " to \"" + this.getUserName() + "\"'s wishlist.");
		if(this.wishList == null) {
			this.wishList = new ArrayList<Integer>();
		}
		this.wishList.add(id);
	}
	
	/**
	 * A customer removes an item from their wishlist, IF the wishlist has the item.
	 * @param id
	 */
	public void removeItemFromWishlist(Integer id) {
		if(this.wishList.contains(id)) {
			this.wishList.remove(id);
			logger.info("Removed item " + id + " from \"" + this.getUserName() + "\"'s wishlist.");
		} else {
			logger.fine("ATTEMPTING TO REMOVE ITEM NOT PRESENT IN WISHLIST (id " + id + " from \"" + this.getUserName() + "\"'s wishlist");
		}
	}
	
	/**
	 * Adds an item to the cart. Creates the cart if needed.
	 * @param id Item id of item added.
	 * @param q  Quantity of how many items.
	 */
	public void addToCart(int id, int q) {
		logger.info("Adding " + q + " of item " + id + " to \"" + this.getUserName() + "\"'s wishlist");
		if(this.cart == null) {
			this.cart = new Sale(this.getUserID());
		}
		this.cart.addItem(q, id);
	}
	
	/**
	 * After a customer makes a purchase, the sale is added.
	 * @param sale The completed sale.
	 */
	public void updatePreviousPurchases(Sale sale) {
		if(this.previousPurchases == null) {
			this.previousPurchases = new ArrayList<Sale>();
		}
		this.previousPurchases.add(sale);
	}
	
}
