package ClassControllers;

import services.SmartphoneService;
import Models.Smartphone;

import java.util.List;


 //Controller for managing smartphone-related operations.

public class SmartphoneController {
    private final SmartphoneService smartphoneService;

    public SmartphoneController() {
        this.smartphoneService = new SmartphoneService();
    }

     //Manages smartphone operations (placeholder for managing all actions).

    public void manageSmartphones() {
        List<Smartphone> smartphones = smartphoneService.getAllSmartphones();
        for (Smartphone smartphone : smartphones) {
            System.out.println(smartphone.getBrand() + " " + smartphone.getModel());
        }
    }

    //Deletes a smartphone by its ID.

    public void deleteSmartphone(String smartphoneId) {
        smartphoneService.deleteSmartphoneById(smartphoneId); // Calls the service method
    }

    //Adds a new smartphone.

    public void addSmartphone(Smartphone smartphone) {
        smartphoneService.addSmartphone(smartphone);
    }

    //Updates an existing smartphone.


    public void updateSmartphone(String id, Smartphone smartphone) {
        smartphoneService.updateSmartphone(id, smartphone);
    }
}
