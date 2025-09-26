package SwingGUI;

import SwingGUI.Elements.SmartphoneTablePanel;
import Models.Smartphone;
import services.SmartphoneService;

import javax.swing.*;
import java.awt.*;

// Main frame for managing smartphones.
public class SmartphoneFrame extends JFrame {
    private final SmartphoneTablePanel tablePanel;
    private final SmartphoneService smartphoneService;

    public SmartphoneFrame() {
        smartphoneService = new SmartphoneService();
        tablePanel = new SmartphoneTablePanel(smartphoneService);

        // Create buttons
        JButton addButton = new JButton("Add Smartphone");
        addButton.addActionListener(e -> new SmartphoneFormDialog(this, smartphoneService, null));

        JButton updateButton = new JButton("Update Smartphone");
        updateButton.addActionListener(e -> updateSmartphone());

        JButton deleteButton = new JButton("Delete Smartphone");
        deleteButton.addActionListener(e -> deleteSmartphone());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> tablePanel.loadSmartphones());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Frame layout
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER); // Add table panel

        // Configure frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Smartphone Management");
        setVisible(true);
    }

    private void updateSmartphone() {
        String smartphoneId = tablePanel.getSelectedSmartphoneId();
        if (smartphoneId == null) {
            JOptionPane.showMessageDialog(this, "Please select a smartphone to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Smartphone selectedSmartphone = smartphoneService.getSmartphoneById(smartphoneId);
        new SmartphoneFormDialog(this, smartphoneService, selectedSmartphone);
        tablePanel.loadSmartphones();
    }

    private void deleteSmartphone() {
        String smartphoneId = tablePanel.getSelectedSmartphoneId();
        if (smartphoneId == null) {
            JOptionPane.showMessageDialog(this, "Please select a smartphone to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this smartphone?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            smartphoneService.deleteSmartphoneById(smartphoneId);
            tablePanel.loadSmartphones();
        }
    }
}
