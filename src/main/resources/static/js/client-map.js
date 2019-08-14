var map,
    activeMarker;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14,
        center: getFinalPlaceLocation(),
        disableDefaultUI: true,
        gestureHandling: 'none',
        zoomControl: false,
        draggable: false
    });
    addMarkerFromForm();
}

function addMarker(location) {
    activeMarker = new google.maps.Marker({
        position: location,
        map: map
    });
}

function addMarkerFromForm() {
    addMarker(getFinalPlaceLocation());
}

function getFinalPlaceLocation() {
    return {lat: lat, lng: lng};
}