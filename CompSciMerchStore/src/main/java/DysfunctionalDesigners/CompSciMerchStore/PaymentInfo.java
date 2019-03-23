package DysfunctionalDesigners.CompSciMerchStore;

public class PaymentInfo {
    private double amount;
    private String cardNumber;
    private Address billingAddress;
    private int CCV;

    public PaymentInfo(double amount, String cardNumber, Address billingAddress, int CCV) {
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
        this.CCV = CCV;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

        if (Double.compare(that.amount, amount) != 0) return false;
        if (CCV != that.CCV) return false;
        if (!cardNumber.equals(that.cardNumber)) return false;
        return billingAddress.equals(that.billingAddress);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + cardNumber.hashCode();
        result = 31 * result + billingAddress.hashCode();
        result = 31 * result + CCV;
        return result;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "amount=" + amount +
                ", cardNumber='" + cardNumber + '\'' +
                ", billingAddress=" + billingAddress +
                ", CCV=" + CCV +
                '}';
    }
}
