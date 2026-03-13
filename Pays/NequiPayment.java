package Pays;

public class NequiPayment {
    public boolean sendTransfer(String phoneNumber, double amount) {
        if (phoneNumber.length() < 10) return false;
        System.out.println("Transferencia de Nequi exitosa al celular: " + phoneNumber);
        return true;
    }
}