package ClassControllers;

import java.util.Scanner;

public class MainController {
    private final SmartphoneController smartphoneController;

    public MainController() {
        this.smartphoneController = new SmartphoneController();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        //Menüausgabe
        do {
            System.out.println("\n ____  _                        __  __                                   \n" +
                    "|  _ \\| |__   ___  _ __   ___  |  \\/  | __ _ _ __   __ _  __ _  ___ _ __ \n" +
                    "| |_) | '_ \\ / _ \\| '_ \\ / _ \\ | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\n" +
                    "|  __/| | | | (_) | | | |  __/ | |  | | (_| | | | | (_| | (_| |  __/ |   \n" +
                    "|_|   |_| |_|\\___/|_| |_|\\___| |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \n" +
                    "                                                         |___/           ");
            System.out.println("--- Admin-Tool für den Online-Shop ---");
            System.out.println("1. Smartphones verwalten");
            System.out.println("0. Beenden");
            System.out.print("Wähle eine Option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Zeilenumbruch einlesen

            switch (choice) {
                case 1 -> smartphoneController.manageSmartphones();
                case 0 -> System.out.println("Programm beendet.");
                default -> System.out.println("Ungültige Eingabe. Bitte versuche es erneut.");
            }
        } while (choice != 0);
    }
}
