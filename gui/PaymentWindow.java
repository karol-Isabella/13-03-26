package gui;

import Pays.*;
import PaymentSystem.*;
import Abstraction.*;

import javax.swing.*;
import java.awt.*;

public class PaymentWindow extends JFrame {

    private JComboBox<String> methodComboBox;
    private JComboBox<String> orderTypeComboBox;
    private JTextField amountField;
    private JTextField accountField;
    private JLabel accountLabel;

    public PaymentWindow() {
//ventana
        setTitle("Store Checkout");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//colores
        Color backgroundColor = new Color(253, 238, 244);
        Color buttonColor = new Color(175, 215, 246);

//panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(backgroundColor);

//componentes
        JLabel titleLabel = new JLabel("COMPLETE SU PAGO");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//selecciona el metodo de pago
        String[] methods = {"Targeta de credito", "Nequi", "PayPal"};
        methodComboBox = new JComboBox<>(methods);
        methodComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//tipo de orden
        String[] orderTypes = {"Orden normal", "Orden con Descuento (10%)"};
        orderTypeComboBox = new JComboBox<>(orderTypes);
        orderTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel amountLabel = new JLabel("Monto ($):");
        amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        accountLabel = new JLabel("Detalles de la cuenta:");
        accountField = new JTextField();
        accountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        accountField.setEnabled(false);

        JButton payButton = new JButton("Paga ahora");
        payButton.setBackground(buttonColor);
        payButton.setFocusPainted(false);
        payButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);

//Cambiar el texto segun metodo de pago
        methodComboBox.addActionListener(e -> updateAccountField());

//Procesar el pago
        payButton.addActionListener(e -> processPayment());

//se añade al panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(new JLabel("Tipo de Orden:"));
        mainPanel.add(orderTypeComboBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Metodo de pago:"));
        mainPanel.add(methodComboBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(amountLabel);
        mainPanel.add(amountField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(accountLabel);
        mainPanel.add(accountField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(payButton);

        add(mainPanel);
    }

    private void updateAccountField() {
        String selected = (String) methodComboBox.getSelectedItem();
        if ("Targeta de credito ".equals(selected)) {
            accountLabel.setText("No requerido para targeta");
            accountField.setEnabled(false);
            accountField.setText("");
        } else if ("Nequi".equals(selected)) {
            accountLabel.setText("Numero de telefono:");
            accountField.setEnabled(true);
        } else {
            accountLabel.setText("Dirección de Gmail:");
            accountField.setEnabled(true);
        }
    }

    private void processPayment() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();

            String selectedMethod = (String) methodComboBox.getSelectedItem();
            String selectedOrderType = (String) orderTypeComboBox.getSelectedItem();
            String accountData = accountField.getText();


            PaymentMethod implementor = null;

            if ("Targeta de credito".equals(selectedMethod)) {
                implementor = new CardPayment();
            } else if ("Nequi".equals(selectedMethod)) {
                implementor = new PaymentAdapter(new NequiPayment(), accountData);
            } else if ("PayPal".equals(selectedMethod)) {
                implementor = new PaymentAdapter(new PaypalPayment(), accountData);
            }


            PaymentOrder order;
            if ("Descuento (10%)".equals(selectedOrderType)) {
                order = new DiscountedOrder(implementor);
            } else {
                order = new SimpleOrder(implementor);
            }


            order.finalizeOrder(amount);

            JOptionPane.showMessageDialog(this,
                    "Orden procesada como: " + selectedOrderType + "\nMetodo: " + selectedMethod,
                    "Exitoso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un monto valido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}