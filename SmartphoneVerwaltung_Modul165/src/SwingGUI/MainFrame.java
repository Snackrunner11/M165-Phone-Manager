package SwingGUI;

import javax.swing.*;
import java.awt.*;

//GUI with swing
public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Online-Shop Verwaltung");
        setSize(300, 300);
        setLayout(new FlowLayout());

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSmartphones = new JButton("Smartphones verwalten");
        JButton btnCustomers = new JButton("Kunden verwalten");
        JButton btnOrders = new JButton("Bestellungen verwalten");

        btnSmartphones.addActionListener(e -> new SmartphoneFrame());
        btnCustomers.addActionListener(e -> new UserFrame());
        btnOrders.addActionListener(e -> new OrderFrame());

        add(btnSmartphones);
        add(btnCustomers);
        add(btnOrders);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

