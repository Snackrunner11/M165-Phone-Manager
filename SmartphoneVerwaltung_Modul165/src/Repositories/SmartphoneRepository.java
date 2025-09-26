package Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Models.Smartphone;
import org.bson.Document;
import org.bson.types.ObjectId;
import Databases.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;

//Repository class for managing smartphones in MongoDB.

public class SmartphoneRepository {
    private final MongoCollection<Document> smartphoneCollection;

    public SmartphoneRepository() {
        MongoDatabase database = DatabaseConnector.getDatabase();
        smartphoneCollection = database.getCollection("smartphones");
    }

//Adds a new smartphone to the database.

    public void add(Smartphone smartphone) {
        Document document = new Document()
                .append("brand", smartphone.getBrand())
                .append("model", smartphone.getModel())
                .append("price", smartphone.getPrice())
                .append("ram", smartphone.getRam())
                .append("screenSize", smartphone.getScreenSize())
                .append("storage", smartphone.getStorage())
                .append("os", smartphone.getOs())
                .append("osVersion", smartphone.getOsVersion())
                .append("resolution", smartphone.getResolution())
                .append("cores", smartphone.getCores())
                .append("batteryCapacity", smartphone.getBatteryCapacity())
                .append("connectivity", smartphone.getConnectivity())
                .append("networkStandard", smartphone.getNetworkStandard());

        smartphoneCollection.insertOne(document);
    }

//Retrieves all smartphones from the database.

    public List<Smartphone> findAll() {
        List<Smartphone> smartphones = new ArrayList<>();
        for (Document doc : smartphoneCollection.find()) {
            smartphones.add(convertDocumentToSmartphone(doc));
        }
        return smartphones;
    }

//Retrieves a smartphone by its ID.

    public Smartphone findById(String id) {
        Document doc = smartphoneCollection.find(new Document("_id", new ObjectId(id))).first();
        return doc != null ? convertDocumentToSmartphone(doc) : null;
    }

//Updates an existing smartphone in the database.

    public void update(String id, Smartphone smartphone) {
        Document updatedDocument = new Document()
                .append("brand", smartphone.getBrand())
                .append("model", smartphone.getModel())
                .append("price", smartphone.getPrice())
                .append("ram", smartphone.getRam())
                .append("screenSize", smartphone.getScreenSize())
                .append("storage", smartphone.getStorage())
                .append("os", smartphone.getOs())
                .append("osVersion", smartphone.getOsVersion())
                .append("resolution", smartphone.getResolution())
                .append("cores", smartphone.getCores())
                .append("batteryCapacity", smartphone.getBatteryCapacity())
                .append("connectivity", smartphone.getConnectivity())
                .append("networkStandard", smartphone.getNetworkStandard());

        smartphoneCollection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set", updatedDocument)
        );
    }

//Deletes a smartphone by its ID.

    public void deleteById(String id) {
        smartphoneCollection.deleteOne(new Document("_id", new ObjectId(id))); // Deletes the smartphone with the given ID
    }

   // Converts a MongoDB document to a Smartphone object.

    private Smartphone convertDocumentToSmartphone(Document doc) {
        return new Smartphone(
                doc.getObjectId("_id").toString(),
                doc.getString("brand"),
                doc.getString("model"),
                doc.getDouble("price"),
                doc.getInteger("ram"),
                doc.getString("screenSize"),
                doc.getInteger("storage"),
                doc.getString("os"),
                doc.getString("osVersion"),
                doc.getString("resolution"),
                doc.getInteger("cores"),
                doc.getInteger("batteryCapacity"),
                doc.getList("connectivity", String.class),
                doc.getString("networkStandard")
        );
    }
}
