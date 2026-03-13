
import gui.PaymentWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            PaymentWindow window = new PaymentWindow();
            window.setVisible(true);
        });
    }
}