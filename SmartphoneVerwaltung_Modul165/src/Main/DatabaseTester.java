package Main;

import com.mongodb.client.MongoDatabase;
import Databases.DatabaseConnector;

public class DatabaseTester {
    public static void main(String[] args) {
        try {
            MongoDatabase database = DatabaseConnector.getDatabase();
            System.out.println("Erfolgreich mit der Datenbank verbunden: " + database.getName());
        } catch (Exception e) {
            System.err.println("Fehler bei der Verbindung: " + e.getMessage());
        }
    }
}
