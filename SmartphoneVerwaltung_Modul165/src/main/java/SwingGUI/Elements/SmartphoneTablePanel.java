package SwingGUI.Elements;

import Models.Smartphone;
import services.SmartphoneService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel to display all smartphones
public class SmartphoneTablePanel extends JPanel {
    private final SmartphoneService smartphoneService;
    private final JTable smartphoneTable;
    private final DefaultTableModel tableModel;

    public SmartphoneTablePanel(SmartphoneService smartphoneService) {
        this.smartphoneService = smartphoneService;

        setLayout(new BorderLayout());

        // Table model with all smartphone fields
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Brand", "Model", "Price (CHF)", "RAM (GB)", "Screen Size", "Storage (GB)",
                "OS", "OS Version", "Resolution", "Cores", "Battery (mAh)", "Connectivity", "Network"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing of table cells
            }
        };

        smartphoneTable = new JTable(tableModel);

        // Disable column reordering
        smartphoneTable.getTableHeader().setReorderingAllowed(false);

        // Configure the table
        smartphoneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        smartphoneTable.setAutoCreateRowSorter(true);
        smartphoneTable.getTableHeader().setReorderingAllowed(false);
        smartphoneTable.getTableHeader().setResizingAllowed(false);

        // Scrollable panel for the table
        JScrollPane scrollPane = new JScrollPane(smartphoneTable);
        add(scrollPane, BorderLayout.CENTER);

        loadSmartphones(); // Load data into the table
    }

    // Loads all smartphones from the database into the table.
    public void loadSmartphones() {
        tableModel.setRowCount(0); // Clear existing rows
        List<Smartphone> smartphones = smartphoneService.getAllSmartphones();
        for (Smartphone smartphone : smartphones) {
            tableModel.addRow(new Object[]{
                    smartphone.getId(),
                    smartphone.getBrand(),
                    smartphone.getModel(),
                    smartphone.getPrice(),
                    smartphone.getRam(),
                    smartphone.getScreenSize(),
                    smartphone.getStorage(),
                    smartphone.getOs(),
                    smartphone.getOsVersion(),
                    smartphone.getResolution(),
                    smartphone.getCores(),
                    smartphone.getBatteryCapacity(),
                    String.join(", ", smartphone.getConnectivity()), // Join connectivity list into a string
                    smartphone.getNetworkStandard()
            });
        }
    }

    // Returns the ID of the selected smartphone.
    public String getSelectedSmartphoneId() {
        int selectedRow = smartphoneTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        return (String) smartphoneTable.getValueAt(selectedRow, 0); // First column is the ID
    }
}
