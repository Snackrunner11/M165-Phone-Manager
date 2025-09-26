import ClassControllers.MainController;

import java.util.logging.Level;
import java.util.logging.Logger;

//Textoutput on the console
//Swing UI is in Mainframe --> execute that

public class Main {
    public static void main(String[] args) {
        // Deaktiviere MongoDB-Treiber-Logs
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING);

        // Starte das Hauptmenü
        MainController controller = new MainController();
        controller.start();
    }
}
