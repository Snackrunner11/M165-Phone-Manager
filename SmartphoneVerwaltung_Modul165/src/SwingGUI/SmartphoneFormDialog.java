package SwingGUI;

import Models.Smartphone;
import services.SmartphoneService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Dialog for adding or updating a smartphone
public class SmartphoneFormDialog extends JDialog {
    private final SmartphoneService smartphoneService;
    private final Smartphone smartphone;

    public SmartphoneFormDialog(JFrame parent, SmartphoneService smartphoneService, Smartphone smartphone) {
        super(parent, (smartphone == null ? "Add Smartphone" : "Update Smartphone"), true);
        this.smartphoneService = smartphoneService;
        this.smartphone = smartphone;

        initializeForm();
    }

    private void initializeForm() {
        setLayout(new BorderLayout());
        setSize(700, 500);
        setLocationRelativeTo(getParent());

        // Panel for the form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Brand field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        JTextField brandField = new JTextField(smartphone != null ? smartphone.getBrand() : "");
        formPanel.add(brandField, gbc);

        // Model field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        JTextField modelField = new JTextField(smartphone != null ? smartphone.getModel() : "");
        formPanel.add(modelField, gbc);

        // Price field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price (CHF):"), gbc);
        gbc.gridx = 1;
        JTextField priceField = new JTextField(smartphone != null ? String.valueOf(smartphone.getPrice()) : "");
        formPanel.add(priceField, gbc);

        // RAM dropdown
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("RAM (GB):"), gbc);
        gbc.gridx = 1;
        JComboBox<Integer> ramDropdown = new JComboBox<>(new Integer[]{4, 6, 8, 12, 16, 32});
        if (smartphone != null) ramDropdown.setSelectedItem(smartphone.getRam());
        formPanel.add(ramDropdown, gbc);

        // Screen size field
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Screen Size (inches):"), gbc);
        gbc.gridx = 1;
        JTextField screenSizeField = new JTextField(smartphone != null ? smartphone.getScreenSize() : "");
        formPanel.add(screenSizeField, gbc);

        // Storage dropdown
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Storage (GB):"), gbc);
        gbc.gridx = 1;
        JComboBox<Integer> storageDropdown = new JComboBox<>(new Integer[]{64, 128, 256, 512, 1024, 2048});
        if (smartphone != null) storageDropdown.setSelectedItem(smartphone.getStorage());
        formPanel.add(storageDropdown, gbc);

        // OS dropdown
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Operating System:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> osDropdown = new JComboBox<>(new String[]{"Android", "iOS", "HarmonyOS", "Windows Mobile", "KaiOS"});
        if (smartphone != null) osDropdown.setSelectedItem(smartphone.getOs());
        formPanel.add(osDropdown, gbc);

        // OS version field
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("OS Version:"), gbc);
        gbc.gridx = 1;
        JTextField osVersionField = new JTextField(smartphone != null ? smartphone.getOsVersion() : "");
        formPanel.add(osVersionField, gbc);

        // Resolution field
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Resolution:"), gbc);
        gbc.gridx = 1;
        JTextField resolutionField = new JTextField(smartphone != null ? smartphone.getResolution() : "");
        formPanel.add(resolutionField, gbc);

        // Core count dropdown
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Cores:"), gbc);
        gbc.gridx = 1;
        JComboBox<Integer> coresDropdown = new JComboBox<>(new Integer[]{2, 4, 6, 8, 10, 12});
        if (smartphone != null) coresDropdown.setSelectedItem(smartphone.getCores());
        formPanel.add(coresDropdown, gbc);

        // Battery capacity field
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(new JLabel("Battery Capacity (mAh):"), gbc);
        gbc.gridx = 1;
        JTextField batteryCapacityField = new JTextField(smartphone != null ? String.valueOf(smartphone.getBatteryCapacity()) : "");
        formPanel.add(batteryCapacityField, gbc);

        // Connectivity fields
        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(new JLabel("Connectivity (Max 3):"), gbc);
        gbc.gridx = 1;
        JPanel connectivityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> connectivity1 = new JComboBox<>(new String[]{"-", "Bluetooth", "NFC", "USB", "WiFi", "5G"});
        JComboBox<String> connectivity2 = new JComboBox<>(new String[]{"-", "Bluetooth", "NFC", "USB", "WiFi", "5G"});
        JComboBox<String> connectivity3 = new JComboBox<>(new String[]{"-", "Bluetooth", "NFC", "USB", "WiFi", "5G"});
        connectivityPanel.add(connectivity1);
        connectivityPanel.add(connectivity2);
        connectivityPanel.add(connectivity3);
        formPanel.add(connectivityPanel, gbc);

        // Network standard dropdown
        gbc.gridx = 0;
        gbc.gridy = 12;
        formPanel.add(new JLabel("Network Standard:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> networkDropdown = new JComboBox<>(new String[]{"2G", "3G", "4G", "5G"});
        if (smartphone != null) networkDropdown.setSelectedItem(smartphone.getNetworkStandard());
        formPanel.add(networkDropdown, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                double price = Double.parseDouble(priceField.getText());
                int ram = (int) ramDropdown.getSelectedItem();
                String screenSize = screenSizeField.getText();
                int storage = (int) storageDropdown.getSelectedItem();
                String os = (String) osDropdown.getSelectedItem();
                String osVersion = osVersionField.getText();
                String resolution = resolutionField.getText();
                int cores = (int) coresDropdown.getSelectedItem();
                int batteryCapacity = Integer.parseInt(batteryCapacityField.getText());

                List<String> connectivity = new ArrayList<>();
                if (!"-".equals(connectivity1.getSelectedItem())) connectivity.add((String) connectivity1.getSelectedItem());
                if (!"-".equals(connectivity2.getSelectedItem())) connectivity.add((String) connectivity2.getSelectedItem());
                if (!"-".equals(connectivity3.getSelectedItem())) connectivity.add((String) connectivity3.getSelectedItem());

                String networkStandard = (String) networkDropdown.getSelectedItem();

                if (smartphone == null) {
                    Smartphone newSmartphone = new Smartphone(
                            null, brand, model, price, ram, screenSize, storage, os, osVersion, resolution, cores, batteryCapacity, connectivity, networkStandard
                    );
                    smartphoneService.addSmartphone(newSmartphone);
                } else {
                    smartphone.setBrand(brand);
                    smartphone.setModel(model);
                    smartphone.setPrice(price);
                    smartphone.setRam(ram);
                    smartphone.setScreenSize(screenSize);
                    smartphone.setStorage(storage);
                    smartphone.setOs(os);
                    smartphone.setOsVersion(osVersion);
                    smartphone.setResolution(resolution);
                    smartphone.setCores(cores);
                    smartphone.setBatteryCapacity(batteryCapacity);
                    smartphone.setConnectivity(connectivity);
                    smartphone.setNetworkStandard(networkStandard);
                    smartphoneService.updateSmartphone(smartphone.getId(), smartphone);
                }

                JOptionPane.showMessageDialog(this, "Smartphone saved successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving smartphone: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
