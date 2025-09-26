package ClassControllers;

import services.UserService;
import Models.User;

import java.util.List;

 //Controller for managing customer-related operations.

public class UserController {
    private final UserService customerService;

    public UserController() {
        this.customerService = new UserService();
    }

    //Manages customer operations

    public void manageCustomers() {
        // Example: Display all customers
        List<User> customers = customerService.getAllCustomers();
        for (User customer : customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " - " + customer.getEmail());
        }
    }

    //Deletes a customer by their ID.

    public void deleteCustomer(String customerId) {
        customerService.deleteCustomer(customerId);
    }

    //Adds a new customer.

    public void addCustomer(User customer) {
        customerService.addCustomer(customer);
    }

    //Updates an existing customer.

    public void updateCustomer(String customerId, User customer) {
        customerService.updateCustomer(customerId, customer);
    }
}