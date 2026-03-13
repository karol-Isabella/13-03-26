package PaymentSystem;

public interface PaymentMethod {
    boolean process(double amount);
}