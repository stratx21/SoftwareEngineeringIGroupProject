package DysfunctionalDesigners.CompSciMerchStore;

import java.util.logging.Logger;

public class Payment {
	private static Logger logger = Logger.getLogger(Payment.class.getName());
	
    private double amount;
    private PaymentInfo paymentInfo;

    public Payment() { 	}

    /**
     * Constructor for Payment
     * @param amount amount to be payed
     * @param paymentInfo the payment info to be used for the payment
     */
    public Payment(double amount, PaymentInfo paymentInfo) {
        this.amount = amount;
        this.paymentInfo = paymentInfo;
        logger.info("Initialized new payment for " + amount);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                "\n paymentInfo=" + paymentInfo +
                '}';
    }

    public double getAmount() { return amount;  }

    public void setAmount(double amount) { this.amount = amount;}

    public PaymentInfo getPaymentInfo() { return paymentInfo; }

    public void setPaymentInfo(PaymentInfo paymentInfo) {this.paymentInfo = paymentInfo;}
}
