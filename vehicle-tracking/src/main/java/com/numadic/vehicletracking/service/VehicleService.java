package com.numadic.vehicletracking.service;

import com.numadic.vehicletracking.model.Vehicle;
import com.numadic.vehicletracking.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
	private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Create or update a vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Get vehicle by ID
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Delete a vehicle by ID
    public void deleteVehicle(Long id) {
        // Check if the vehicle exists before deleting
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }

    // Update a vehicle by ID
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        // Check if the vehicle exists
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isPresent()) {
            Vehicle updatedVehicle = existingVehicle.get();
            updatedVehicle.setLicensePlate(vehicle.getLicensePlate());
            updatedVehicle.setVehicleNumber(vehicle.getVehicleNumber());
            updatedVehicle.setLatitude(vehicle.getLatitude());
            updatedVehicle.setLongitude(vehicle.getLongitude());
            updatedVehicle.setModel(vehicle.getModel());
            updatedVehicle.setOwner(vehicle.getOwner());
            return vehicleRepository.save(updatedVehicle);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }



	
}

