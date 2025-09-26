// OrderFormDialog.java
package SwingGUI;

import Models.User;
import Models.Order;
import Models.OrderItem;
import Models.Smartphone;
import services.UserService;
import services.OrderService;
import services.SmartphoneService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import com.toedter.calendar.JCalendar;
import java.util.ArrayList;
import java.util.Date;

public class OrderFormDialog extends JDialog {
    private final OrderService orderService;
    private final UserService customerService;
    private final SmartphoneService smartphoneService;
    private final Order order;
    private JTable itemListTable;
    private DefaultTableModel itemTableModel;

    public OrderFormDialog(JFrame parent, OrderService orderService, UserService customerService, SmartphoneService smartphoneService, Order order) {
        super(parent, (order == null ? "Add Order" : "Update Order"), true);
        this.orderService = orderService;
        this.customerService = customerService;
        this.smartphoneService = smartphoneService;
        this.order = order;
        initializeForm();
    }

    private void initializeForm() {
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Order Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Order Number:"), gbc);
        gbc.gridx = 1;
        JTextField orderNumberField = new JTextField(order != null ? order.getOrderNumber() : "");
        formPanel.add(orderNumberField, gbc);

        // Order Date calendar
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Order Date:"), gbc);
        gbc.gridx = 1;
        JCalendar orderDateCalendar = new JCalendar();
        if (order != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(order.getOrderDate());
                orderDateCalendar.setDate(date);
            } catch (Exception e) {
                // Handle invalid date
            }
        }
        formPanel.add(orderDateCalendar, gbc);

        // Customer
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Customer:"), gbc);
        gbc.gridx = 1;
        JComboBox<User> customerComboBox = new JComboBox<>();
        List<User> customers = customerService.getAllCustomers();
        for (User customer : customers) {
            customerComboBox.addItem(customer);
        }
        if (order != null) {
            customerComboBox.setSelectedItem(customerService.getCustomerById(order.getCustomerId()));
        }
        formPanel.add(customerComboBox, gbc);

        // Items table
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Items:"), gbc);

        itemTableModel = new DefaultTableModel(new Object[]{"Smartphone", "Price (CHF)", "Quantity"}, 0);
        itemListTable = new JTable(itemTableModel);
        JScrollPane itemScrollPane = new JScrollPane(itemListTable);
        itemListTable.getTableHeader().setResizingAllowed(false);
        itemListTable.getTableHeader().setReorderingAllowed(false);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        formPanel.add(itemScrollPane, gbc);

        if (order != null) {
            for (OrderItem item : order.getItems()) {
                Smartphone smartphone = smartphoneService.getSmartphoneById(item.getSmartphoneId());
                itemTableModel.addRow(new Object[]{smartphone, item.getPrice(), item.getQuantity()});
            }
        }

