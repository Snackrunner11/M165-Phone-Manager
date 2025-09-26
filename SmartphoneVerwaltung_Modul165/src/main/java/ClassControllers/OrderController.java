package ClassControllers;

import Models.Order;
import Models.OrderItem;
import services.OrderService;

import java.util.List;

// Controller for managing orders.
public class OrderController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    // Creates a new order.
    public void createOrder(String orderNumber, String orderDate, String customerId, List<OrderItem> items, double total) {
        Order newOrder = new Order(null, orderNumber, orderDate, customerId, items, total);
        orderService.addOrder(newOrder);
    }

    // Adds an item to an order.
    public OrderItem createOrderItem(String smartphoneId, double price, int quantity) {
        return new OrderItem(smartphoneId, price, quantity); // Correctly matches the constructor
    }

    // Retrieves all orders.
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Updates an order.
    public void updateOrder(String id, String orderNumber, String orderDate, String customerId, List<OrderItem> items, double total) {
        Order updatedOrder = new Order(id, orderNumber, orderDate, customerId, items, total);
        orderService.updateOrder(id, updatedOrder);
    }

    // Deletes an order by its ID.
    public void deleteOrder(String id) {
        orderService.deleteOrder(id);
    }
}
