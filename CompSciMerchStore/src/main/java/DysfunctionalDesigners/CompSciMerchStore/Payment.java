package DysfunctionalDesigners.CompSciMerchStore;

public class Payment {
    private double amount;
    private PaymentInfo paymentInfo;

    public Payment(double amount, PaymentInfo paymentInfo) {
        this.amount = amount;
        this.paymentInfo = paymentInfo;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", paymentInfo=" + paymentInfo +
                '}';
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