        // Item management buttons
        JPanel itemButtonPanel = new JPanel();
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> addItem());
        itemButtonPanel.add(addItemButton);

        JButton editItemButton = new JButton("Edit Item");
        editItemButton.addActionListener(e -> editItem());
        itemButtonPanel.add(editItemButton);

        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> deleteItem());
        itemButtonPanel.add(deleteItemButton);

        gbc.gridy = 5;
        gbc.weighty = 0;
        formPanel.add(itemButtonPanel, gbc);

        // Total
        gbc.gridy = 8;
        formPanel.add(new JLabel("Total (CHF):"), gbc);
        gbc.gridy = 9;
        JTextField totalField = new JTextField();
        totalField.setEditable(false);
        if (order != null) {
            totalField.setText(String.format("%.2f", order.getTotal()));
        }
        formPanel.add(totalField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveOrder(orderNumberField, orderDateCalendar, customerComboBox, totalField));
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addItem() {
        List<Smartphone> smartphones = smartphoneService.getAllSmartphones();
        Smartphone selectedSmartphone = (Smartphone) JOptionPane.showInputDialog(
                this,
                "Select Smartphone:",
                "Add Item",
                JOptionPane.QUESTION_MESSAGE,
                null,
                smartphones.toArray(),
                null
        );

        if (selectedSmartphone != null) {
            double price = selectedSmartphone.getPrice();
            int quantity = 1;

            JTextField quantityField = new JTextField("1");
            int result = JOptionPane.showConfirmDialog(
                    this,
                    new Object[]{
                            "Quantity:", quantityField
                    },
                    "Item Details",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    itemTableModel.addRow(new Object[]{selectedSmartphone, price, quantity});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void editItem() {
        int selectedRow = itemListTable.getSelectedRow();
        if (selectedRow != -1) {
            Smartphone currentSmartphone = (Smartphone) itemTableModel.getValueAt(selectedRow, 0);
            int currentQuantity = (int) itemTableModel.getValueAt(selectedRow, 2);

            List<Smartphone> smartphones = smartphoneService.getAllSmartphones();
            Smartphone selectedSmartphone = (Smartphone) JOptionPane.showInputDialog(
                    this,
                    "Select Smartphone:",
                    "Edit Item",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    smartphones.toArray(),
                    currentSmartphone
            );

            if (selectedSmartphone != null) {
                double price = selectedSmartphone.getPrice();
                JTextField quantityField = new JTextField(String.valueOf(currentQuantity));

                int result = JOptionPane.showConfirmDialog(
                        this,
                        new Object[]{
                                "Quantity:", quantityField
                        },
                        "Item Details",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        itemTableModel.setValueAt(selectedSmartphone, selectedRow, 0);
                        itemTableModel.setValueAt(price, selectedRow, 1);
                        itemTableModel.setValueAt(quantity, selectedRow, 2);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void deleteItem() {
        int selectedRow = itemListTable.getSelectedRow();
        if (selectedRow != -1) {
            itemTableModel.removeRow(selectedRow);
        }
    }
    private void saveOrder(JTextField orderNumberField, JCalendar orderDateCalendar,
                           JComboBox<User> customerComboBox, JTextField totalField) {
        try {
            String orderNumber = orderNumberField.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = dateFormat.format(orderDateCalendar.getDate());
            User selectedCustomer = (User) customerComboBox.getSelectedItem();
            String customerId = selectedCustomer != null ? selectedCustomer.getId() : null;

            if (orderNumber == null || orderNumber.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Order number is null", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (orderService.orderExists(orderNumber)) {
                JOptionPane.showMessageDialog(this, "Order number is already in use","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            List<OrderItem> items = new ArrayList<>();
            for (int i = 0; i < itemTableModel.getRowCount(); i++) {
                Smartphone smartphone = (Smartphone) itemTableModel.getValueAt(i, 0);

                // Parse price (could be Double or String)
                Object priceObj = itemTableModel.getValueAt(i, 1);
                double price;
                if (priceObj instanceof Number) {
                    price = ((Number) priceObj).doubleValue();
                } else if (priceObj instanceof String) {
                    try {
                        price = Double.parseDouble((String) priceObj);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid price in row " + (i + 1));
                        return;
                    }
                } else {
                    throw new IllegalStateException("Unexpected value type for price");
                }

                // Parse quantity (could be Integer or String)
                Object quantityObj = itemTableModel.getValueAt(i, 2);
                int quantity;
                if (quantityObj instanceof Number) {
                    quantity = ((Number) quantityObj).intValue();
                } else if (quantityObj instanceof String) {
                    try {
                        quantity = Integer.parseInt((String) quantityObj);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid quantity in row " + (i + 1));
                        return;
                    }
                } else {
                    throw new IllegalStateException("Unexpected value type for quantity");
                }

                items.add(new OrderItem(smartphone.getId(), price, quantity));
            }

            double total = calculateTotal(items);

            if (order == null) {
                Order newOrder = new Order(null, orderNumber, orderDate, customerId, items, total);
                orderService.addOrder(newOrder);
            } else {
                order.setOrderNumber(orderNumber);
                order.setOrderDate(orderDate);
                order.setCustomerId(customerId);
                order.setItems(items);
                order.setTotal(total);
                orderService.updateOrder(order.getId(), order);
            }

            JOptionPane.showMessageDialog(this, "Order saved successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateTotal(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}