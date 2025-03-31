package com.numadic.vehicletracking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
//@CrossOrigin(origins = "http://localhost:8080")


@SpringBootApplication(scanBasePackages = "com.numadic.vehicletracking")
@Configuration
public class VehicleTrackingApplication {
	
    void contextLoads() {
}
	public static void main(String[] args) {
		  
		    
		SpringApplication.run(VehicleTrackingApplication.class, args);
	}
	
}






