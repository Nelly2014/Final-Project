package com.numadic.vehicletracking.model;



import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, unique = true) // ✅ Explicit column mapping
    private String licensePlate;

    @Column(name = "vehicle_number", nullable = false, unique = true) // ✅ Explicit column mapping
    private String vehicleNumber;

    private Double latitude;
    private Double longitude;
    private String model;
    private String owner;

    // Constructors
    public Vehicle() {}

    public Vehicle(String licensePlate, String vehicleNumber, Double latitude, Double longitude, String model, String owner) {
        this.licensePlate = licensePlate;
        this.vehicleNumber = vehicleNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.model = model;
        this.owner = owner;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", licensePlate=" + licensePlate + ", vehicleNumber=" + vehicleNumber
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", model=" + model + ", owner=" + owner + "]";
	}

    
}
