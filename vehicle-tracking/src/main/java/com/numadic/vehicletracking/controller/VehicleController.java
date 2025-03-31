package com.numadic.vehicletracking.controller;

import com.numadic.vehicletracking.model.Vehicle;
import com.numadic.vehicletracking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // Return 401 Unauthorized if no user
        }
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);  // Return 200 OK with list of vehicles
    }

    // Register a new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // Return 401 Unauthorized if no user
        }
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);  // Return 201 Created with saved vehicle
    }

    // Get a specific vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);  // Return 200 OK with the vehicle
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 Not Found if vehicle doesn't exist
        }
    }

    // Update a vehicle by ID
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleService.getVehicleById(id);
        if (existingVehicle.isPresent()) {
            vehicle.setId(id);  // Make sure to set the ID for update
            Vehicle updatedVehicle = vehicleService.saveVehicle(vehicle);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);  // Return updated vehicle with 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 Not Found if vehicle doesn't exist
        }
    }

    // Delete a vehicle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 No Content if deletion is successful
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 Not Found if vehicle doesn't exist
        }
    }
}
