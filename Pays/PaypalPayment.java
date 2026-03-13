package Pays;

public class PaypalPayment {
    public boolean executeTransaction(double amount, String emailAccount) {
        if (!emailAccount.contains("@")) return false;
        System.out.println("PayPal transaction successful for: " + emailAccount);
        return true;
    }
}