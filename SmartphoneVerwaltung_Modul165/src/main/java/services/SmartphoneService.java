package services;

import Models.Smartphone;
import Repositories.SmartphoneRepository;

import java.util.List;


// Service for managing Smartphones.

public class SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneService() {
        this.smartphoneRepository = new SmartphoneRepository();
    }

    //Adds a new smartphone to the database.

    public void addSmartphone(Smartphone smartphone) {
        smartphoneRepository.add(smartphone);
    }

    //Retrieves all smartphones from the database.

    public List<Smartphone> getAllSmartphones() {
        return smartphoneRepository.findAll();
    }

    //Retrieves a smartphone by its ID.


    public Smartphone getSmartphoneById(String id) {
        return smartphoneRepository.findById(id);
    }

    //Updates an existing smartphone in the database.

    public void updateSmartphone(String id, Smartphone smartphone) {
        smartphoneRepository.update(id, smartphone);
    }

    //Deletes a smartphone by its ID.


    public void deleteSmartphoneById(String id) {
        smartphoneRepository.deleteById(id); // Calls the repository method to delete
    }
}
