package SwingGUI.Elements;

import Models.Order;
import Models.User;
import services.UserService;
import services.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderTablePanel extends JPanel {
    private final OrderService orderService;
    private final UserService customerService;
    private final JTable orderTable;
    private final DefaultTableModel tableModel;

    public OrderTablePanel(OrderService orderService, UserService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Order Number", "Date", "Customer", "Total (CHF)"}, 0
        );

        orderTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make the ID column non-editable
            }
        };
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderTable.getTableHeader().setResizingAllowed(false);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        loadOrders();
    }

    public void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            User customer = customerService.getCustomerById(order.getCustomerId());
            String customerName = customer != null ? customer.getFirstName() : "Unknown";
            tableModel.addRow(new Object[]{
                    order.getId(),
                    order.getOrderNumber(),
                    order.getOrderDate(),
                    customerName,
                    order.getTotal()
            });
        }
    }

    public String getSelectedOrderId() {
        int selectedRow = orderTable.getSelectedRow();
        return selectedRow == -1 ? null : (String) orderTable.getValueAt(selectedRow, 0);
    }
}