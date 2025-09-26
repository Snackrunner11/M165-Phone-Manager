package Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Models.Order;
import Models.OrderItem;
import org.bson.Document;
import org.bson.types.ObjectId;
import Databases.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;

// Repository for managing orders in MongoDB.
public class OrderRepository {
    private final MongoCollection<Document> orderCollection;

    public OrderRepository() {
        MongoDatabase database = DatabaseConnector.getDatabase();
        orderCollection = database.getCollection("orders");
    }

    // Inserts a new order into the database.
    public void insertOrder(Order order) {
        Document orderDoc = new Document()
                .append("orderNumber", order.getOrderNumber())
                .append("orderDate", order.getOrderDate())
                .append("customerId", order.getCustomerId())
                .append("total", order.getTotal())
                .append("items", convertOrderItemsToDocuments(order.getItems()));

        orderCollection.insertOne(orderDoc);
    }

    // Retrieves all orders from the database.
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        for (Document doc : orderCollection.find()) {
            orders.add(convertDocumentToOrder(doc));
        }
        return orders;
    }

    // Retrieves an order by its ID.
    public Order findOrderById(String id) {
        Document doc = orderCollection.find(new Document("_id", new ObjectId(id))).first();
        return doc != null ? convertDocumentToOrder(doc) : null;
    }

    // Updates an existing order in the database.
    public void updateOrder(String id, Order updatedOrder) {
        Document updatedDoc = new Document()
                .append("orderNumber", updatedOrder.getOrderNumber())
                .append("orderDate", updatedOrder.getOrderDate())
                .append("customerId", updatedOrder.getCustomerId())
                .append("total", updatedOrder.getTotal())
                .append("items", convertOrderItemsToDocuments(updatedOrder.getItems()));

        orderCollection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set", updatedDoc)
        );
    }

    // Deletes an order by its ID.
    public void deleteOrder(String id) {
        orderCollection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    // Converts a MongoDB document to an Order object.
    private Order convertDocumentToOrder(Document doc) {
        List<Document> itemsDocs = doc.getList("items", Document.class);
        List<OrderItem> items = new ArrayList<>();
        for (Document itemDoc : itemsDocs) {
            items.add(new OrderItem(
                    itemDoc.getString("smartphoneId"),
                    itemDoc.getDouble("price"),
                    itemDoc.getInteger("quantity")
            ));
        }

        return new Order(
                doc.getObjectId("_id").toString(),
                doc.getString("orderNumber"),
                doc.getString("orderDate"),
                doc.getString("customerId"),
                items,
                doc.getDouble("total")
        );
    }

    // Converts a list of OrderItem objects to MongoDB documents.
    private List<Document> convertOrderItemsToDocuments(List<OrderItem> items) {
        List<Document> itemsDocs = new ArrayList<>();
        for (OrderItem item : items) {
            itemsDocs.add(new Document()
                    .append("smartphoneId", item.getSmartphoneId())
                    .append("price", item.getPrice())
                    .append("quantity", item.getQuantity())
            );
        }
        return itemsDocs;
    }
}
