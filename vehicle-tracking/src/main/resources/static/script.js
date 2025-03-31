// Initialize Leaflet Map
let map = L.map('map').setView([-26.2041, 28.0473], 6);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

let markers = {};
let bounds = L.latLngBounds();

// Handle form submission
document.getElementById('vehicleForm').addEventListener('submit', async function(event) {
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

    const response = await fetch('/api/vehicles', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ vehicleNumber, licensePlate, latitude, longitude, model, owner })
    });

    if (response.ok) {
        alert("Vehicle Registered Successfully!");
        document.getElementById('vehicleForm').reset();
        fetchVehicles();
    } else {
        alert("Failed to register vehicle.");
    }
});

// Fetch and display all vehicles
async function fetchVehicles() {
    const response = await fetch('/api/vehicles');
    const vehicles = await response.json();
    const vehicleList = document.getElementById('vehicleList');
    vehicleList.innerHTML = '';

    bounds = L.latLngBounds();

    vehicles.forEach(vehicle => {
        const row = `<tr>
                        <td>${vehicle.id}</td>
                        <td>${vehicle.latitude}</td>
                        <td>${vehicle.licensePlate}</td>
                        <td>${vehicle.longitude}</td>
                        <td>${vehicle.model}</td>
                        <td>${vehicle.owner}</td>
                        <td>${vehicle.vehicleNumber}</td>
                    </tr>`;
        vehicleList.innerHTML += row;

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

    if (vehicles.length > 0) {
        map.fitBounds(bounds, { padding: [50, 50] });
    }
}

// Fetch and display all vehicles on load
fetchVehicles();

