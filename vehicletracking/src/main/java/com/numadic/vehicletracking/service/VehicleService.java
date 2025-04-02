package com.numadic.vehicletracking.service;

import com.numadic.vehicletracking.model.Vehicle;
import com.numadic.vehicletracking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

	  @Autowired
	    private VehicleRepository vehicleRepository;

	    public List<Vehicle> getAllVehicles() {
	        return vehicleRepository.findAll();
	    }

	    public Optional<Vehicle> getVehicleById(Long id) {
	        return vehicleRepository.findById(id);
	    }

	    public Vehicle saveVehicle(Vehicle vehicle) {
	        return vehicleRepository.save(vehicle);
	    }

	    public boolean deleteVehicle(Long id) {
	        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
	        if (vehicle.isPresent()) {
	            vehicleRepository.delete(vehicle.get());
	            return true;
	        }
	        return false;
	    }
}