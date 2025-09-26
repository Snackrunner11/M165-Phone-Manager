package SwingGUI.Elements;

import Models.User;
import services.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserTablePanel extends JPanel {
    private final UserService customerService;
    private final JTable customerTable;
    private final DefaultTableModel tableModel;

    public UserTablePanel(UserService customerService) {
        this.customerService = customerService;
        setLayout(new BorderLayout());

        // Define customer table columns
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Title","First Name", "Last Name", "Email","Phone Mobile", "Phone Private", "Postal Code", "City", "Address", "Birth Date", "Username", "Password"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        customerTable = new JTable(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setAutoCreateRowSorter(true);
        customerTable.getTableHeader().setResizingAllowed(false);
        customerTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        loadCustomers(); // Populate initial data
    }

    public void loadCustomers() {
        tableModel.setRowCount(0); // Clear existing data
        List<User> customers = customerService.getAllCustomers();
        for (User customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getId(),
                    customer.getTitle(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhoneMobile(),
                    customer.getPhonePrivate(),
                    customer.getPostalCode(),
                    customer.getCity(),
                    customer.getAddress(),
                    customer.getBirthDate(),
                    customer.getUsername(),
                    customer.getPassword(),
            });
        }
    }

    public String getSelectedCustomerId() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) return null;
        return (String) customerTable.getValueAt(selectedRow, 0); // ID is first column
    }
}