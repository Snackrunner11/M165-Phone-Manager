package Models;

import java.util.List;

// Represents a smartphone.
public class Smartphone {
    private String id;
    private String brand;
    private String model;
    private double price;
    private int ram;
    private String screenSize;
    private int storage;
    private String os;
    private String osVersion;
    private String resolution;
    private int cores;
    private int batteryCapacity;
    private List<String> connectivity;
    private String networkStandard;

    public Smartphone(String id, String brand, String model, double price, int ram, String screenSize, int storage,
                      String os, String osVersion, String resolution, int cores, int batteryCapacity,
                      List<String> connectivity, String networkStandard) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.ram = ram;
        this.screenSize = screenSize;
        this.storage = storage;
        this.os = os;
        this.osVersion = osVersion;
        this.resolution = resolution;
        this.cores = cores;
        this.batteryCapacity = batteryCapacity;
        this.connectivity = connectivity;
        this.networkStandard = networkStandard;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public List<String> getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(List<String> connectivity) {
        this.connectivity = connectivity;
    }

    public String getNetworkStandard() {
        return networkStandard;
    }

    public void setNetworkStandard(String networkStandard) {
        this.networkStandard = networkStandard;
    }
}
