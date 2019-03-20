package DysfunctionalDesigners.CompSciMerchStore;

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
		if(this.wishList.contains(item)) {
			this.wishList.remove(item);
		}
		else {
			this.wishList.add(item);
		}
	}
	
	public void addToCart(int id, int q) {
		this.cart.addItem(q, id);
	}
	public void updatePreviousPurchases(Sale sale) {
		this.previousPurchases.add(sale);
	}
	
}
