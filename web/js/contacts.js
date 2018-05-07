function myMap() {
    var company = {lat: 39.512729, lng: -0.424220};
    var mapProp = {
        center: company,
        zoom: 10
    };
    var map = new google.maps.Map(document.getElementById("map"), mapProp);
    var marker = new google.maps.Marker({
        position: company,
        map: map
    });
}