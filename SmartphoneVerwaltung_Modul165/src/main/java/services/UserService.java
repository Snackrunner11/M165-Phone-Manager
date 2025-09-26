package services;

import Models.User;
import Repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository customerRepository;

    // Service for managing users.


    public UserService() {
        this.customerRepository = new UserRepository();
    }

    // Kunde hinzufügen
    public void addCustomer(User customer) {
        customerRepository.insertCustomer(customer);
        System.out.println("Kunde erfolgreich hinzugefügt: " + customer.getFirstName() + " " + customer.getLastName());
    }

    // Alle Kunden abrufen
    public List<User> getAllCustomers() {
        List<User> customers = customerRepository.findAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Keine Kunden in der Datenbank gefunden.");
        }
        return customers;
    }

    // Kunde anhand der ID finden
    public User getCustomerById(String id) {
        User customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            System.out.println("Kein Kunde mit der ID gefunden: " + id);
        }
        return customer;
    }

    // Kunde aktualisieren
    public void updateCustomer(String id, User customer) {
        customerRepository.updateCustomer(id,customer);
    }

    // Kunde löschen
    public void deleteCustomer(String id) {
        boolean deleted = customerRepository.deleteCustomer(id);
    }
}
