package Abstraction;
import PaymentSystem.PaymentMethod;

public abstract class PaymentOrder {

    protected PaymentMethod method;

    protected PaymentOrder(PaymentMethod method) {
        this.method = method;
    }

    public abstract void finalizeOrder(double amount);
}