package DysfunctionalDesigners.CompSciMerchStore;

import java.util.logging.Logger;

public class PaymentInfo {
	private static Logger logger = Logger.getLogger(PaymentInfo.class.getName());
	
    private String cardNumber;
    private Address billingAddress;
    private int CCV;

    /**
     * For the JSON parser
     */
    public PaymentInfo() {} 
    
    /**
     * Makes a PaymentInfo Object
     * @param cardNumber the credit card number to add
     * @param billingAddress the billing address to associate with the payment
     * @param CCV the 3 number code on the card
     */
    public PaymentInfo(String cardNumber, Address billingAddress, int CCV) {
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
        this.CCV = CCV;
        logger.info("Initialized payment info with card number " + cardNumber);
    }
    
    
    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getCCV() {
        return CCV;
    }

    public void setCCV(int CCV) {
        this.CCV = CCV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentInfo that = (PaymentInfo) o;

        if (CCV != that.CCV) return false;
        if (!cardNumber.equals(that.cardNumber)) return false;
        return billingAddress.equals(that.billingAddress);
    }

    @Override
    public int hashCode() {
        int result = getCardNumber().hashCode();
        result = 31 * result + getBillingAddress().hashCode();
        result = 31 * result + getCCV();
        return result;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "cardNumber='" + cardNumber + '\'' +
                "\n billingAddress=" + billingAddress +
                "\n CCV=" + CCV +
                '}';
    }
}
