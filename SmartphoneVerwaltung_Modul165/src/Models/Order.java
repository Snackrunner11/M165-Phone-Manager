package Models;

import java.util.List;

// Represents an order.
public class Order {
    private String id;
    private String orderNumber;
    private String orderDate;
    private String customerId; // Use customer ID instead of the full Customer object
    private List<OrderItem> items;
    private double total;

    public Order(String id, String orderNumber, String orderDate, String customerId, List<OrderItem> items, double total) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
    }

    //Getter and setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
