package Abstraction;
import PaymentSystem.PaymentMethod;

public class DiscountedOrder extends PaymentOrder {
    public DiscountedOrder(PaymentMethod method) {
        super(method);
    }

    @Override
    public void finalizeOrder(double amount) {
        double finalAmount = amount * 0.9;
        System.out.println("Aplicando el 10%...");
        method.process(finalAmount);
    }
}