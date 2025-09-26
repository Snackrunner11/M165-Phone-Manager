package services;

import Models.Order;
import Repositories.OrderRepository;

import java.util.List;

// Service for managing orders.
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
    }

    // Adds a new order.
    public void addOrder(Order order) {
        orderRepository.insertOrder(order); // Calls the repository to insert the order
    }

    // Retrieves all orders.
    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders(); // Calls the repository to fetch all orders
    }

    // Retrieves an order by its ID.
    public Order getOrderById(String id) {
        return orderRepository.findOrderById(id); // Calls the repository to fetch an order by ID
    }

    // Updates an existing order.
    public void updateOrder(String id, Order updatedOrder) {
        orderRepository.updateOrder(id, updatedOrder); // Calls the repository to update the order
    }

    // Deletes an order by its ID.
    public void deleteOrder(String id) {
        orderRepository.deleteOrder(id); // Calls the repository to delete the order
    }

    // Checks if an order with the given order number already exists.

    public boolean orderExists(String orderNumber) {
        List<Order> orders = getAllOrders();
        for (Order order : orders) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return true;
            }
        }
        return false;
    }
}