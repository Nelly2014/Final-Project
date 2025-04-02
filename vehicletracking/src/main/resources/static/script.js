document.addEventListener("DOMContentLoaded", function () {
    // Initialize the Leaflet map centered on Johannesburg with zoom level 6
    let map = L.map('map').setView([-26.2041, 28.0473], 6);

    // Add OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    // Object to store markers (keyed by vehicleNumber)
    let markers = {};
    let bounds = L.latLngBounds();

    // Function to fetch vehicles from the backend and update table and map
    async function fetchVehicles() {
        try {
            const response = await fetch('http://localhost:8080/api/vehicles');
            if (!response.ok) throw new Error("Failed to fetch vehicles");

            const vehicles = await response.json();
            const vehicleList = document.getElementById('vehicleList');
            vehicleList.innerHTML = ''; // Clear existing table rows

            // Reset map bounds
            bounds = L.latLngBounds();

            vehicles.forEach(vehicle => {
                // Add a row to the table for each vehicle
                const row = `
                    <tr>
                        <td>${vehicle.id}</td>
                        <td>${vehicle.latitude}</td>
                        <td>${vehicle.licensePlate}</td>
                        <td>${vehicle.longitude}</td>
                        <td>${vehicle.model}</td>
                        <td>${vehicle.owner}</td>
                        <td>${vehicle.vehicleNumber}</td>
                    </tr>`;
                vehicleList.innerHTML += row;

                // Update bounds and add marker
                const latLng = [vehicle.latitude, vehicle.longitude];
                bounds.extend(latLng);

                if (markers[vehicle.vehicleNumber]) {
                    markers[vehicle.vehicleNumber].setLatLng(latLng)
                        .bindPopup(`<b>${vehicle.vehicleNumber}</b><br>${vehicle.model}<br>${vehicle.owner}`);
                } else {
                    markers[vehicle.vehicleNumber] = L.marker(latLng)
                        .addTo(map)
                        .bindPopup(`<b>${vehicle.vehicleNumber}</b><br>${vehicle.model}<br>${vehicle.owner}`)
                        .openPopup();
                }
            });

            // Fit map to markers
            if (vehicles.length > 0) {
                map.fitBounds(bounds, { padding: [50, 50] });
            }
        } catch (error) {
            console.error('Error fetching vehicles:', error);
        }
    }

    // Handle vehicle registration form submission
    document.getElementById('vehicleForm').addEventListener('submit', async function (event) {
        event.preventDefault();

        const vehicleNumber = document.getElementById('vehicleNumber').value.trim();
        const licensePlate = document.getElementById('licensePlate').value.trim();
        const latitude = parseFloat(document.getElementById('latitude').value);
        const longitude = parseFloat(document.getElementById('longitude').value);
        const model = document.getElementById('model').value.trim();
        const owner = document.getElementById('owner').value.trim();

        if (isNaN(latitude) || isNaN(longitude)) {
            alert("Please enter valid latitude and longitude.");
            return;
        }

        const vehicleData = { vehicleNumber, licensePlate, latitude, longitude, model, owner };

        try {
            const response = await fetch('http://localhost:8080/api/vehicles', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(vehicleData)
            });

            const responseBody = await response.text(); // Read response body
            if (response.ok) {
                alert("Vehicle Registered Successfully!");
                document.getElementById('vehicleForm').reset();
                fetchVehicles(); // Refresh vehicles list and markers
            } else {
                alert(`Failed to register vehicle: ${responseBody}`);
                console.error('Error:', responseBody);
            }
        } catch (error) {
            console.error('Error registering vehicle:', error);
            alert("Error registering vehicle.");
        }
    });

    // Load vehicles when the page loads
    fetchVehicles();
});
