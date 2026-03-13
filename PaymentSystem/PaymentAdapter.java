package PaymentSystem;
import Pays.*;

public class PaymentAdapter implements PaymentMethod {
    private NequiPayment nequi;
    private PaypalPayment paypal;
    private String accountId;
    private String paymentType;

    public PaymentAdapter(NequiPayment nequi, String phoneNumber) {
        this.nequi = nequi;
        this.accountId = phoneNumber;
        this.paymentType = "nequi";
    }

    public PaymentAdapter(PaypalPayment paypal, String email) {
        this.paypal = paypal;
        this.accountId = email;
        this.paymentType = "paypal";
    }

    @Override
    public boolean process(double amount) {
        if ("nequi".equalsIgnoreCase(paymentType)) {
            return nequi.sendTransfer(accountId, amount);
        } else if ("paypal".equalsIgnoreCase(paymentType)) {
            return paypal.executeTransaction(amount, accountId);
        }
        return false;
    }
}