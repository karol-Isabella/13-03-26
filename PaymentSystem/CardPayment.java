package PaymentSystem;

public class CardPayment implements PaymentMethod {
    @Override
    public boolean process(double amount) {
        System.out.println("Processing card payment: $" + amount);
        return true;
    }
}