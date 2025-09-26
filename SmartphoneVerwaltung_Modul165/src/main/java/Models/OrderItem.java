package Models;

// Represents an item in an order.
public class OrderItem {
    private String smartphoneId; // Refers to the smartphone ID
    private double price;
    private int quantity;

    public OrderItem(String smartphoneId, double price, int quantity) {
        this.smartphoneId = smartphoneId;
        this.price = price;
        this.quantity = quantity;
    }

    //Getter and setter

    public String getSmartphoneId() {
        return smartphoneId;
    }

    public void setSmartphoneId(String smartphoneId) {
        this.smartphoneId = smartphoneId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
