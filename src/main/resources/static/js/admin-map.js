var map,
    activeMarker;

function initMap() {
    var locationOfZlin = {lat: 49.22439, lng: 17.66485};

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14,
        center: locationOfZlin,
        draggableCursor:'crosshair'
    });

    map.addListener('click', function(event) {
        deleteMarker();
        addMarker(event.latLng);
        fillLocationToForm(event);
    });

    addMarkerFromForm();
}

function addMarker(location) {
    activeMarker = new google.maps.Marker({
        position: location,
        map: map
    });
}

function fillLocationToForm(event) {
    document.getElementById('location').value = event.latLng.lat() + ',' + event.latLng.lng();
}

function deleteMarker() {
    if (activeMarker) {
        activeMarker.setMap(null);
    }
}

function addMarkerFromForm() {
    var latlng = document.getElementById('location').value;
    if (latlng) {
        var toupleLatLng = latlng.split(",");
        var lat = parseFloat(toupleLatLng[0]);
        var lng = parseFloat(toupleLatLng[1]);
        var latLngObject = {lat: lat, lng: lng};
        addMarker(latLngObject);
    }
}