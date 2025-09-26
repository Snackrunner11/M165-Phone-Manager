package SwingGUI;

import Models.User;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JCalendar;
import java.util.Date;

public class UserFormDialog extends JDialog {
    private final UserService customerService;
    private final User customer;

    public UserFormDialog(JFrame parent, UserService customerService, User customer) {
        super(parent, (customer == null ? "Add Customer" : "Update Customer"), true);
        this.customerService = customerService;
        this.customer = customer;
        initializeForm();
    }

    private void initializeForm() {
        setLayout(new BorderLayout());
        setSize(600, 700);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title dropdown
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        String[] titles = {"-", "Mr.", "Mrs.", "Dr.", "Prof."};
        JComboBox<String> titleDropdown = new JComboBox<>(titles);
        if (customer != null && customer.getTitle() != null) {
            titleDropdown.setSelectedItem(customer.getTitle());
        }
        formPanel.add(titleDropdown, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        JTextField firstNameField = new JTextField(customer != null ? customer.getFirstName() : "");
        formPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(customer != null ? customer.getLastName() : "");
        formPanel.add(lastNameField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        JTextArea addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        if (customer != null) {
            addressArea.setText(customer.getAddress());
        }
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        formPanel.add(addressScrollPane, gbc);

        // Postal Code
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Postal Code:"), gbc);
        gbc.gridx = 1;
        JTextField postalCodeField = new JTextField(customer != null ? customer.getPostalCode() : "");
        formPanel.add(postalCodeField, gbc);

        // City
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        JTextField cityField = new JTextField(customer != null ? customer.getCity() : "");
        formPanel.add(cityField, gbc);

        // Private Phone
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Private Phone:"), gbc);
        gbc.gridx = 1;
        JTextField privatePhoneField = new JTextField(customer != null ? customer.getPhonePrivate() : "");
        formPanel.add(privatePhoneField, gbc);

        // Mobile Phone
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Mobile Phone:"), gbc);
        gbc.gridx = 1;
        JTextField mobilePhoneField = new JTextField(customer != null ? customer.getPhoneMobile() : "");
        formPanel.add(mobilePhoneField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(customer != null ? customer.getEmail() : "");
        formPanel.add(emailField, gbc);

        // Birth Date calendar
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Birth Date:"), gbc);
        gbc.gridx = 1;
        JCalendar birthDateCalendar = new JCalendar();
        if (customer != null && customer.getBirthDate() != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = dateFormat.parse(customer.getBirthDate());
                birthDateCalendar.setDate(date);
            } catch (Exception e) {
                // Handle invalid date format
            }
        }
        formPanel.add(birthDateCalendar, gbc);

        // Username (optional)
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(new JLabel("Username (optional):"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(customer != null ? customer.getUsername() : "");
        formPanel.add(usernameField, gbc);

        // Password (optional)
        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(new JLabel("Password (optional):"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(customer != null ? customer.getPassword() : "");
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String title = (String) titleDropdown.getSelectedItem();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressArea.getText();
                String postalCode = postalCodeField.getText();
                String city = cityField.getText();
                String phonePrivate = privatePhoneField.getText();
                String phoneMobile = mobilePhoneField.getText();
                String email = emailField.getText();

                // Get Birthdate from calendar
                Date birthDate = birthDateCalendar.getDate();
                String formattedBirthDate = "";
                if (birthDate != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    formattedBirthDate = dateFormat.format(birthDate);
                }

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (customer == null) {
                    User newCustomer = new User(
                            null, title, firstName, lastName, address, postalCode, city,
                            phonePrivate, phoneMobile, email, formattedBirthDate, username, password
                    );
                    customerService.addCustomer(newCustomer);
                } else {
                    customer.setTitle(title);
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customer.setAddress(address);
                    customer.setPostalCode(postalCode);
                    customer.setCity(city);
                    customer.setPhonePrivate(phonePrivate);
                    customer.setPhoneMobile(phoneMobile);
                    customer.setEmail(email);
                    customer.setBirthDate(formattedBirthDate);
                    customer.setUsername(username);
                    customer.setPassword(password);
                    customerService.updateCustomer(customer.getId(), customer);
                }

                JOptionPane.showMessageDialog(this, "Customer saved successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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