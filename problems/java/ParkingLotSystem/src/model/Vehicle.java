package model;

import enums.VehicleSize;

public abstract class Vehicle {

    private final String vehicleNumber;
    private final VehicleSize vehicleSize;

    public Vehicle(String no, VehicleSize size) {
        this.vehicleNumber = no;
        this.vehicleSize = size;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

}
