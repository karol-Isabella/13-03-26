package Abstraction;
import PaymentSystem.PaymentMethod;

public class SimpleOrder extends PaymentOrder {
    public SimpleOrder(PaymentMethod method) {
        super(method);
    }

    @Override
    public void finalizeOrder(double amount) {
        System.out.println("Procesando...");
        method.process(amount);
    }
}