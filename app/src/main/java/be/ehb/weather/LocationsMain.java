package be.ehb.weather;

import androidx.lifecycle.LiveData;

import java.util.List;

import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.network.resources.LocationForecast;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;

public class LocationsMain {
    public static void main(String[] args) {
        LocationRepository locationRepository = new LocationRepository(
                "AIzaSyCzjf1DgiM9H7KeWuOwOMVTS6KRd9CJO9I"
        );

        LiveData<List<NamedLocation>> locations = locationRepository.searchLocations(
                "Antw"
        );

//        LiveData<GeocodedNamedLocation> location = locationRepository.getLocationDetail(
//                new NamedLocation(
//                        "Antwerpen, Belgium",
//                        "ChIJfYjDv472w0cRuIqogoRErz4"
//                )
//        );
    }
}
