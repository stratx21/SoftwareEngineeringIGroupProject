package DysfunctionalDesigners.CompSciMerchStore;

import java.util.ArrayList;
import java.util.List;

enum MemberLevel{
	
	GENERAL, MIDDLE, ELITE;
}

public class Customer extends Vendor{

	MemberLevel status;
	List<PaymentInfo> paymentInfo;
	Address shippingAddr;
	List<ItemInfo> wishList;
	Sale cart;
	List<Sale> previousPurchases;	
	
	
	/**
	 * 
	 * @param d
	 * @param status
	 * @param paymentInfo
	 * @param shippingAddr
	 * @param wishList
	 * @param cart
	 * @param previousPurchases
	 */
	public Customer(String[] d, MemberLevel status, List<PaymentInfo> paymentInfo, Address shippingAddr,
			List<ItemInfo> wishList, Sale cart, List<Sale> previousPurchases) {
		super(d);
		this.status = status;
		this.paymentInfo = paymentInfo;
		this.shippingAddr = shippingAddr;
		this.wishList = wishList;
		this.cart = cart;
		this.previousPurchases = previousPurchases;
	}
	public Customer(String [] d) {
		super(d);
		this.status = MemberLevel.GENERAL;
		this.paymentInfo = null;
		this.shippingAddr = null;
		this.wishList = null;
		this.cart = null;
		this.previousPurchases = null;
	}
	
	
	public MemberLevel getStatus() {
		return status;
	}
	public void setStatus(MemberLevel status) {
		this.status = status;
	}
		
	
	public Address getShippingAddr() {
		return shippingAddr;
	}
	public void setShippingAddr(Address shippingAddr) {
		this.shippingAddr = shippingAddr;
	}
	public List<ItemInfo> getWishList() {
		return wishList;
	}
	public void setWishList(List<ItemInfo> wishList) {
		this.wishList = wishList;
	}
	

	public void updateWishlist(ItemInfo item) {
		if(this.wishList == null) {
			this.wishList = new ArrayList<ItemInfo>();
		}
		
		if(this.wishList.contains(item)) {
			this.wishList.remove(item);
		}
		else {
			this.wishList.add(item);
		}
	}
	
	public void addToCart(int id, int q) {
		if(this.cart == null) {
			this.cart = new Sale(this.getUserID());
		}
		this.cart.addItem(q, id);
	}
	public void updatePreviousPurchases(Sale sale) {
		if(this.previousPurchases == null) {
			this.previousPurchases = new ArrayList<Sale>();
		}
		this.previousPurchases.add(sale);
	}
	
}
