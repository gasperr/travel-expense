/**
 * Created by gasper on 12/21/14.
 */




window.onload = function(){
    var map;
    var infowindow;

    $('.check').click(function() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);

        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }

    });

    $("#radius").keypress(function() {
        console.log("asddsa");
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);

        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }

    });


    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);

    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
};



function showPosition(position) {
    var lat=position.coords.latitude;
    var long=position.coords.longitude;

    var mojaLokacija = new google.maps.LatLng(lat, long);

    map = new google.maps.Map(document.getElementById('zemljevid'), {
        center: mojaLokacija,
        zoom: 15
    });

    var request = {
        location: mojaLokacija,
        radius: 900,
        types: ['insurance_agency', 'airport', 'bus_station', 'car_rental', 'car_dealer', 'car_repair', 'parking']
    };

    request.radius = $("#radius").val();



    if($("#insurance_agency").is(':checked')){
        request.types.push("insurance_agency");
    }
    else{
       var index = request.types.indexOf("insurance_agency");
        request.types.splice(index, 1);
    }

    if($("#airport").is(':checked')){
        request.types.push("airport");
    }
    else{
        index = request.types.indexOf("airport");
        request.types.splice(index, 1);
    }

    if($("#bus_station").is(':checked')){
        request.types.push("doctor");
    }
    else{
        index = request.types.indexOf("bus_station");
        request.types.splice(index, 1);
    }

    if($("#car_dealercar_rental").is(':checked')){
        request.types.push("car_rental");
        request.types.push("car_dealer");
    }
    else{
        index = request.types.indexOf("car_rental");
        request.types.splice(index, 1);
        index = request.types.indexOf("car_dealer");
        request.types.splice(index, 1);
    }

    if($("#car_repair").is(':checked')){
        request.types.push("car_repair");
    }
    else{
        index = request.types.indexOf("car_repair");
        request.types.splice(index, 1);
    }

    if($("#parking").is(':checked')){
        request.types.push("parking");
    }
    else{
        index = request.types.indexOf("parking");
        request.types.splice(index, 1);
    }




    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, callback);

}

function callback(results, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
        }
    }
}

function createMarker(place) {
    var placeLoc = place.geometry.location;
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent(place.name);
        infowindow.open(map, this);
    });
}

//google.maps.event.addDomListener(window, 'load', showPosition);

