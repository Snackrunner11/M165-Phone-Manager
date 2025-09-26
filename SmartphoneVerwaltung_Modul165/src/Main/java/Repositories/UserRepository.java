package Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Models.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import Databases.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final MongoCollection<Document> customerCollection;

    public UserRepository() {
        MongoDatabase database = DatabaseConnector.getDatabase();
        this.customerCollection = database.getCollection("customers");
    }

    // Kunde hinzufügen
    public void insertCustomer(User customer) {
        Document document = new Document("title", customer.getTitle())
                .append("firstName", customer.getFirstName())
                .append("lastName", customer.getLastName())
                .append("address", customer.getAddress())
                .append("postalCode", customer.getPostalCode())
                .append("city", customer.getCity())
                .append("phonePrivate", customer.getPhonePrivate())
                .append("phoneMobile", customer.getPhoneMobile())
                .append("email", customer.getEmail())
                .append("birthDate", customer.getBirthDate())
                .append("username", customer.getUsername())
                .append("password", customer.getPassword());
        customerCollection.insertOne(document);
    }

    // Alle Kunden abrufen
    public List<User> findAllCustomers() {
        List<User> customers = new ArrayList<>();
        for (Document doc : customerCollection.find()) {
            customers.add(documentToCustomer(doc));
        }
        return customers;
    }

    // Kunde anhand der ID finden
    public User findCustomerById(String id) {
        Document doc = customerCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc != null ? documentToCustomer(doc) : null;
    }

    // Kunde aktualisieren
    public void updateCustomer(String id, User customer) {
        Document updatedoc = new Document()
                .append("title", customer.getTitle())
                .append("firstName", customer.getFirstName())
                .append("lastName", customer.getLastName())
                .append("address", customer.getAddress())
                .append("postalCode", customer.getPostalCode())
                .append("city", customer.getCity())
                .append("phonePrivate", customer.getPhonePrivate())
                .append("phoneMobile", customer.getPhoneMobile())
                .append("email", customer.getEmail())
                .append("birthDate", customer.getBirthDate())
                .append("username", customer.getUsername())
                .append("password", customer.getPassword());
        customerCollection.updateOne(
        new Document("_id", new ObjectId(id)),
        new Document("$set", updatedoc)
        );
    };

    // Kunde löschen
    public boolean deleteCustomer(String id) {
        return customerCollection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    private User documentToCustomer(Document doc) {
        return new User(
                doc.getObjectId("_id").toString(),
                doc.getString("title"),
                doc.getString("firstName"),
                doc.getString("lastName"),
                doc.getString("address"),
                doc.getString("postalCode"),
                doc.getString("city"),
                doc.getString("phonePrivate"),
                doc.getString("phoneMobile"),
                doc.getString("email"),
                doc.getString("birthDate"),
                doc.getString("username"),
                doc.getString("password")
        );
    }
}
