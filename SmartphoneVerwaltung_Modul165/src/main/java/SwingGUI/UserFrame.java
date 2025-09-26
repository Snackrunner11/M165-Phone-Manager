package SwingGUI;

import SwingGUI.Elements.UserTablePanel;
import Models.User;
import services.UserService;

import javax.swing.*;
import java.awt.*;

public class UserFrame extends JFrame {
    private final UserTablePanel tablePanel;
    private final UserService customerService;

    public UserFrame() {
        customerService = new UserService();
        tablePanel = new UserTablePanel(customerService);

        // Erstelle Buttons
        JButton addButton = new JButton("Kunde hinzufügen");
        addButton.addActionListener(e -> new UserFormDialog(this, customerService, null));

        JButton updateButton = new JButton("Kunde aktualisieren");
        updateButton.addActionListener(e -> updateCustomer());

        JButton deleteButton = new JButton("Kunde löschen");
        deleteButton.addActionListener(e -> deleteCustomer());

        JButton refreshButton = new JButton("Aktualisieren");
        refreshButton.addActionListener(e -> tablePanel.loadCustomers());

        // Button-Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Frame-Layout
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Frame-Konfiguration
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kundenverwaltung");
        setVisible(true);
    }

    private void updateCustomer() {
        String customerId = tablePanel.getSelectedCustomerId();
        if (customerId == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Kunden aus, um ihn zu aktualisieren.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User selectedCustomer = customerService.getCustomerById(customerId);
        if (selectedCustomer != null) {
            new UserFormDialog(this, customerService, selectedCustomer);
            tablePanel.loadCustomers();
        }
    }

    private void deleteCustomer() {
        String customerId = tablePanel.getSelectedCustomerId();
        if (customerId == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Kunden aus, um ihn zu löschen.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Sind Sie sicher, dass Sie diesen Kunden löschen möchten?",
                "Löschen bestätigen",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            customerService.deleteCustomer(customerId);
            tablePanel.loadCustomers();
        }
    }
}