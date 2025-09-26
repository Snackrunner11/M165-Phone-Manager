package SwingGUI;

import SwingGUI.Elements.OrderTablePanel;
import Models.Order;
import services.UserService;
import services.OrderService;
import services.SmartphoneService;

import javax.swing.*;
import java.awt.*;

public class OrderFrame extends JFrame {
    private final OrderTablePanel tablePanel;
    private final OrderService orderService;
    private final UserService customerService;
    private final SmartphoneService smartphoneService;

    public OrderFrame() {
        orderService = new OrderService();
        customerService = new UserService();
        smartphoneService = new SmartphoneService();
        tablePanel = new OrderTablePanel(orderService, customerService);

        // Create buttons
        JButton addButton = new JButton("Bestellung hinzufügen");
        addButton.addActionListener(e -> new OrderFormDialog(this, orderService, customerService, smartphoneService, null));

        JButton updateButton = new JButton("Bestellung aktualisieren");
        updateButton.addActionListener(e -> updateOrder());

        JButton deleteButton = new JButton("Bestellung löschen");
        deleteButton.addActionListener(e -> deleteOrder());

        JButton refreshButton = new JButton("Aktualisieren");
        refreshButton.addActionListener(e -> tablePanel.loadOrders());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Frame layout
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Frame configuration
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bestellungsverwaltung");
        setVisible(true);
    }

    private void updateOrder() {
        String orderId = tablePanel.getSelectedOrderId();
        if (orderId == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Bestellung aus, um sie zu aktualisieren.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order selectedOrder = orderService.getOrderById(orderId);
        if (selectedOrder != null) {
            new OrderFormDialog(this, orderService, customerService, smartphoneService, selectedOrder);
            tablePanel.loadOrders();
        }
    }

    private void deleteOrder() {
        String orderId = tablePanel.getSelectedOrderId();
        if (orderId == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Bestellung aus, um sie zu löschen.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Sind Sie sicher, dass Sie diese Bestellung löschen möchten?",
                "Löschen bestätigen",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            orderService.deleteOrder(orderId);
            tablePanel.loadOrders();
        }
    }
}